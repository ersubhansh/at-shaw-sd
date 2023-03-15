package com.netcracker.shaw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClass {

	public static void main(String[] args) throws InterruptedException  {

		System.setProperty("webdriver.chrome.driver", "C:\\SHAW\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.get("http://qaapp843cn.netcracker.com:8140/admin/users/group.jsp");
		System.out.println("Start Testing...");
		driver.findElement(By.id("user")).sendKeys("Administrator");
		Thread.sleep(1000);
		driver.findElement(By.id("pass")).sendKeys("netcracker");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[text()='Login']/../td//input")).sendKeys("CLECOPS");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='password1']")).sendKeys("123");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='password2']")).sendKeys("123");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//a[@role='button'])[4]")).click();
		Thread.sleep(2000);
		driver.switchTo().frame("ui-id-2");
		driver.findElement(By.xpath("//input[@name='user']")).sendKeys("Administrator");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='pass']")).sendKeys("netcracker");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@value='Log In")).click();
		driver.quit();
	}
}


