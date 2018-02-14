package com.angcyo.hsfbill.iview

import android.os.Bundle
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseItemUIView
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.resources.ResUtil

/**
 * Created by angcyo on 2018/02/13 23:12
 */
class MainUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleBarBGDrawable(ResUtil.createGradientDrawable())
                .setTitleString("账单管理")
    }

    override fun onBackPressed(): Boolean {
        mActivity.moveTaskToBack()
        return false
        //return super.onBackPressed()
    }

    override fun onViewShowNotFirst(bundle: Bundle?) {
        super.onViewShowNotFirst(bundle)
        notifyItemChanged(0)
    }

    override fun createItems(items: MutableList<SingleItem>) {
        //个数信息展示
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                RRealm.where {
                    holder.tv(R.id.bill_num_view).text = "${it.where(BillRealm::class.java).findAll().size}"
                    holder.tv(R.id.complete_num_view).text = "${it.where(BillRealm::class.java).equalTo("statue", Integer.valueOf(1)).findAll().size}"
                    holder.tv(R.id.other_num_view).text = "${it.where(BillRealm::class.java).notEqualTo("statue", Integer.valueOf(1)).findAll().size}"
                }

                RRealm.where {
                    //holder.tv(R.id.complete_num_view).text = "${it.where(BillRealm::class.java).notEqualTo("statue", Integer.valueOf(1)).findAll().size}"
                }

                RRealm.where {
                    //holder.tv(R.id.complete_num_view).text = "${it.where(BillRealm::class.java).equalTo("statue", Integer.valueOf(1)).findAll().size}"
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_all_bill_status_info
            }

        })
        //账单控制按钮
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.click(R.id.add_bill_button) {
                    startIView(AddBillUIView())
                }
                holder.click(R.id.see_all_button) {
                    startIView(AddBillUIView())
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_bill_control_layout
            }

        })

        //货物出售信息
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_all_goods_status_info
            }
        })
    }
}