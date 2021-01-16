package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.Thread.sleep;

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
}
