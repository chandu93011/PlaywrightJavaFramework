package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    Playwright playwright;
    Browser browser;
    Page page;
    String baseurl;
    String merchanturl;
    String adminurl;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        Properties properties=new Properties();
        FileInputStream fileInputStream=new FileInputStream("src/main/resources/config.properties");
        properties.load(fileInputStream);

        String browserName=properties.getProperty("browser");

        playwright = Playwright.create();
        if("firefox".equals(browserName)){
            browser=playwright.firefox().launch();
        } else if ("safari".equals(browserName)) {
            browser=playwright.webkit().launch();
        }else {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }

        page = browser.newPage();
        baseurl=properties.getProperty("qa.base_url");
        merchanturl=properties.getProperty("merchant.base_url");
        adminurl=properties.getProperty("admin.base_url");
    }

}
