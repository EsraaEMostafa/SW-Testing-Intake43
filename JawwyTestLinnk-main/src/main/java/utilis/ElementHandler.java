package utilis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementHandler {

    public static Boolean isDisplayed (WebDriver driver , String element){

        Boolean isDisplayed = false;

        isDisplayed = driver.findElement(By.xpath(element)).isDisplayed();

        return  isDisplayed;
    }

    public static  String getText (WebDriver driver , String element){

        String text;

        text = driver.findElement(By.xpath(element)).getText();

        return  text;
    }

}
