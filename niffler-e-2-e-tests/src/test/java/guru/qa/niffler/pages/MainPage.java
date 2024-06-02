package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement peoplePageBtn = $("[data-tooltip-id='people']");
    private final SelenideElement friendsPageBtn = $("[data-tooltip-id='friends']");
    private final ElementsCollection spendingRows = $(".spendings-table tbody").$$("tr");
    private final SelenideElement deleteSpendingBtn = $(".spendings__bulk-actions button");

    @Step("Выбрать расход по описанию:{description}")
    public MainPage chooseSpendingByDescription(String description) {
        SelenideElement spendingRow = spendingRows.find(text(description));
        spendingRow.$$("td").first().scrollTo().click();
        return this;
    }

    @Step("Удалить расход")
    public MainPage deleteSpending() {
        deleteSpendingBtn.click();
        return this;
    }

    @Step("Проверка количества расходов: {expectedSize}")
    public void checkCountOfSpendings(int expectedSize) {
        spendingRows.shouldHave(size(expectedSize));
    }


    @Step("Открыть страницу Friends")
    public void clickFriendsBtn() {
        friendsPageBtn.click();
    }

    @Step("Открыть страницу People")
    public void clickPeopleBtn() {
        peoplePageBtn.click();
    }

}
