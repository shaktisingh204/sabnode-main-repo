package com.aghajari.simplechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineChart extends View {

    final List<ChartData> list = new ArrayList<>();
    final Rect textBounds = new Rect();
    float max = -1;
    int divideItemBy = 2;
    boolean drawHelperLine = true;
    float textPadding = 8;
    float pointRadius = 6;
    float firstPointPadding = 16;
    float lastPointPadding = 16;

    final Paint helperPaint = new Paint();
    final Paint linePaint = new Paint();
    final Paint pointPaint = new Paint();
    final Paint textPaint = new Paint();

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        helperPaint.setStyle(Paint.Style.STROKE);
        helperPaint.setColor(Color.LTGRAY);
        helperPaint.setStrokeWidth(context.getResources().getDisplayMetrics().density);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(2 * context.getResources().getDisplayMetrics().density);

        pointPaint.setColor(Color.BLUE);
        pointPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(15 * context.getResources().getDisplayMetrics().scaledDensity);

        lastPointPadding *= context.getResources().getDisplayMetrics().density;
        firstPointPadding *= context.getResources().getDisplayMetrics().density;
        pointRadius *= context.getResources().getDisplayMetrics().density;
        textPadding *= context.getResources().getDisplayMetrics().density;
    }

    public void setFirstPointPadding(float firstPointPadding) {
        this.firstPointPadding = firstPointPadding;
        invalidate();
    }

    public void setLastPointPadding(float lastPointPadding) {
        this.lastPointPadding = lastPointPadding;
        invalidate();
    }

    public void setDivideItemBy(int divideItemBy) {
        this.divideItemBy = divideItemBy;
        invalidate();
    }

    public void setDrawHelperLine(boolean drawHelperLine) {
        this.drawHelperLine = drawHelperLine;
        invalidate();
    }

    public void setMaxValue(float max) {
        this.max = max;
        invalidate();
    }

    public void setPointColor(int color) {
        pointPaint.setColor(color);
        invalidate();
    }

    public void setLineColor(int color) {
        linePaint.setColor(color);
        invalidate();
    }

    public void setHelperLineColor(int color) {
        helperPaint.setColor(color);
        invalidate();
    }

    public void setHelperLineWidth(float width) {
        helperPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setLineWidth(float width) {
        linePaint.setStrokeWidth(width);
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        invalidate();
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        invalidate();
    }

    public void setTextSize(float size) {
        textPaint.setTextSize(size * getContext().getResources().getDisplayMetrics().scaledDensity);
        invalidate();
    }

    public void setTextPadding(float padding) {
        textPadding = padding;
        invalidate();
    }

    public void addData(float data, String text){
        list.add(new ChartData(data, text));
        invalidate();
    }

    public void addData(float... values){
        for (int i = 1; i <= values.length; i++)
            list.add(new ChartData(values[i - 1], String.valueOf(i)));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (list.size() == 0) return;

        float maxData = max;
        if (maxData == -1) {
            maxData = 0;
            for (ChartData d : list)
                maxData = Math.max(maxData, d.data);
        }
        maxData = Math.max(1, maxData);


        float pr = pointRadius * 2;
        float left = getPaddingLeft() + pr;
        float top = getPaddingTop() + pr;
        float right = getMeasuredWidth() - getPaddingRight() - pr;
        float bottom = getMeasuredHeight() - getPaddingBottom();
        float height = bottom - top;

        textPaint.getTextBounds("AMIR", 0, 1, textBounds);
        float height2 = (int) (height - pr - textBounds.height() - textPadding);

        float linePadding = height2 / (maxData + 1);

        if (drawHelperLine) {
            for (int i = 0; i <= maxData; ) {
                final float t = top + i * linePadding;
                if (t > height2) break;
                canvas.drawLine(left, t, right, t, helperPaint);
                i += Math.max(divideItemBy, 1);
            }
        }

        left += firstPointPadding;
        right -= lastPointPadding;
        float width = right - left;

        final int count = list.size();
        float xPadding = (width - count * pr) / Math.max(count - 1, 1);

        for (int i = 0; i < count; i++){
            ChartData mData = list.get(i);
            float y = (maxData - mData.data) * linePadding + top;
            float x = left + (i * xPadding) + (i * pr);

            if (i+1 < count) {
                ChartData nextData = list.get(i + 1);
                float y2 = (maxData - nextData.data) * linePadding + top;
                float x2 = left + ((i+1) * xPadding) + ((i+1) * pr);
                canvas.drawLine(x, y, x2, y2, linePaint);
            }
            canvas.drawCircle(x, y, pointRadius, pointPaint);

            textPaint.getTextBounds(mData.text, 0, mData.text.length(), textBounds);
            float tx = x - textBounds.width() / 2f;
            canvas.drawText(mData.text, tx, bottom - textPadding - textBounds.height(), textPaint);
        }
    }

    public static class ChartData {
        float data;
        String text;

        public ChartData(float data, String text) {
            this.data = data;
            this.text = text;
        }
    }
}
