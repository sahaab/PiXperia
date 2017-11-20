package com.android.launcher3.pixel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.launcher3.R;
import com.google.android.apps.nexuslauncher.graphics.IcuDateTextView;

public class ShadowHostView extends LinearLayout {
    private static final int cf = 38;
    private static final int cg = 89;
    private Bitmap ch;
    private final BlurMaskFilter blurMaskFilter; //ci
    private final int cj;
    private Bitmap ck;
    private final int cl;
    private final Canvas mCanvas;
    private final Paint mPaint;
    private View mView;

    public ShadowHostView(Context context) {
        this(context, null);
    }

    public ShadowHostView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShadowHostView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mCanvas = new Canvas();
        mPaint = new Paint(3);
        setWillNotDraw(false);
        cl = getResources().getDimensionPixelSize(R.dimen.qsb_shadow_blur_radius);
        cj = getResources().getDimensionPixelSize(R.dimen.qsb_key_shadow_offset);
        blurMaskFilter = new BlurMaskFilter((float) cl, Blur.NORMAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("SHV","SHV");
        final int n =  android.graphics.Color.BLACK;
        if (mView != null && mView.getWidth() > 0 && mView.getHeight() > 0) {
            if (ch == null || ch.getHeight() != mView.getHeight() || ch.getWidth() != mView.getWidth()) {
                ch = Bitmap.createBitmap(mView.getWidth(), mView.getHeight(), Bitmap.Config.ALPHA_8);
            }
            mCanvas.setBitmap(ch);
            mCanvas.drawColor(n, PorterDuff.Mode.CLEAR);
            mView.draw(mCanvas);
            final int n2 = ch.getWidth() + cl + cl;
            final int n3 = ch.getHeight() + cl + cl;
            if (ck == null || ck.getWidth() != n2 || ck.getHeight() != n3) {
                ck = Bitmap.createBitmap(n2, n3, Bitmap.Config.ALPHA_8);
            }
            mCanvas.setBitmap(ck);
            mCanvas.drawColor(n, PorterDuff.Mode.CLEAR);
            mPaint.setMaskFilter(blurMaskFilter);
            mPaint.setAlpha(100);
            mCanvas.drawBitmap(ch, (float)cl, (float)cl, mPaint);

            mCanvas.setBitmap(null);
            mPaint.setMaskFilter(null);
            final float n4 = mView.getLeft() - cl;
            final float n5 = mView.getTop() - cl;
            mPaint.setAlpha(ShadowHostView.cf);
            canvas.drawBitmap(ck, n4, n5, mPaint);
            mPaint.setAlpha(ShadowHostView.cg);
            canvas.drawBitmap(ck, n4, n5 + cj, mPaint);
        }
    }
    public static View getView(final RemoteViews remoteViews, final ViewGroup viewGroup, final View view) {
        if (remoteViews == null) {
            return null;
        }
        ShadowHostView shadowHostView;
        if (view instanceof ShadowHostView) {
            shadowHostView = (ShadowHostView) view;
        }
        else {
            shadowHostView = (ShadowHostView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shadow_host_view, viewGroup, false);
        }
        if (!shadowHostView.applyView(remoteViews)) {
            shadowHostView = null;
        }

        return shadowHostView;
    }

    private boolean applyView(final RemoteViews remoteViews) {
        final boolean b = true;
        if (mView != null) {
            try {
                final Context context = getContext();
                try {
                    remoteViews.reapply(context, mView);
                    invalidate();
                    return b;
                }
                catch (RuntimeException ex) {
                    Log.e("ShadowHostView", "View reapply failed", ex);
                    removeView(mView);
                    mView = null;
                }
            }
            catch (RuntimeException ex3) {}
        }
        try {
            Dim(mView = remoteViews.apply(getContext(), this));
            addView(mView);
            IcuDateTextView icu = (IcuDateTextView) getChildAt(0);
            icu.setGravity(Gravity.CENTER);
            View weatherView = getChildAt(2);
            if(weatherView instanceof  ViewGroup) {
                ((ViewGroup) weatherView).removeViewAt(1);
                View weatherTxt = ((ViewGroup)((ViewGroup) weatherView).getChildAt(0)).getChildAt(1);
                if(weatherTxt instanceof TextView) {
                    ((TextView) weatherTxt).setTypeface(Typeface.create("google-sans", Typeface.NORMAL));
                    ((TextView) weatherTxt).setTextSize(getResources().getDimension(R.dimen.smartspace_weather_size));
                    ((TextView) weatherTxt).setGravity(Gravity.CENTER_VERTICAL);
                }
                View weatherImg = ((ViewGroup)((ViewGroup) weatherView).getChildAt(0)).getChildAt(0);
                LayoutParams centerParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                centerParams.gravity =  Gravity.CENTER;
                centerParams.height = Math.round(getResources().getDimension(R.dimen.smartspace_weather_img_size));
                centerParams.width = Math.round(getResources().getDimension(R.dimen.smartspace_weather_img_size));
                weatherImg.setLayoutParams(centerParams);
            }
            return b;
        }
        catch (RuntimeException ex2) {
            Log.e("ShadowHostView", "View apply failed", ex2);
            return false;
        }
    }

    private void Dim(final View view) {
        if (view instanceof TextView) {
            ((TextView)view).setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        }
        else if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup)view;
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                Dim(viewGroup.getChildAt(i));
            }
        }
    }
}
