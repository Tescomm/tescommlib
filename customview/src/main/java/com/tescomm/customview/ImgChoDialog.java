package com.tescomm.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by zwl on 2017/5/9 0009.
 * 图片选择对话框
 */
public class ImgChoDialog extends Dialog implements View.OnClickListener {
    //上下文对象
    private Context context;
    //view
    private View layoutView;
    //拍照layout
    private RelativeLayout rl_cp_take;
    //相册获取layout
    private RelativeLayout rl_cp_pick;
    //录制视频layout
    private RelativeLayout rl_cp_recoder;
    //取消layout
    private RelativeLayout rl_cp_cancel;
    //回调listener;
    private ChoImgListener listener;

    public ImgChoDialog(Context context) {
        super(context, R.style.common_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        layoutView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_choosephoto, null);
        Window win = this.getWindow();
        //设置全屏
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);
        setContentView(layoutView);
        rl_cp_take = (RelativeLayout) layoutView.findViewById(R.id.rl_cp_take);
        rl_cp_pick = (RelativeLayout) layoutView.findViewById(R.id.rl_cp_pick);
        rl_cp_cancel = (RelativeLayout) layoutView.findViewById(R.id.rl_cp_cancel);
        rl_cp_recoder = (RelativeLayout) layoutView.findViewById(R.id.rl_cp_recoder);
        rl_cp_cancel.setOnClickListener(this);
        rl_cp_pick.setOnClickListener(this);
        rl_cp_take.setOnClickListener(this);
        rl_cp_recoder.setOnClickListener(this);
    }
    public void setChoImgListener(ChoImgListener clickListenerInterface) {
        this.listener = clickListenerInterface;
    }

    @Override
    public void onClick(View v) {
        int  id=v.getId();
        if (id == R.id.rl_cp_take) {//拍照
            listener.onTakePhotoClick();

        } else if (id == R.id.rl_cp_pick) {//选择照片
            listener.onGallaryClick();

        } else if (id == R.id.rl_cp_cancel) {//取消
            listener.onCancelClick();

        } else if (id == R.id.rl_cp_recoder) {//录制视频
            listener.onRecoderClick();

        }

    }
}
