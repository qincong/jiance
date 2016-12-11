package com.example.qincong.jiance;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;

/**
 * Created by qincong on 2016/11/25.
 */
public class MyEditText extends LinearLayout {
    View view;
    String text;
    TextView textView;
    EditText editText;

    public MyEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        view = View.inflate(context, R.layout.my_edit_text, this);
        textView = (TextView) view.findViewById(R.id.explain);
        editText = (EditText) view.findViewById(R.id.edit_text);

        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.MyEditText);
        text = ta.getString(R.styleable.MyEditText_text);
        ta.recycle();

        textView.setText(text);
    }

    public void setText(String text) {
        editText.setText(text);
    }
    public String getText() {
        return editText.getText().toString();
    }
}
