package com.learningplatfrom.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningplatfrom.cons.RegisterProcess;

public class RegisterManagerServlet extends HttpServlet {
	public Vector<RegisterProcess> registerManager = new Vector<RegisterProcess>(
			100, 10);

	public RegisterManagerServlet() {
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
		String clientID = request.getRemoteAddr(); // get the ip of the client;
		int index = HasTheClientServlet(clientID); // 根据当前客户端的ip地址获得id
		RegisterProcess newProcess = new RegisterProcess();
		newProcess.SetClientID(clientID);
		if (index == -1) { // 没有当前客户的Servlet
			registerManager.add(newProcess);
			newProcess.SetProcessID(registerManager.size());
		} else { // 当前管理Vector中有客户的Servelt服务对象
			RegisterProcess oldRegProcess = registerManager.get(index);
			int curRequestCount = oldRegProcess.GetRequestCount();
			oldRegProcess.PrepareQuit(); // 旧的Servlet准备销毁
			registerManager.remove(index); // 从Vetor管理中移除
			registerManager.add(newProcess); // 添加新的servlet
			newProcess.SetProcessID(registerManager.size());
			newProcess.SetRequestCount(curRequestCount + 1);
		}
		newProcess.ServerClient(request, response); // 响应客户端
	}

	public void init() throws ServletException {

	}

	public int HasTheClientServlet(String clientIP) {
		for (int i = 0; i < registerManager.size(); i++) {
			if (registerManager.get(i).GetClientID().equals(clientIP)) {
				return i;
			}
		}
		return -1;
	}
}
