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
		//΢�ŷ�����GET����Я�����ĸ�����
		//signature��΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce����
		String signature = req.getParameter("signature");//ע���������˫���ţ�����
		//timestamp��ʱ���
		String timestamp = req.getParameter("timestamp");
		//nonce�������
		String nonce = req.getParameter("nonce");
		//echostr������ַ���
		String echostr = req.getParameter("echostr");
		
		//����CheckUtil���checkSignature����У�飬���򷵻�echostrֵ
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
				text.setContent("�װ���"+fromUserName+"���ã�"+"�����͵���Ϣ�ǣ�"+content);
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
