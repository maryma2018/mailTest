package com.netease.mail.MailAuto;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.log4testng.Logger;

import com.netease.mail.bean.UserInfo;
import com.netease.mail.util.ElementUtil;
import com.netease.mail.util.FileUtil;

import org.openqa.selenium.remote.DesiredCapabilities;



public class Login {
	Logger logger=Logger.getLogger(Login.class);
	private AndroidDriver<AndroidElement> driver;
	private ElementUtil el;
	
	private static String LAUNCH_ACTIVITY="com.netease.mobimail.activity.LaunchActivity";//启动页面的路径
	private static String LOGIN_ACTIVITY="com.netease.mobimail.activity.LoginActivity";//登录页面的路径
	private static String ADD_ACCOUTN_ACTIVITY="com.netease.mobimail.activity.AddAccountActivity";//从设置页面打开添加帐号的路径
	private static String INIT_LOGIN_ACTIVITY="com.netease.mobimail.activity.InitAddAccountActivity";//首次添加帐号的登录页面的路径
	private static String TAB_ACTIVITY="com.netease.mobimail.activity.TabActivity";//主页面的路径
	private static String PERMISSION_ACTIVITY=".permission.ui.GrantPermissionsActivity";//授权页面路径
	private static String GUIDE_ACTIVITY="com.netease.mobimail.activity.GuideActivity";//新功能引导页
	
	
	private String accountName;
	private String pwd;
	
