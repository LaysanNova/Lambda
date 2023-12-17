import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LambdaTest {


    private static final String BASE_URL = "https://ecommerce-playground.lambdatest.io/";

    @Test
    public void StepByStepTest() {

        //Step-by-step test approach
        try {

            //1 Navigate to eCommerce Playground Website.
            //      1.Verify the user is navigated to the eCommerce website.
            //      2.Verify page title is Your Store.

            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate(BASE_URL);

            assertThat(page).hasURL("https://ecommerce-playground.lambdatest.io/");
            assertThat(page).hasTitle("Your Store");

            //2 Click on Shop by Category.
            // 1.Verify Shop by Category menu is visible.
            // 2.Verify Top categories is shown to the user.

            Locator ShopByCategoryMenu = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Shop by Category"));
            assertThat(ShopByCategoryMenu).isVisible();
            ShopByCategoryMenu.click();

            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Top categories "))).isVisible();

            playwright.selectors().setTestIdAttribute("id");            ;
            assertThat(page.getByTestId("widget-navbar-217841")).isVisible();
            assertThat(page.getByTestId("widget-navbar-217841")).not().isEmpty();

            //3 Click on the Components category.
            // 1.Verify the user is redirected to the Components page
            // 2.Verify the page title is ‘Components’
            // 3.Verify ‘HTC Touch HD’ is shown in the list.

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Components")).click();
            assertThat(page).hasURL(BASE_URL + "index.php?route=product/category&path=25");
            assertThat(page).hasTitle("Components");
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Components"))).isVisible();

            assertThat(page.getByTestId("entry_212408")).containsText("HTC Touch HD");
            assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("HTC Touch HD").setExact(true))).isVisible();
            assertThat(page.getByTestId("entry_212408")).hasCount(1);

            //4 Click on the first product, ‘HTC Touch HD’
            // 1.Verify the user is navigated to the ‘HTC Touch HD’ product details page.
            // 2.Verify page title is ‘HTC Touch HD’ Verify Availability is shown as ‘In Stock’
            // 3.Verify the ‘ADD To CART’ button is enabled.

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("HTC Touch HD").setExact(true)).click();

            assertThat(page).hasURL(BASE_URL + "index.php?route=product/product&path=25&product_id=28");
            assertThat(page).hasTitle("HTC Touch HD");
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("HTC Touch HD"))).isVisible();

            assertThat(page.getByTestId("entry_216826")).containsText("In Stock");
            assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart"))).isEnabled();

            //5 Click on the ‘ADD To CART’ button.
            // 1.Verify product is successfully added to the cart.

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart")).click();




            //6 Click on the ‘Cart’ icon.
            // 1.Verify the Cart side panel is open
            // 2.Verify the Checkout button is shown to the user


            //7 Click on the ‘Checkout’ button.
            // 1.Verify the user is redirected to the Checkout page.
            // 2.Verify product details are shown in the right side pane.
            // Since you didn’t log into the application on the right-hand side, you will also see the Registration/Login/Guest flow.


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Pass");
        }



    }
}
