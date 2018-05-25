package com.netease.mail.page.object;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
/**
 * 基类，所有pageobject都继承它
 * @author maryma
 *
 */
public class BasePage {
	public WebDriver driver;	

	// 超时时间
	private final int TIMEOUT = 5;

	public BasePage(WebDriver dr) {
		this.driver = dr;
		//PageFactory.initElements(dr, this);
		try{
		//通过initElements方法初始化的各个页面对象，加入了初始化元素时等待时间TIMEOUT
		PageFactory.initElements(new AjaxElementLocatorFactory(dr, TIMEOUT),
				this);
		}catch(TimeoutException te){
			Assert.fail("超时 !! " + TIMEOUT + " 秒之后还没找到元素", te);
		}
	}

	/**
	 * AjaxElementLocatorFactory方法查找元素时在指定的TIMEOUT时间内不断重试，
	 * 如果在指定时间内定位到元素则马上继续，如果指定时间内未找到则抛出NoSuchElementException异常。
	 * @param dr
	 * @param title
	 */
//	public BasePage(WebDriver dr, final String title) {
//		this.driver = dr;
//
//		// 如果不进行判断，
//		WebDriverWait wait = new WebDriverWait(dr, TIMEOUT);
//		try {
//			boolean flag = wait.until(new ExpectedCondition<Boolean>() {
//				@Override
//				public Boolean apply(WebDriver arg0) {
//					// TODO Auto-generated method stub
//					String acttitle = arg0.getTitle();
//					System.out.println("acttitle==="+acttitle);
//					return acttitle.equals(title);
//				}
//			});
//		} catch (TimeoutException te) {
//			throw new IllegalStateException("当前不是预期页面，当前页面title是："
//					+ dr.getTitle());
//		}
//
////		PageFactory.initElements(new AjaxElementLocatorFactory(dr, TIMEOUT),
////				this);
//
//	}

}
