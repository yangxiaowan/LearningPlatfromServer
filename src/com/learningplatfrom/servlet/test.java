package com.learningplatfrom.servlet;

import java.sql.Connection;

import com.learningplatfrom.db.BaseDatabaseUtils;

public class test {

	public static void main(String[] args) {
		 BaseDatabaseUtils databaseUtils = new BaseDatabaseUtils();
		 Connection connection = databaseUtils.getDatabaseConnection();
		 boolean isExsit = databaseUtils. isExsitNote("user","mAccount","yangwan");
		 if(isExsit){
			 System.out.print("存在该记录");
		 }else{
			 System.out.println("不存在该记录");
		 }
		 String insertStr = "Insert into user (mAccount,mPassword,mEmail)Values('yangyangyang','password','123@qq')";
		 if(databaseUtils.UpDateDatabase(insertStr)){
			 System.out.println("注册成功");
		 }else{
			 System.out.println("注册失败");
		 }
	}

}
