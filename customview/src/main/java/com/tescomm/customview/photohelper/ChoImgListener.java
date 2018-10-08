package com.tescomm.customview.photohelper;

/**
 * Created by zwl on 2017/5/9 0009.
 */
public interface ChoImgListener {
    //点击取消
    public void onCancelClick();

    //点击拍照
    public void onTakePhotoClick();

    //点击从相册获取
    public void onGallaryClick();

    /**
     * 点击录制视频
     */
    public void onRecoderClick();
}

