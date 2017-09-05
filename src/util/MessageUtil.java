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
 * ������Ϣ�ĸ�ʽת����Ϊ�˷��㴦����Ҫ��΢�Ŵ�����xmlת���ɼ��ϣ���������Ϣ��Ҫ������ת��Ϊxml
 */
public class MessageUtil {
	//Ϊ�����������͵���Ϣ��Ӿ�̬��Ա����
	public static final String message_text="text";//�ı���Ϣ
//	public static final String message_image="image";//ͼƬ��Ϣ
//	public static final String message_voice="voice";//������Ϣ
//	public static final String message_video="video";//��Ƶ��Ϣ
//	public static final String message_link="link";//������Ϣ
//	public static final String message_location="location";//����λ����Ϣ
//	public static final String message_event="event";//�¼�����
//	public static final String message_subscribe="subscribe";//��ע�¼�
//	public static final String message_unsubscribe="unsubscribe";//ȡ����עʱ��
//	public static final String message_click="click";//�˵�����¼�
//	public static final String message_view="view";//�˵�����¼���
	
	
	//xmlתΪmap���ϣ���Ҫdom4j�ܰ�
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		//
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();//��Ҫdom4j�ܰ�
		//��request�л�ȡ������
		InputStream ins = req.getInputStream();//�׳�IOException�쳣
		Document doc = reader.read(ins);//�׳�DocumentException�쳣
		//��ȡ���ڵ�Ԫ��
		Element root = doc.getRootElement();
		//
		List<Element> list = root.elements();
		for(Element e:list){
			map.put(e.getName(),e.getText());
		}
		ins.close();
		return map;
	}
	
	//������תΪxml����Ҫxstream�ܰ�
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		//�����Ϊxml�����򷵻ص���com.package.textMessage����ʽ��΢��Ҫ�����xml��ʽ���ı�
		xstream.alias("xml",textMessage.getClass());//alias���
		return xstream.toXML(textMessage);
	}
}
