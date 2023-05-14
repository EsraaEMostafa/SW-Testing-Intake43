import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.Requests.Requests;
import org.example.ResponseModels.ErrorLogin;
import org.example.ResponseModels.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.example.Requests.Requests.loginRequest2;

public class LoginTest {
    static String UserName="eve.holt@reqres.in";
    static String Password="cityslicka";


    @Test //Hard
  public void loginSuccess()
   {
       Response login = loginRequest2(UserName,Password);
       LoginResponse loginPath= login.as(LoginResponse.class);
       Assert.assertEquals(login.statusCode(),200,"Fail");
       Assert.assertNotNull( loginPath.token,"Token is Null");
   }
   @Test
   public void SoftLoginSuccess()
   {
       Response login = loginRequest2(UserName,Password);
       JsonPath loginPath= login.jsonPath();
       SoftAssert softassert = new SoftAssert();
       softassert.assertEquals(login.statusCode(),200,"Fail");
       softassert.assertNotNull( loginPath.getString("token"),"Token is Null");
       softassert.assertAll();
   }
    @Test
    public void loginUsername()
    {
        Response login = loginRequest2(" ",Password);
        JsonPath loginPath= login.jsonPath();
        Assert.assertEquals(login.statusCode(),400,"Fail");
        Assert.assertEquals(loginPath.getString("error"),"user not found");
    }
    @Test
    public void loginPassword()
    {
        Response login = loginRequest2(UserName,"");
        JsonPath loginPath= login.jsonPath();
        Assert.assertEquals(login.statusCode(),400,"Fail");
        Assert.assertEquals(loginPath.getString("error"),"Missing password");
    }

    @Test
    public void wrongEmail()
    {
        SoftAssert  softAssert = new SoftAssert() ;
        Response loginResponse = Requests.loginRequest2("",Password);
        ErrorLogin errorLogin= loginResponse.as(ErrorLogin.class);
        softAssert.assertEquals(errorLogin.error,"Missing email or username");


    }
}
