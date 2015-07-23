package com.example.txwl_first;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.UpLoadImgBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.*;
import java.net.URI;
import java.net.URL;

/**
 * Created by wang on 2015/4/6.
 */
public class PhotoActivity extends Activity{
    private TextView takePhoto,photoAlbum,cancel;
    private int from;
    private static String path= ImageUtils.path;     //sd路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        Intent intent1 = getIntent();
        from = intent1.getIntExtra("from", -1);
        init();
        initListener();

    }

    private void init(){
        takePhoto = (TextView)this.findViewById(R.id.tab3_take_pictures);
        photoAlbum = (TextView)this.findViewById(R.id.tab3_photo_album);
        cancel = (TextView)this.findViewById(R.id.tab3_photo_cancel);

    }

    private void initListener(){
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "img.jpg")));
                intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intent2, 2);//采用ForResult打开
            }
        });
        photoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1, 1);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    ContentResolver resolver = getContentResolver();
                    try{
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, data.getData());
                        String imgKey = TimeUtil.getFileKeyByNowDate();
                        setPicToView(photo, imgKey);
                        upLoadImg(imgKey);
                    } catch (Exception e){
                        // 保存不成功时捕获异常
                        e.printStackTrace();
                    }
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), "img.jpg").getAbsolutePath());
                    String imgKey = TimeUtil.getFileKeyByNowDate();
                    setPicToView(photo, imgKey);
                    upLoadImg(imgKey);
                }

                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Bitmap mBitmap, String imgKey) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path +imgKey + "img.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    //上传头像
    private void upLoadImg(String imgKey) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.UPLOADURL;     //改用post请求数据
        Log.d("upLoadImg_url -->" ,url);

        File file = new File(path + imgKey + "img.jpg");


//        Bitmap head = BitmapFactory.decodeFile(path + "head.jpg");

        if (file.exists() && file.length() > 0) {
            RequestParams params = new RequestParams();
            try {
                params.put("img", file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("upLoadImg -->" , new String(bytes));
                    try {
                        UpLoadImgBean upLoadImgBean = new GsonBuilder().create().fromJson(new String(bytes), UpLoadImgBean.class);

                        if (upLoadImgBean.getStatus().equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("imgUrl", upLoadImgBean.getImgurl());
                            intent.putExtra("from", from);
                            intent.putExtra("img", file);
                            setResult(Constant.GETPHOTO, intent);
                            finish();
                        } else {
                            TXWLApplication.getInstance().showTextToast("获取图片失败");
                        }
                    }catch (Exception e) {
                        TXWLApplication.getInstance().showErrorConnected(e);
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    TXWLApplication.getInstance().showTextToast("网络错误");
                }
            });

        }
    }
}
