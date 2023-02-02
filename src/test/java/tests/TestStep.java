package tests;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestStep {

    @Step("Перейти на указанный url {url}")
    public void openMainPage(String url) {
        open(url);
    }

    @Step("В поле Search ввести критерий поиска {value}")
    public void setSearchValue(String value) {
        $(byAttribute("placeholder", "Search GitHub")).setValue(value)
                .pressEnter();
    }

    @Step("Перейти в нужный репозиторий {repository}")
    public void repositoryClick(String repository) {
        $(byAttribute("href", "/" + repository)).shouldBe(visible)
                .click();
    }

    @Step("Перейти в раздел Issues")
    public void issueTabClick() {
        $("#issues-tab").shouldBe(exist)
                .click();
    }

    @Step("Проверить, что открыта нужная страница с разделом Issues {title}")
    public void checkIssueTabTitle(String title) {
        $(".blankslate h3").shouldBe(visible)
                .shouldHave(exactText(title));
    }
}
