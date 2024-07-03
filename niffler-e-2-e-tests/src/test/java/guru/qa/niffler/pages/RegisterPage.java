package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    // Selenide Elements
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement signUpButton = $(".form__submit");

    @Step("Открыть страницу регистрации")
    public RegisterPage open() {
        Selenide.open("/register"); // Adjust the URL if needed
        return this;
    }

    @Step("Задать имя пользователя: {username}")
    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Задать пароль пользователя: {password}")
    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Повторить пароль пользователя: {password}")
    public RegisterPage setSubmitPassword(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку SignUp")
    public void register() {
        signUpButton.click();
    }

    @Step("Перейти на страницу авторизации")
    public void goToLogin() {
        $("a[href='http://127.0.0.1:3000/redirect']").click();
    }
}
