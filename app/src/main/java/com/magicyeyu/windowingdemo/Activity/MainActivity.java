package com.magicyeyu.windowingdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.magicyeyu.windowingdemo.R;
import com.magicyeyu.windowingdemo.Utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.suspensionWindowsBt)
    Button suspensionWindowsBt;
    @BindView(R.id.dynamicWindowsBt)
    Button dynamicWindowsBt;
    @BindView(R.id.addPermissionBt)
    Button addPermissionBt;
    @BindView(R.id.Cons)
    ConstraintLayout Cons;

    private Context context;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private final int REQUEST_CODE_DUNAMICVIEW = 101;

    private ConstraintSet set;
    private int x;   //移动后 动态控件x坐标
    private int y;   //移动后 动态控件坐标
    private int firstX;//按下时x坐标
    private int firstY;//按下时y坐标
    private int IMAGEID = 1001;//控件ID
    private boolean isRemove = false;

    private ImageView dynamicView;//动态添加的控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        init();

    }

    private void init() {
        set = new ConstraintSet();
        set.clone(Cons);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DUNAMICVIEW) {
            DynamicAddView();
        }

    }

    //动态添加 控件
    private void DynamicAddView() {
        dynamicView = new ImageView(context);
        dynamicView.setId(IMAGEID);
        Glide.with(this).load("https://b-ssl.duitang.com/uploads/item/201109/18/20110918212819_KmxMa.thumb.1900_0.jpg").into(dynamicView);

        dynamicView.setOnClickListener(this);
        dynamicView.setOnTouchListener(this);
        Cons.addView(dynamicView, Cons.getChildCount());

//        set.constrainWidth(imageView.getId(), ConstraintSet.WRAP_CONTENT);
//        set.constrainHeight(imageView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(dynamicView.getId(), 600);   //设置控件宽度
        set.constrainHeight(dynamicView.getId(), ConstraintSet.WRAP_CONTENT);  //控件高度 随着宽度自动变化
        set.connect(dynamicView.getId(), ConstraintSet.TOP, Cons.getId(), ConstraintSet.TOP, 0);//控件的位置
        set.connect(dynamicView.getId(), ConstraintSet.LEFT, Cons.getId(), ConstraintSet.LEFT, 0);

        set.applyTo(Cons);

    }

    @OnClick({R.id.suspensionWindowsBt, R.id.dynamicWindowsBt, R.id.addPermissionBt})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.suspensionWindowsBt:
                if (dynamicView != null) {    //移除动态控件
                    Cons.removeView(dynamicView);
                    dynamicView = null;
                }
                intent = new Intent(MainActivity.this, SuspensionActivity.class);  //悬浮窗
                startActivity(intent);
                break;
            case R.id.dynamicWindowsBt:
                if (dynamicView != null) {//移除动态控件
                    Cons.removeView(dynamicView);
                    dynamicView = null;
                }
                intent = new Intent(MainActivity.this, DynamicActivity.class);  //动态控件
                startActivityForResult(intent, REQUEST_CODE_DUNAMICVIEW);
                break;
            case R.id.addPermissionBt:
                //  浮悬窗的权限必须手动去 设置页面打开
                intent = new Intent(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        if (dynamicView != null) {
            Cons.removeView(dynamicView);
            dynamicView = null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();//移动后 动态控件x坐标
                y = (int) event.getRawY(); //移动后 动态控件坐标
                firstX = (int) event.getRawX();//按下时x坐标
                firstY = (int) event.getRawY();//按下时y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getRawX(); //移动过程中 实时 控件坐标
                int nowY = (int) event.getRawY();
                int movedX = nowX - x;   //移动距离
                int movedY = nowY - y;
                x = nowX;     //移动后的控件坐标
                y = nowY;
                set.connect(v.getId(), ConstraintSet.TOP, Cons.getId(), ConstraintSet.TOP, movedY + v.getTop());
                set.connect(v.getId(), ConstraintSet.LEFT, Cons.getId(), ConstraintSet.LEFT, movedX + v.getLeft());
                set.applyTo(Cons);  //更新控件 位置信息
                break;
            case MotionEvent.ACTION_UP:
                //判断  是点击还是移动,  如果返回false  则不会触发onclick事件
                LogUtils.Loge((firstX - (int) event.getRawX()) + "   " + (firstY - (int) event.getRawY()));
                if (Math.abs(firstX - (int) event.getRawX()) < 5 && Math.abs(firstY - (int) event.getRawY()) < 5)
                    isRemove = false;
                else
                    isRemove = true;
            default:
                break;
        }
        return isRemove;
    }
}
