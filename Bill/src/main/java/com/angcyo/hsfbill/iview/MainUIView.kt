package com.angcyo.hsfbill.iview

import android.view.LayoutInflater
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.resources.ResUtil

/**
 * Created by angcyo on 2018/02/13 23:12
 */
class MainUIView : BaseContentUIView() {
    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleBarBGDrawable(ResUtil.createGradientDrawable())
                .setTitleString("账单管理")
    }

    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.activity_main)
    }

}