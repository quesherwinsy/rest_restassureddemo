package files;

import io.restassured.path.json.JsonPath;

public class reusableMethods {

	// convert string to json format
	public static JsonPath rawToJson(String response) {
		JsonPath jsn = new JsonPath(response);
		return jsn;
	}
}
