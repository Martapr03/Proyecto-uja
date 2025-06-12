package com.example.proyectouja;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class QRScannerOverlay extends View {

    private Paint borderPaint;
    private Paint linePaint;
    private Paint overlayPaint;
    private RectF rect;
    private float lineY;
    private Handler handler;
    private Runnable animator;

    public QRScannerOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    private void init(Context context) {
        // Borde punteado
        borderPaint = new Paint();
        borderPaint.setColor(ContextCompat.getColor(context, R.color.verde));
        borderPaint.setStrokeWidth(6f);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setPathEffect(new DashPathEffect(new float[]{20f, 15f}, 0f));

        // Línea escáner animada
        linePaint = new Paint();
        linePaint.setColor(ContextCompat.getColor(context, R.color.verde));
        linePaint.setStrokeWidth(4f);
        linePaint.setStyle(Paint.Style.FILL);

        // Fondo oscuro transparente
        overlayPaint = new Paint();
        overlayPaint.setColor(Color.parseColor("#AA000000")); // Negro semitransparente

        // Animación de la línea
        handler = new Handler();
        animator = () -> {
            lineY += 8f;
            if (rect != null && lineY > rect.bottom) {
                lineY = rect.top;
            }
            invalidate();
            handler.postDelayed(animator, 16); // ~60 FPS
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(animator);
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(animator);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float boxWidth = getWidth() * 0.85f;
        float boxHeight = getHeight() * 0.6f;

        float left = (getWidth() - boxWidth) / 2;
        float top = (getHeight() - boxHeight) / 2;
        rect = new RectF(left, top, left + boxWidth, top + boxHeight);

        // Capa temporal para aplicar clear
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);

        // Fondo semitransparente
        canvas.drawColor(Color.parseColor("#AA000000"));

        // Paint para recortar el centro
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(rect, 40f, 40f, clearPaint);

        // Restaurar capa
        canvas.restoreToCount(saveCount);

        // Dibuja marco punteado
        canvas.drawRoundRect(rect, 40f, 40f, borderPaint);

        // Dibuja línea animada
        if (lineY < rect.top || lineY > rect.bottom) {
            lineY = rect.top;
        }
        canvas.drawLine(rect.left + 20, lineY, rect.right - 20, lineY, linePaint);
    }

}




