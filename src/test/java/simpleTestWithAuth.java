import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;

public class simpleTestWithAuth {

    @Test
    public void postDataWithAuth(){
        String URI = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";
        RequestSpecification request  = RestAssured.given();

        Response responseToGenerateToken = request
                .header("Content-type", "application/x-www-form-urlencoded")
                .queryParam("scope", "offline_access")
                .queryParam("grant_type", "password")
                .queryParam("username", "api-user4@iwt.net")
                .queryParam("password", "b3z0nV0cLO")
                .queryParam("client_id", "0oahdhjkutaGcIK2M4x6")
                .body("").post(URI);

        responseToGenerateToken.prettyPrint();
        String jsonString = responseToGenerateToken.getBody().asString();
        String token = JsonPath.from(jsonString).get("access_token");
        System.out.println("token" + token);

        request.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json");

        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";

        request.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("_id", Matchers.notNullValue());

    }

    @Test
    public void deleteTestWithAuth() {
        String URI = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";
        RequestSpecification request  = RestAssured.given();

        Response responseToGenerateToken = request
                .header("Content-type", "application/x-www-form-urlencoded")
                .queryParam("scope", "offline_access")
                .queryParam("grant_type", "password")
                .queryParam("username", "api-user4@iwt.net")
                .queryParam("password", "b3z0nV0cLO")
                .queryParam("client_id", "0oahdhjkutaGcIK2M4x6")
                .body("").post(URI);

        responseToGenerateToken.prettyPrint();
        String jsonString = responseToGenerateToken.getBody().asString();
        String token = JsonPath.from(jsonString).get("access_token");
        System.out.println("token" + token);


        RequestSpecification requestToCreate  = RestAssured.given();
        requestToCreate.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json");

        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";

        Response response = requestToCreate.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

        JsonPath jsonPathEval = response.jsonPath();
        String id = jsonPathEval.get("_id");

        RequestSpecification requestToDelete  = RestAssured.given();
        requestToDelete.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .when()
                .delete("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void putTestWithAuth() {
        String URI = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";
        RequestSpecification request  = RestAssured.given();

        Response responseToGenerateToken = request
                .header("Content-type", "application/x-www-form-urlencoded")
                .queryParam("scope", "offline_access")
                .queryParam("grant_type", "password")
                .queryParam("username", "api-user4@iwt.net")
                .queryParam("password", "b3z0nV0cLO")
                .queryParam("client_id", "0oahdhjkutaGcIK2M4x6")
                .body("").post(URI);

        responseToGenerateToken.prettyPrint();
        String jsonString = responseToGenerateToken.getBody().asString();
        String token = JsonPath.from(jsonString).get("access_token");
        System.out.println("token" + token);


        RequestSpecification requestToCreate  = RestAssured.given();
        requestToCreate.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json");

        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";

        Response response = requestToCreate.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
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

        RequestSpecification requestToDelete  = RestAssured.given();
        requestToDelete.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .body(passengerDataNew)
                .when()
                .put("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void getTestWithAuth() {
        String URI = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";
        RequestSpecification request  = RestAssured.given();

        Response responseToGenerateToken = request
                .header("Content-type", "application/x-www-form-urlencoded")
                .queryParam("scope", "offline_access")
                .queryParam("grant_type", "password")
                .queryParam("username", "api-user4@iwt.net")
                .queryParam("password", "b3z0nV0cLO")
                .queryParam("client_id", "0oahdhjkutaGcIK2M4x6")
                .body("").post(URI);

        responseToGenerateToken.prettyPrint();
        String jsonString = responseToGenerateToken.getBody().asString();
        String token = JsonPath.from(jsonString).get("access_token");
        System.out.println("token" + token);


        RequestSpecification requestToCreate  = RestAssured.given();
        requestToCreate.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json");

        String passengerData = "{" +
                "\"name\": \"Stan Smith\", " +
                "\"trips\": 56, " +
                "\"airline\": 2" +
                "}";

        Response response = requestToCreate.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("_id", notNullValue())
                .contentType(ContentType.JSON)
                .extract().response();

        JsonPath jsonPathEval = response.jsonPath();
        String id = jsonPathEval.get("_id");

        RequestSpecification request1  = RestAssured.given();
        request1
                .header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .get("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .assertThat().statusCode(200);

    }
}
