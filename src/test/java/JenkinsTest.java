
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class JenkinsTest extends BaseTest {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final String JOB_NAME = "Test";


    private void signInJenkins() {
        getPage().navigate(BASE_URL);
        getPage().getByLabel("Username").fill("admin");
        getPage().getByLabel("Password").fill("admin");
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
    }
    private void createJob(String jobName) {
        getPage().getByText("New Item").click();
        getPage().getByLabel("Enter an item name").fill(jobName);
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Freestyle project")).click();
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dashboard")).click();
    }

    @Test
    public void testJenkins() {

        signInJenkins();
        createJob(JOB_NAME);

        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(JOB_NAME).setExact(true)).click();

        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Configure")).click();
        getPage().locator("[tooltip = 'Help for feature: Discard old builds']").click();

        System.out.println(getPage().locator(".help-area > .help > div").allTextContents());

        assertThat(getPage().locator(".help-area > .help > div")).containsText("This determines when");
    }

    @Test()
    public void testDelete() {

        signInJenkins();
        createJob(JOB_NAME);

        getPage().onDialog(dialog -> {
            System.out.println("+++++++++++++++++++++++");
            dialog.accept();
            System.out.println("*************");
        });

        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dashboard")).click();
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(JOB_NAME).setExact(true)).waitFor();
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(JOB_NAME).setExact(true)).click();
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete Project").setExact(true)).click();
    }

    @Test
    public void testDeleteDropDown() {

        signInJenkins();
        createJob("Job1");
        createJob("Job2");
        createJob("Job3");

        getPage().onDialog(Dialog::accept);
        List<Locator> jobs = getPage().locator(".jenkins-table__link").all();

        for (int job = 0; job < jobs.size(); job++) {
            jobs.get(0).waitFor();
            jobs.get(0).click();
            getPage().getByText("Delete Project").click();
        }
    }
}
