package org.modal;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;

    protected static final HealingCollector healingCollector = HealingCollector.getInstance();

    @BeforeClass(alwaysRun = true)
    public static void initExtent() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");

        // JavaScript to update the dashboard with the number of healed tests
        String js =
                "document.addEventListener('DOMContentLoaded', function() {" +
                        "  const healedTests = document.querySelectorAll('.node > .test-info > .healed').length;" +
                        "  const dashboard = document.querySelector('.dashboard');" +
                        "  if (dashboard) {" +
                        "    const healedDiv = document.createElement('div');" +
                        "    healedDiv.innerHTML = '<p><span class=\"badge healed\">' + healedTests + '</span>Healed tests</p>';" +
                        "    dashboard.insertBefore(healedDiv, dashboard.firstChild);" +
                        "  }" +
                        "});";

        spark.config().setJs(js);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setTimelineEnabled(false);

    }

    @BeforeMethod(alwaysRun = true)
    public void before(Method m) {
        test = extent.createTest(m.getName());
        HealingLogAppender.setTestName(m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void after(ITestResult result) {
        List<HealingRecord> healed = healingCollector.getRecords()
                .stream()
                .filter(r -> r.testName.equals(result.getName()))
                .collect(Collectors.toList());

        if (!healed.isEmpty()) {
            // Mark the test as healed
            test.pass(MarkupHelper.createLabel("<span class='healed'>HEALED</span>", ExtentColor.GREEN));
            test.warning("This test has been healed");

            // Add healing details to the test
            String[][] data = new String[healed.size()][5];
            for (int i = 0; i < healed.size(); i++) {
                HealingRecord r = healed.get(i);
                data[i] = new String[]{ r.original, r.healed,
                        String.valueOf(r.confidence), r.status, r.timestamp };
            }
            test.info(MarkupHelper.createTable(data));
        }

        // Clear records for the current test
        healingCollector.clearRecordsForTest(result.getName());
    }

    @AfterClass(alwaysRun = true)
    public static void tearDown() {
        // 2. Dump full JSON and clear ThreadLocals once per class
        healingCollector.saveToJson("healing.json");
        int healed = HealingCollector.getInstance().getHealedCount();
        extent.setSystemInfo("Healed Tests", String.valueOf(healed));
        HealingLogAppender.clearTestContext();
        extent.flush();
    }
}