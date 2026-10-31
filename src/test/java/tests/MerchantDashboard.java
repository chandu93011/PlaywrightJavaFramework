package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.MerchantLoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MerchantDashboard extends TestBase {



    public void DemoTest() {
        MerchantLoginPage merchantLoginPage=new MerchantLoginPage(page,merchanturl);
        merchantLoginPage.loginToMecrhantDashboard();
    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}