package com.tescomm.tescommlib;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tescomm.customview.Constants;
import com.tescomm.customview.TakePhotoHelper;

public class PhoteHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private TakePhotoHelper helper;
    private Button btn;
    private String headerIconPath;
    private ImageView  ivPhoto;
    private String filePath;// 头像路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photohelper_activity);
        helper = new TakePhotoHelper(this);
        btn = (Button) findViewById(R.id.bt_photohelper);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.bt_photohelper){
            helper.start();
        }
    }
    /**
     * 拍照或选择图片所返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                helper.crop(uri);
            }
        } else if (requestCode == Constants.PHOTO_REQUEST_CAREMA && resultCode == RESULT_OK) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                helper.crop();
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.PHOTO_REQUEST_CUT && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap imageBitmap = helper.getBitmap(320, 320);
                headerIconPath = helper.savaFile(imageBitmap);
                String[] strSplit = headerIconPath.split("/");
                String fileName = strSplit[(strSplit.length) - 1];
                Glide.with(getApplicationContext()).load(headerIconPath).into(ivPhoto);
            }
        }
    }
}
