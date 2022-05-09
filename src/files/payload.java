package files;

public class payload {

	// class containing JSON body or payload
	// static method so no need to instantiate or create an object class
	public static String addPlace() {
		return "{\r\n" + "    \"location\": {\r\n" + "        \"lat\": -38.383494,\r\n"
				+ "        \"lng\": 33.427362\r\n" + "    },\r\n" + "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"Frontline house\",\r\n" + "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\r\n" + "    \"types\": [\r\n"
				+ "        \"shoe park\",\r\n" + "        \"shop\"\r\n" + "    ],\r\n"
				+ "    \"website\": \"http://google.com\",\r\n" + "    \"language\": \"French-IN\"\r\n" + "}";
	}

	public static String putPlace(String placeID, String newAddress) {
		return "{\r\n" + "    \"place_id\": \"" + placeID + "\",\r\n" + "    \"address\": \"" + newAddress + "\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n" + "}";
	}

	// mock response sample 1
	public static String MockCoursePrice() {
		return "{\r\n" + "  \"dashboard\": {\r\n" + "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n" + "  },\r\n" + "  \"courses\": [\r\n" + "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n" + "      \"price\": 50,\r\n" + "      \"copies\": 6\r\n"
				+ "    },\r\n" + "    {\r\n" + "      \"title\": \"Cypress\",\r\n" + "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n" + "    },\r\n" + "    {\r\n" + "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n" + "      \"copies\": 10\r\n" + "    },\r\n" + "    {\r\n"
				+ "      \"title\": \"Appium\",\r\n" + "      \"price\": 36,\r\n" + "      \"copies\": 7\r\n"
				+ "    }\r\n" + "  ]\r\n" + "}";
	}

	// mock response sample rahul
	public static String MockGetRequestResp() {
		return "{\r\n" + "  \"instructor\": \"RahulShetty\",\r\n" + "  \"url\": \"rahulshettycademy.com\",\r\n"
				+ "  \"services\": \"projectSupport\",\r\n" + "  \"expertise\": \"Automation\",\r\n"
				+ "  \"courses\": {\r\n" + "    \"webAutomation\": [\r\n" + "      {\r\n"
				+ "        \"courseTitle\": \"Selenium Webdriver Java\",\r\n" + "        \"price\": \"50\"\r\n"
				+ "      },\r\n" + "      {\r\n" + "        \"courseTitle\": \"Cypress\",\r\n"
				+ "        \"price\": \"40\"\r\n" + "      },\r\n" + "      {\r\n"
				+ "        \"courseTitle\": \"Protractor\",\r\n" + "        \"price\": \"40\"\r\n" + "      }\r\n"
				+ "    ],\r\n" + "    \"api\": [\r\n" + "      {\r\n"
				+ "        \"courseTitle\": \"Rest Assured Automation using Java\",\r\n"
				+ "        \"price\": \"50\"\r\n" + "      },\r\n" + "      {\r\n"
				+ "        \"courseTitle\": \"SoapUI Webservices testing\",\r\n" + "        \"price\": \"40\"\r\n"
				+ "      }\r\n" + "    ],\r\n" + "    \"mobile\": [\r\n" + "      {\r\n"
				+ "        \"courseTitle\": \"Appium-Mobile Automation using Java\",\r\n"
				+ "        \"price\": \"50\"\r\n" + "      }\r\n" + "    ]\r\n" + "  },\r\n"
				+ "  \"linkedIn\": \"https://www.linkedin.com/in/rahul-shetty-trainer/\"\r\n" + "}";
	}

	public static String addBook(String addIsbn, String addAisle) {
		return "{\r\n" + "    \"name\": \"Learn Appium Automation with Java\",\r\n" + "    \"isbn\": \"" + addIsbn
				+ "\",\r\n" + "    \"aisle\": \"" + addAisle + "\",\r\n" + "    \"author\": \"John foer\"\r\n" + "}";
	}

	public static String deleteBook(String delID) {
		return "{\r\n" + "    \"ID\": \"" + delID + "\"\r\n" + "}";
	}

	// JIRA add comment payload JSON body
	public static String jiraAddComment(String addComment) {
		return "{\r\n" + "    \"body\": \"" + addComment + "\",\r\n" + "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}";
	}

	// JIRA login user payload JSON body
	public static String jiraLoginUser(String user, String pass) {
		return "{\r\n" + "    \"username\": \"" + user + "\",\r\n" + "    \"password\": \"" + pass + "\"\r\n" + "}";
	}

}
