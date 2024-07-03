package guru.qa.niffler.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement signInButton = $(".form__submit");

    public void checkIsError() {
        $(".form__error").shouldBe(Condition.visible);
    }

    // Open the login page
    public LoginPage open() {
        Selenide.open("/login"); // Adjust the URL if needed
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Ввести пароль: {password}")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку SignIn")
    public void signIn() {
        signInButton.click();
    }

    @Step("Клик по переключателю видимости пароля")
    public LoginPage togglePasswordVisibility() {
        $(".form__password-button").click();
        return this;
    }

    @Step("Перейти на страницу регистрации")
    public void goToRegistration() {
        $("a[href='/register']").click();
    }
}
