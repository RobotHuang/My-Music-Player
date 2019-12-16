package com.hwy.mymusicplayer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hwy.mymusicplayer.R;

public class InputView extends FrameLayout {

    private int inputIcon;

    private String inputHint;

    private boolean isPassword;

    private ImageView ivIcon;

    private View view;

    private EditText etInput;

    public InputView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化布局
     * @param context
     * @param attrs
     */
    public void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.inputView);
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.user);
        isPassword = typedArray.getBoolean(R.styleable.inputView_is_password, false);
        typedArray.recycle();

        //绑定Layout布局
        view = LayoutInflater.from(context).inflate(R.layout.input_view, this, false);
        ivIcon = view.findViewById(R.id.iv_icon);
        etInput = view.findViewById(R.id.et_input);

        //布局关联属性
        ivIcon.setImageResource(inputIcon);
        etInput.setHint(inputHint);
        etInput.setInputType(isPassword ? InputType.TYPE_CLASS_TEXT|InputType.
                TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);

        addView(view);
    }

    /**
     * 返回输入的内容
     * @return
     */
    public String getInputStr() {
        return etInput.getText().toString().trim();
    }

    /**
     * 添加输入内容
     * @param str
     */
    public void setInputStr(String str) {
        etInput.setText(str);
    }
}