	@BeforeClass
	public void setUp() throws Exception {
		/*
		 * File classpathRoot = new File(System.getProperty("user.dir"));
		 * System.out.println(classpathRoot); File appDir = new
		 * File(classpathRoot, "/apps"); File app = new File(appDir, "");
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("noReset", true);// 设置不重复安装
		// 设置要调试的模拟器的名字
		capabilities.setCapability("deviceName", "Android Emulator");// Android Emulator
		// 设置模拟器的系统版本
		capabilities.setCapability("platformVersion", "6.0");
		// 支持中文输入
		capabilities.setCapability("unicodeKeyboard", "True");
		
		capabilities.setCapability("appPackage", "com.netease.mail");
		capabilities.setCapability("appActivity", LAUNCH_ACTIVITY);
//		capabilities.setCapability("automationName", "uiautomator2");//appium版本6.0以上可指定使用uiautomator2
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		
		//实例化工具类
		el=new ElementUtil(driver);
	}

	@Test
	public void login() {
		  //验证APP启动成功，并且LaunchActivity打开
//	      if(activityVerify(3000, driver,LAUNCH_ACTIVITY))
//	      {
//	          logger.info("当前启动的页面:"+driver.currentActivity().toString());
	          //断言两个对象相等，若不满足，方法抛出带有相应信息
//	          Assert.assertEquals(driver.currentActivity().toString(), LAUNCH_ACTIVITY);
		//首次启动，授权提醒
		if(el.activityVerify(5000, driver,PERMISSION_ACTIVITY)){
		   Assert.assertEquals(driver.currentActivity().toString(), PERMISSION_ACTIVITY);
		   //1、访问照片、媒体内容和文件权限弹窗
		   el.getElementByName("允许").click();
		   //2、拨打电话和管理通话权限弹窗
		   el.getElementByName("允许").click();
	    }
		//新功能引导页判断
		if(el.activityVerify(3000, driver,GUIDE_ACTIVITY)){
		  Assert.assertEquals(driver.currentActivity().toString(), GUIDE_ACTIVITY);
		  el.getElementByName("知道了").click();
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
	        	  if(el.activityVerify(3000, driver, INIT_LOGIN_ACTIVITY)){
		            	logger.info("1、当前页面:"+driver.currentActivity().toString());
		            	Assert.assertEquals(driver.currentActivity().toString(), INIT_LOGIN_ACTIVITY);
		            	boolean flag=addCommonAcct();
		            	if(!flag){
		            		continue;
		            	}
		            }
	        	  //2、如果当前所在为主页面
	        	  else if(el.activityVerify(3000, driver,TAB_ACTIVITY)){
	        		System.out.println("2、当前页面："+driver.currentActivity().toString());
		        	//检查Tab栏——邮件
   	                //driver.findElementById("com.netease.mail:id/tab_mail").getAttribute("text");  
		            
		            //从设置页面打开登录页面,依次点击我-邮箱帐号-添加邮箱帐号
		            WebElement tab_personal=el.getElementById("com.netease.mail:id/tab_settings");
		            tab_personal.click();
		           
		            
		            WebElement acctManage=el.getElementById("com.netease.mail:id/mine_account_item");
		            acctManage.click();
		            
		            WebElement addAcct=el.getElementById("com.netease.mail:id/add_account_item");
		            addAcct.click();
		            //当前页面是否是登录页面          
		            if(el.activityVerify(3000, driver, ADD_ACCOUTN_ACTIVITY)){
		            	System.out.println("3、当前启动的页面:"+driver.currentActivity().toString());
		            	Assert.assertEquals(driver.currentActivity().toString(), ADD_ACCOUTN_ACTIVITY);
		            	boolean flag=addCommonAcct();
		            	if(!flag){
		            		continue;
		            	}
		            }
		            //3、当前页面为添加帐号的页面——已添加过帐号，再次添加时的使用的登录页面
	        	  }else if(el.activityVerify(3000, driver, ADD_ACCOUTN_ACTIVITY)){
		            	System.out.println("4、当前启动的页面:"+driver.currentActivity().toString());
		            	Assert.assertEquals(driver.currentActivity().toString(), ADD_ACCOUTN_ACTIVITY);
		            	boolean flag=addCommonAcct();
		            	if(!flag){
		            		continue;
		            	}
	        	  }
		        }catch (NoSuchElementException e){ 
		        	System.out.println("没有找到："+e.getMessage());
		  	        //关闭APP
		  	        closeApp();
	              }
			}
		}
	}
	


	@AfterClass
	public void tearDown() {
	      driver.quit();
	  }
//	 /**
//	  * 线程等待方法
//	  * @param time
//	  */
//	  public void appWait(long time)
//	  {
//	      try {
//	          Thread.sleep(time);
//	      } catch (InterruptedException e) {
//	          // TODO Auto-generated catch block
//	          e.printStackTrace();
//	      }
//	  }
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
	   * 验证activity是否存在
	   * @param time
	   * @param tdriver
	   * @param activity
	   * @return
	   */
//	 public Boolean activityVerify(long time,AndroidDriver<AndroidElement> tdriver,String activity)
//	 {
//	         for(int i=1;i<time/1000;i++){
//	             appWait(1000);
//	             System.out.println("activityVerify==========="+tdriver.currentActivity());
//	             if(tdriver.currentActivity().equals(activity))
//	             {
//	                 return true;
//	             }    
//	         }
//	         System.out.println("==============can not find "+activity+"=============");
//	         return false;
//	     
//	 }
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
//         PageFactory.initElements(driver, LoginPage.class);
         return flag;
	 }
	 
