package com.example.txwl_first.business;

import android.widget.ImageView;
import com.example.txwl_first.R;
import com.example.txwl_first.Util.AnimateFirstDisplayListener;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class LoaderBusiness {
    private static LoaderBusiness loaderBusiness = new LoaderBusiness();
    public ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener;

    private LoaderBusiness(){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(TXWLApplication.getInstance()));
        animateFirstListener = new AnimateFirstDisplayListener();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.defaul_head_image)
                .showImageForEmptyUri(R.drawable.defaul_head_image)
                .showImageOnFail(R.drawable.defaul_head_image)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                        //.displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    public static LoaderBusiness getInstance(){
        return loaderBusiness;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public DisplayImageOptions getOptions() {
        return options;
    }

    public ImageLoadingListener getAnimateFirstListener() {
        return animateFirstListener;
    }

    public static void loadImage(String url , ImageView iv){
        DisplayImageOptions options = getInstance().getOptions();
        ImageLoadingListener animateFirstListener = getInstance().getAnimateFirstListener();
        url = Url.URL + "UserImage/Thumbnail/" + url;
        getInstance().getImageLoader().displayImage(url, iv, options, animateFirstListener);
    }
}
