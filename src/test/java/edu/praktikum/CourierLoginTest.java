package edu.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static edu.praktikum.UtilsForDataPrepare.stringRandomGenerate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CourierLoginTest {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    private static Courier courier = new Courier.Builder()
            .login(stringRandomGenerate(new Random().nextInt(254) + 1))
            .password(stringRandomGenerate(new Random().nextInt(254) + 1))
            .firstName(stringRandomGenerate(new Random().nextInt(254) + 1))
            .build();
    private CourierAfterLogin courierAfterLogin;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        //чтобы проверять логин курьера нужно чтобы он был успешно создан и мог залогиниться
        courierAfterLogin = checksCorrectCreateAndLoginCourier(courier).as(CourierAfterLogin.class);
    }

    public static Stream<Arguments> getCourierDataForLoginWithoutRequiredField() {
        return Stream.of(
                arguments(new CourierForLogin.Builder()
                        .password(courier.getPassword())
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("getCourierDataForLoginWithoutRequiredField")
    @Description("Checking the impossibility of the courier's login without specifying the required field")
    public void loginCourierWithoutRequiredFieldNotPossible(CourierForLogin courier) {
        //пытаемся залогинить курьера без указания обязательного поля
        Response response = CourierClient.login(courier);
        //проверяем статус код ответа
        Assertions.assertEquals(400, response.statusCode(), "Получаемый статус код при логине курьера не соответствует ожидаемому");
        //проверяем тело ответа
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));

        //установлено, что если при попытке логина не указать логин, то сообщение об ошибке не выдается, срабатывает time out
        //если не указать пароль поведение системы соответствует ожидаемому
    }

    @Step("Checking the possibility of creating a new courier and his login in the system")
    public static Response checksCorrectCreateAndLoginCourier(Courier courier) {
        //выполняем запрос на создание курьера
        Response response = CourierClient.create(courier);
        //проверяем статус код ответа
        Assertions.assertEquals(201, response.statusCode(), "Получаемый статус код при создании курьера не соответствует ожидаемому");
        //проверяем тело ответа
        response.then().assertThat().body("ok", equalTo(true));
        //чтобы убедиться, что курьер действительно создался попытаемся залогинить его
        response = CourierClient.login(new CourierForLogin(courier.getLogin(), courier.getPassword()));
        //проверяем статус код, полученный при логине
        Assertions.assertEquals(200, response.statusCode(), "Получаемый статус код при логине нового курьера не соответствует ожидаемому");
        //проверяем тело ответа
        response.then().assertThat().body("id", notNullValue());
        //возвращаем тело ответа после логина, чтобы можно было в последствии удалить созданного курьера
        return response;
    }

    @AfterEach
    public void tearDown() {
        CourierClient.delete(courierAfterLogin);
    }
}
