package com.learningplatfrom.cons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningplatfrom.servlet.RegisterServlet;

public class RegisterProcess {
	private RegisterServlet registerServlet; // 当前注册服务Servlet
	private int requestCount; // 客户当前请求次数，防止恶意攻击
	private int processID; // 当前服务Servlet的id
	private String clientID; // 当前请求服务客户的ip

	public RegisterProcess() {
		processID = 0;
		clientID = "";
		requestCount = 0;
	}

	public void SetProcessID(int processID) {
		this.processID = processID;
	}

	public void SetRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public void SetClientID(String clientID) {
		this.clientID = clientID;
	}

	public int GetProcessID() {
		return this.processID;
	}

	public String GetClientID() {
		return this.clientID;
	}

	public RegisterServlet GetRegisterServlet() {
		return this.registerServlet;
	}

	public int GetRequestCount() {
		return this.requestCount;
	}

	public void ServerClient(HttpServletRequest request,
			HttpServletResponse response) {
		registerServlet = new RegisterServlet();
		try {
			registerServlet.init();
			registerServlet.doPost(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PrepareQuit() {
		registerServlet.destroy();
	}
}
