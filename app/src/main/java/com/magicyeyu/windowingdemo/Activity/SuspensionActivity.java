package com.magicyeyu.windowingdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.magicyeyu.windowingdemo.R;
import com.magicyeyu.windowingdemo.Service.WindowService;
import com.magicyeyu.windowingdemo.Utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuspensionActivity extends AppCompatActivity {

    @BindView(R.id.exitWindowsBt)
    Button exitWindowsBt;
    @BindView(R.id.imageView)
    ImageView imageView;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension_windows);
        ButterKnife.bind(this);
        context = this;

        init();
    }

    private void init() {
        Glide.with(context).load("https://b-ssl.duitang.com/uploads/item/201109/18/20110918212819_KmxMa.thumb.1900_0.jpg").into(imageView);
    }

    @OnClick(R.id.exitWindowsBt)
    public void onViewClicked() {
        OpenWindows();
    }

    @Override
    public void onBackPressed() {
        OpenWindows();
    }

    private void OpenWindows() {
        Intent intent = new Intent();
        intent.setClass(this, WindowService.class);
        startService(intent);
        finish();
    }
}
