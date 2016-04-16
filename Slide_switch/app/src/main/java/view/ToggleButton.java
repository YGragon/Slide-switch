package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 滑动开关
 */
public class ToggleButton extends View {

    private ToggleState toggleState = ToggleState.close;//关
    private Bitmap slideBg;
    private Bitmap switchBg;

    private boolean isSliding = false;

    private int currentX;//当前位置

    public ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 开关的状态
     *
     * @param state
     */
    public void setToggleState(ToggleState state) {
        toggleState = state;
    }

    public enum ToggleState {
        open, close
    }

    /**
     * 设置滑动开关的背景图片
     *
     * @param slideBackgroudSource
     */
    public void setSlideBackgroudSource(int slideBackgroudSource) {
        slideBg = BitmapFactory.decodeResource(getResources(), slideBackgroudSource);
    }

    /**
     * 设置滑动开关的背景图片
     *
     * @param switchBackgroudSource
     */
    public void setSwitchBackgroudSource(int switchBackgroudSource) {
        switchBg = BitmapFactory.decodeResource(getResources(), switchBackgroudSource);
    }

    /**
     * 设置当前控件在屏幕显示的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBg.getWidth(), switchBg.getHeight());
    }

    /**
     * 绘制自己在屏幕时的样子
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图片
        canvas.drawBitmap(switchBg, 0, 0, null);
        //绘制滑动的图片
        if (isSliding) {
            int left = currentX - slideBg.getWidth() / 2;
            if (left < 0) {
                left = 0;
            }
            if (left > (switchBg.getWidth() - slideBg.getWidth())) {
                left = switchBg.getWidth() - slideBg.getWidth();
            }
            canvas.drawBitmap(slideBg, left, 0, null);
        } else {
            if (toggleState == ToggleState.open) {
                canvas.drawBitmap(slideBg, switchBg.getWidth() - slideBg.getWidth(), 0, null);
            } else {
                canvas.drawBitmap(slideBg, 0, 0, null);
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentX = (int) event.getX();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isSliding = false;
                break;

            case MotionEvent.ACTION_UP:
                int centerX = switchBg.getWidth() / 2;
                if (currentX > centerX) {
                    if (toggleState != toggleState.open) {
                        toggleState = ToggleState.open;
                        if (listener == null) {
                            listener.onToggleStateChange(toggleState);
                        }
                    }
                } else {
                    if (toggleState != toggleState.close) {
                        toggleState = ToggleState.close;
                    }
                }


                break;

        }
        invalidate();
        return true;
    }

    private OnToggleStateChangeListener listener;

    public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener) {
        this.listener = listener;
    }

    public interface OnToggleStateChangeListener {
        void onToggleStateChange(ToggleState state);
    }
}
