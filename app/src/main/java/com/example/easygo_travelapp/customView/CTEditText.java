package com.example.easygo_travelapp.customView;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.easygo_travelapp.R;

public class CTEditText extends LinearLayout implements View.OnFocusChangeListener, TextView.OnEditorActionListener {
    private TextView tvTitle;
    private EditText edtInput;

    public CTEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponents(context);
    }

    private void initComponents(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ctv_edit_text, this, true);
        tvTitle = view.findViewById(R.id.title);
        edtInput = view.findViewById(R.id.content);
        initActionDone();
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setContent(String content) {
        this.edtInput.setText(content);
    }

    public String getContent() {
        return edtInput.getText().toString().trim();
    }

    public void setHint(String title) {
        this.edtInput.setHint(title);
    }

    public void updateAction(String input) {
        if (input.equals(getResources().getString(R.string.password)) ||
                input.equals(getResources().getString(R.string.confirm_password))) {
            this.edtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        setTitle(input);
        setHint(input);
        this.tvTitle.setVisibility(INVISIBLE);
        this.edtInput.setOnFocusChangeListener(this);
    }

    private void initActionDone(){
        this.edtInput.setOnEditorActionListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean focus) {
        if (!focus) {
            this.tvTitle.setVisibility(INVISIBLE);
        } else {
            this.tvTitle.setVisibility(VISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i!=0 && EditorInfo.IME_MASK_ACTION!=0){
            this.edtInput.clearFocus();
        }
        return false;
    }
}
