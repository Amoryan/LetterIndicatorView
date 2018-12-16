package com.fxyan.letterindicatorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * @author fxYan
 */
public final class LetterIndicatorView extends View {

    private int backgroundColor;

    private float itemWidth;
    private float itemHeight;

    private float textSize;
    private int selectedTextColor;
    private int selectedTextBorderColor;
    private float selectedTextBorderRadius;
    private int unSelectTextColor;

    private float zoomTextSize;

    private Paint paint;
    private Path path;
    private Rect textBounds;
    private ArrayList<String> titles;
    private boolean isInTouchMode;
    private int current;
    private OnTitleIndexChangeListener onTitleIndexChangeListener;

    public LetterIndicatorView(Context context) {
        super(context);
        init(null);
    }

    public LetterIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        configDefaultAttrs();
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LetterIndicatorView);

            backgroundColor = array.getColor(R.styleable.LetterIndicatorView_livBackgroundColor, backgroundColor);

            itemWidth = array.getDimension(R.styleable.LetterIndicatorView_livItemWidth, itemWidth);
            itemHeight = array.getDimension(R.styleable.LetterIndicatorView_livItemHeight, itemHeight);

            textSize = array.getDimension(R.styleable.LetterIndicatorView_livTextSize, textSize);
            selectedTextColor = array.getColor(R.styleable.LetterIndicatorView_livSelectedTextColor, selectedTextColor);
            selectedTextBorderColor = array.getColor(R.styleable.LetterIndicatorView_livSelectedTextBorderColor, selectedTextBorderColor);
            selectedTextBorderRadius = array.getDimension(R.styleable.LetterIndicatorView_livSelectedTextBorderRadius, selectedTextBorderRadius);
            unSelectTextColor = array.getColor(R.styleable.LetterIndicatorView_livUnSelectTextColor, unSelectTextColor);

            array.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textBounds = new Rect();
        titles = new ArrayList<>();
        path = new Path();
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles.clear();
        this.titles.addAll(titles);
    }

    public void setCurrent(int index) {
        current = index;
        invalidate();
    }

    public void setOnTitleIndexChangeListener(OnTitleIndexChangeListener listener) {
        onTitleIndexChangeListener = listener;
    }

    private void configDefaultAttrs() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        backgroundColor = Color.TRANSPARENT;

        itemWidth = itemHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, metrics);

        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);
        selectedTextColor = Color.parseColor("#1b8fe6");
        selectedTextBorderColor = Color.TRANSPARENT;
        selectedTextBorderRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, metrics);
        unSelectTextColor = Color.parseColor("#646464");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isInTouchMode = true;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                calculateYIndex(y);
                if (onTitleIndexChangeListener != null) {
                    onTitleIndexChangeListener.onTitleIndexChanged(current);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isInTouchMode = false;
                break;
            default:
        }
        invalidate();
        return true;
    }

    private void calculateYIndex(float y) {
        float firstItemTop = (getHeight() - getTotalItemHeight()) / 2;
        current = (int) ((y - firstItemTop) / itemHeight);
        if (current < 0) {
            current = 0;
        }
        if (current >= titles.size()) {
            current = titles.size() - 1;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        if (wMode == MeasureSpec.AT_MOST) {
            wSize = (int) Math.floor(itemWidth + 0.5);
        }

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if (hMode == MeasureSpec.AT_MOST) {
            hSize = (int) Math.floor(getTotalItemHeight() + 0.5);
        }

        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawTitles(canvas);
        drawZoomText(canvas);
    }

    private void drawBackground(Canvas canvas) {
        float left = getWidth() - itemWidth;
        float top = 0;
        float right = getWidth();
        float bottom = getHeight();
        paint.setColor(backgroundColor);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawTitles(Canvas canvas) {
        float firstItemTop = (getHeight() - getTotalItemHeight()) / 2;
        for (int i = 0; i < titles.size(); i++) {
            float left = getWidth() - itemWidth;
            float top = firstItemTop + i * itemHeight;
            float right = getWidth();
            float bottom = top + itemHeight;

            if (i == current) {
                paint.setColor(selectedTextBorderColor);
                float centerX = (left + right) / 2;
                float centerY = (top + bottom) / 2;
                path.reset();
                path.addCircle(centerX, centerY, selectedTextBorderRadius, Path.Direction.CCW);
                canvas.drawPath(path, paint);

                paint.setColor(selectedTextColor);
            } else {
                paint.setColor(unSelectTextColor);
            }
            paint.setTextSize(textSize);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            String title = titles.get(i);
            paint.getTextBounds(title, 0, title.length(), textBounds);
            float xOffset = left + (itemWidth - textBounds.width()) / 2;
            float yOffset = top + itemHeight / 2 + fontMetrics.descent;
            canvas.drawText(title, xOffset, yOffset, paint);
        }
    }

    public void drawZoomText(Canvas canvas) {
        if (isInTouchMode) {
            // TODO
        }
    }

    private float getTotalItemHeight() {
        float total = 0;
        total += titles.size() * itemHeight;
        return total;
    }

    interface OnTitleIndexChangeListener {
        void onTitleIndexChanged(int index);
    }
}
