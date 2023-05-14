import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.JawwyEgy;

public class JawwyEgyTest {

    // Instants
    WebDriver driver;
    JawwyEgy jawwyEgy;

    @BeforeTest
    public void Init (){
        // set up the web driver manger
        WebDriverManager.chromedriver().setup();
        // set up options for chrome driver
        ChromeOptions options = new ChromeOptions();
        // set options to headless mode
        options.addArguments("--headless");
        // init web driver
        driver = new ChromeDriver(options);
        jawwyEgy = new JawwyEgy(driver);
    }
    @Test
    public void tc01_trialCostEgy (){
        Boolean isDisplayed = false;
        String trialCost ;
       // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
       // check if the element is displayed
        isDisplayed = jawwyEgy.trialCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        trialCost = jawwyEgy.getTrialCostInEgy();

        Assert.assertTrue(trialCost.contains("Egyptian pound/month"));


    }
    @Test
    public void tc02_litePlanIsAvailable(){
        Boolean isDisplayed = false;

        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.litePlanIsDisplayed();
        Assert.assertTrue(isDisplayed);


    }
    @Test
    public void tc03_LitePlanCost(){
        Boolean isDisplayed = false;
        String litePlanCost ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.litePlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        litePlanCost = jawwyEgy.getLitePlanCost();

        Assert.assertEquals(litePlanCost , "0.25 USD/month");





    }
    @Test
    public void tc04_LitePlanCurrency(){
        Boolean isDisplayed = false;
        String litePlanCurrency ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.litePlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        litePlanCurrency = jawwyEgy.getLitePlanCurrency();

        Assert.assertEquals(litePlanCurrency , "USD/month");





    }
    @Test
    public void tc05_classicPlanIsAvailable(){
        Boolean isDisplayed = false;

        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.classicPlanIsDisplayed();
        Assert.assertTrue(isDisplayed);


    }
    @Test
    public void tc06_classicPlanCost(){
        Boolean isDisplayed = false;
        String classicPlanCost ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.classicPlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        classicPlanCost = jawwyEgy.getClassicPlanCost();

        Assert.assertEquals(classicPlanCost , "0.5 USD/month");





    }
    @Test
    public void tc07_classicPlanCurrency(){
        Boolean isDisplayed = false;
        String classicPlanCurrency ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.classicPlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        classicPlanCurrency = jawwyEgy.getClassicPlanCurrency();

        Assert.assertEquals(classicPlanCurrency , "USD/month");





    }
    @Test
    public void tc08_premiumPlanIsAvailable(){
        Boolean isDisplayed = false;

        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.premiumPlanIsDisplayed();
        Assert.assertTrue(isDisplayed);


    }
    @Test
    public void tc09_premiumPlanCost(){
        Boolean isDisplayed = false;
        String premiumPlanCost ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.premiumPlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        premiumPlanCost = jawwyEgy.getPremiumPlanCost();

        Assert.assertEquals(premiumPlanCost , "1 USD/month");





    }
    @Test
    public void tc10_classicPlanCurrency(){
        Boolean isDisplayed = false;
        String premiumPlanCurrency ;
        // navigate to the page
        jawwyEgy.navigateToJawwyEgyPage();
        // check if the element is displayed
        isDisplayed = jawwyEgy.premiumPlanCostIsDisplayed();
        Assert.assertTrue(isDisplayed);
        // check cost currency In EGY
        premiumPlanCurrency = jawwyEgy.getPremiumPlanCurrency();

        Assert.assertEquals(premiumPlanCurrency , "USD/month");





    }



    @AfterClass
    public void teardown() {
        driver.quit();
    }

}
