package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import po.TextMessage;
import util.CheckUtil;
import util.MessageUtil;

public class WeixinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doPost(req, resp);
		//微信服务器GET请求携带的四个参数
		//signature：微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String signature = req.getParameter("signature");//注意括号里加双引号！！！
		//timestamp：时间戳
		String timestamp = req.getParameter("timestamp");
		//nonce：随机数
		String nonce = req.getParameter("nonce");
		//echostr：随机字符串
		String echostr = req.getParameter("echostr");
		
		//调用CheckUtil类的checkSignature方法校验，真则返回echostr值
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.println(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			
			String message = null;
			if(MessageUtil.message_text.equals(msgType)){
				TextMessage text = new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setMsgType(MessageUtil.message_text);
				text.setCreateTime(new Date().getTime());
				StringBuffer sb = new StringBuffer();
				text.setContent("亲爱的"+fromUserName+"您好！"+"您发送的消息是："+content);
				message = MessageUtil.textMessageToXml(text);
			}
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			out.close();
		}
		
	}
}
