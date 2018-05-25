package com.netease.mail.page.object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
/**
 * 主页面元素操作
 * @author maryma
 *
 */
public class HomePage extends BasePage {

	public HomePage(WebDriver dr) {
		super(dr);
	}
	
	 @FindBy(id="tab_settings")
     private WebElement personal; 
	 
	 @FindBy(id="mine_account_item")
     private WebElement acctManger; 

	 @FindBy(id="add_account_item")
     private WebElement addAcct; 
	 
	 @FindBy(id="iv_mail_list_folder")
     private WebElement mailListFolder; 
	 
	 @FindBy(name="添加邮箱")
     private WebElement addmail; 
	 
	 
	 /**
	  * 点击我——邮箱管理——添加帐号，打开登录页面
	  * @param username
	  * @param pwd
	  */
     public void addAcctClick(){
//    	 personal.click();
//    	 acctManger.click();
//    	 addAcct.click();
    	 mailListFolder.click();
    	 addmail.click();
      } 
     
     
     
}
