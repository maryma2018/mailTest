package com.netease.mail.MailAuto.login;

import java.util.List;
import java.util.NoSuchElementException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.log4testng.Logger;
import com.netease.mail.bean.UserInfo;
import com.netease.mail.config.ActivityName;
import com.netease.mail.page.control.LoginControl;
import com.netease.mail.page.object.HomePage;
import com.netease.mail.page.object.PermissionPage;
import com.netease.mail.util.Assertion;
import com.netease.mail.util.ElementUtil;
import com.netease.mail.util.FileUtil;
import com.netease.mail.util.WebDriver;
/**
 * 用户登录
 * @author maryma
 *
 */
public class UserLogin {
	Logger logger=Logger.getLogger(UserLogin.class);
	
	private AndroidDriver<AndroidElement> driver;
	private ElementUtil el;
	private String accountName;
	private String pwd;
	private LoginControl loginpage;
	
  @SuppressWarnings("unchecked")
  @BeforeClass
  public void setUp() {
	  WebDriver webdriver=new WebDriver();
	  driver=webdriver.InitWebDriver();
	  //实例化工具类
	  el=new ElementUtil(driver);
  }

  @Test
  public void login() {
	        PermissionPage perm=new PermissionPage(driver);
	        //首次启动，授权提醒
			if(el.activityVerify(5000, driver,ActivityName.PERMISSION_ACTIVITY)){
			   Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.PERMISSION_ACTIVITY,"当前是否是授权页面：");
			   //1、访问照片、媒体内容和文件权限弹窗
			   perm.allowClick();
			   //2、拨打电话和管理通话权限弹窗
			   perm.allowClick();
		    }
			//新功能引导页判断
			if(el.activityVerify(3000, driver,ActivityName.GUIDE_ACTIVITY)){
			  Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.GUIDE_ACTIVITY,"当前页面是否是新功能引导页：");
			  perm.knownClick();
			 }
			
			List<UserInfo> users = FileUtil.getAccount("userInfo.json");
			if (null != users && users.size() > 0) {
				System.out.println("size======="+users.size());
				for (int i = 0; i < users.size(); i++) {
					UserInfo user = users.get(i);
					 accountName = user.getEmail();
					 pwd = user.getPwd(); 
		          try{  
		        	//1、如果当前为初始登录页面
		        	  if(el.activityVerify(3000, driver, ActivityName.INIT_LOGIN_ACTIVITY)){
			            	logger.info("1、当前页面:"+driver.currentActivity().toString());
			            	Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.INIT_LOGIN_ACTIVITY,"当前页面是否是首次登录页面：");
			            	boolean flag=addCommonAcct();
			            	if(!flag){
			            		continue;
			            	}
			            }
		        	  //2、如果当前所在为主页面
		        	  else if(el.activityVerify(3000, driver,ActivityName.TAB_ACTIVITY)){
		        		System.out.println("2、当前页面："+driver.currentActivity().toString());
		        		Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.TAB_ACTIVITY,"当前页面是否是主页面：");
			        	//检查Tab栏——邮件
			            HomePage home=new HomePage(driver);
			            //从设置页面打开登录页面,依次点击我-邮箱帐号-添加邮箱帐号
			            home.addAcctClick();
		        		
			           
			            //当前页面是否是登录页面          
			            if(el.activityVerify(3000, driver, ActivityName.ADD_ACCOUTN_ACTIVITY)){
			            	System.out.println("3、当前启动的页面:"+driver.currentActivity().toString());
			            	Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.ADD_ACCOUTN_ACTIVITY,"当前页面是否登录页面：");
			            	boolean flag=addCommonAcct();
			            	if(!flag){
			            		continue;
			            	}
			            }
			            //3、当前页面为添加帐号的页面——已添加过帐号，再次添加时的使用的登录页面
		        	  }else if(el.activityVerify(3000, driver, ActivityName.ADD_ACCOUTN_ACTIVITY)){
			            	System.out.println("4、当前启动的页面:"+driver.currentActivity().toString());
			            	Assertion.verifyEquals(driver.currentActivity().toString(), ActivityName.ADD_ACCOUTN_ACTIVITY,"当前页面是否登录页面：");
			            	boolean flag=addCommonAcct();
			            	if(!flag){
			            		continue;
			            	}
		        	  }
			        }catch (NoSuchElementException e){ 
			        	Assert.fail("UserLogin=======login()>>"+e.getMessage());
			  	        //关闭APP
			  	        closeApp();
		              }
				}
			}
	  
  }
  
  /**
   * 关闭APP方法
   */
  public void closeApp()
  {
      for(int h=0;h<6;h++)
      {
          driver.pressKeyCode(4);
      }
  }
  
  /**
	  * 添加帐号
	  * qq、gmail和outlook使用webmail授权登录
	  * 其他域使用邮箱大师的登录页面登录
	  * @return
	  */
	 public boolean addAccounts(){
		 boolean flag=false;
      if(accountName.endsWith("gmail.com") || accountName.endsWith("outlook.com") || accountName.endsWith("qq.com")){
     	 flag=addSpecialAcct();
		 }else{
			 flag=addCommonAcct();
		 }
      return flag;
	 }
  
	 
	 /**
	  *登录页面添加帐号操作
	  *普通帐号登录，即非webmail登录方式
	  */	 
	 public boolean addCommonAcct() {
		 System.out.println("当前所在的页面:"+driver.currentActivity().toString());
		 try{
			 //登录页面的操作
		    loginpage=new LoginControl(driver);
		    loginpage.userLogin(accountName, pwd);
		    
			//登录中的错误
		    //1、判断密码是否正确，密码错误关闭弹窗,跳过添加该帐号，添加下一帐号；否则进入第2步
		    if(!loginpage.pwdError()){
		    	return true;
		    }
		    
		    //2、帐号已存在，点确定按钮关闭弹窗。如果帐号存在，跳过此步继续添加下一个帐号
			if(!loginpage.hadAddAcct()){
				return true;
			}
			
			//3、帐号登录成功后的操作
//			boolean flag=afterLoginSuccess();
//			return flag;
			return true;
		 }catch(NoSuchElementException ne){
			 System.out.println("addAccounts======"+ne.getMessage());
			 return false;
		  }
	 }
	 
	  public boolean afterLoginSuccess(){ 
		  
		//密码错误
		    By by=By.name("帐号或密码错误，请重新输入");
		    el.intelligentWait(driver, 10, by);
		    
			WebElement msgElement=el.getElementByName("帐号或密码错误，请重新输入");
			if(null!=msgElement){
				WebElement alertDialog= el.getElementById("com.netease.mail:id/alert_dialog_btnOK");
				alertDialog.click();
				return false;
			}
			//帐号已存在
			By by1=By.name("该帐号已存在");
		    el.intelligentWait(driver, 10, by1);
			WebElement exitElement=el.getElementByName("该帐号已存在");
			System.out.println("===exitElement==="+exitElement);
			if(null!=exitElement){
				WebElement cancelButn= el.getElementByName("取消");
				cancelButn.click();
				return false;
			}
			
			WebElement nextButn= el.getElementByName("下一步");
			//1、判断用户是否进入添加关联帐号页面
			 //如果添加的帐号有关联帐号，会进入引导添加关联帐号页面，点下一步进入2
			 if(null!=nextButn){
				 nextButn.click();
			 }	 		

			 //2、如果添加的帐号没有头像会进入引导设置头像页面
			 WebElement enterButn= el.getElementByName("进入邮箱");
			 if(null!=enterButn){
				 enterButn.click();
				 return true;
			 }	 
//			 3、是否有新功能引导页，如果有点【跳过】按钮进入应用
			 WebElement jumpButn= el.getElementByName("跳过");
			 logger.info("jumpButn="+jumpButn);
		     if(null!=jumpButn){
				 jumpButn.click();
				 return true;
			 }
			
			 
			 //4、进入邮件列表后，开启新邮件提醒授权弹
			 WebElement remindTitle  = el.getElementByName("开启新邮件提醒");
			 if(null!=remindTitle){
				 WebElement cancelButn= el.getElementByName("取消");
				 cancelButn.click();
			 }
			 // 进入邮件列表
		  Assert.assertTrue( driver.findElement(By.name("收件箱")).isDisplayed());
	      return true;
	    }
	 
		/**
		 * 添加外域特殊帐号
		 */
		public boolean addSpecialAcct(){
			WebElement emailEdit = el.getElementById("com.netease.mail:id/editor_email");//帐号输入框
			if(null!=emailEdit){
			   emailEdit.click();//激活该文本框
			   WebElement emailClear =el.getElementById("com.netease.mail:id/button_email_clear");//帐号清空按钮
			   if(null!=emailClear)emailClear.click();
			   emailEdit.sendKeys(accountName);
			}
			
			//帐号已存在
			WebElement exitElement=el.getElementByName("帐号已存在");
			if(null!=exitElement){
				WebElement cancelButn= el.getElementByName("取消");
				cancelButn.click();
				return false;
			}
			
			WebElement nextButn= el.getElementByName("下一步");
			//1、判断用户是否进入添加关联帐号页面
			 //如果添加的帐号有关联帐号，会进入引导添加关联帐号页面，点下一步进入2
			 if(null!=nextButn){
				 nextButn.click();
			 }
			 
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 等它一会
			 driver.context("WEBVIEW_0");
			 WebElement titleTxt= el.getElementByName("登录 - Google 帐号");
			 if(null!=titleTxt){
			 }
			
			return true;
		}
	 
	 
  @AfterClass
  public void tearDown() {
      driver.quit();
  }

}
