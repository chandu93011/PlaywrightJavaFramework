package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class MoreUIValidationTest {
    Playwright playwright;
    Browser browser;
    Page page;
    //Page pageB;
    BrowserContext context;


    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        playwright=Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

        //chrome browser -- incognito, browser
        context=browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page=context.newPage();
//        BrowserContext contextB=browser.newContext();
//        pageB=contextB.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @Test
    public void childWindowHandling(){
        Locator blinkingTexts=page.locator(".blinkingText");
        Page childPage=context.waitForPage(()->blinkingTexts.first().click());   //Lamda expression
        childPage.waitForLoadState();
        String childText=childPage.locator(".red").textContent();

        //Please email us at mentor@rahulshettyacademy.com with below template to receive response
        String emailId = childText.trim().split(" at ")[1].trim().split(" ")[0];
        System.out.println(emailId);
        page.getByLabel("Username:").fill(emailId);
        page.getByLabel("Password:").fill("Test@1234");

    }

    @Test(groups = "smoke")
    public void UIControls(){
        page.getByLabel("Username:").fill("rahulshettyacademy");
        page.getByLabel("Password:").fill("Learning@830$3mK2");
        Locator usedRdbtn = page.getByRole(AriaRole.RADIO,new Page.GetByRoleOptions().setName("Admin"));
        usedRdbtn.click();
        Assert.assertTrue(usedRdbtn.isChecked()); // for boolean Assert.assertTrue
        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");
        Locator box=page.getByRole(AriaRole.CHECKBOX,new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));
        box.check();
        page.waitForTimeout(3000);
       page.getByText("Sign In").click();
       page.waitForTimeout(3000);


    }
    @AfterMethod
    public void tearDown() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
