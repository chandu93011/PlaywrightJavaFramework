package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

public class APITest {
    private static final String BASE = "https://api.eventhub.rahulshettyacademy.com/api";
    Playwright playwright;

    @Test
    public void e2eApiTest() {
        // ---- Login ----
        HashMap<String, Object> loginPayload = new HashMap<>();
        loginPayload.put("email", "cy93011@gmail.com");
        loginPayload.put("password", "Chandan@123445566");

        playwright = Playwright.create();
        APIRequestContext apiRequest = playwright.request().newContext();

        APIResponse loginResponse = apiRequest.post(BASE + "/auth/login",
                RequestOptions.create().setData(loginPayload));
        Assert.assertTrue(loginResponse.ok(),
                "Login failed: " + loginResponse.status() + " " + loginResponse.text());

        String token = JsonPath.read(loginResponse.text(), "$.token");
        System.out.println("Login success");

        // ---- Create event ----
        // Use a future date, otherwise the API rejects it
        String futureDate = Instant.now().plus(7, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.SECONDS).toString();

        HashMap<String, Object> eventPayload = new HashMap<>();
        eventPayload.put("title", "Dussehra Pooja");
        eventPayload.put("description", "Please visit with fam");
        eventPayload.put("category", "Concert");
        eventPayload.put("venue", "Mina Market");
        eventPayload.put("city", "Bagaha-2");
        eventPayload.put("eventDate", futureDate);
        eventPayload.put("price", 1);
        eventPayload.put("totalSeats", 100);

        APIResponse eventResponse = apiRequest.post(BASE + "/events",
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(eventPayload));
        // Message now shows WHY it failed
        Assert.assertTrue(eventResponse.ok(),
                "Create event failed: " + eventResponse.status() + " " + eventResponse.text());
        System.out.println(eventResponse.text());

        int eventId = JsonPath.read(eventResponse.text(), "$.data.id");
        System.out.println("Event id is " + eventId);

        // ---- Get events ----
        APIResponse retrieveEvents = apiRequest.get(BASE + "/events",
                RequestOptions.create()
                        .setQueryParam("page", "1")
                        .setQueryParam("limit", "12")
                        .setHeader("Authorization", "Bearer " + token));
        Assert.assertTrue(retrieveEvents.ok(), "Event retrieval API should succeed");

        List<Integer> allEventIds = JsonPath.read(retrieveEvents.text(), "$.data[*].id");
        Assert.assertTrue(allEventIds.contains(eventId), "Created event should appear in event list");

        // ---- Delete event ----
        APIResponse eventDeleted = apiRequest.delete(BASE + "/events/" + eventId,
                RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        Assert.assertTrue(eventDeleted.ok(),
                "Delete failed: " + eventDeleted.status() + " " + eventDeleted.text());

        // ---- Verify deleted ----
        APIResponse deleteEventCheck = apiRequest.get(BASE + "/events",
                RequestOptions.create()
                        .setQueryParam("page", "1")
                        .setQueryParam("limit", "12")
                        .setHeader("Authorization", "Bearer " + token));
        Assert.assertEquals(deleteEventCheck.status(), 200);

        List<Integer> idsAfterDelete = JsonPath.read(deleteEventCheck.text(), "$.data[*].id");
        Assert.assertFalse(idsAfterDelete.contains(eventId),
                "Deleted event should not be in the event list");
        System.out.println("Deletion verified, event no longer in the list");
    }

    @AfterClass
    public void tearDown() {
        if (playwright != null) {
            playwright.close();
        }
    }
}