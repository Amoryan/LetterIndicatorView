package com.fxyan.letterindicatorview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * @author fxYan
 */
public final class ItemDecoration extends RecyclerView.ItemDecoration {

    private float dp12;
    private float dp30;
    private SparseArray<String> array;
    private Paint paint;
    private Paint.FontMetrics fontMetrics;

    public ItemDecoration(Context context, SparseArray<String> array) {
        dp12 = Tools.dp2px(context, 12);
        dp30 = Tools.dp2px(context, 30);
        this.array = array;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Tools.dp2px(context, 14));
        fontMetrics = paint.getFontMetrics();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (array.indexOfKey(position) >= 0) {
                String tmp = array.get(position);
                float xOffset = dp12;
                float yOffset = child.getTop() - dp30 / 2 + fontMetrics.descent;
                paint.setColor(Color.parseColor("#646464"));
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
            } else {
                break;
            }
        }

        if (array.indexOfKey(secondVisibleViewIndex) >= 0
                && firstVisibleView.getBottom() <= dp30) {
            // 第一个可见的控件是该组最后一个控件
            paint.setColor(Color.parseColor("#f5f5f5"));
            float top = firstVisibleView.getBottom() - dp30;
            c.drawRect(0, top, parent.getWidth(), top + dp30, paint);
            float xOffset = dp12;
            float yOffset = top + dp30 / 2 + fontMetrics.descent;
            paint.setColor(Color.parseColor("#646464"));
            c.drawText(tmp, xOffset, yOffset, paint);
        } else {
            paint.setColor(Color.parseColor("#f5f5f5"));
            float top = 0;
            c.drawRect(0, top, parent.getWidth(), top + dp30, paint);
            float xOffset = dp12;
            float yOffset = top + dp30 / 2 + fontMetrics.descent;
            paint.setColor(Color.parseColor("#646464"));
            c.drawText(tmp, xOffset, yOffset, paint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (array.indexOfKey(childAdapterPosition) >= 0) {
            outRect.top = (int) dp30;
        }
    }
}
