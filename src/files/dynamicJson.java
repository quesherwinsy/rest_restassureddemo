package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class dynamicJson {

	// TestNG getData method for parameter
	@Test(dataProvider = "getData")
	public void addBook(String postIsbn, String postAisle) {
		// set base URI
		RestAssured.baseURI = "http://216.10.245.166";

		// POST add book API
		String addBookResp = given().header("Content-Type", "application/json")
				.body(payload.addBook(postIsbn, postAisle)).when().post("Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath jsResp = reusableMethods.rawToJson(addBookResp);
		String bookid = jsResp.getString("ID");
		System.out.println("result ID " + bookid);

		// DELETE remove book API
		String deleteBookResp = given().header("Content-Type", "application/json").body(payload.deleteBook(bookid))
				.when().delete("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response()
				.asString();

		jsResp = reusableMethods.rawToJson(deleteBookResp);
		System.out.println(jsResp.getString("msg"));

	}

	// TestNG Provides data and parameter use for test case
	@DataProvider
	public Object[][] getData() {
		// create 3 test case with 2 parameter
		/*
		 * Object[][] data = new Object[3][2]; data[0][0] = "quek"; data[0][1] = "8777";
		 * data[1][0] = "reyyy"; data[1][1] = "619"; data[2][0] = "toona"; data[2][1] =
		 * "555";
		 */
		Object[][] data = { { "quek", "8777" }, { "reyyy", "619" }, { "toona", "555" } };
		return data;
	}
}
