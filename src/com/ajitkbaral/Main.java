package com.ajitkbaral;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static boolean askUserNameAndPassword = false; // If true, Username and password has to be entered from the console
    public static String userName = "YOUR_INSTA_USERNAME", password = "YOUR_INSTA_PASSWORD"; // Ignore if askUserNameAndPassword = true;
    public static WebDriver driver = null;
    public static String INSTAGRAM_URL = "https://www.instagram.com/", EXPLORE_URL = INSTAGRAM_URL+"explore/people/";
    public static WebElement usernameEl = null, passwordEl = null, loginFormEl = null;
    public static String CHROME_DRIVER_PATH = "YOUR_CHROME_DRIVER_PATH";

    public static void main(String[] args) {
        try {
            initWebDriver();
            Thread.sleep(3000);
            initLoginFormElements();
            if (loginFormEl != null && usernameEl != null && passwordEl != null) {
                loginToInstagram(loginFormEl, usernameEl, passwordEl);
                Thread.sleep(3000);
                navigateToInstagramSuggestions(driver);
                Thread.sleep(5000);
                followAll(driver);
            } else {
                System.out.println("========Error========");
                System.out.println("Login Form Fields not Found");
                driver.quit();
            }
        } catch (InterruptedException interruptedException) {
            System.out.println("===========InterruptedException========");
            System.out.println(interruptedException.getMessage());
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void initWebDriver(){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.get(INSTAGRAM_URL);
    }

    public static void navigateToInstagramSuggestions(WebDriver driver) {
        driver.get(EXPLORE_URL);
    }

    public static void initLoginFormElements() {
        usernameEl = driver.findElement(By.name("username"));
        passwordEl = driver.findElement(By.name("password"));
        loginFormEl = driver.findElement(By.id("loginForm"));
    }

    public static void loginToInstagram(WebElement loginFormEl, WebElement usernameEl, WebElement passwordEl) {
        System.out.println("LOGIN FORM======>"+ loginFormEl);
        System.out.println("USERNAME======>"+ usernameEl);
        System.out.println("PASSWORD======>"+ passwordEl);
        if(askUserNameAndPassword) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter username");
            userName = scanner.nextLine();
            System.out.println("Enter password");
            password = scanner.nextLine();
        }
        usernameEl.sendKeys(userName);
        passwordEl.sendKeys(password);
        loginFormEl.submit();
    }
    public static void followAll(WebDriver driver) throws InterruptedException {
        List<WebElement> followButtons = driver.findElements(By.xpath("//button[text()='Follow']"));
        System.out.println("======================");
        for(WebElement el: followButtons) {
            System.out.println("Button Follow Elements => "+el);
            el.click();
            Thread.sleep(1000);
        }
        System.out.println("===========COMPLETE===========");
    }
}
