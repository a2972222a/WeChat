package util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author QXY
 *开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，
 *请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
 *1）将token、timestamp、nonce三个参数进行字典序排序
 *2）将三个参数字符串拼接成一个字符串进行sha1加密
 *3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
 */
public class CheckUtil {
	//开发者自己写token令牌
	private static final String token="qxy";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		
		//拼接
		StringBuffer content = new StringBuffer();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		//生成字符串并sha1加密
		String temp = getSha1(content.toString());
		
		return temp.equals(signature);
	}
	
	//sha1加密，自己从网上下载
	public static String getSha1(String str){
	    if(str==null||str.length()==0){
	        return null;
	    }
	    //十六进制
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
