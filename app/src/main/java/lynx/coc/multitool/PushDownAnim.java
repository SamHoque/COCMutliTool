package lynx.coc.multitool;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.ref.WeakReference;


/**
 * Self Explanatory
 */


public class PushDownAnim implements PushDown {
    @Retention(SOURCE)
    @IntDef({MODE_SCALE, MODE_STATIC_DP})
    @interface Mode {
    }

    static final int MODE_SCALE = 0;
    static final int MODE_STATIC_DP = 1;
    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private final float defaultScale;
    private float pushScale = 0.97f;
    private float pushStatic = 2;
    private long durationPush = 50;
    private long durationRelease = 125;
    private AccelerateDecelerateInterpolator interpolatorPush = DEFAULT_INTERPOLATOR;
    private AccelerateDecelerateInterpolator interpolatorRelease = DEFAULT_INTERPOLATOR;
    private WeakReference<View> weakView;
    private AnimatorSet scaleAnimSet;

    private PushDownAnim(final View view) {
        this.weakView = new WeakReference<>(view);
        this.weakView.get().setClickable(true);
        defaultScale = view.getScaleX();
    }


    public static PushDownAnim e(final View view) {
        PushDownAnim pushAnim = new PushDownAnim(view);
        pushAnim.h(null);
        return pushAnim;
    }


    @Override
    public PushDown i(View.OnClickListener clickListener) {
        if (weakView.get() != null) {
            weakView.get().setOnClickListener(clickListener);
        }
        return this;
    }

    @Override
    public PushDown h(final View.OnTouchListener eventListener) {
        if (weakView.get() != null) {
            if (eventListener == null) {
                weakView.get().setOnTouchListener(new View.OnTouchListener() {
                    boolean isOutSide;
                    Rect rect;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (view.isClickable()) {
                            int i = motionEvent.getAction();
                            if (i == MotionEvent.ACTION_DOWN) {
                                isOutSide = false;
                                rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                                a(view, MODE_SCALE, pushScale, pushStatic, durationPush, interpolatorPush
                                );
                            } else if (i == MotionEvent.ACTION_MOVE) {
                                if (rect != null && !isOutSide && !rect.contains(view.getLeft() + (int) motionEvent.getX(), view.getTop() + (int) motionEvent.getY())) {
                                    isOutSide = true;
                                    a(view, MODE_SCALE, defaultScale, 0, durationRelease, interpolatorRelease
                                    );
                                }
                            } else if (i == MotionEvent.ACTION_CANCEL || i == MotionEvent.ACTION_UP) {
                                a(view, MODE_SCALE, defaultScale, 0, durationRelease, interpolatorRelease
                                );
                            }
                        }
                        return false;
                    }
                });

            } else {
                weakView.get().setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent motionEvent) {
                        return eventListener.onTouch(weakView.get(), motionEvent);
                    }
                });
            }
        }

        return this;
    }

    /* =========================== Private method =============================================== */
    private void a(final View view, @Mode int mode, float pushScale, float pushStatic, long duration, TimeInterpolator interpolator) {
        float tmpScale = pushScale;
        if (mode == 1) {
            tmpScale = c(pushStatic);
        }
        b(view, tmpScale, duration, interpolator);
    }

    private void b(final View view, float scale, long duration, TimeInterpolator interpolator) {
        view.animate().cancel();
        if (scaleAnimSet != null) {
            scaleAnimSet.cancel();
        }

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale);
        scaleX.setInterpolator(interpolator);
        scaleX.setDuration(duration);
        scaleY.setInterpolator(interpolator);
        scaleY.setDuration(duration);
        scaleAnimSet = new AnimatorSet();
        scaleAnimSet.play(scaleX).with(scaleY);
        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        scaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                View p = (View) view.getParent();
                if (p != null) p.invalidate();
            }
        });
        scaleAnimSet.start();
    }

    private float c(float sizeStaticDp) {
        if (sizeStaticDp <= 0) return defaultScale;

        float sizePx = f(sizeStaticDp);
        if (e() > d()) {
            if (sizePx > e()) return 1.0f;
            float pushWidth = e() - (sizePx * 2);
            return pushWidth / e();
        } else {
            if (sizePx > d()) return 1.0f;
            float pushHeight = d() - (sizePx * 2);
            return pushHeight / (float) d();
        }
    }

    private int d() {
        return weakView.get().getMeasuredHeight();
    }

    private int e() {
        return weakView.get().getMeasuredWidth();
    }

    private float f(final float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, weakView.get().getResources().getDisplayMetrics());
    }


}
