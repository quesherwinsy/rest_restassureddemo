import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo2.AddPlace;
import pojo2.Location;

public class serializeTest {

	public static void main(String[] args) {
		// set base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Initialize and set JAVA object for Serialization
		AddPlace ap = new AddPlace();
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		ap.setLocation(loc);
		ap.setAccuracy(50);
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setAddress("29, side layout, cohen 09");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		ap.setTypes(myList);
		ap.setWebsite("http://google.com");
		ap.setLanguage("French-IN");

		// POST add place API
		// SERIALIZATION convert JAVA object Pojo into JSON body
		Response res = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(ap)
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract().response();
		String getResponseBody = res.asString();

		// parse JSON string or file
		JsonPath jp = reusableMethods.rawToJson(getResponseBody);
		String placeID = jp.getString("place_id");
		System.out.println(placeID);

		// DESERIALIZATION is in oauth2Demo.java file
	}

}
