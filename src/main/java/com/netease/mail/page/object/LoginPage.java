package com.netease.mail.page.object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
/**
 * 登录页面
 * 控件
 * @author maryma
 *
 */
public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver dr) {
		super(dr);
	}
	protected WebDriver driver;
	 //用户名
     @FindBy(id="editor_email")
     public WebElement username;
     
     //密码
     @FindBy(id="editor_password")
     public WebElement password;
     
     //添加按钮com.netease.mail:id/register_button_next
     @FindBy(id="register_button_next")
     public WebElement loginBtn;

     //帐号输入框清空按钮
     @FindBy(id="button_email_clear")
     public WebElement acct_clearBtn; 
     
     //密码输入框清空按钮
     @FindBy(id="button_password_clear")
     public WebElement pwd_clearBtn; 
     
     //进入邮箱按钮
     @FindBy(id="enter_mail")
     public WebElement enterButn; 
     
     //登录错误提示
     @FindBy(name="帐号或密码错误，请重新输入")
     public WebElement errorMsgTxt; 
     
     @FindBy(name="该帐号已存在")
     public WebElement hadAddTxt; 
     
     //密码错误弹窗中的确定按钮
     @FindBy(name="确定")
     public WebElement confirmBtn;
     
     @FindBy(name="取消")
     public WebElement cancelButn; 
     
     
     @FindBy(id="com.netease.mail:id/alert_dialog_btnCancel")
     public WebElement cancelBtn;

     @FindBy(name="下一步")
     public WebElement nextButn; 
     
     @FindBy(name="跳过")
     public WebElement jumpButn;
     
     @FindBy(name="开启新邮件提醒")
     public WebElement remindTitle;
     
     
     
     
}
