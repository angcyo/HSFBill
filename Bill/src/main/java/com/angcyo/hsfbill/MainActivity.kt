package com.angcyo.hsfbill

import android.content.Intent
import com.angcyo.hsfbill.iview.MainUIView
import com.angcyo.uiview.base.UILayoutActivity

class MainActivity : UILayoutActivity() {
    override fun onLoadView(intent: Intent?) {
        //Tip.ok(RUtils.getAppVersionName() + " -- " + RUtils.getAppVersionCode())
        startIView(MainUIView(), false)
    }
}

