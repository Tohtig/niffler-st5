package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.LandingPage;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.PeoplePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.Selector.*;

@WebTest
public class FriendStatusTest {
    private final LandingPage landingPage = new LandingPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final PeoplePage peoplePage = new PeoplePage();

    static {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void checkIfFriendIsPresent(@User(selector = WITH_FRIENDS) UserJson testUser) {
        Selenide.open("http://127.0.0.1:3000/");

        landingPage.clickLogin();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .signIn();
        mainPage.clickPeopleBtn();
        peoplePage.checkFriendShip();
    }

    @Test
    void checkFriendShipInviteIsSend(@User(selector = INVITATION_SEND) UserJson testUser) {
        Selenide.open("http://127.0.0.1:3000/");

        landingPage.clickLogin();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .signIn();
        mainPage.clickPeopleBtn();
        peoplePage.addFriendIsInviteSent("barsik");
        peoplePage.checkSendedInvitation("barsik");

    }

    @Test
    void CheckFriendshipInvitationReceived(@User(selector = INVITATION_RECEIVED) UserJson testUser) {
        Selenide.open("http://127.0.0.1:3000/");
        landingPage.clickLogin();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .signIn();
        mainPage.clickPeopleBtn();
        peoplePage.checkReceivedInvite("duck");
    }

    @Test
    void testCheckFriendsAndInvitationReceived(@User(selector = WITH_FRIENDS) UserJson testUser,
                                               @User(selector = INVITATION_SEND) UserJson testUser2) {
        Selenide.open("http://127.0.0.1:3000/");
        landingPage.clickLogin();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .signIn();
        mainPage.clickPeopleBtn();
    }
}
