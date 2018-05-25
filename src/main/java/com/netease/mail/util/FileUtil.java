package com.netease.mail.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.log4testng.Logger;

import com.netease.mail.bean.UserInfo;

/**
 * 用于定义要添加的邮箱帐号
 * @author hzmayanli
 *
 */
public class FileUtil{
	private static String filepath=System.getProperty("user.dir")+"/assets/";
	private static  Logger logger= Logger.getLogger(FileUtil.class);
    /**
     * 从文件中读取用户帐号
     * @return
     */
    public static List<UserInfo> getAccount(String fileName){
    	List<UserInfo> users=null;
    	BufferedReader br=null;
    	InputStreamReader isr=null;
    	try {
    		File file = new File(filepath,fileName);
    		System.out.println("path="+file.getAbsolutePath());
    	    InputStream inStream = new FileInputStream(file.getAbsolutePath());
            isr = new InputStreamReader(inStream,"UTF-8");
            br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder("");
            while((line = br.readLine()) != null){
            	sb.append(line);
            }
            br.close();
            isr.close();
            JSONObject testjson = new JSONObject(sb.toString());//builder读取了JSON中的数据。
            JSONArray array = testjson.getJSONArray("emails");         //从JSONObject中取出数组对象
            //把解析出的对象存入数组中
            users=new ArrayList<UserInfo>();
            UserInfo user;
            for (int i = 0; i < array.length(); i++) {
                JSONObject role = array.getJSONObject(i);    //取出数组中的对象
                user=new UserInfo();
                if(null!=role || !"".equals(role)){
                   user.setEmail(role.getString("userName"));  //取出数组中对象的各个值
                   user.setPwd(role.getString("pwd"));   
                   users.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("====FileUtil====="+e.getMessage());
        }
    	return users;
    }
    
}

