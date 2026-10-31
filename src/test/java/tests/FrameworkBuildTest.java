package tests;

import Validation.Learning.DashboardPage;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class FrameworkBuildTest extends TestBase{

//Invoke browser ->Invoke tab/page->type url
    //HeadMode- Headless


    @Test(groups = {"framework"},description = "Create Event -Book that event and verify if its booked")
    public void DemoTest()
    {
       String eventTitle = "Playwright Framework Test";

        LoginPage loginPage = new LoginPage(page,baseurl);
        DashboardPage dashboardPage=loginPage.loginToApplication();
        dashboardPage.waitForPageToLoad();
        AdminEventsPage adminEventsPage = new AdminEventsPage(page);
        adminEventsPage.goTo();
        //Step 1 - Create Event from Admin pageeventTitle
        adminEventsPage.createEvent(
                eventTitle,
                "Playwright test event",
                "Test City",
                "Test Venue",
                "2027-12-31T10:00",
                "100",
                "50"
        );

        //Step 2 - Find newly created event in the events page
        EventsPage eventsPage = new EventsPage(page);
        eventsPage.goTo();
        Locator targetCard = eventsPage.findEventCard(eventTitle);
        int seatsNumBeforeBooking = eventsPage.getSeatsCount(targetCard);
        BookingFormPage bookingFormPage = eventsPage.proceedToBookingEvent(targetCard);
        bookingFormPage.fillAndConfirm("Test Student",
                "test.student@example.com",
                "9876543210");






















































        //locators
    }


    @AfterMethod
    public void tearDown()
    {


    }





}
