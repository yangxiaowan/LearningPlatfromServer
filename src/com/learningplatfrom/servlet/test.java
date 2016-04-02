package com.learningplatfrom.servlet;

import java.sql.Connection;
import java.sql.SQLException;

import com.learningplatfrom.db.BaseDatabaseUtils;

public class test {

	public static void main(String[] args) throws SQLException {
		 BaseDatabaseUtils databaseUtils = new BaseDatabaseUtils();
		 Connection connection = databaseUtils.getDatabaseConnection();
		 
		 if(databaseUtils.isRightNote("user", "mPassword", "mAccount", "yangwanwan", "yangwan23")){
			 System.out.println("YES");
		 }else{
			 System.out.println("No");
		 }
		
	}
	
}
class student{
	int age;
	String name;
	public student(int age,String name){
		this.age = age;
		this.name = name;
	}
}