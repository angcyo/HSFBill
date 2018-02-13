package com.angcyo.hsfbill

import android.content.Intent
import com.angcyo.uiview.base.UILayoutActivity
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip

class MainActivity : UILayoutActivity() {
    override fun onLoadView(intent: Intent?) {
        Tip.ok(RUtils.getAppName() + " -- " + RUtils.getAppVersionCode())
    }
}
