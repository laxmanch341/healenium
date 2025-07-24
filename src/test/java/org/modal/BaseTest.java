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
            test.warning(MarkupHelper.createLabel("HEALED ELEMENTS", ExtentColor.ORANGE));

            String[][] table = new String[ healed.size() + 1 ][4];   // +1 for header row
            table[0] = new String[]{ "Original Locator", "Healed Locator", "Status" , "Confidence Score"};

            for (int i = 0; i < healed.size(); i++) {
                HealingRecord r = healed.get(i);
                table[i + 1] = new String[]{ r.original, r.healed, r.status, String.valueOf(r.confidence)};
            }

            test.info(MarkupHelper.createTable(table));
        }
        int testStatus = result.getStatus();

        if(testStatus == ITestResult.SUCCESS) {
            test.pass(MarkupHelper.createLabel("Test Passed after Healing", ExtentColor.GREEN));
        }

        else if (testStatus == ITestResult.FAILURE) {
            test.fail(MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
        }

        // Clear records for the current test
        healingCollector.clearRecordsForTest(result.getName());
    }

    @AfterClass(alwaysRun = true)
    public static void tearDown() {
        healingCollector.saveToJson("healing.json");
        int healed = HealingCollector.getInstance().getHealedCount();
        extent.setSystemInfo("Total Healed Locators", String.valueOf(healed));
        extent.setSystemInfo("Total Healed Tests", "1");
        HealingLogAppender.clearTestContext();
        extent.flush();
    }
}