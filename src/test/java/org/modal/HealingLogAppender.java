package org.modal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HealingLogAppender extends AppenderBase<ILoggingEvent> {

    private static final ThreadLocal<String> currentOriginalLocator = new ThreadLocal<>();

    private static final ThreadLocal<String> currentTestName = new ThreadLocal<>();

    public static void setTestName(String name) {
        currentTestName.set(name);
    }

    public void setCurrentOriginalLocator(String locator) {
        currentOriginalLocator.set(locator);
    }

    public static void clearTestContext() {
        currentTestName.remove();
        currentOriginalLocator.remove();
    }
    @Override
    protected void append(ILoggingEvent event) {
        String testName = currentTestName.get() != null ? currentTestName.get() : "UnknownTest";
        String msg = event.getFormattedMessage();
        if (!event.getLoggerName().contains("healenium")) return;

        // Store the original locator when first failure occurs
        if (msg.contains("Failed to find an element using locator")) {
            Matcher m = Pattern.compile("By\\.xpath: (.+)").matcher(msg);
            if (m.find()) {
                currentOriginalLocator.set(m.group(1).trim());
            }
            return; // Don't create record yet - wait to see if healing occurs
        }

        // If healing succeeds, create success record
        if (msg.contains("Using healed locator: Scored(")) {
            Matcher m = Pattern.compile("score=([0-9.]+), value=By\\.cssSelector: (.+)\\)").matcher(msg);
            if (m.find() && currentOriginalLocator.get() != null) {
                double confidence = Double.parseDouble(m.group(1));
                String healed = m.group(2).trim();

                HealingRecord record = new HealingRecord(
                        testName,
                        currentOriginalLocator.get(),
                        healed,
                        confidence,
                        "success"
                );

                HealingCollector.getInstance().addRecord(record);
                currentOriginalLocator.remove();
            }
            return;
        }

        // If no healing occurred after failure, create failure record
        if (msg.contains("Failed to find an element using locator") &&
                !msg.contains("Using healed locator") &&
                currentOriginalLocator.get() != null) {

            HealingRecord record = new HealingRecord(
                    testName,
                    currentOriginalLocator.get(),
                    null,
                    0.0,
                    "failed"
            );

            HealingCollector.getInstance().addRecord(record);
            currentOriginalLocator.remove();
        }
    }
}


