package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ApplicationFormTest {
    @BeforeEach
    void openPageInBrowser() {
        open("http://localhost:9999");
    }

    @Nested
    public class SuccessfulSendingWithAllFieldsForFilling {
        @Test
        void shouldCheckSendingOfFormTest() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='name'] [type='text']").setValue("Александр Салтыков-Щедрин");
            form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
            form.$("[data-test-id='agreement']").click();
            form.$(".button").click();
            $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена!"));
        }
    }

    @Nested
    public class SendingOfEmptyForm {
        @Test
        void shouldSendEmptyForm() {
            SelenideElement form = $(".form");
            form.$(".button").click();
            $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }
    }

    @Nested
    public class FormWithOneEmptyField {
        @Test
        void shouldCheckOfSendingFormWithoutPhoneTest() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='name'] [type='text']").setValue("Александр Свифт");
            form.$("[data-test-id='agreement']").click();
            form.$(".button").click();
            $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldCheckOfSendingFormWithoutNameTest() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
            form.$("[data-test-id='agreement']").click();
            form.$(".button").click();
            $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldCheckOfSendingFormWithoutCheckboxTest() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='name'] [type='text']").setValue("Александр Свифт");
            form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
            form.$(".button").click();
            $(".checkbox__text").getCssValue("Color").contentEquals("rgb(255, 92, 92)");
        }
    }

    @Nested
    public class SendingOfFormWithInvalidValues {
        @Test
        void shouldCheckOfSendingFormWithInvalidName() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='name'] [type='text']").setValue("Ivan Petrov");
            form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
            form.$("[data-test-id='agreement']").click();
            form.$(".button").click();
            $(".input_invalid .input__sub")
            .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

        }

        @Test
        void shouldCheckOfSendingFormWithInvalidPhone() {
            SelenideElement form = $(".form");
            form.$("[data-test-id='name'] [type='text']").setValue("Юрий Светлов");
            form.$("[data-test-id='phone'] [type='tel']").setValue("825123695");
            form.$("[data-test-id='agreement']").click();
            form.$(".button").click();
            $("[data-test-id='phone'] .input__sub")
            .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }
    }
}
