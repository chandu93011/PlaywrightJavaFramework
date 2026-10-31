package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasicsTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();  //  browser.newContext();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
    }

    @Test(description = "create Event - Book that event and verify if its booked")
    public void DemoTest() {
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");

        page.getByLabel("Email").fill("cy93011@gmail.com");
        page.getByLabel("Password").fill("Chandan@123445566");
        //button
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

        //Step 1 Create new event

        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.getByLabel("Title").fill("Chandan QA Summit");

        //Dropdown
        page.getByLabel("Category").selectOption("Sports");
        page.getByLabel("City").fill("Bangalore");
        page.getByLabel("Venue").fill("Semra,Bagaha-2");

        //fill the date inspect and see the date format fill it as value that's all
        page.getByLabel("Event Date & Time").fill("2028-10-15T02:28");
        page.getByLabel("Price ($)").fill("10.10");
        page.getByLabel("Total Seats").fill("100");

        //Button
        //page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+ Add Event")).click();
        page.locator("#add-event-btn").click();

        page.waitForTimeout(3000);
        assertThat(page.getByText("Event Created!")).isVisible();

        page.locator("#nav-events").click();
        Locator eventCards = page.locator("#event-card");
        eventCards.first().waitFor();                                  // wait until cards are rendered
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText("Chandan QA Summit"));
        assertThat(targetCard.first()).isVisible();
        //book tickets
        String seatsText=targetCard.getByText("seats").innerText();
        int BeforeSeats = Integer.parseInt(seatsText.split( " ")[0]);

        targetCard.locator("#book-now-btn").click();
        assertThat(page.getByText("Book Tickets")).isVisible();
        page.getByText("+").click();
        page.getByLabel("Full Name").fill("Chandan");
        page.getByPlaceholder("you@email").fill("cy93011@gmail.com");
        page.getByLabel("Phone Number").fill("8765432130");
        //page.getByText("Confirm Booking").click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        assertThat(page.getByText("Booking Confirmed!")).isVisible();
        String bookingRef=page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("View My Bookings")).click();
        assertThat(page.getByText(bookingRef)).isVisible();

        //seat count reduction check
        page.locator("#nav-events").click();
        page.waitForTimeout(2000);
        Locator eventCardsAfterBookings=page.getByTestId("event-card");
       Locator targetCardAfterBooking= eventCardsAfterBookings.filter(new Locator.FilterOptions().setHasText("Chandan QA Summit"));
       String seatsTextAfterBookings=targetCardAfterBooking.getByText("seats").innerText();

        //compare the seats afterBooking <Before booking
        int AfterSeats = Integer.parseInt(seatsTextAfterBookings.split(" ")[0]);
        System.out.println("Before seats "+BeforeSeats);
        System.out.println("after seats "+AfterSeats);
    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}