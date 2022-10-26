package com.employeeTest;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.POJO.ResPojo;
import com.POJO.ResponsePOJO;
import com.POJO.CreatePOJO;
import com.POJO.DataPOJO;
import com.POJO.DeleteResponsePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.*;


public class EmployeeTest {
		
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static String value;
	
	@BeforeTest
	public static void setup() {
		
		RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";
					
	}
	
	@Test
	public static void getEmployees() {
				
		ResponsePOJO respojo=RestAssured
				.given()
					.contentType(ContentType.JSON)
				.when()
					.get("/employees")
					.as(ResponsePOJO.class);

		
	System.out.println(respojo.toString());
	System.out.println(respojo.getStatus());
	System.out.println("list" +respojo.getData());

	
	List<DataPOJO> list =respojo.getData();
		for(int i=0;i<list.size();i++) {
			System.out.println("this is my first elemnet"+ list.get(i));
			}
			
	}
	
	@Test
	public static void createEmployee() throws JsonProcessingException {
		
		CreatePOJO pojo= new CreatePOJO("test","123","23");
		
		String json = MAPPER.writeValueAsString(pojo);
		
		
		ResPojo response =RestAssured
			.given()
			    .contentType(ContentType.JSON)
				.log().all(true)
				.body(json)
			.when()
				.post("/create")
				.as(ResPojo.class);
		
		String jsonString = response.toString();
		System.out.println(jsonString);
		System.out.println(response.getStatus());
		 
	    String Age=response.getData().getAge();
	    String Salary=response.getData().getSalary();
	    String Name =response.getData().getName();
	    int id= response.getData().getId();
	    
	     value = String.valueOf(id);
	    System.out.println(value);
		
		Assert.assertEquals(jsonString.contains("message=Successfully! Record has been added."),true);
		Assert.assertEquals(Salary, "123");
		Assert.assertEquals(Age, "23");
		Assert.assertEquals(Name, "test");
				
			}
	
	
	@Test(dependsOnMethods = {"createEmployee"})
	
		public static void deleteEmployee () {
		
		DeleteResponsePojo delete_res =RestAssured
				.given()
					.log()
					.all()
					.contentType(ContentType.JSON)
					.pathParam("id", value)
				.when()
					.delete("/delete/{id}")
					.as(DeleteResponsePojo.class);
		
		System.out.println(delete_res.getData());
		String status = delete_res.getStatus();
		String deleted_id= delete_res.getData();
		String message =delete_res.getMessage();
		
		
		Assert.assertEquals(status, "success");
		Assert.assertEquals(message, "Successfully! Record has been deleted");
		Assert.assertEquals(deleted_id, value);
			
			
	}
	
	@Test
	public static void deleteWithZERO() {
		
		Response deletedResponse =RestAssured
			.given()
			.log()
			.all()
			.contentType(ContentType.JSON)
			.pathParam("id", 0)
			.when()
			.delete("/delete/{id}")
			.then()
		    .statusCode(400)
		    .log().body()
		    .extract().response();
	
	String jsonString = deletedResponse.asString();
	System.out.println(jsonString);
	
	String status =deletedResponse.getBody().jsonPath().get("status");
	String error =deletedResponse.getBody().jsonPath().get("data");
	String message =deletedResponse.getBody().jsonPath().get("message");
	System.out.println(message);
	Assert.assertEquals(status, "error");
	}
	@Test
	public static void fetchUser() {
		
		Response getUser=RestAssured
		.given()
		.contentType(ContentType.JSON)
		.log().all()
		.pathParam("id", 2)
		.when()
		.get("/employee/{id}")
		.then()
		.log().body()
		.statusCode(200)
		
		.extract().response();
		
		String getResponse =getUser.asString();
		System.out.println(getResponse);
		
		
		String employeeName = getUser.getBody().jsonPath().get("data.employee_name");
		int employeeSal=getUser.getBody().jsonPath().get("data.employee_salary");
		String eSal =String.valueOf(employeeSal);
		int employeeAge =getUser.getBody().jsonPath().get("data.employee_age");
		String eAge =String.valueOf(employeeAge);
		
		
		Assert.assertEquals(employeeName, "Garrett Winters");
		Assert.assertEquals(eSal, "170750");
		Assert.assertEquals(eAge, "63");
		
	
		
		
	}
			
		
	}
	
	
