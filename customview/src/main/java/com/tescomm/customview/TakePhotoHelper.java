package com.tescomm.customview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zwl on 2017/11/17 0017.
 * 调用相机拍照工具类
 */
public class TakePhotoHelper {
    private Activity context;
    private static final int OPENCAMAREREQUEST = 2;
    String permissionCama = "android.permission.CAMERA";
    String permissionWrite = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    Uri imageUriFromCamera;
    Uri cropImageUri;
    File corpFile;
    public final String USER_CROP_IMAGE_NAME = "temporary.png";

    public TakePhotoHelper(Activity context) {
        this.context = context;
    }

    public void start() {
        //弹出窗口让用户选择
        final ImgChoDialog dialog = new ImgChoDialog(context);
        dialog.show();
        dialog.setChoImgListener(new ChoImgListener() {
            @Override
            public void onCancelClick() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onTakePhotoClick() {
                requestPermissionsAndTakePhoto();
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onGallaryClick() {
                pickerPhoto();
                //pickerMorePhoto();
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onRecoderClick() {
                recoderVideo();
                dialog.dismiss();
            }
        });
    }


    /**
     * 跳转至视频录制界面
     */
    private void recoderVideo() {
//        Intent intent=new Intent(context, VideoRecoderActivity.class);
//        context.startActivity(intent);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, permissionCama);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionCama}, OPENCAMAREREQUEST);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                context.startActivityForResult(intent, Constants.REQUEST_RECODERVIDEO);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            context.startActivityForResult(intent, Constants.REQUEST_RECODERVIDEO);
        }
    }


    /**
     * android 6.0 需要用户进行手动授权
     */
    public void requestPermissionsAndTakePhoto() {

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, permissionCama);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionCama}, OPENCAMAREREQUEST);
                takePhoto();
            } else {
                takePhoto();
            }
        } else {
            takePhoto();
        }
    }

    /**
     * 拍照选择图片
     */
    public void takePhoto() {
        Constants.tempFile= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis()+".png");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUriFromCamera = FileProvider.getUriForFile(context,
                        "com.tescomm.smarttown.photoprovider", Constants.tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
            } else {
                imageUriFromCamera = Uri.fromFile( Constants.tempFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
            context.startActivityForResult(intent, Constants.PHOTO_REQUEST_CAREMA);
        } else {
            Log.e("tag", "未找到存储卡");
        }

    }

    public File getResultFile() {
        return Constants.tempFile;
    }


    /**
     * 从相册获取图片 带裁剪
     */
    public void pickerPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivityForResult(intent, Constants.PHOTO_REQUEST_GALLERY);
    }

    /**
     * 从图库多选
     */
    public void pickerMorePhoto() {
//        Intent intent = new Intent(context, GallaryPhotoActivity.class);
//        context.startActivityForResult(intent, Constants.PHOTO_REQUEST_GALLARY_MORE);
    }


    /**
     * 裁剪图片
     *
     * @param
     */
    public void crop() {
        if (imageUriFromCamera != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            corpFile = new File(context.getExternalCacheDir(), USER_CROP_IMAGE_NAME);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //高版本一定要加上这两句话，做一下临时的Uri
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                FileProvider.getUriForFile(context, "com.tescomm.smarttown.photoprovider", corpFile);
            }
            cropImageUri = Uri.fromFile(corpFile);
            intent.setDataAndType(imageUriFromCamera, "image/*");
            intent.putExtra("crop", "true");
            if (Build.MANUFACTURER.contains("HUAWEI")) {
                intent.putExtra("aspectX", 9999);
                intent.putExtra("aspectY", 9998);
            } else {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            }
//            intent.putExtra("outputX", Integer.MAX_VALUE);
//            intent.putExtra("outputY", Integer.MAX_VALUE);
            intent.putExtra("outputFormat", "JPEG");
            intent.putExtra("noFaceDetection", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
            context.startActivityForResult(intent, Constants.PHOTO_REQUEST_CUT);
        }
    }


    public void resetUri() {
        imageUriFromCamera = null;
    }


    public void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        corpFile = new File(context.getExternalCacheDir(), USER_CROP_IMAGE_NAME);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //高版本一定要加上这两句话，做一下临时的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            FileProvider.getUriForFile(context, "com.tescomm.smarttown.photoprovider", corpFile);
        }
        cropImageUri = Uri.fromFile(corpFile);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        if (Build.MANUFACTURER.contains("HUAWEI")) {
            intent.putExtra("aspectX", 9999);
            intent.putExtra("aspectY", 9998);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        context.startActivityForResult(intent, Constants.PHOTO_REQUEST_CUT);
    }


    public Bitmap getBitmap(int w, int h) {
        final String s = context.getExternalCacheDir() + "/" + USER_CROP_IMAGE_NAME;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(s, opts);
        if (bitmap != null) {
            return bitmap;
        } else {
            return BitmapFactory.decodeFile(new File(context.getExternalCacheDir(), USER_CROP_IMAGE_NAME).getPath());
        }

    }

    /**
     * 保存图片到sd卡
     * 返回图片保存路径
     */
    public String savaFile(Bitmap bm) {
        try {
            File path = Environment.getExternalStorageDirectory();
            if (!path.exists()) {
                path.mkdir();
            }
            File myCaptureFile = new File(path, System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = null;
            fos = new FileOutputStream(myCaptureFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return myCaptureFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath 视频路径
     * @return
     */
    public Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }
}
