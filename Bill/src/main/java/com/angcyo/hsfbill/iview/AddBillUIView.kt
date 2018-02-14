package com.angcyo.hsfbill.iview

import com.angcyo.github.pickerview.view.WheelTime
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseRecyclerUIView
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.hsfbill.realm.GoodsRealm
import com.angcyo.uiview.dialog.UIInputExDialog
import com.angcyo.uiview.github.pickerview.DateDialog
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.utils.RUtils

/**
 * Created by angcyo on 2018/02/14 16:04
 */
class AddBillUIView : BaseRecyclerUIView<String, GoodsRealm, String>() {

    /**准备添加的账单数据*/
    val addBillRealm = BillRealm()

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("添加账单")
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mExBaseAdapter.resetHeaderData("")
        mExBaseAdapter.resetFooterData("")
    }

    override fun createAdapter(): RExBaseAdapter<String, GoodsRealm, String> {
        return object : RExBaseAdapter<String, GoodsRealm, String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                if (isHeaderItemType(viewType)) {
                    return R.layout.item_add_bill_header
                }
                if (isFooterItemType(viewType)) {
                    return R.layout.item_add_bill_footer
                }
                return R.layout.item_add_bill_data
            }

            override fun onBindHeaderView(holder: RBaseViewHolder, posInHeader: Int, headerBean: String?) {
                super.onBindHeaderView(holder, posInHeader, headerBean)
                holder.item(R.id.create_time_info_layout).setItemDarkText(RUtils.getDataTime("yyyy-MM-dd HH:mm", addBillRealm.createTime))
                holder.item(R.id.user_info_layout).setItemDarkText(addBillRealm.user)

                holder.click(R.id.create_time_info_layout) {
                    startIView(DateDialog(object : DateDialog.SimpleDateConfig() {
                        override fun onDateSelector(wheelTime: WheelTime?) {
                            super.onDateSelector(wheelTime)
                            //addBillRealm.createTime =
                        }
                    }))
                }
                holder.click(R.id.user_info_layout) {
                    startIView(UIInputExDialog().apply {
                        onInputTextResult = {
                            addBillRealm.user = it
                            notifyItemChanged(0)
                        }
                    })
                }
            }

            override fun onBindFooterView(holder: RBaseViewHolder, posInFooter: Int, footerBean: String?) {
                super.onBindFooterView(holder, posInFooter, footerBean)
                holder.click(R.id.add_goods_button) {

                }
            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: GoodsRealm?) {
                super.onBindDataView(holder, posInData, dataBean)
            }
        }
    }


}