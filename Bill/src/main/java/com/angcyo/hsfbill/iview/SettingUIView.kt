package com.angcyo.hsfbill.iview

import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.orhanobut.hawk.Hawk

/**
 * Created by angcyo on 2018/02/15 08:58
 */
class SettingUIView : BaseItemUIView() {

    companion object {
        var hideTradePrice = true
            get() {
                return Hawk.get<Boolean>("hideTradePrice", true)
            }
            set(value) {
                field = value
                Hawk.put("hideTradePrice", field)
            }

        var hideAllPrice = true
            get() {
                return Hawk.get<Boolean>("hideAllPrice", true)
            }
            set(value) {
                field = value
                Hawk.put("hideAllPrice", field)
            }
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("设置")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val hideTradePrice1 = hideTradePrice
                holder.cV(R.id.trade_price_cb).isChecked = hideTradePrice1
                holder.click(R.id.trade_price_layout) {
                    hideTradePrice = !hideTradePrice1
                    notifyItemChanged(this)
                }

                val hideAllPrice1 = hideAllPrice
                holder.cV(R.id.all_price_cb).isChecked = hideAllPrice1
                holder.click(R.id.all_price_layout) {
                    hideAllPrice = !hideAllPrice1
                    notifyItemChanged(this)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_setting_switch_layout
            }

        })
    }

}