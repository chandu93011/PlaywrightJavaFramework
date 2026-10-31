package pages;

import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MerchantLoginPage {

    Page page;
    String merchanturl;

   private static final String email="cy93011@gmail.com";
   private static final String password="Chandan@123445566";

    public MerchantLoginPage(Page page, String merchanturl) {
        this.page=page;
        this.merchanturl=merchanturl;
    }

    @Test
    public void loginToMecrhantDashboard(){
        page.navigate(merchanturl);
        System.out.println(page.title());
        assertThat(page).hasTitle("PB Pay");


     //  signIn(email,password);
    }

//    private void signIn(String email, String password) {
//        page.getByPlaceholder("you@email.com").fill(email);
//        page.getByLabel("Password").fill(password);
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
//    }





}
