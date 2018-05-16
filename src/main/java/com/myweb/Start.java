package com.myweb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Start {
    public static void main(String[] args) throws Exception {
//  WebDriver driver = new FirefoxDriver();
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://baidu.com");
        WebElement input = driver.findElement(By.xpath(".//*[@id='kw']"));
        CharSequence[] cs = new CharSequence[1];
        cs[0]="安居客";
        input.sendKeys(cs);

        WebElement btn = driver.findElement(By.xpath(".//*[@id='su']"));
        btn.click();
        // WebElement btn1 = driver.findElement(By.xpath(".//*[@id='w-75cn8k']/div/h2/a[1]"));
        //btn1.click();
        System.out.println("Page title is:"+driver.getTitle());
        //Sleep(2000);
        driver.close();
    }
}
}
