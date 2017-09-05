package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import po.TextMessage;

/**
 * @author QXY
 * 进行消息的格式转换，为了方便处理，需要将微信传来的xml转换成集合，而发送消息需要将对象转换为xml
 */
public class MessageUtil {
	//为后续各种类型的消息添加静态成员变量
	public static final String message_text="text";//文本消息
//	public static final String message_image="image";//图片消息
//	public static final String message_voice="voice";//语音消息
//	public static final String message_video="video";//视频消息
//	public static final String message_link="link";//链接消息
//	public static final String message_location="location";//地理位置消息
//	public static final String message_event="event";//事件推送
//	public static final String message_subscribe="subscribe";//关注事件
//	public static final String message_unsubscribe="unsubscribe";//取消关注时间
//	public static final String message_click="click";//菜单点击事件
//	public static final String message_view="view";//菜单点击事件？
	
	
	//xml转为map集合，需要dom4j架包
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		//
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();//需要dom4j架包
		//从request中获取输入流
		InputStream ins = req.getInputStream();//抛出IOException异常
		Document doc = reader.read(ins);//抛出DocumentException异常
		//获取根节点元素
		Element root = doc.getRootElement();
		//
		List<Element> list = root.elements();
		for(Element e:list){
			map.put(e.getName(),e.getText());
		}
		ins.close();
		return map;
	}
	
	//将对象转为xml，需要xstream架包
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		//起别名为xml，否则返回的是com.package.textMessage的形式，微信要求的是xml格式的文本
		xstream.alias("xml",textMessage.getClass());//alias别号
		return xstream.toXML(textMessage);
	}
}
