package shihoo.wang.coursedir.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;

import shihoo.wang.coursedir.utils.BreatheInterpolator;

/**
 * Created by shihoo.wang on 2018/11/16.
 * Email shihu.wang@bodyplus.cc
 */

public class ViewAnimUtils {

    /**
     * 呼吸灯效果 无限重复播放
     * @param views
     */
    public static void startBreathAnimation(View... views) {

        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            view.clearAnimation();
            Animation animation = view.getAnimation();
            if (animation != null) {
                animation.cancel();
            }

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1f);

            scaleX.setRepeatCount(ValueAnimator.INFINITE);
            scaleY.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);

            AnimatorSet animatorSet = new AnimatorSet();

            animatorSet.playTogether(scaleX, scaleY, alphaAnimator);
            animatorSet.setDuration(2800);
            animatorSet.setInterpolator(new BreatheInterpolator());
            animatorSet.start();
        }
    }


    /**
     * Visiable 和 Gone 状态切换动画
     * @param isVisiable
     * @param views
     */
    public static void startVisiableAnim( boolean isVisiable, View... views) {

        for (int i = 0; i < views.length; i++) {
            final View view = views[i];
            view.clearAnimation();
            Animation animation = view.getAnimation();
            if (animation != null) {
                animation.cancel();
            }

            if (isVisiable){
                // 从 不可见 到 可见
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1f);
                alphaAnimator.setDuration(300);
                alphaAnimator.start();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.VISIBLE);
                    }
                },300);
            }else {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.4f);
                alphaAnimator.setDuration(300);
                alphaAnimator.start();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                },300);
            }

        }
    }
}
