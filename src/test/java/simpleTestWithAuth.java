import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;

public class simpleTestWithAuth {

    @Test
    public void getDataWithAuth(){
        String URI = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";
        RequestSpecification request  = RestAssured.given();
        String payload = "{\n" +
                "    \"username\": \"api-user2@iwt.net\",\n" +
                "    \"password\": \"b3z0nV0cLO\"\n" +
                "}";

        request.header("Content-type", "application/json");
        Response responseToGenerateToken = request.body(payload).post(URI);

        responseToGenerateToken.prettyPrint();
        String jsonString = responseToGenerateToken.getBody().asString();
        String token = JsonPath.from(jsonString).get("access_token");

        request.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json");

        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";
        System.out.println(passengerData);
        Response postPassenger = (Response) request.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
                .then().log().all()
                .assertThat().statusCode(201)
                .body("_id", notNullValue());
        System.out.println(postPassenger);

    }
}
