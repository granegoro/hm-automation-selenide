import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardDeliveryServiceTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String generateDateInPAst(int days) {
        return LocalDate.now().minusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    String clearAll = Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.DELETE;

    @Test
    public void shouldPassWithValidData() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жора Жорович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("button .spin").shouldBe(visible);
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldPassWithDashInName() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жо-ра Жо-рович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("button .spin").shouldBe(visible);
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldShowCorrectDateInPopup() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жо-ра Жо-рович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("button .spin").shouldBe(visible);
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(visible);

    }

    @Test
    public void shouldNotPassWithInvalidCity() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("абвгд");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жора Жорович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldNotPassWithBlankCity() {
        String date = generateDate(3);

        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жора Жорович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldNotPassWithInvalidDate() {
        String date = generateDateInPAst(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Мистер");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldNotPassWithCurrentDate() {
        String date = generateDate(0);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Мистер");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldNotPassWithBlankDate() {
        String date = generateDate(0);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=name] input").setValue("Мистер");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));

    }

    @Test
    public void shouldNotPassWithInvalidName() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Lars");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldNotPassWithBlankName() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldNotPassWithInvalidPhone() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Мистер");
        $("[data-test-id=phone] input").setValue("999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldNotPassWithBlankPhone() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Мистер");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldNotPassWithoutAgreement() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(clearAll);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Жора Жорович");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }


}
