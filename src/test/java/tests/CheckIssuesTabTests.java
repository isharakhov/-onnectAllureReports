package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Owner("isharakhov")
@Epic("Issue tab in repository")
public class CheckIssuesTabTests {

    private final static String BASE_URL = "https://github.com/",
            SEARCH_VALUE = "isharakhov/",
            IN_ISSUE_TITLE = "Welcome to issues!",
            REPOSITORY_NAME = "isharakhov/SearchOnGithub";
    private static final SelenideElement SEARCH_FIELD =
            $(byAttribute("placeholder", "Search GitHub")),
            REPOSITORY_TITLE = $(byAttribute("href", "/" + REPOSITORY_NAME)),
            ISSUE_TAB = $("#issues-tab"),
            NO_ISSUES_TITLE = $(".blankslate h3");

    @BeforeEach
    public void setUp() {
        Configuration.browserSize = "1280x800";
    }

    @Test
    @Feature("Issues")
    @DisplayName("Проверка таба Issue в репозитории, используя чистый Selenide")
    public void pureSelenideRepositoryIssueTest() {
        open(BASE_URL);
        SEARCH_FIELD.setValue(SEARCH_VALUE)
                .pressEnter();
        REPOSITORY_TITLE.shouldBe(visible).click();
        ISSUE_TAB.shouldBe(exist)
                .click();
        NO_ISSUES_TITLE.shouldBe(visible)
                .shouldHave(exactText(IN_ISSUE_TITLE));
    }

    @Test
    @Feature("Issues")
    @DisplayName("Проверка таба Issue в репозитории, используя шаги с аннотацией @Step")
    public void annotatedStepsRepositoryIssueTest() {
        TestStep step = new TestStep();
        step.openMainPage(BASE_URL);
        step.setSearchValue(SEARCH_VALUE);
        step.repositoryClick(REPOSITORY_NAME);
        step.issueTabClick();
        step.checkIssueTabTitle(IN_ISSUE_TITLE);
    }

    @Test
    @Feature("Issues")
    @DisplayName("Проверка таба Issue в репозитории, используя лямбда шаги через step")
    public void lambdaRepositoryIssueTest() {

        step("Открыть главную страницу GitHub " + BASE_URL, () -> open(BASE_URL));

        step("В поле Search GitHub ввести критерий поиска" + SEARCH_VALUE, () -> {
            SEARCH_FIELD.setValue(SEARCH_VALUE).
                    pressEnter();
        });

        step("Перейти в нужный репозиторий " + REPOSITORY_NAME, () -> {
            REPOSITORY_TITLE.shouldBe(visible)
                    .click();
        });
        step("Перейти в раздел Issues", () -> {
            ISSUE_TAB.shouldBe(exist)
                    .click();
        });
        step("Проверить, что открыта нужная страница с разделом Issues " + IN_ISSUE_TITLE, () -> {
            NO_ISSUES_TITLE.shouldBe(visible)
                    .shouldHave(exactText(IN_ISSUE_TITLE));
        });
    }
}
