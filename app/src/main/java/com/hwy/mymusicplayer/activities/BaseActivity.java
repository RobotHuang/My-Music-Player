package com.hwy.mymusicplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwy.mymusicplayer.R;

public class BaseActivity extends Activity {

    private ImageView ivBack, ivMe;

    private TextView tvTitle;

    /**
     * 初始化NavigationBar
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {
        ivBack = findViewById(R.id.iv_back);
        ivMe = findViewById(R.id.iv_me);
        tvTitle = findViewById(R.id.tv_title);

        ivBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        tvTitle.setText(title);
        ivMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, MeActivity.class));
            }
        });
    }
}
