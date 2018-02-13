package com.angcyo.hsfbill

import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.RApplication

/**
 * Created by angcyo on 2018/02/13 23:02
 */
class App : RApplication() {
    override fun onInit() {
        super.onInit()
        RRealm.init(this)
    }
}