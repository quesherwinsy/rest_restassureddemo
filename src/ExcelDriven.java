import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.dataDriven;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.testng.Assert;
import files.payload;
import files.reusableMethods;

public class ExcelDriven {

	public static void main(String[] args) throws IOException {
		// valid POST add place API if working
		// give - all input details.
		// when - submit the api
		// then - validate the response

		// instantiate object (dataDriven.java) to read excel file
		dataDriven d = new dataDriven();
		ArrayList<String> datas = d.getData("RestAddBook", "RestAssured");

		// set base URI
		RestAssured.baseURI = "http://216.10.245.166";

		// RestAssured HashMap to create JSON body
		// https://github.com/rest-assured/rest-assured/wiki/Usage#create-json-from-a-hashmap
		HashMap<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("name", datas.get(1));
		jsonAsMap.put("isbn", datas.get(2));
		jsonAsMap.put("aisle", datas.get(3));
		jsonAsMap.put("author", datas.get(4));

		// POST add book API
		// Read external json file in body() to byte, then convert to string
		String getResponseBody = given().header("Content-Type", "application/json").body(jsonAsMap).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		// parse JSON string or file
		JsonPath jp = reusableMethods.rawToJson(getResponseBody);
		String bookID = jp.getString("ID");
		System.out.println("book ID: " + bookID);
/*
		String newAddress = "70 winter walk, USA";

		// PUT update place API with new address
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.putPlace(bookID, newAddress)).when().put("/maps/api/place/update/json").then()
				.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

		// GET retrieve place API then validate new address
		// no need to send header() because no body() being sent
		String getPlaceBody = given().queryParam("key", "qaclick123").queryParam("place_id", bookID).when()
				.get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		// parse JSON string or file
		JsonPath jpGet = reusableMethods.rawToJson(getPlaceBody);
		String getAddress = jpGet.getString("address");
		Assert.assertEquals(getAddress, newAddress);
*/
	}

}
