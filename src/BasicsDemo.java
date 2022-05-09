import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
import files.reusableMethods;

public class BasicsDemo {

	public static void main(String[] args) throws IOException {
		// valid POST add place API if working
		// give - all input details.
		// when - submit the api
		// then - validate the response

		// set base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		/*
		 * String getResponse = given().log().all().queryParam("key", "qaclick123")
		 * .header("Content-Type", "application/json").body(payload.addPlace()).when()
		 * .post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(
		 * 200) .body("scope", equalTo("APP")).header("Server",
		 * equalTo("Apache/2.4.18 (Ubuntu)")).extract().response() .asString();
		 */

		// POST add place API
		// Read external json file in body() to byte, then convert to string
		String getResponseBody = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\home\\Desktop\\restAPI\\PostAddPlace.json"))))
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response()
				.asString();

		// parse JSON string or file
		JsonPath jp = reusableMethods.rawToJson(getResponseBody);
		String placeID = jp.getString("place_id");
		System.out.println(placeID);
		String newAddress = "70 winter walk, USA";

		// PUT update place API with new address
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.putPlace(placeID, newAddress)).when().put("/maps/api/place/update/json").then()
				.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

		// GET retrieve place API then validate new address
		// no need to send header() because no body() being sent
		String getPlaceBody = given().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		// parse JSON string or file
		JsonPath jpGet = reusableMethods.rawToJson(getPlaceBody);
		String getAddress = jpGet.getString("address");
		Assert.assertEquals(getAddress, newAddress);
	}

}
