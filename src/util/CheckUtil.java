package util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author QXY
 *������ͨ������signature���������У�飨������У�鷽ʽ������ȷ�ϴ˴�GET��������΢�ŷ�������
 *��ԭ������echostr�������ݣ��������Ч����Ϊ�����߳ɹ����������ʧ�ܡ�����/У���������£�
 *1����token��timestamp��nonce�������������ֵ�������
 *2�������������ַ���ƴ�ӳ�һ���ַ�������sha1����
 *3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
 */
public class CheckUtil {
	//�������Լ�дtoken����
	private static final String token="qxy";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//����
		Arrays.sort(arr);
		
		//ƴ��
		StringBuffer content = new StringBuffer();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		//�����ַ�����sha1����
		String temp = getSha1(content.toString());
		
		return temp.equals(signature);
	}
	
	//sha1���ܣ��Լ�����������
	public static String getSha1(String str){
	    if(str==null||str.length()==0){
	        return null;
	    }
	    //ʮ������
	    char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
	            'a','b','c','d','e','f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char buf[] = new char[j*2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];      
	        }
	        return new String(buf);
	    } catch (Exception e) {
	        // TODO: handle exception
	        return null;
	    }
	}
}
