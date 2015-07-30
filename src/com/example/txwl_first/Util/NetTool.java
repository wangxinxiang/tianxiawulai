package com.example.txwl_first.Util;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 104468 on 2015/2/11.
 */
public class NetTool {

    //以流方式向服务器端发送xml文件数据，并获得服务器端输出流
    public static InputStream sendXMLData(String urlPath,byte[] data,String encoding) throws Exception{
        URL url = new URL(urlPath);
        //打开连接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置提交方式
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        //post方式不能使用缓存
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        //设置连接超时时间
        conn.setConnectTimeout(6*1000);
        //配置本次连接的Content-Type
        conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
        //维持长连接
        conn.setRequestProperty("Connection", "Keep-Alive");
        //设置浏览器编码
        conn.setRequestProperty("Charset", "UTF-8");
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        //将请求参数数据向服务器端发送
        dos.write(data);
        dos.flush();
        dos.close();
        if(conn.getResponseCode() == 200){
            //获得服务器端输出流
            return conn.getInputStream();
        }
        return null;
    }

    //通过输入流获得字节数组
    public static byte[] readStream(InputStream is) throws Exception {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        while((len=is.read(buffer)) != -1){
            bos.write(buffer, 0, len);
        }
        is.close();
        return bos.toByteArray();
    }

    //读取XML文件获取时间戳
    public static String[] readXML(InputStream is) {
        String[] data = new String[2];
        //获取pull解析器
        XmlPullParser xp = Xml.newPullParser();
        try {
            xp.setInput(is, "UTF-8");

            //获取当前节点的事件类型
            int type = xp.getEventType();
            while(type != XmlPullParser.END_DOCUMENT){
                switch (type) {
                    case XmlPullParser.START_TAG:
                        //				获取当前节点的名字

                        if("is_success".equals(xp.getName())){
                            data[0] = xp.nextText();
                        }

                        if("error".equals(xp.getName())){
                            data[1] = xp.nextText();
                        }

                        if("encrypt_key".equals(xp.getName())){
                            data[1] = xp.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                //把指针移动到下一个节点，并且返回该节点的事件类型
                type = xp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  data;
    }
}
