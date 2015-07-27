package com.example.txwl_first.beifu;

import android.os.AsyncTask;
import android.util.Log;
import com.example.txwl_first.Util.NetTool;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.google.gson.GsonBuilder;

import java.io.InputStream;

/**
 * Created by 104520 on 2015/2/27.
 */
public class BeiFuHttpPost extends AsyncTask<String, Integer, String> {

    private String token;

    @Override
    protected String  doInBackground(String... params) {
        FastpayBean fastpayBean=new FastpayBean();
        fastpayBean.setService("ebatong_getdyn_fastpay");
        fastpayBean.setPartner(Url.Partner);
        fastpayBean.setSign_type("MD5");
        fastpayBean.setInput_charset("UTF-8");
//            fastpayBean.setPan("6212261208007246059");
        // new  BeiFuHttpPost().execute(Billno,name,idcard,bankid,phone,String.valueOf(cz_money));
        fastpayBean.setPan(params[3]);
        try {
//                fastpayBean.setCardHolderName(URLEncoder.encode("林燕军","UTF-8"));
            fastpayBean.setCardHolderName(params[1]);
//            } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        fastpayBean.setIdType("0");
        fastpayBean.setCardHolderId(params[2]);

        fastpayBean.setPhone(params[4]);
        fastpayBean.setOut_trade_no(params[0]);
        fastpayBean.setAmount(params[5]);
        fastpayBean.setCustomerId(params[6]);
//            fastpayBean.setBankId("CCB");

        //从sdk卡中读取persons.xml文件
        String urlPath = "https://www.ebatong.com/fppay/getDynNum.htm";
        String postJson = fastpayBean.getPostJson();
        Log.i("lyjtest", "getPostJson:" + postJson);
        byte[] data = postJson.getBytes();
        InputStream is = null;
        try {
            is = NetTool.sendXMLData(urlPath, data, "UTF-8");
            data = NetTool.readStream(is);
            Log.i("lyjtest", new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(data);
    }

    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(String result) {
        FastPayReturn   returnBean = new GsonBuilder().create().fromJson( result, FastPayReturn.class);
        Log.d("onPostExecute --> ", result);
        if("00".equals(returnBean.getResponseCode())){
            token = returnBean.getToken();
            }else{
            TXWLApplication.getInstance().showTextToast(returnBean.getResponseTextMessage());
        }
    }

    public String getToken() {
        return token;
    }
}
