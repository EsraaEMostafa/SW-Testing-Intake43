import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.Requests.Requests;
import org.example.ResponseModels.ListUserResponse.ListUsersResponse;
import org.example.ResponseModels.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class ListUserTests {
    static String UserName="eve.holt@reqres.in";
    static String Password="cityslicka";
    static String BaseLine="https://reqres.in/api";
    static String SingleUserEndPoint="/users";
    static String loginEndPoint="/login";
    static String userEndPoint="/users";

    String token="";
    @BeforeClass
    public void loginSuccess(){
        Response login = Requests.loginRequest2(UserName,Password);
        //token = login.jsonPath().getString("token");
        LoginResponse loginResponse = login.as(LoginResponse.class);
        token = loginResponse.token;
    }
    @Test
    public void ListUsersSuccess()
    {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization",token);
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("page","2");
        //queryParams.put("page","1");
        Response listUsersResponse= Requests.ListUsers(headers ,queryParams ) ;
        listUsersResponse.prettyPrint();
        JsonPath listUsersPath= listUsersResponse.jsonPath();

        //check data list
        //List<Map<String,String>> userlist =listUsersPath.getList("data");
        for (int i =0 ;i<listUsersPath.getList("data").size();i++)
        {
            Assert.assertNotNull(listUsersPath.getString("data["+i+"].first_name"));
        }

        Assert.assertEquals(listUsersResponse.statusCode(),200,"Fail");
        Assert.assertEquals( listUsersPath.getString("per_page"),"6");

        Assert.assertEquals(listUsersPath.getList("data").size(),6);

        int page =listUsersPath.getInt("page");
        int per_page =listUsersPath.getInt("per_page");
        int total = page * per_page ;
        Assert.assertEquals(listUsersPath.getInt("total"),total);
    }
    @Test
    public void ListUsersSuccess2()
    {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization",token);
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("page","2");
        Response listUsersResponse= Requests.ListUsers(headers ,queryParams ) ;
        listUsersResponse.prettyPrint();
        // convert JSON response to java class
        ListUsersResponse listUsers= listUsersResponse.as(ListUsersResponse.class);

        for (int i =0 ;i<listUsers.data.size();i++)
        {
            Assert.assertNotNull(listUsers.data.get(i).firstName);
        }

        Assert.assertEquals(listUsersResponse.statusCode(),200,"Fail");
        Assert.assertEquals( listUsers.perPage,6);

        Assert.assertEquals(listUsers.data.size(),6);

        int page = listUsers.page;
        int per_page =listUsers.perPage;
        int total = page * per_page ;
        Assert.assertEquals(listUsers.total,total);
    }
}
