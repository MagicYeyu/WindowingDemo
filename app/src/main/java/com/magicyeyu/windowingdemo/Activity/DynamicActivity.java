package com.magicyeyu.windowingdemo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.magicyeyu.windowingdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DynamicActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.exitWindowsBt)
    Button exitWindowsBt;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        ButterKnife.bind(this);
        context = this;

        init();
    }

    private void init() {
        Glide.with(context).load("https://b-ssl.duitang.com/uploads/item/201109/18/20110918212819_KmxMa.thumb.1900_0.jpg").into(imageView);
    }

    @OnClick({R.id.imageView, R.id.exitWindowsBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                break;
            case R.id.exitWindowsBt:
                finish();
                break;
        }
    }
}
