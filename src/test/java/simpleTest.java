import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;

public class simpleTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.instantwebtools.net/v1/passenger";
    }

    @Test
    void simpleGetTest(){
        String passengerData = "{" +
                "\"name\": \"Eric Cartman\", " +
                "\"trips\": 6, " +
                "\"airline\": 1" +
                "}";
        System.out.println(passengerData);

        RequestSpecification request  = RestAssured.given();
        request.header("Content-type", "application/json");
        Response response =  request.body(passengerData)
                .post("https://api.instantwebtools.net/v1/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

        JsonPath jsonPathEval = response.jsonPath();
        String id = jsonPathEval.get("_id");

        RequestSpecification request1  = RestAssured.given();
        request1.header("Content-type", "application/json");
        request1.get("/"+ id)
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    void simplePostTest() {
        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
        System.out.println(passengerData);

        RequestSpecification request  = RestAssured.given();
        request.header("Content-type", "application/json");
       Response response =  request.body(passengerData)
                .post("https://api.instantwebtools.net/v1/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

       JsonPath jsonPathEval = response.jsonPath();
       System.out.printf(jsonPathEval.get("_id"));
    }

    @Test
    void simplePutTest() {
        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
        System.out.println(passengerData);

        RequestSpecification request  = RestAssured.given();
        request.header("Content-type", "application/json");
        Response response =  request.body(passengerData)
                .post("https://api.instantwebtools.net/v1/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

        JsonPath jsonPathEval = response.jsonPath();
        String id = jsonPathEval.get("_id");

        String passengerDataNew = "{" +
                "\"name\": \"Stan Lee\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
        RequestSpecification request1  = RestAssured.given();
        request1.header("Content-type", "application/json");
        request1.body(passengerDataNew)
                    .put("/"+ id)
                    .then().log().all()
                    .assertThat().statusCode(200);
    }

    @Test
    void simpleDeleteTest(){
        String passengerData = "{" +
                "\"name\": \"Johnny Bravo\", " +
                "\"trips\": 10, " +
                "\"airline\": 1" +
                "}";
        System.out.println(passengerData);

        RequestSpecification request  = RestAssured.given();
        request.header("Content-type", "application/json");
        Response response =  request.body(passengerData)
                .post("https://api.instantwebtools.net/v1/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

        JsonPath jsonPathEval = response.jsonPath();
        String id = jsonPathEval.get("_id");

        RequestSpecification request1  = RestAssured.given();
        request1.header("Content-type", "application/json")
                .when()
                .delete("/"+ id)
                .then().log().all()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }
}
