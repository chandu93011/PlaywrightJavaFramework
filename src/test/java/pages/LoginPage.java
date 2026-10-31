package pages;

import Validation.Learning.DashboardPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {

    Page page;
    String baseurl;

   private static final String email="cy93011@gmail.com";
    private static final String password="Chandan@123445566";

    public LoginPage(Page page, String baseurl) {
        this.page=page;
        this.baseurl=baseurl;
    }


    public DashboardPage loginToApplication(){
        page.navigate(baseurl);
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        signIn(email,password);

        DashboardPage dashboardPage = new DashboardPage(page);
        return dashboardPage;

    }

    private void signIn(String email, String password) {
        page.getByPlaceholder("you@email.com").fill(email);
        page.getByLabel("Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
    }





}
