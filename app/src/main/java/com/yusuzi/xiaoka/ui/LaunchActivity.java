package com.yusuzi.xiaoka.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.yusuzi.xiaoka.MainActivity;
import com.yusuzi.xiaoka.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import io.reactivex.Observable;

public class LaunchActivity extends AppCompatActivity {

    private ImageView mLeftLogoImg;

    private TextView mDescTitleTextView;


    private ArrayList<SpringAnimation> mLetterAnims;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status ui.
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        // get the screen height.
        DisplayMetrics dm           = getResources().getDisplayMetrics();
        int            screenHeight = dm.heightPixels;

        // text about 'Native messaging'
        mDescTitleTextView = findViewById(R.id.tv_slogon);
        mDescTitleTextView.setTranslationY(500f);
        mDescTitleTextView.setAlpha(0f);
        final SpringAnimation descTitleAnimY = new SpringAnimation(mDescTitleTextView, DynamicAnimation.TRANSLATION_Y, 0);
        descTitleAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        descTitleAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        final ValueAnimator descTitleAlphaAnim = ObjectAnimator.ofFloat(0f, 1f);
        descTitleAlphaAnim.setDuration(300);
        descTitleAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate (ValueAnimator valueAnimator) {
                mDescTitleTextView.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        // top logo by left
        mLeftLogoImg = findViewById(R.id.iv_logo);
        mLeftLogoImg.setTranslationY(400f);
        mLeftLogoImg.setAlpha(0f);
        final SpringAnimation leftLogoAnimY = new SpringAnimation(mLeftLogoImg, SpringAnimation.TRANSLATION_Y, 0);
        leftLogoAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        leftLogoAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        leftLogoAnimY.setStartVelocity(-2000);

        final ValueAnimator logoAlphaAnim = ObjectAnimator.ofFloat(0f, 1f);
        logoAlphaAnim.setDuration(600);
        logoAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate (ValueAnimator valueAnimator) {
                mLeftLogoImg.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        mLeftLogoImg.postDelayed(new Runnable() {
            @Override
            public void run () {
                leftLogoAnimY.start();
                mDescTitleTextView.postDelayed(() -> {
                    descTitleAlphaAnim.setStartDelay(100);
                    descTitleAlphaAnim.start();

                    descTitleAnimY.start();
                }, 300);
                logoAlphaAnim.start();
            }
        }, 500);

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(aLong -> {

                }, throwable -> {

                }, () -> {
                    MainActivity.start(LaunchActivity.this);
                    LaunchActivity.this.finish();
                });


    }
}