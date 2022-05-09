import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo2.AddPlace;
import pojo2.Location;

public class specBuilderTest {

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

		// Request spec builder reusable setting for request
		RequestSpecification ReqAddPlace = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		// Response spec builder reusable setting for response
		ResponseSpecification ResAddPlace = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		// POST add place API request with ResquestSpecBuilder
		// SERIALIZATION convert JAVA object Pojo body into JSON body
		RequestSpecification res = given().spec(ReqAddPlace).body(ap);

		// Response process POST API with ResponseSpecBuilder
		Response ResponseBody = res.when().post("/maps/api/place/add/json").then().assertThat().spec(ResAddPlace)
				.extract().response();

		String ResponseStr = ResponseBody.asString();

		// parse JSON string or file
		JsonPath jp = reusableMethods.rawToJson(ResponseStr);
		String placeID = jp.getString("place_id");
		System.out.println(placeID);

		// DESERIALIZATION is in oauth2Demo.java file
	}

}
