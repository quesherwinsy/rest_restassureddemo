package oauth2;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oauth2Demo {

	public static void main(String[] args) throws InterruptedException {
		// set base URI JIRA
		// RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Additional security layer OAUTH 2.0 authorization code
		// Selenium GetAuthorization Code - 2020 not allowed by google
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\home\\Desktop\\seleni\\chromedriver_win32\\chromedriver.exe");
		 * WebDriver driver = new ChromeDriver();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		 * driver.manage().window().maximize(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); Thread.sleep(2000);
		 * driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys(
		 * "quesherwinsy@gmail.com");
		 * driver.findElement(By.xpath("//span[normalize-space()='Susunod']")).click();
		 * Thread.sleep(2000);
		 * driver.findElement(By.xpath("//input[@name='password']")).sendKeys(
		 * "Qwerty@3DS");
		 * driver.findElement(By.xpath("//span[normalize-space()='Susunod']")).click();
		 * String urlResult = driver.getCurrentUrl();
		 */

		// JAVA parse URL to get code value
		String urlResult = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWj47AOoKERb7wIl53n6U0JwycMrgwRu5w0sLlH41aWvIDwEkLPHJ1bS0X7a7PKsIw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String[] arrOfStr = urlResult.split("code=", 2);
		String[] arrCode = arrOfStr[1].split("&scope=", 2);
		String authCode = arrCode[0];

		// POST GetAccessToken OAUTH 2 authorization code
		String postGetCodeResp = given().relaxedHTTPSValidation().urlEncodingEnabled(false)
				.queryParams("code", authCode)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when()
				.post("https://www.googleapis.com/oauth2/v4/token").then().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath jpGetCode = new JsonPath(postGetCodeResp);
		String accessToken = jpGetCode.getString("access_token");

		// GET request OAUTH 2 authorization, convert Json response as pojo class
		// DESERIALIZATION convert JSON response into JAVA object
		GetCourse getRequestResp = given().relaxedHTTPSValidation().queryParam("access_token", accessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php").then().log()
				.all().extract().response().as(GetCourse.class);

		System.out.println(getRequestResp.getLinkedIn());
		System.out.println(getRequestResp.getInstructor());
		System.out.println(getRequestResp.getCourses().getApi().get(1).getCourseTitle());

		// Traverse list of course API list then print price value
		List<Api> apiCourses = getRequestResp.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}

		// array initialization for expected result
		String[] arrayCourse = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		// convert array into list
		List<String> expectedCourse = Arrays.asList(arrayCourse);
		// array list initialization for actual result
		ArrayList<String> actualCourse = new ArrayList<String>();

		// get all course title in web automation
		List<WebAutomation> webAutoCourses = getRequestResp.getCourses().getWebAutomation();
		for (int i = 0; i < webAutoCourses.size(); i++) {
			actualCourse.add(webAutoCourses.get(i).getCourseTitle());
		}

		// compare expected and actual course title with TestNG
		Assert.assertTrue(actualCourse.equals(expectedCourse));
	}

}
