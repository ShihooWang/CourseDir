package shihoo.wang.coursedir.floatviewgroup;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

public class FloatViewGroup extends ViewGroup implements View.OnClickListener {

    private FloatAdapter mAdapter;
    private ImageView mSwitchView;
    private int mItemCount;
    private View[] mItems;
    private boolean IS_EXPAND = false;
    private int CHANGE_TIME = 200;

    private int padding = 50; //距离边上

    public FloatViewGroup(Context context) {
        this(context, null);
    }

    public FloatViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(width - view.getMeasuredWidth() - padding,
                    height - view.getMeasuredHeight()- padding,
                    width - padding, height - padding);
        }
    }

    public void setAdapter(FloatAdapter mAdapter) {
        this.mAdapter = mAdapter;
        initView();
    }

    public void initView() {
        if (mAdapter == null) {
            return;
        }
        mSwitchView = mAdapter.getSwitchView();
        mItemCount = mAdapter.getCount();
        mItems = new View[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mItems[i] = mAdapter.getItem(i);
            mItems[i].setVisibility(INVISIBLE);
            addView(mItems[i]);
        }
        addView(mSwitchView);

        mSwitchView.setClickable(true);
        mSwitchView.setOnClickListener(this);
    }

    //打开
    public void expandViews() {
        setEnabled(true);
        ObjectAnimator.ofFloat(mSwitchView, "rotation", 0f, 135f).setDuration(CHANGE_TIME).start();
        for (int i = 0; i < mItemCount; i++) {
            View item = mItems[i];
            float curTranslationY = item.getTranslationY();
            Animator animator = ObjectAnimator.ofFloat(item, "translationY", curTranslationY, -(i + 1) * mItems[i].getMeasuredHeight());
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(CHANGE_TIME).start();
            item.setVisibility(VISIBLE);
        }
        IS_EXPAND = true;
        setBackgroundColor(Color.parseColor("#F0FFFFFF"));
    }

    public boolean checkShrinkViews() {
        if (IS_EXPAND) {
            shrinkViews();
            setOnClickListener(null);
            setClickable(false);
            return true;
        }
        return false;
    }

    //关闭
    public void shrinkViews() {
        setClickable(false);
        ObjectAnimator.ofFloat(mSwitchView, "rotation", 135f, 0f).setDuration(CHANGE_TIME).start();
        for (int i = 0; i < mItemCount; i++) {
            final View item = mItems[i];
            float curTranslationY = item.getTranslationY();
            Animator animator = ObjectAnimator.ofFloat(item, "translationY", curTranslationY, 0f);
            animator.setDuration(CHANGE_TIME).start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    item.setVisibility(GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        IS_EXPAND = false;
        setBackgroundColor(Color.parseColor("#00000000"));
        setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        if (IS_EXPAND) {
            shrinkViews();
            setOnClickListener(null);
            setClickable(false);
        } else {
            setEnabled(true);
            expandViews();
            setOnClickListener(onClickListener);
        }
    }
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            shrinkViews();
            setOnClickListener(null);
            setClickable(false);
        }
    };

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}