	 /**
	  *普通帐号登录，即非webmail登录方式
	  */	 
	 public boolean addCommonAcct() {
		 System.out.println("当前所在的页面:"+driver.currentActivity().toString());
//		  if(!checkApp()){//app正常启动执行下面的方法
		 
		 try{
			 //登录页面的操作
			WebElement emailEdit = el.getElementById("com.netease.mail:id/editor_email");//帐号输入框
			if(null!=emailEdit){
//			   emailEdit.clear();
			   //clear()不好用，当输入框有内容时先清空
			   emailEdit.click();//激活该文本框
			   WebElement emailClear =el.getElementById("com.netease.mail:id/button_email_clear");//帐号清空按钮
			   if(null!=emailClear)emailClear.click();
			   emailEdit.sendKeys(accountName);
			}
			WebElement pwdEdit =el.getElementById("com.netease.mail:id/editor_password");//密码输入框
			if(null!=pwdEdit){
				//clear()不好用，光标会定位在文本头部，不会清空
				pwdEdit.click();//激活该文本框
				WebElement pwdClear =el.getElementById("com.netease.mail:id/button_password_clear");//密码清空按钮
				if(null!=pwdClear)pwdClear.click();
			    pwdEdit.sendKeys(pwd);
			}
//			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//如果添加按钮被键盘遮挡，关闭键盘
			 if(driver.findElementsById("com.netease.mail:id/button_register").isEmpty())
             {
                 driver.pressKeyCode(4);
             }
			 WebElement loginBtn=el.getElementById("com.netease.mail:id/register_button_next");
			 if(null!=loginBtn){
			   loginBtn.click();
			 }
			
			//登录中处理
			//密码错误
			WebElement msgElement=el.getElementByName("帐号或密码错误，请重新输入");
			if(null!=msgElement){
				WebElement alertDialog= el.getElementById("com.netease.mail:id/alert_dialog_btnOK");
				alertDialog.click();
				return false;
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

			 //2、如果添加的帐号没有头像会进入引导设置头像页面
			 WebElement enterButn= el.getElementByName("进入邮箱");
			 if(null!=enterButn){
				 enterButn.click();
			 }	 
//			 3、是否有新功能引导页，如果有点【跳过】按钮进入应用
			 WebElement jumpButn= el.getElementByName("跳过");
			 logger.info("jumpButn="+jumpButn);
		     if(null!=jumpButn){
				 jumpButn.click();
			 }
			 // 进入邮件列表
			 Assert.assertTrue( driver.findElement(By.name("收件箱")).isDisplayed());
			 
			 //4、进入邮件列表后，开启新邮件提醒授权弹
			 WebElement remindTitle  = el.getElementByName("开启新邮件提醒");
			 if(null!=remindTitle){
				 WebElement cancelButn= el.getElementByName("取消");
				 cancelButn.click();
			 }
			
			 return true;
//			}
//			}
		 }catch(NoSuchElementException ne){
			 System.out.println("addAccounts======"+ne.getMessage());
			 return false;
		  }
		 
	 }
//		}
	 
	 
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
		
		/**
		 * 获取webview的name
		 * @param contextName
		 * @return
		 */
//		public WebDriver webView(String contextName) {
//
//	        Set contextNames = ((AppiumDriver)driver).getContextHandles();
//
//	        for (Object context : contextNames) {
//
//	            if (context.toString().contains(contextName)) {
//
//	                System.out.println(context);
//
//	                return (AppiumDriver) driver.context(contextName);
//
//	            }
//	        }
//	        return null;
//	    }

		
		
	 /**
	  * 根据text值获取元素
	  * @param name
	  * @return
	  */
//	 public WebElement getElementByName(final String name){
//		 WebDriverWait webDriverWait = new WebDriverWait(driver,2,500);
//		 WebElement wInfoNo = null;
//		 try {  
//	            wInfoNo = webDriverWait.until(new ExpectedCondition<WebElement>() {  
//	                @Override  
//	                public WebElement apply(WebDriver input) {  
//	                    return driver.findElementByName(name);  
//	                }  
//	            });  
//	        } catch (Exception e) {  
//	            System.out.println("找不到");  
//	        }  
//		 return wInfoNo;
//	 }
	 
	 /**
	  * 根据resource-id获取页面元素
	  * @param id
	  * @return
	  */
//	 public WebElement getElementById(final String id){
//		 WebDriverWait webDriverWait = new WebDriverWait(driver,2,500);
//		 WebElement wInfoNo = null;
//		 try {  
//	            wInfoNo = webDriverWait.until(new ExpectedCondition<WebElement>() {  
//	                @Override  
//	                public WebElement apply(WebDriver input) {  
//	                    return driver.findElementById(id);  
//	                }  
//	            });  
//	        } catch (Exception e) {  
//	            System.out.println("找不到");  
//	        }  
//		 return wInfoNo;
//	 }
	 
	 
}
