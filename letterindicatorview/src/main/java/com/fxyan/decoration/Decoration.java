package com.fxyan.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

/**
 * @author fxYan
 */
public final class Decoration extends RecyclerView.ItemDecoration {

    private DecorationConfig config;
    private SparseArray<String> array;
    private Paint paint;
    private Paint.FontMetrics fontMetrics;

    private OnTitleIndexChangeListener onTitleIndexChangeListener;

    public Decoration(DecorationConfig config, SparseArray<String> array) {
        this.config = config;
        this.array = array;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(config.getTextSize());
        fontMetrics = paint.getFontMetrics();
    }

    public int keyAt(int position) {
        return array.keyAt(position);
    }

    public void setOnTitleIndexChangeListener(OnTitleIndexChangeListener listener) {
        onTitleIndexChangeListener = listener;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (array.indexOfKey(position) >= 0) {
                String tmp = array.get(position);
                float xOffset = config.getTextXOffset();
                float yOffset = child.getTop() - config.getHeight() / 2 - (fontMetrics.bottom + fontMetrics.top) / 2;

                View firstVisibleView = parent.getChildAt(0);
                View secondVisibleView = parent.getChildAt(1);
                int secondVisibleViewIndex = parent.getChildAdapterPosition(secondVisibleView);
                int currentTextR = config.getUnSelectTextColorR();
                int currentTextG = config.getUnSelectTextColorG();
                int currentTextB = config.getUnSelectTextColorB();
                int currentBgR = config.getUnSelectBgColorR();
                int currentBgG = config.getUnSelectBgColorG();
                int currentBgB = config.getUnSelectBgColorB();
                if (array.indexOfKey(secondVisibleViewIndex) >= 0
                        && firstVisibleView.getBottom() <= config.getHeight()) {
                    int endTextR = config.getSelectedTextColorR();
                    int endTextG = config.getSelectedTextColorG();
                    int endTextB = config.getSelectedTextColorB();
                    int endBgR = config.getSelectedBgColorR();
                    int endBgG = config.getSelectedBgColorG();
                    int endBgB = config.getSelectedBgColorB();
                    float percent = (config.getHeight() - firstVisibleView.getBottom()) / config.getHeight();
                    // text
                    currentTextR = (int) (currentTextR + (endTextR - currentTextR) * percent);
                    currentTextG = (int) (currentTextG + (endTextG - currentTextG) * percent);
                    currentTextB = (int) (currentTextB + (endTextB - currentTextB) * percent);
                    // bg
                    currentBgR = (int) (currentBgR + (endBgR - currentBgR) * percent);
                    currentBgG = (int) (currentBgG + (endBgG - currentBgG) * percent);
                    currentBgB = (int) (currentBgB + (endBgB - currentBgB) * percent);
                }
                paint.setColor(Color.rgb(currentBgR, currentBgG, currentBgB));
                c.drawRect(firstVisibleView.getLeft(), firstVisibleView.getTop(), firstVisibleView.getRight(),
                        firstVisibleView.getBottom(), paint);
                paint.setColor(Color.rgb(currentTextR, currentTextG, currentTextB));
                c.drawText(tmp, xOffset, yOffset, paint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        View firstVisibleView = parent.getChildAt(0);
        int firstVisibleViewIndex = parent.getChildAdapterPosition(firstVisibleView);
        View secondVisibleView = parent.getChildAt(1);
        int secondVisibleViewIndex = parent.getChildAdapterPosition(secondVisibleView);

        String tmp = "";
        for (int i = 0; i < array.size(); i++) {
            int position = array.keyAt(i);
            if (firstVisibleViewIndex >= position) {
                tmp = array.get(array.keyAt(i));
                if (onTitleIndexChangeListener != null) {
                    onTitleIndexChangeListener.onTitleIndexChanged(i);
                }
            } else {
                break;
            }
        }

        if (!TextUtils.isEmpty(tmp)) {
            float top = 0;
            int currentTextR = config.getSelectedTextColorR();
            int currentTextG = config.getSelectedTextColorG();
            int currentTextB = config.getSelectedTextColorB();
            int currentBgR = config.getSelectedBgColorR();
            int currentBgG = config.getSelectedBgColorG();
            int currentBgB = config.getSelectedBgColorB();
            if (array.indexOfKey(secondVisibleViewIndex) >= 0
                    && firstVisibleView.getBottom() <= config.getHeight()) {
                // 第一个可见的控件是该组最后一个控件
                top = firstVisibleView.getBottom() - config.getHeight();
                // text
                int endTextColorR = config.getUnSelectTextColorR();
                int endTextColorG = config.getUnSelectTextColorG();
                int endTextColorB = config.getUnSelectTextColorB();
                // bg
                int endBgColorR = config.getUnSelectBgColorR();
                int endBgColorG = config.getUnSelectBgColorG();
                int endBgColorB = config.getUnSelectBgColorB();

                float percent = Math.abs(top) / config.getHeight();

                currentTextR = (int) (currentTextR - (currentTextR - endTextColorR) * percent);
                currentTextG = (int) (currentTextG - (currentTextG - endTextColorG) * percent);
                currentTextB = (int) (currentTextB - (currentTextB - endTextColorB) * percent);
                currentBgR = (int) (currentBgR - (currentBgR - endBgColorR) * percent);
                currentBgG = (int) (currentBgG - (currentBgG - endBgColorG) * percent);
                currentBgB = (int) (currentBgB - (currentBgB - endBgColorB) * percent);
            }
            paint.setColor(Color.rgb(currentBgR, currentBgG, currentBgB));
            c.drawRect(0, top, parent.getWidth(), top + config.getHeight(), paint);
            float xOffset = config.getTextXOffset();
            float yOffset = top + config.getHeight() / 2 - (fontMetrics.bottom + fontMetrics.top) / 2;

            int color = Color.rgb(currentTextR, currentTextG, currentTextB);
            paint.setColor(color);
            c.drawText(tmp, xOffset, yOffset, paint);
        } else {
            if (onTitleIndexChangeListener != null) {
                onTitleIndexChangeListener.onTitleIndexChanged(-1);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (array.indexOfKey(childAdapterPosition) >= 0) {
            outRect.top = config.getHeight();
        }
    }

    public interface OnTitleIndexChangeListener {
        void onTitleIndexChanged(int index);
    }
}
