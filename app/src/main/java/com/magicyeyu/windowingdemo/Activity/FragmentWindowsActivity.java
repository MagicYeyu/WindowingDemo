package com.magicyeyu.windowingdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.magicyeyu.windowingdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentWindowsActivity extends AppCompatActivity {

    @BindView(R.id.exitWindowsBt)
    Button exitWindowsBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_windows);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.exitWindowsBt)
    public void onViewClicked() {
    }
}
