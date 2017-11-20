package com.google.android.apps.nexuslauncher.qsb;

/**
 * Created by sahaa_000 on 2017-11-19.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.android.launcher3.CellLayout;
import com.android.launcher3.Launcher;
import com.android.launcher3.R;
import com.android.launcher3.dragndrop.DragLayer;

public class HotseatQsbWidget extends e {
    private boolean cg;
    private boolean ch;
    private AnimatorSet ci;
    private boolean cj;
    private final BroadcastReceiver ck;

    public HotseatQsbWidget(final Context context) {
        this(context, null);
    }

    public HotseatQsbWidget(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }

    public HotseatQsbWidget(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.ck = new h(this);
        this.cg = this.bO();
        this.bJ();
        this.setOnClickListener(this);
        this.setAccessibilityDelegate( new a());
    }

    static int bH(final Launcher launcher) {
        return launcher.getDragLayer().getInsets().bottom + launcher.getResources().getDimensionPixelSize(R.dimen.qsb_hotseat_bottom_margin);
    }

    private void bI() {
        final boolean ch = true;
        this.cj = false;
        this.bQ(this.ch = ch, ch);
    }

    private void bJ() {
        final Context context = this.getContext();
        int n;
        if (this.cg) {
            n = 2131886103;
        } else {
            n = 2131886102;
        }
        View.inflate(new ContextThemeWrapper(context, n), R.layout.qsb_hotseat_content, this);
        int n2;
        if (this.cg) {
            n2 = -855638017;
        } else {
            n2 = -1711604998;
        }
        this.bz(n2);
    }

    private void bK(final boolean b) {
        this.cj = false;
        if (this.ch) {
            this.bQ(this.ch = false, b);
        }
    }

    private Intent bL() {
        final int[] array = new int[2];
        this.getLocationInWindow(array);
        final Rect rect = new Rect(0, 0, this.getWidth(), this.getHeight());
        rect.offset(array[0], array[1]);
        rect.inset(this.getPaddingLeft(), this.getPaddingTop());
        return bV(rect, this.findViewById(R.id.search_container_hotseat), this.findViewById(R.id.mic_icon));
        //return null;
    }

    public static Intent bV(final Rect sourceBounds, final View view, final View view2) {
        final boolean b = true;
        final Intent intent = new Intent("com.google.nexuslauncher.FAST_TEXT_SEARCH");
        intent.setSourceBounds(sourceBounds);
        if (view2.getVisibility() != View.VISIBLE) {
            intent.putExtra("source_mic_alpha", 0.0f);
        }
        return intent.putExtra("source_round_left", b).putExtra("source_round_right", b).putExtra("source_logo_offset", cc(view, sourceBounds)).putExtra("source_mic_offset", cc(view2, sourceBounds)).putExtra("use_fade_animation", b).setPackage("com.google.android.googlequicksearchbox").addFlags(1342177280);
        //return intent.putExtra("source_round_left", true).putExtra("source_round_right", true).putExtra("source_logo_offset", cc(view, sourceBounds)).setPackage("com.google.android.googlequicksearchbox").addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    private static Point cc(View view, Rect rect) {
        int[] array = new int[2];
        view.getLocationInWindow(array);
        Point point = new Point();
        point.x = array[0] - rect.left + view.getWidth() / 2;
        point.y = array[1] - rect.top + view.getHeight() / 2;
        return point;
    }

    private void bM() {
        if (this.cg != this.bO()) {
            this.cg ^= true;
            this.removeAllViews();
            this.bJ();
            this.bE();
        }
    }

    private void bN() {
        if (this.hasWindowFocus()) {
            this.cj = true;
        } else {
            this.bI();
        }
    }

    private boolean bO() {
        final WallpaperInfo wallpaperInfo = WallpaperManager.getInstance(this.getContext()).getWallpaperInfo();
        return wallpaperInfo != null && wallpaperInfo.getComponent().flattenToString().equals(this.getContext().getString(R.string.default_live_wallpaper));
    }

    private void bP() {
        /*final f f = new f(this, false);
        if (this.cb.dY().startSearch(f.build(), f.getExtras())) {
            final SharedPreferences devicePrefs = Utilities.getDevicePrefs(this.getContext());
            devicePrefs.edit().putInt("key_hotseat_qsb_tap_count", devicePrefs.getInt("key_hotseat_qsb_tap_count", 0) + 1).apply();
            this.bN();
            return;
        }*/
        //this.getContext().sendOrderedBroadcast(this.bL(), (String) null, (BroadcastReceiver) new i(this), (Handler) null, 0, (String) null, (Bundle) null);
    }

    private void bQ(final boolean b, final boolean b2) {
        final long n = 200L;
        final int n2 = 1;
        if (this.ci != null) {
            this.ci.cancel();
        }
        (this.ci = new AnimatorSet()).addListener((Animator.AnimatorListener) new j(this));
        if (b) {
            final AnimatorSet ci = this.ci;
            final DragLayer dragLayer = this.cb.getDragLayer();
            final Property alpha = View.ALPHA;
            final float[] array = new float[n2];
            array[0] = 0.0f;
            ci.play((Animator) ObjectAnimator.ofFloat((Object) dragLayer, alpha, array));
            final DragLayer dragLayer2 = this.cb.getDragLayer();
            final Property translation_Y = View.TRANSLATION_Y;
            final float[] array2 = new float[n2];
            array2[0] = -this.cb.getHotseat().getHeight() / 2;
            final ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) dragLayer2, translation_Y, array2);
            ofFloat.setInterpolator((TimeInterpolator) new AccelerateInterpolator());
            this.ci.play((Animator) ofFloat);
            this.ci.setDuration(n);
        } else {
            final AnimatorSet ci2 = this.ci;
            final DragLayer dragLayer3 = this.cb.getDragLayer();
            final Property alpha2 = View.ALPHA;
            final float[] array3 = new float[n2];
            array3[0] = 1.0f;
            ci2.play((Animator) ObjectAnimator.ofFloat((Object) dragLayer3, alpha2, array3));
            final DragLayer dragLayer4 = this.cb.getDragLayer();
            final Property translation_Y2 = View.TRANSLATION_Y;
            final float[] array4 = new float[n2];
            array4[0] = 0.0f;
            final ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) dragLayer4, translation_Y2, array4);
            ofFloat2.setInterpolator((TimeInterpolator) new DecelerateInterpolator());
            this.ci.play((Animator) ofFloat2);
            this.ci.setDuration(n);
        }
        this.ci.start();
        if (!b2) {
            this.ci.end();
        }
    }

    protected int bw(final int n) {
        final CellLayout layout = this.cb.getHotseat().getLayout();
        return n - ((View) layout).getPaddingLeft() - ((View) layout).getPaddingRight();
    }

    protected void by() {
        ((ViewGroup.MarginLayoutParams) this.getLayoutParams()).bottomMargin = bH(this.cb);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getContext().registerReceiver(this.ck, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
    }

    public void onClick(final View view) {
        super.onClick(view);
        if (view == this) {
            //this.bA(2);
            //this.bP();

            //getContext().sendOrderedBroadcast(bm("com.google.nexuslauncher.FAST_TEXT_SEARCH"), null, new HotseatQsbWidget.FastTextSearchReceiver(this), null, 0, null, null);
            getContext().sendOrderedBroadcast(bL(), null, new HotseatQsbWidget.FastTextSearchReceiver(this),  null, 0, null, null);

        }
    }



    protected void setGoogleAnimationStart(Rect rect, Intent intent) {
    }

    private Intent bm(String str) {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + getWidth(), iArr[1] + getHeight());
        Intent intent = new Intent(str);
        setGoogleAnimationStart(rect, intent);
        intent.setSourceBounds(rect);
        return intent.putExtra("source_round_left", true).putExtra("source_round_right", true).putExtra("source_logo_offset", MidLocation(findViewById(R.id.search_container_hotseat), rect)).setPackage("com.google.android.googlequicksearchbox").addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private Point MidLocation(View view, Rect rect) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point point = new Point();
        point.x = (location[0] - rect.left) + (view.getWidth() / 2);
        point.y = (location[1] - rect.top) + (view.getHeight() / 2);
        return point;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getContext().unregisterReceiver(this.ck);
    }

    public void onWindowFocusChanged(final boolean b) {
        super.onWindowFocusChanged(b);
        if (!b && this.cj) {
            this.bI();
        } else if (b) {
            this.bK(true);
        }
    }

    protected void onWindowVisibilityChanged(final int n) {
        super.onWindowVisibilityChanged(n);
        this.bK(false);
    }

    final class i extends BroadcastReceiver {
        final /* synthetic */ HotseatQsbWidget cu;

        i(final HotseatQsbWidget cu) {
            this.cu = cu;
        }

        public void onReceive(final Context context, final Intent intent) {
            if (this.getResultCode() == 0) {
                this.cu.bF("com.google.android.googlequicksearchbox.TEXT_ASSIST");
            } else {
                this.cu.bN();
            }
        }
    }

    class j extends AnimatorListenerAdapter {
        final /* synthetic */ HotseatQsbWidget cv;

        j(final HotseatQsbWidget cv) {
            this.cv = cv;
        }

        public void onAnimationEnd(final Animator animator) {
            if (animator == this.cv.ci) {
                this.cv.ci = null;
            }
        }
    }

    class h extends BroadcastReceiver {
        final /* synthetic */ HotseatQsbWidget ct;

        h(final HotseatQsbWidget ct) {
            this.ct = ct;
        }

        public void onReceive(final Context context, final Intent intent) {
            this.ct.bM();
        }
    }

    final class FastTextSearchReceiver extends BroadcastReceiver {
        final HotseatQsbWidget cq;

        FastTextSearchReceiver(HotseatQsbWidget qsbView) {
            cq = qsbView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            /*if (getResultCode() == 0) {
                //Animation not allowed, start the normal search bar
                cq.startQsbActivity(Qsb.TEXT_ASSIST);
            } else {
                cq.loadWindowFocus();
            }*/
            cq.bM();
        }
    }
}
