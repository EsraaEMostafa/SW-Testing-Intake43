package pages;

import org.openqa.selenium.WebDriver;
import utilis.ElementHandler;

public class JawwyEgy {
    // web driver instants
    WebDriver driver;
    // page URL as string
    String pageURL = "https://subscribe.jawwy.tv/eg-en";
    // locator for elements to be Tested
    String trialCostInEgy = "//div[@class = 'trial-cost']";
    String litePlan = "//div/strong[@id = 'name-lite']";
    String litePlanCost = "//div[@id = 'currency-lite']";
    String litePlanCurrency = "//div[@id = 'currency-lite']/i";
    String classicPlan = "//div/strong[@id = 'name-classic']";
    String classicPlanCost = "//div[@id = 'currency-classic']";
    String classicPlanCurrency = "//div[@id = 'currency-classic']/i";
    String premiumPlan = "//div/strong[@id = 'name-premium']";
    String premiumPlanCost = "//div[@id = 'currency-premium']";
    String premiumPlanCurrency = "//div[@id = 'currency-premium']/i" ;

    public JawwyEgy(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToJawwyEgyPage (){
        driver.get(pageURL);
    }


 //Trial cost functions
  public Boolean trialCostIsDisplayed (){
        Boolean isDisplayed = false;

        isDisplayed = ElementHandler.isDisplayed(driver , trialCostInEgy);

        return  isDisplayed;

  }

  public String getTrialCostInEgy (){

      String cost ;

      cost = ElementHandler.getText(driver , trialCostInEgy);

      return  cost;


  }


 // Lite Plan Function

 public Boolean litePlanIsDisplayed (){


     Boolean isDisplayed = false;

     isDisplayed = ElementHandler.isDisplayed(driver , litePlan);

     return  isDisplayed;
 }


  // Lite Plan Cost functions

  public Boolean   litePlanCostIsDisplayed(){

      Boolean isDisplayed = false;

      isDisplayed = ElementHandler.isDisplayed(driver , litePlanCost);

      return  isDisplayed;

  }

  public String getLitePlanCost (){
        String cost ;

      cost = ElementHandler.getText(driver , litePlanCost);

        return  cost;
  }

    public String getLitePlanCurrency (){
        String currency ;

        currency = ElementHandler.getText(driver , litePlanCurrency);

        return  currency;
    }


    // classic Plan Function

    public Boolean classicPlanIsDisplayed (){


        Boolean isDisplayed = false;

        isDisplayed = ElementHandler.isDisplayed(driver , classicPlan);

        return  isDisplayed;
    }


    // Classic Plan Cost functions

    public Boolean   classicPlanCostIsDisplayed(){

        Boolean isDisplayed = false;

        isDisplayed = ElementHandler.isDisplayed(driver , classicPlanCost);

        return  isDisplayed;

    }

    public String getClassicPlanCost (){
        String cost ;

        cost = ElementHandler.getText(driver , classicPlanCost);

        return  cost;
    }
    public String getClassicPlanCurrency (){
        String currency ;

        currency = ElementHandler.getText(driver , classicPlanCurrency);

        return  currency;
    }
    // premium Plan Function

    public Boolean premiumPlanIsDisplayed (){


        Boolean isDisplayed = false;

        isDisplayed = ElementHandler.isDisplayed(driver , premiumPlan);

        return  isDisplayed;
    }


    // premium Plan Cost functions

    public Boolean   premiumPlanCostIsDisplayed(){

        Boolean isDisplayed = false;

        isDisplayed = ElementHandler.isDisplayed(driver , premiumPlanCost);

        return  isDisplayed;

    }

    public String getPremiumPlanCost(){
        String cost ;

        cost = ElementHandler.getText(driver , premiumPlanCost);

        return  cost;
    }

    public String getPremiumPlanCurrency(){
        String currency ;

        currency = ElementHandler.getText(driver , premiumPlanCurrency);

        return  currency;
    }


}
