package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {
    private final SelenideElement friendTable = $(".abstract-table tbody");
    private final ElementsCollection friendRows = friendTable.$$("tr");
    private final String submitBtn = ".button-icon_type_submit";

    public FriendsPage checkFriendRequestBtn(String name) {
        friendRows.find(text(name)).$(submitBtn).shouldHave(visible);
        return this;
    }
    @Step("Проверка наличия имени: {user} и статуса: {status} в таблице Friends")
    public FriendsPage checkNameAndStatusByFriendsTable(String name, String status) {
        friendRows.find(text(name)).shouldHave(text(status));
        return this;
    }
}