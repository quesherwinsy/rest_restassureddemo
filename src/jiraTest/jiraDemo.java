package jiraTest;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class jiraDemo {

	public static void main(String[] args) {
		// set base URI JIRA
		RestAssured.baseURI = "http://localhost:8080";

		// initialize session filter for login session IDs
		SessionFilter sf = new SessionFilter();

		// POST login API JIRA with session filter & relax HTTPS
		String user = "quesherwinsy";
		String pass = "mingguai92";
		String loginUserResp = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body(payload.jiraLoginUser(user, pass)).filter(sf).when().post("/rest/auth/1/session").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath jsResp = reusableMethods.rawToJson(loginUserResp);
		String sName = jsResp.getString("session.name");
		String sValue = jsResp.getString("session.value");
		System.out.println("result login " + sName + " " + sValue);

		// POST add comment API JIRA to existing issue
		String addComment = "hello add new comment";
		String addCommentResp = given().pathParam("issueID", "10100").header("Content-Type", "application/json")
				.body(payload.jiraAddComment(addComment)).filter(sf).when().post("/rest/api/2/issue/{issueID}/comment")
				.then().assertThat().statusCode(201).extract().response().asString();

		jsResp = reusableMethods.rawToJson(addCommentResp);
		String commentID = jsResp.getString("id");
		System.out.println("comment JIRA issue ID " + commentID);

		// POST add attachment API JIRA to existing issue
		// curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check"
		// JIRA username:password, method POST, header key:value
		// -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		// use of multipart method of RestAssureds to locate file
		given().header("X-Atlassian-Token", "no-check").filter(sf).pathParam("issueID", "10100")
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("jiraAttach.txt")).when()
				.post("/rest/api/2/issue/{issueID}/attachments").then().log().all().assertThat().statusCode(200);

		// GET issue API JIRA with Path & Query parameter
		String getIssueResp = given().filter(sf).pathParam("issueID", "10100").queryParam("fields", "comment").when()
				.get("/rest/api/2/issue/{issueID}").then().assertThat().statusCode(200).extract().response().asString();

		// Get issue ID then traverse, if exist, check comment body if same
		JsonPath jpIssue = new JsonPath(getIssueResp);
		int commenCount = jpIssue.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commenCount; i++) {
			String commentIdIssue = jpIssue.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIdIssue == commentID) {
				String actualComment = jpIssue.get("fields.comment.comments[" + i + "].id.body");
				System.out.print("Extracted comment " + actualComment);
				Assert.assertEquals(actualComment, addComment);
			}
		}
	}

}
