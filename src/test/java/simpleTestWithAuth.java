import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.example.dto.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

        PassengerResponse response = request.body(passengerData)
                .post("https://api.instantwebtools.net/v2/passenger")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(PassengerResponse.class);


        assertThat(response.getId()).isNotEmpty();
        assertThat(response.getName()).isEqualTo("Stan Smith");
        assertThat(response.getTrips()).isEqualTo(56);
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
       PassengerResponseMessage responseDelete =  requestToDelete.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .delete("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(PassengerResponseMessage.class);

        assertThat(responseDelete.getMessage()).contains("Passenger data deleted successfully.");
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
        PassengerResponseMessage message = requestToDelete.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .body(passengerDataNew)
                .put("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(PassengerResponseMessage.class);

        assertThat(message.getMessage()).isEqualTo("Passenger data put successfully completed.");
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
        PassengerResponse getResponse = request1
                .header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .get("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(PassengerResponse.class);

        assertThat(getResponse.getId()).isEqualTo(id);
        assertThat(getResponse.getName()).isEqualTo("Stan Smith");
        assertThat(getResponse.getTrips()).isEqualTo(56);
        assertThat(getResponse.getAirline().get(0).getName()).isEqualTo("Air India");
    }

    @Test
    public void putWithWrongId() {
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
        PassengerResponseMessage message = requestToDelete.header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .body(passengerDataNew)
                .put("https://api.instantwebtools.net/v2/passenger/"+ id+213)
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().response().as(PassengerResponseMessage.class);

        assertThat(message.getMessage()).isEqualTo("valid passenger id must submit.");
    }

    @Test
    public void getDeletedPassenger() {
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
                .delete("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        RequestSpecification request1  = RestAssured.given();
        request1
                .header("Authorization", "Bearer "+token)
                .header("Content-type", "application/json")
                .get("https://api.instantwebtools.net/v2/passenger/"+ id)
                .then().log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().response();
    }
}
