// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.apps.nexuslauncher.graphics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import android.util.AttributeSet;

public class IcuDateTextView extends DoubleShadowTextView
{
    private DateFormat eR;
    private final BroadcastReceiver eS;
    private boolean eT;
    
    public IcuDateTextView(final Context context) {
        this(context, null);
    }
    
    public IcuDateTextView(final Context context, final AttributeSet set) {
        super(context, set, 0);
        this.eS = new a(this);
    }
    
    private void dr(final boolean b) {
        //if (this.eR == null || b) {

            //eR = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
            //eR = DateFormat.getInstance(, Locale.getDefault()).setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        //}

        final String format = DateFormat.format("EEEE, MMM d", System.currentTimeMillis()).toString();
        this.setText(format);
        this.setContentDescription(format);
    }
    
    private void ds() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.getContext().registerReceiver(this.eS, intentFilter);
    }
    
    private void dt() {
        this.getContext().unregisterReceiver(this.eS);
    }
    
    public void onVisibilityAggregated(final boolean b) {
        final boolean et = true;
        super.onVisibilityAggregated(b);
        if (!this.eT && b) {
            this.eT = et;
            this.ds();
            this.dr(et);
        }
        else if (this.eT && (b ^ true)) {
            this.dt();
            this.eT = false;
        }
    }

    final class a extends BroadcastReceiver
    {
        final /* synthetic */ IcuDateTextView eU;

        a(final IcuDateTextView eu) {
            this.eU = eu;
        }

        public void onReceive(final Context context, final Intent intent) {
            this.eU.dr("android.intent.action.TIME_TICK".equals(intent.getAction()) ^ true);
        }
    }
}
