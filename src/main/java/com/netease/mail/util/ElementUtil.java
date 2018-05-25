package com.netease.mail.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * 用于查找页面元素
 * @author maryma
 *
 */
public class ElementUtil {
	private AndroidDriver<AndroidElement> driver;
	
	public ElementUtil(AndroidDriver<AndroidElement> driver) {
		super();
		this.driver = driver;
	}


	/**
	 * 获取webview的name
	 * @param contextName
	 * @return
	 */
	public WebDriver webView(String contextName) {

        Set contextNames = ((AppiumDriver)driver).getContextHandles();

        for (Object context : contextNames) {

            if (context.toString().contains(contextName)) {

                System.out.println(context);

                return (AppiumDriver) driver.context(contextName);

            }
        }
        return null;
    }
	
	
	/**
	  * 根据text值获取元素
	  * @param name
	  * @return
	  */
	 public WebElement getElementByName(final String name){
		 WebDriverWait webDriverWait = new WebDriverWait(driver,2,500);
		 WebElement wInfoNo = null;
		 try {  
	            wInfoNo = webDriverWait.until(new ExpectedCondition<WebElement>() {  
	                @Override  
	                public WebElement apply(WebDriver input) {  
	                    return driver.findElementByName(name);  
	                }  
	            });  
	        } catch (Exception e) {  
	            System.out.println("找不到");  
	        }  
		 return wInfoNo;
	 }
	 
	 /**
	  * 根据resource-id获取页面元素
	  * @param id
	  * @return
	  */
	 public WebElement getElementById(final String id){
		 WebDriverWait webDriverWait = new WebDriverWait(driver,2,500);
		 WebElement wInfoNo = null;
		 try {  
	            wInfoNo = webDriverWait.until(new ExpectedCondition<WebElement>() {  
	                @Override  
	                public WebElement apply(WebDriver input) {  
	                    return driver.findElementById(id);  
	                }  
	            });  
	        } catch (Exception e) {  
	            System.out.println("找不到");  
	        }  
		 return wInfoNo;
	 }
	 
	 
	 /**
	   * 验证activity是否存在
	   * @param time
	   * @param tdriver
	   * @param activity
	   * @return
	   */
	 public Boolean activityVerify(long time,AndroidDriver<AndroidElement> tdriver,String activity)
	 {
	         for(int i=1;i<time/1000;i++){
	             appWait(1000);
	             System.out.println("activityVerify==========="+tdriver.currentActivity());
	             if(tdriver.currentActivity().equals(activity))
	             {
	                 return true;
	             }    
	         }
	         System.out.println("==============can not find "+activity+"=============");
	         return false;
	     
	 }
	 
	 /**这是智能等待元素加载的方法*/
	    public void intelligentWait(WebDriver driver,int timeOut, final By by) {
	        try {
	            (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
	                public Boolean apply(WebDriver driver) {
	                    WebElement element = driver.findElement(by);
	                    return element.isDisplayed();
	                }
	            });


	        } catch (TimeoutException e) {
	        Assert.fail("超时L !! " + timeOut + " 秒之后还没找到元素 [" + by + "]", e);
	        }
	    }			
	 
	 
	 /**
	  * 线程等待方法
	  * @param time
	  */
	  public void appWait(long time)
	  {
	      try {
	          Thread.sleep(time);
	      } catch (InterruptedException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	      }
	  }
	 
	  /**
	     * 逐字删除编辑框中的文字
	     * 碰到问题：无论如何都删除不了密码输入框的内容
	     * 解决方法：不管有没有内容都 都在密码框进行回退按键删除
	     * @param element
	     * 文本框架控件
	     *            
	     */
	 public static final int KEYCODE_A=29;
	 public static final int BACKSPACE=8;
	 public static final int META_CTRL_MASK=17;
	 public void clearText(WebElement element) {
	        String text = element.getText();
	        System.out.println(
	                "text length is:" + text.length() + ",text is:" + text);
	        // 跳到最后 新版中sendKeyEvent()已经被删除，用pressKeyCode()取代
	        // driver.pressKeyCode(KEYCODE_MOVE_END);定位到文本输入框最后
	        driver.pressKeyCode(KEYCODE_A, META_CTRL_MASK);//文本内容全选
	        int size = text.length() == 0 ? 50 : text.length();
	        for (int i = 0; i < size; i++) {
	            // 循环后退删除
	            driver.pressKeyCode(BACKSPACE);//删除
	        }

	    }
}
