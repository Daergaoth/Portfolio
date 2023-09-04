package com.daergaoth.videoplayer.selenium.tests;

import com.daergaoth.videoplayer.selenium.SeleniumTests;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

public class SeleniumTestOne extends SeleniumTests {

    @Test
    public void testOne() {
        driver.get("http://localhost:8080/");

        driver.manage().window().setSize(new Dimension(1900, 1012));
        waiting(2);
        try{
            driver.findElement(By.cssSelector(".mat-toolbar > .navbarlink")).click();
            waiting(2);

            driver.findElement(By.cssSelector("div > .navbarlink:nth-child(1)")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".mat-mdc-header-cell:nth-child(1)")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".mat-mdc-header-cell:nth-child(2)")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".mat-mdc-header-cell:nth-child(3)")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".mat-mdc-header-cell:nth-child(4)")).click();
            waiting(2);

            driver.findElement(By.id("input_20")).click();
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".ng-tns-c1205077789-14 > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);

            driver.findElement(By.id("input_20")).sendKeys("100");
            waiting(2);

            driver.findElement(By.cssSelector(".ng-tns-c1205077789-14 > .mat-mdc-button-touch-target")).click();
            waiting(2);

            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);

            driver.findElement(By.cssSelector(".mdc-text-field--focused")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".mat-mdc-row:nth-child(2) > .cdk-column-title")).click();
            waiting(2);

            driver.findElement(By.cssSelector("#layoutContainer > #title")).click();
            waiting(2);

            WebElement titleElement = driver.findElement(By.id("title"));
            assert(titleElement.getText().contains("Naruto Shippuuden - "));

            driver.findElement(By.id("middleContainer")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".controlButtonLast")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".controlButtonLast")).click();
            waiting(2);

            driver.findElement(By.cssSelector(".controlButtonFirst")).click();
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".ng-tns-c1205077789-9 > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".mat-mdc-row:nth-child(10) > .mat-mdc-cell > .mat-mdc-tooltip-trigger > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".mat-mdc-row:nth-child(10) > .mat-mdc-cell > .mat-mdc-tooltip-trigger > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".ng-tns-c1205077789-10 > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);

            {
                WebElement element = driver.findElement(By.cssSelector(".ng-tns-c1205077789-10 > .mat-mdc-button-touch-target"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
            }
            waiting(2);
            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            waiting(2);
        }catch (AssertionError error){
            error.printStackTrace();
            handleAssertionError(error);
        }

    }
}
