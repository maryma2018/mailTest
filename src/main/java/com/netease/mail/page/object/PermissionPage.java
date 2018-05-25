package com.netease.mail.page.object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 授权弹窗
 * @author maryma
 *
 */
public class PermissionPage extends BasePage{

	public PermissionPage(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}
	@FindBy(name="允许")
	 private WebElement allowBtn;
	
	@FindBy(name="知道了")
	private WebElement knownBtn;
	
	
	public void allowClick(){
		  this.allowBtn.click();
			
	}
	public void knownClick(){
		  this.knownBtn.click();
	}
	
	
}
