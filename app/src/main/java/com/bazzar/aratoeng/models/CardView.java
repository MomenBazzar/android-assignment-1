package com.bazzar.aratoeng.models;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bazzar.aratoeng.R;

public class CardView extends FrameLayout {
    private TextView mFrontTextView;
    private ImageView mBackImageView;
    private boolean mIsFlipped = false;

    public CardView(Context context) {
        super(context);
        init();
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_card, this);
        mFrontTextView = findViewById(R.id.iv_front);
        mBackImageView = findViewById(R.id.iv_back);
    }

    public void setText(String text) {
        this.mFrontTextView.setText(text);
    }

    public boolean isFlipped() {
        return mIsFlipped;
    }

    public void flip() {
        if (mIsFlipped) {
            flipBack();
        } else {
            flipFront();
        }
        mIsFlipped = !mIsFlipped;
    }

    private void flipFront() {
        mFrontTextView.setVisibility(GONE);
        mBackImageView.setVisibility(VISIBLE);
    }

    private void flipBack() {
        mBackImageView.setVisibility(GONE);
        mFrontTextView.setVisibility(VISIBLE);
    }

    public void setFlipped(boolean b) {
        mIsFlipped = b;
    }
}
