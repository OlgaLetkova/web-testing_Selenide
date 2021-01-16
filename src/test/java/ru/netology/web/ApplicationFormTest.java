package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ApplicationFormTest {
    @Test
    void shouldCheckSendingOfFormTest(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] [type='text']").setValue("Александр Салтыков-Щедрин");
        form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.exactText("Ваша заявка успешно отправлена!"));
    }

    @Test
    void shouldCheckValidationTest(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] [type='text']").setValue("Ivan Petrov");
        form.$("[data-test-id='phone'] [type='tel']").setValue("+79513265800");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldCheckOfSendingFormWithoutPhoneTest(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] [type='text']").setValue("Александр Свифт");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
