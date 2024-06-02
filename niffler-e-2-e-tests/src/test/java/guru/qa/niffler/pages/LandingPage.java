package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LandingPage {

    private final SelenideElement header = $(".main__header");
    private final SelenideElement loginBtnSection = $(".main__links");
    private final SelenideElement loginLink = loginBtnSection.$(byText("Login"));
    private final SelenideElement registerLink = loginBtnSection.$(byText("Register"));

    @Step("Открыть страницу лендинга")
    public LandingPage open() {
        Selenide.open("/"); // Adjust the URL as needed, for example, the root URL of the site
        return this;
    }

    @Step("Проверка прогрузилась ли страница")
    public void checkIsLoaded() {
        header.shouldBe(visible);
    }

    @Step("Перейти на страницу авторизации")
    public void clickLogin() {
        loginLink.click();
    }

    @Step("Перейти на страницу регистрации")
    public void clickRegister() {
        registerLink.click();
    }
}
