package com.tescomm.customview.photohelper;

import android.os.Environment;

import java.io.File;

/**
 * Copyright  2018 北京泰合佳通信息技术有限公司. All rights reserved.
 * <p>
 * <文件内容描述>
 *
 * @author [Administrator]
 * @date [创建日期，2018/08/21]
 * @Version version: 1.0
 */

public class Constants {
    //图片文件名称
    public static final String PHOTO_FILE_NAME = "temp_photo.png";
    public static File tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis()+".png");
    //拍照选取请求码
    public static final int PHOTO_REQUEST_CAREMA = 1;//拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;//相册选取
    public static final int PHOTO_REQUEST_CUT = 3;//裁剪
    public static final int PHOTO_REQUEST_GALLARY_MORE = 4;//相册选取（多选）
    public static final int PHOTO_RESPONS_GALLARY_MORE = 5;//相册选取（多选）
    //视频录制请求码
    public static final int REQUEST_RECODERVIDEO = 6;

    //验证身份信息请求码
    public static final int REQUEST_CONFIRMMSG = 100;
}
