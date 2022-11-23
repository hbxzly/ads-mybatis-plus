package com.hbx.ads.service.impl;

import com.hbx.ads.domain.pojo.AdAccountRechargeVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.service.SeleniumService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SeleniumServiceImpl implements SeleniumService {


    WebDriver driver;

    @Override
    public void loginAccountSystem(AccountSystem accountSystem) {

        //参数配置
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        ChromeOptions option = new ChromeOptions();
//        option.addArguments("no-sandbox");//禁用沙盒
//        option.addArguments("--headless");//无头浏览器，不打开窗口
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

    }

    @Override
    public void adAccountRecharge(AccountSystem accountSystem, AdAccountRechargeVo adAccountRechargeVo) throws InterruptedException {

        loginAccountSystem(accountSystem);
        Thread.sleep(500);
        driver.get("https://business.sinoclick.com/client/myorder/recharge/ad-account?account="+adAccountRechargeVo.getId()+"&channel=1&user="+accountSystem.getUserId()+"");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20,1);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[@id='root']/section[@class='m-design-layout layout']/section[@class='m-design-layout m-design-layout-has-sider']/section[@class='m-design-layout con-layout']/main[@class='m-design-layout-content site-layout-background home-main-safari-hack m-design-layout-content-dark']/div[@class='recharge__RechartStyle-sc-17j3ul1-0 kMjQCQ']/div[@class='m-design-card card__CardStyle-sc-a60nbi-0 cuGpQt']/div[@class='m-design-card-body']/div[@class='form-container']/button[@class='m-design-btn m-design-btn-primary m-design-btn-lg']/span")));
        WebElement rechargeButton = driver.findElement(By.xpath("/html/body/div[@id='root']/section[@class='m-design-layout layout']/section[@class='m-design-layout m-design-layout-has-sider']/section[@class='m-design-layout con-layout']/main[@class='m-design-layout-content site-layout-background home-main-safari-hack m-design-layout-content-dark']/div[@class='recharge__RechartStyle-sc-17j3ul1-0 kMjQCQ']/div[@class='m-design-card card__CardStyle-sc-a60nbi-0 cuGpQt']/div[@class='m-design-card-body']/div[@class='form-container']/button[@class='m-design-btn m-design-btn-primary m-design-btn-lg']/span"));
        WebElement rechargeAmount = driver.findElement(By.id("recharge_amount"));
        rechargeAmount.sendKeys(adAccountRechargeVo.getRechargeAmount());
        rechargeButton.click();
    }

    @Override
    public void closeChrome() {
        driver.close();
        driver.quit();
    }


}
