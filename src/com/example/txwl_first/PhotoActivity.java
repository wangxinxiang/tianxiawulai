package com.example.txwl_first;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.UpLoadImgBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

import java.io.*;

/**
 * Created by wang on 2015/4/6.
 */
public class PhotoActivity extends Activity{
    private static final String TAG ="PhotoActivity" ;
    private Context mContext;
    private TextView takePhoto,photoAlbum,cancel;
    private int from;
    private static String path= ImageUtils.path;     //sd路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        mContext=this;
        Intent intent1 = getIntent();
        from = intent1.getIntExtra("from", -1);
        init();
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Log.d(TAG, "onPause");

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
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                ContentResolver resolver = getContentResolver();
                try{
//                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, data.getData());

                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inPreferredConfig = Bitmap.Config.RGB_565;
                    opt.inPurgeable = true;
                    opt.inInputShareable = true;
                    InputStream input = resolver.openInputStream(data.getData());
                    Bitmap photo = BitmapFactory.decodeStream(input, null, opt);

                    showPhoto(photo);

                } catch (Exception e){
                    // 保存不成功时捕获异常
                    e.printStackTrace();
                }

                break;
            case 2:
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                Bitmap photo = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), "img.jpg").getAbsolutePath(), opt);

                //预览图片
                showPhoto(photo);

                break;
            default:
                TXWLProgressDialog.Dismiss();
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPhoto(final Bitmap photo) {
        //预览图片
        View imgEntryView =  LayoutInflater.from(PhotoActivity.this).inflate(R.layout.dialog_photo_choice, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(PhotoActivity.this).create();
        ImageView imageView = (ImageView) imgEntryView.findViewById(R.id.large_image);
        imageView.setImageBitmap(photo);
        dialog.setView(imgEntryView);
        dialog.show();
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                TXWLProgressDialog.createDialog(mContext);
                TXWLProgressDialog.setMessage("图片上传中...");
                String imgKey = TimeUtil.getFileKeyByNowDate();
                setPicToView(photo, imgKey);
                upLoadImg(imgKey);
            }
        });

    }

    private void setPicToView(Bitmap mBitmap, String imgKey) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + imgKey + "img.jpg";//图片名字


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 2000 && options > 0) {
            baos.reset();
            options -= 10;
            mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            b = new FileOutputStream(fileName);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                //关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
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
                Log.d("upLoadImg --->", file.toString());
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
                            TXWLProgressDialog.Dismiss();
                            finish();
                        } else {
                            TXWLProgressDialog.Dismiss();
                            TXWLApplication.getInstance().showTextToast("获取图片失败");
                        }
                    }catch (Exception e) {
                        TXWLProgressDialog.Dismiss();
                        TXWLApplication.getInstance().showErrorConnected(e);
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    TXWLProgressDialog.Dismiss();
                    TXWLApplication.getInstance().showTextToast("网络错误");

                }
            });

        }
    }


}
