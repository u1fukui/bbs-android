package com.u1fukui.bbs.view.customview;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1fukui.bbs.R;

public class ErrorView extends LinearLayout {

    public interface ErrorViewListener {
        void onClickReloadButton();
    }

    public ErrorView(Context context) {
        super(context);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_error, this, true);
    }

    @BindingAdapter("errorMessage")
    public static void errorMessage(View view, String message) {
        TextView messageView = (TextView) view.findViewById(R.id.error_message);
        messageView.setText(message);
    }

    @BindingAdapter("errorViewListener")
    public static void onClickReloadButton(View view, final ErrorViewListener listener) {
        View reloadButton = view.findViewById(R.id.reload_btn);
        if (reloadButton != null && listener != null) {
            reloadButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClickReloadButton();
                    }
                }
            });
        }
    }
}
