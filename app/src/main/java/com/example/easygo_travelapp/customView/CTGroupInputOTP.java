package com.example.easygo_travelapp.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.easygo_travelapp.R;

public class CTGroupInputOTP extends LinearLayout {
    CTInputOTP ctiCode1, ctiCode2, ctiCode3, ctiCode4, ctiCode5, ctiCode6;
    Context context;

    public CTGroupInputOTP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initComponent();
        initAction();
    }

    public void initAction() {
        ctiCode1.updateBgEdtCode(ctiCode1, ctiCode2);
        ctiCode2.updateBgEdtCode(ctiCode1, ctiCode3);
        ctiCode3.updateBgEdtCode(ctiCode2, ctiCode4);
        ctiCode4.updateBgEdtCode(ctiCode3, ctiCode5);
        ctiCode5.updateBgEdtCode(ctiCode4, ctiCode6);
        ctiCode6.updateBgEdtCode(ctiCode5, null);
    }

    private void initComponent() {
        View view = LayoutInflater.from(context).inflate(R.layout.ctv_group_input_otp, this);
        ctiCode1 = findViewById(R.id.ctiCode1);
        ctiCode2 = findViewById(R.id.ctiCode2);
        ctiCode3 = findViewById(R.id.ctiCode3);
        ctiCode4 = findViewById(R.id.ctiCode4);
        ctiCode5 = findViewById(R.id.ctiCode5);
        ctiCode6 = findViewById(R.id.ctiCode6);
    }

    public String getResultInput() {
        String code1 = ctiCode1.getValue();
        String code2 = ctiCode2.getValue();
        String code3 = ctiCode3.getValue();
        String code4 = ctiCode4.getValue();
        String code5 = ctiCode5.getValue();
        String code6 = ctiCode6.getValue();
        String codeInput = code1 + code2 + code3 + code4 + code5 + code6;
        return codeInput;
    }
}
