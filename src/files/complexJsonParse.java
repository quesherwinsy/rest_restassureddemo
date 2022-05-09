package files;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	@Test
	public void JsonPerse() {
		// parse mock json response
		JsonPath jpMock = new JsonPath(payload.MockCoursePrice());

		// 1. Print No of courses returned by API
		System.out.println(jpMock.getInt("courses.size()"));

		// 2. Print Purchase Amount
		System.out.println(jpMock.getInt("dashboard.purchaseAmount"));

		// 3. Print Title of the first course
		System.out.println(jpMock.getString("courses[0].title"));

		// 4. Print All course titles and their respective Prices
		for (int i = 0; i < jpMock.getInt("courses.size()"); i++) {
			System.out.print(jpMock.getString("courses[" + i + "].title") + " ");
			System.out.println(jpMock.getInt("courses[" + i + "].price"));
		}

		// 5. Print no of copies sold by RPA Course
		String findCourse = "RPA";
		for (int i = 0; i < jpMock.getInt("courses.size()"); i++) {
			if (jpMock.getString("courses[" + i + "].title").equals(findCourse)) {
				System.out.println("RPA copies " + jpMock.getInt("courses[" + i + "].copies"));
				break;
			}
		}

		// 6. Verify if Sum of all Course prices matches with Purchase Amount
		int actualAmount = 0;
		for (int i = 0; i < jpMock.getInt("courses.size()"); i++) {
			actualAmount += (jpMock.getInt("courses[" + i + "].price") * jpMock.getInt("courses[" + i + "].copies"));
		}
		Assert.assertEquals(actualAmount, jpMock.getInt("dashboard.purchaseAmount"));
	}
}
