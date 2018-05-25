package com.netease.mail.util;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import com.netease.mail.config.ActivityName;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebDriver {
	
	private AndroidDriver<AndroidElement> driver;
	//设备类型
    public static String device = "Android";
	 // 调试设备名字
    public static String deviceName = "65e62d2e";
    
    //调试设备系统版本
    public static String platformVersion = "5.1.1";
    
    //包名
    public static String appPackage = "com.netease.mail";
    
    //要启动的Activity
    public static String appActivity = ActivityName.LAUNCH_ACTIVITY;
 
    //不重置应用数据，如重新启动登录过的App时，仍然是登录状态，不需要重新登录
    public static String noReset = "True";
    
    // 是否重新签名
    public static String noSign = "True";
    
    //automationName表示appium使用的测试引擎，默认是appium，也可以是uiautomator,appium版本6.0以上可指定使用uiautomator2
    public static String automationName = "appium";
    
   //设置为true表示我们要使用appium自带的输入法，用来支持中文和隐藏键盘，并且将其设置为默认输入法
    public static String unicodeKeyboard = "True";
    
   //在执行完测试之后，将手机的输入法从appium输入法还原成手机默认输入法
    public static String resetKeyboard = "True";
    
    //dirver的session的超时时间，默认是60秒
    public static String commandTimeout = "6000";
	
    //apk路径
    public static String appPath = System.getProperty("user.dir") 
            + "/apps/mail.apk";
    
    public WebDriver() {
		super();
		InitWebDriver();
	}

	public AndroidDriver InitWebDriver(){
	   
	   /*
		 * File classpathRoot = new File(System.getProperty("user.dir"));
		 * System.out.println(classpathRoot); File appDir = new
		 * File(classpathRoot, "/apps"); File app = new File(appDir, "");
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("noReset", noReset);// 设置不重复安装
		
		capabilities.setCapability("device", device);
		// 设置要调试的模拟器的名字
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);//Android Emulator
		
		// 设置模拟器的系统版本
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, commandTimeout);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        //支持中文输入
        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, unicodeKeyboard);
        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD,resetKeyboard);
        capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, noSign);
		
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		try {
			//启动Driver
			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(driver);     
		
		return driver;
   }
}
