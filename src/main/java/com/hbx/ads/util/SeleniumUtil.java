package com.hbx.ads.util;

import com.hbx.ads.domain.sinoclick.AccountSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.Set;

public class SeleniumUtil {

    public static String getCookie(AccountSystem accountSystem) throws InterruptedException {
        //参数配置
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver;
        ChromeOptions option = new ChromeOptions();
        option.addArguments("no-sandbox");//禁用沙盒
        option.addArguments("--headless");//无头浏览器，不打开窗口
        //通过ChromeOptions的setExperimentalOption方法，传下面两个参数来禁止掉谷歌受自动化控制的信息栏
        option.setExperimentalOption("useAutomationExtension", false);
        option.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(option);
        driver.get("https://passport.sinoclick.com/login");
/*        String html=driver.getPageSource();
        // 这里只是打印源码，后续可以根据自己的需求来解析相关的数据
        System.out.println(html);*/

//        Thread.sleep(1000);
        WebElement account = driver.findElement(By.id("account"));
        account.sendKeys(accountSystem.getAccount());
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(accountSystem.getPassword());
//        Thread.sleep(1000L);
        WebElement loginButton = driver.findElement(By.xpath("/html/body/lang/div[@id='root']/div[@class='App ']/div/div[@class='passport-wrapper login-wrapper']/div[@class='login-wrapper-right']/div[@class='login-component ']/div[@class='login-box']/form[@class='m-design-form m-design-form-horizontal m-design-form-large login-form']/div[@class='m-design-row m-design-form-item']/div[@class='m-design-col m-design-form-item-control']/div[@class='m-design-form-item-control-input']/div[@class='m-design-form-item-control-input-content']/button[@class='m-design-btn m-design-btn-primary m-design-btn-lg m-design-btn-block']"));
        loginButton.click();
        Thread.sleep(500);
        Set<Cookie> cookies=driver.manage().getCookies();
        driver.close();
        driver.quit();
        String cookieStr ="";
        for (Cookie cookie : cookies) {
//            System.out.println(cookie.getName()+":"+cookie.getValue());
            cookieStr =cookieStr+cookie.getName()+"="+cookie.getValue()+";";
        }
        return cookieStr;
    }

}
