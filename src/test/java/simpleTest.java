import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;


public class simpleTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.instantwebtools.net/v1/passenger";
    }

    @Test
    void simpleGetTest(){
        given().
                when().
                get().
                then().log().all().
                statusCode(200);
    }

    @Test
    void simplePostTest() {
        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
        System.out.println(passengerData);
        given().body(passengerData)
                .when().
                post()
                .then().log().all()
                .assertThat().statusCode(201)
                .body("_id", notNullValue());
    }

    @Test
    void simplePutTest() {
        String passengerData = "{" +
                "\"name\": \"Stan Lee\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(passengerData)
                    .when()
                    .put("/passenger/:id")
                    .then()
                    .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void simpleDeleteTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/passenger/:id")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

}
