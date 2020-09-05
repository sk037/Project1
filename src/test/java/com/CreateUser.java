package com;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.XLUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.io.IOException;

public class CreateUser {
	//static String payload;
	
	@BeforeClass
	public void setup() {
		RestAssured.useRelaxedHTTPSValidation();
	}
	@Test(dataProvider="getData")
	void Test01(String UN,String userFullName,String userEmailId,String isManager,String managerEmailId,
			String teamId,String createUserName,String roleId,String updateUserName)
	{
		RestAssured.baseURI="https://pegasus-qa.aka.amazon.com";
		given().
		       log().all().
		       header("Content-Type","application/json").
		       body(AddPayload.setAttribute(UN, userFullName, userEmailId, false, managerEmailId, teamId, createUserName, roleId, updateUserName)).
		when().
		       log().all().
		       post("/maintenance/addUsers").
		then().
		       log().all().
		       assertThat().statusCode(200).and().
		       contentType(ContentType.TEXT);
	
	}
			
	@DataProvider
	public Object [][] getData() throws IOException
	{
//		Object data[][]= {{"kumarivk","veena","kumarivk@amazon.com","false","stiku@amazon.com","2","stiku","2","stiku"},{"bhissi","Abhishek","bhissi@amazon.com","false","stiku@amazon.com","2","stiku","2","stiku"}};
//		return data;
		String xlFilePath = null;
		try {
			xlFilePath = System.getProperty("user.dir") + "\\AddUserList.xlsx";
			//xlFilePath = "/Desktop/Excel/AddUserList.xlsx";
		}catch(Exception e) { 
			   System.out.println("File is not present");  
			  } 
        
        int rowCount = XLUtility.getRowCount(xlFilePath, "Sheet2");
        System.out.println(rowCount);
        int columnCount = XLUtility.getCellCount(xlFilePath, "Sheet2", 1);
        System.out.println(columnCount);

        String data[][] = new String [rowCount][columnCount];
        for (int currentRow = 1; currentRow <= rowCount; currentRow++){
            for(int currentColumn = 0; currentColumn < columnCount; currentColumn++){
            	data[currentRow-1][currentColumn] = XLUtility.getCellData(xlFilePath, "Sheet2", currentRow, currentColumn);
            }
        }
        return data;
    }
	
}
		
class AddPayload
{
	public static String setAttribute(String data1,String data2,String data3,boolean data4,String data5,
			String data6,String data7,String data8,String data9 )
	{
		String payload = "[\r\n" + 
				"	{\r\n" + 
				"		\"userName\":\""+data1+"\",\r\n" + 
				"		\"userFullName\":\""+data2+"\",\r\n" + 
				"		\"userEmailId\":\""+data3+"\",\r\n" + 
				"		\"isManager\":\""+data4+"\",\r\n" + 
				"		\"managerEmailId\":\""+data5+"\",\r\n" + 
				"		\"teamId\":\""+data6+"\",\r\n" + 
				"		\"createUserName\":\""+data7+"\",\r\n" + 
				"		\"roleId\":\""+data8+"\",\r\n" + 
				"		\"updateUserName\":\""+data9+"\"\r\n" + 
				"	}\r\n" + 
				"]";
	return payload;
	 
}
	
}



