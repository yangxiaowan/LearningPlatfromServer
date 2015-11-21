package com.learningplatfrom.servlet;

import java.sql.Connection;

import com.learningplatfrom.db.BaseDatabaseUtils;

public class test {

	public static void main(String[] args) {
		 BaseDatabaseUtils databaseUtils = new BaseDatabaseUtils();
		 Connection connection = databaseUtils.getDatabaseConnection();
		 boolean isExsit = databaseUtils. isExsitNote("user","yangwan");
		 if(isExsit){
			 System.out.print("存在该记录");
		 }else{
			 System.out.println("不存在该记录");
		 }
	}

}
