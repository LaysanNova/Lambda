import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeAll
    protected static void launchBrowser() {
        playwright = Playwright.create();
        browser = getBrowser("chromium", false);
    }

    @AfterAll
    protected static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    protected void createContextAndPage() {
        this.context = browser.newContext();
        this.page = context.newPage();
    }

    @AfterEach
    protected void closeContext() {
        context.close();
    }

    protected static Browser getBrowser(String browser, boolean headless) {
        switch (browser) {
            case "chromium" -> {
                return getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            }
            case "webkit" -> {
                return getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            }
            case "firefox" -> {
                return getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            }
            default -> {
                return null;
            }
        }
    }

    protected Page getPage() {
        return page;
    }

    protected static Playwright getPlaywright() {
        return playwright;
    }
}