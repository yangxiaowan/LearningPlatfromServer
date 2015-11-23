package com.learningplatfrom.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningplatfrom.db.BaseDatabaseUtils;
public class RegisterServlet extends HttpServlet {
	private Connection connection = null;
	private BaseDatabaseUtils registerInfoDataBaseUtils = null;
	public static final int REGISTER_PASSED = 1;
	public static final int REGISTER_FAILED = 0;
	public static final int REGISTER_SUCCESSFULLY = 2;
	public static final int USER_ACCOUNT_EXSIT = 3;
	public static final int USER_EMAIL_EXSIT = 4;
	private int mRegisterResult = 0;  //默认注册失败
	public RegisterServlet() {
		super();
	}
	public void destroy() {
		super.destroy();  
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("registerInfoDataBaseUtils关闭connection出现异常");
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getHeader("user_name");
		String userPassword = request.getHeader("user_password");
		String userEmail = request.getHeader("user_email");
		mRegisterResult = REGISTER_PASSED; //改账号可以被注册了。
		if(registerInfoDataBaseUtils.isExsitNote("user","mAccount",userName)){
			mRegisterResult = USER_ACCOUNT_EXSIT;//该注册账号已经存在
		}
		if(registerInfoDataBaseUtils.isExsitNote("user","mEmail",userEmail)){
			mRegisterResult = USER_EMAIL_EXSIT;//该邮箱已经被注册了
		}
		if(mRegisterResult == REGISTER_PASSED){
			//接下来要存入注册信息到数据库了；
			String addRegisterInfoStr = "INSERT INTO user (mAccount,mPassword,mEmail)VALUES('"+userName+"','"+userPassword+"','"+userEmail+"')";
			//插入数据库SQL语句
			System.out.println(addRegisterInfoStr);
			if(registerInfoDataBaseUtils.UpDateDatabase(addRegisterInfoStr)){
				mRegisterResult = REGISTER_SUCCESSFULLY;  //用户注册成功
			}else{
				mRegisterResult = REGISTER_FAILED;  //服务器端原因，用户无法注册
			}
		}
		OutputStream os = response.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeInt(mRegisterResult);
		dos.flush();
		dos.close();
		os.close();
		System.out.println(mRegisterResult);
		System.out.println(userName);
		System.out.println(userPassword);
		System.out.println(userEmail);
	}
	public void init() throws ServletException {
		registerInfoDataBaseUtils = new BaseDatabaseUtils();
		connection = registerInfoDataBaseUtils.getDatabaseConnection();
	}
}
