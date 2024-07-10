package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.LandingPage;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@WebTest
public class SpendingTest {

    static {
        Configuration.browserSize = "1920x1080";
    }

    private final LandingPage landingPage = new LandingPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    @Disabled
    @Test
    void anotherTest() {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").should(visible);
    }

    @GenerateCategory(category = "Отпуск",
            username = "dima")
    @GenerateSpend(
            username = "dima",
            description = "QA.GURU Advanced 5",
            amount = 85000.00,
            currency = CurrencyValues.RUB,
            category = "Отпуск"
    )
    @Test
    void spendingShouldBeDeletedAfterTableActionUsePages(SpendJson spendJson) {
        Selenide.open("http://127.0.0.1:3000/");
        landingPage.clickLogin();
        loginPage.setUsername("dima")
            .setPassword("12345")
            .signIn();

//        SelenideElement rowWithSpending = mainPage.findSpendingRowByDescription(spendJson.description());
        mainPage.chooseSpendingByDescription(spendJson.description())
                .deleteSpending()
                .checkCountOfSpendings(0);
    }
}
