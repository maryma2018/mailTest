package com.netease.mail.page.control;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.netease.mail.config.ActivityName;
import com.netease.mail.page.object.LoginPage;
/**
 * 登录页面控件的操作
 * @author maryma
 *
 */
public class LoginControl extends LoginPage {

	public LoginControl(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}
	//登录
    public void userLogin(String name,String pwd){
       this.username.sendKeys("");
       this.username.sendKeys(name);
       this.password.sendKeys("");
       this.password.sendKeys(pwd);
       loginBtn.click();
    } 
    //清空密码输入框
    public void   cleanPwd(){
   	 pwd_clearBtn.click(); 
    }
    //清空帐号输入框
    public void cleanAcct(){
   	 acct_clearBtn.click(); 
    }
      
    //密码错误
    public boolean pwdError(){
    try{
    	if(null!=errorMsgTxt){
    		confirmBtn.click();
    	}
    	}catch(NoSuchElementException ne){
    		System.out.println(ne.getMessage());
    		return false;
    	}
    	return true;
    }
    //帐号已存在,点取消按钮，关闭弹窗
    public boolean hadAddAcct(){
      try{	
    	  cancelBtn.click();
      }catch(NoSuchElementException ne){
  		System.out.println("该帐号已存在=="+ne.getMessage());
  		return false;
  	}
    	return true;
    }
    
    //进入邮箱按钮点击事件
    public boolean enterMailClick(){
    	try{
    	 enterButn.click(); 
    	}catch(NoSuchElementException ne){
      		System.out.println(ne.getMessage());
      		return false;
      	}
    	return true;
       }
    
    
    //跳过按钮点击事件
    public void jumpButnClick(){
    	try{
    	  jumpButn.click(); 
    	}catch(NoSuchElementException ne){
      		System.out.println(ne.getMessage());
      	}
       }
    //下一步按钮点击事件
    public void nextButnClick(){
      try{
    	nextButn.click();
      }catch(NoSuchElementException ne){
    		System.out.println(ne.getMessage());
    	}
       }
    
    public void cancelButnClick(){
    	try{
    		cancelButn.click(); 
    	}catch(NoSuchElementException ne){
      		System.out.println(ne.getMessage());
      	}
       }
    
    /**
     * 登录成功后的操作
     * 1、关联帐号引导页
     * 2、新功能引导页
     * 3、新邮件提醒授权弹窗
     * 4、设置头像引导页    
     * 
     **/
    public boolean afterLoginSuccess(){
      try{
    	 nextButn.click();
    	 enterButn.click();
    	 jumpButn.click();
    	if(null!=remindTitle){
    		 cancelButn.click();
    	 }
      }catch(NoSuchElementException ne){
  		System.out.println(ne.getMessage());
  		return false;
  	}
      return true;
    }
    
  		
    
}
