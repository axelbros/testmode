package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        val notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] [type='text']").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] [type='password']").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        val blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] [type='text']").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] [type='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }
    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        val registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        val wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id=login] [type='text']").setValue(wrongLogin);
        $("[data-test-id='password'] [type='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        val registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        val wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id=login] [type='text']").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] [type='password']").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}