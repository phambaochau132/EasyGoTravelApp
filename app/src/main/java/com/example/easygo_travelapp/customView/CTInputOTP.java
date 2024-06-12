package com.example.easygo_travelapp.customView;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.easygo_travelapp.R;

public class CTInputOTP extends LinearLayout implements View.OnFocusChangeListener, TextWatcher {
    private EditText edtCode;
    private LinearLayout llBackground;
    private Context context;
    private CTInputOTP codePre, codeNext;

    public CTInputOTP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initComponent(context);
        showAttribute(context, attrs);
    }

    private void showAttribute(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CTInputOTP);
        setBgEdtCode(R.drawable.layers);
        typedArray.recycle();
    }

    private void initComponent(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.ctv_input_otp, this);
        edtCode = findViewById(R.id.edtCode);
        llBackground = findViewById(R.id.linearLayout);
    }

    public String getValue() {
        return edtCode.getText().toString();
    }

    public void setBgEdtCode(int drawable) {
        this.llBackground.setBackgroundResource(drawable);
    }

    public void updateBgEdtCode(CTInputOTP ctInputPre, @Nullable CTInputOTP ctInputNext) {
        codePre = ctInputPre;
        codeNext = ctInputNext;
        this.edtCode.setOnFocusChangeListener(this);
        this.edtCode.addTextChangedListener(this);

    }

    @Override
    public void onFocusChange(View view, boolean isFocus) {
        if (isFocus) {
            if (getValue().isEmpty()) {
                setBgEdtCode(R.drawable.layer_focus);
            } else {
                setBgEdtCode(R.drawable.layers_line_focus);
            }
        } else {
            if (!getValue().isEmpty()) {
                setBgEdtCode(R.drawable.layers_line);
            } else {
                setBgEdtCode(R.drawable.layers);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (getValue().isEmpty()) {
            setBgEdtCode(R.drawable.layer_focus);
            if (codePre != null) {
                this.edtCode.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                            clearFocus();
                            codePre.edtCode.requestFocus();
                        }
                        return false;
                    }
                });
            }

        } else {
            setBgEdtCode(R.drawable.layers_line);
            if (codeNext != null) {
                if (!codeNext.getValue().isEmpty()) {
                    hideKeyboardFrom();
                } else {
                    codeNext.edtCode.requestFocus();
                }
            } else {
                hideKeyboardFrom();
                if (this.getFocusable() == NOT_FOCUSABLE) {
                    hideKeyboardFrom();
                }
            }
        }
    }

    private void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
        clearFocus();
    }
}
