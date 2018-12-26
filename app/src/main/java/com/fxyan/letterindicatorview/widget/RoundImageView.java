package com.fxyan.letterindicatorview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @author fxYan
 */
public final class RoundImageView extends AppCompatImageView {

    private Path path;
    private RectF rectF;
    private float radius;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        rectF = new RectF();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, metrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = canvas.save();
        path.reset();
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = getWidth();
        rectF.bottom = getHeight();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.restoreToCount(count);
    }
}
