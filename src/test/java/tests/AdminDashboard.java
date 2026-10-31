package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.AdminLoginPage;

public class AdminDashboard extends TestBase {


    @Test()
    public void DemoTest() {
        AdminLoginPage adminLoginPage=new AdminLoginPage(page,adminurl);
        adminLoginPage.loginToAdminDashboard();

    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}