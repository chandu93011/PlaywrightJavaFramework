package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UiValidationsContinuedTest {
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();  //  browser.newContext();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/#top");
    }
    @Test(groups = "smoke")
    public void popupValidations(){
        assertThat(page.getByPlaceholder("Hide/Show Example")).isVisible();
        page.locator("#hide-textbox").click();
        assertThat(page.getByPlaceholder("Hide/Show Example")).isHidden();
        page.onDialog(dialog -> dialog.accept());
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Alert")).click();

        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Mouse Hover")).hover();
       // page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Top")).hover();

       FrameLocator framesPage= page.frameLocator("#courses-iframe");
        framesPage.getByRole(AriaRole.LINK,new FrameLocator.GetByRoleOptions().setName("Learning Paths")).click();
        String text=framesPage.locator(".inner-box h1").textContent();
        System.out.println(text);

    }

    @Test
    public void screenShotTest(){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagescreenshot.png")).setFullPage(true));
    }
    @AfterMethod
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
