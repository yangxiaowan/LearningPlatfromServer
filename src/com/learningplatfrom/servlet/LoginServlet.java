package com.learningplatfrom.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningplatfrom.db.BaseDatabaseUtils;

public class LoginServlet extends HttpServlet {
	private BaseDatabaseUtils baseDatabaseUtils;
	private Connection dataBaseConnection = null;
	private int responseCode = 0; // 登陆返回码
	public static int LOGIN_ACCOUNT_NOT_EXIST = 1; // 登陆账号不存在
	public static int LOGIN_PASSWORD_NOT_RIGHT = 2; // 密码不正确
	public static int LOGIN_ABNORMITY = 3; // 登陆异常
	public static int LOGIN_FAILED = 4; // 登陆失败
	public static int LOGIN_SUCCESSFULLY = 5; // 登陆成功

	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginName = request.getHeader("login_name");
		String loginPassword = request.getHeader("login_password");
		System.out.println("login_name: " + loginName + "   login_password:  "+loginPassword);
		if(!baseDatabaseUtils.isExsitNote("user", "mAccount", loginName)){
			responseCode = LOGIN_ACCOUNT_NOT_EXIST;
		}
		
		OutputStream out = response.getOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		dout.writeInt(responseCode);
	}

	public void init() throws ServletException {
		// Put your code here
		baseDatabaseUtils = new BaseDatabaseUtils();
		dataBaseConnection = baseDatabaseUtils.getDatabaseConnection(); // 获得数据的链接
	}

}
