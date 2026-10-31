package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MockWebTest extends TestBase{



//    Playwright playwright;
//    Browser browser;
//    Page page;
//
//    @BeforeMethod
//    public void setUp() {
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        page = browser.newPage();  //  browser.newContext();
//        page.navigate("https://eventhub.rahulshettyacademy.com/login");
//    }

    @Test
    public void DemoTest() {

        LoginPage loginPage=new LoginPage(page,baseurl);
        loginPage.loginToApplication();

//        page.getByLabel("Email").fill("cy93011@gmail.com");
//        page.getByLabel("Password").fill("Chandan@123445566");
//        //button
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

        //fake the real events number and the show the mocked event which you have passed as a JSON
        page.route("**/api/events**", route -> route.fulfill(
                new Route.FulfillOptions().setPath(Paths.get("src/main/resources/events_6.json"))
        ));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");


        Locator eventCards = page.getByTestId("event-card");
        assertThat(eventCards.first()).isVisible();
        Assert.assertEquals(eventCards.count(), 6);
        Assert.assertTrue(page.locator(".mx-1").first().isVisible()); //text is visible when event is more or equal to


        //text is hidden when the value is 4
        page.route("**/api/events**", route -> route.fulfill(
                new Route.FulfillOptions().setPath(Paths.get("src/main/resources/events_4.json"))
        ));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");


        Locator eventCards1 = page.getByTestId("event-card");
        assertThat(eventCards1.first()).isVisible();
        Assert.assertEquals(eventCards1.count(), 4);
        Assert.assertTrue(page.locator(".mx-1").first().isHidden());
    }
        @Test
        public void routeResumeTest(){  //resume method

        LoginPage loginPage=new LoginPage(page,baseurl);
        loginPage.loginToApplication();

//        page.getByLabel("Email").fill("cy93011@gmail.com");
//        page.getByLabel("Password").fill("Chandan@123445566");

        //button
//            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
            assertThat(page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

            //route.resume
        page.getByTestId("nav-bookings").click();
        page.route("**/api/bookings**",route -> route.resume(
                new Route.ResumeOptions().setUrl("https://api.eventhub.rahulshettyacademy.com/api/bookings/35207")
        ));
      //  page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("View Details")).first().click();

        //assertThat(page.getByText("Access Denied")).isVisible();

    }
}
