package com.angcyo.hsfbill.iview

import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import com.angcyo.github.pickerview.view.WheelTime
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseRecyclerUIView
import com.angcyo.hsfbill.dialog.GoodsInputDialog
import com.angcyo.hsfbill.dialog.UserInputDialog
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.hsfbill.realm.GoodsRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.github.pickerview.DateDialog
import com.angcyo.uiview.kotlin.clickIt
import com.angcyo.uiview.kotlin.nowTime
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip
import io.realm.RealmList

/**
 * Created by angcyo on 2018/02/14 16:04
 */
class AddBillUIView(var isAddBill: Boolean = true /*是否是添加账单*/) : BaseRecyclerUIView<String, GoodsRealm, String>() {

    /**准备添加的账单数据*/
    var addBillRealm = BillRealm().apply {
        goodsList = RealmList<GoodsRealm>()
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleString(if (isAddBill) "添加账单" else "修改账单")
                .addRightItem(TitleBarPattern.TitleBarItem(if (isAddBill) "保存" else "更新") {
                    if (TextUtils.isEmpty(addBillRealm.user)) {
                    } else {
                        if (isAddBill) {
                            RRealm.save(addBillRealm)
                            Tip.ok("保存成功")
                        } else {
                            RRealm.update(addBillRealm)
                            Tip.ok("更新成功")
                        }
                    }
                    finishIView()
                })
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mRefreshLayout.setNoNotifyPlaceholder()
        mExBaseAdapter.resetHeaderData("")
        mExBaseAdapter.resetFooterData("")
    }

    override fun createAdapter(): RExBaseAdapter<String, GoodsRealm, String> {
        return object : RExBaseAdapter<String, GoodsRealm, String>(mActivity, addBillRealm.goodsList) {
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
                val createTime = RUtils.getDataTime("yyyy-MM-dd HH:mm", addBillRealm.createTime)
                val nowTime = RUtils.getDataTime("yyyy-MM-dd HH:mm", nowTime())
                holder.item(R.id.create_time_info_layout).setItemDarkText(createTime)
                holder.item(R.id.user_info_layout).setItemDarkText(addBillRealm.user)

                //时间选择
                holder.click(R.id.create_time_info_layout) {
                    startIView(DateDialog(object : DateDialog.SimpleDateConfig() {
                        override fun onDateSelector(wheelTime: WheelTime?) {
                            super.onDateSelector(wheelTime)
                            //addBillRealm.createTime =
                            wheelTime?.let {
                                RRealm.exe {
                                    addBillRealm.createTime = DateDialog.parseTime("${wheelTime.selectorYear}-${wheelTime.selectorMonth}-${wheelTime.selectorDay} ${nowTime.split(" ")[1]}:0")
                                }
                            }
                            notifyItemChanged(0)
                        }
                    }))
                }

                //收货单位输入
                holder.click(R.id.user_info_layout) {
                    startIView(UserInputDialog().apply {
                        inputHintString = holder.item(R.id.user_info_layout).textView.text.toString()
                        inputDefaultString = addBillRealm.user
                        onInputTextResult = { result ->
                            RRealm.exe {
                                addBillRealm.user = result
                            }
                            notifyItemChanged(0)
                        }
                    })
                }
            }

            override fun onBindFooterView(holder: RBaseViewHolder, posInFooter: Int, footerBean: String?) {
                super.onBindFooterView(holder, posInFooter, footerBean)

                var pAll = 0f
                var tpAll = 0f
                for (goods in addBillRealm.goodsList) {
                    pAll += goods.price * goods.num
                    tpAll += goods.tradePrice * goods.num
                }

                if (dataCount == 0) {
                    holder.gone(R.id.control_layout)
                } else {
                    holder.visible(R.id.control_layout)
                }

                holder.tv(R.id.price).text = "总计:"
                holder.tv(R.id.price_all).text = "${pAll}元"
                holder.tv(R.id.trade_price).text = "总计:"
                holder.tv(R.id.trade_price_all).text = "${tpAll}元"

                if (isAddBill) {
                    holder.tv(R.id.add_goods_button).text = "添加商品"
                } else {
                    holder.tv(R.id.add_goods_button).text = "追加商品"
                }


                holder.click(R.id.add_goods_button) {
                    startIView(GoodsInputDialog().apply {
                        inputHintString = "输入商品名称"

                        onInputTextResult = {
                            if (it.isNotEmpty()) {
                                val count = mExBaseAdapter.dataCount
                                val goodsRealm = getGoodsRealm()

                                RRealm.exe {
                                    it.copyToRealm(goodsRealm)
                                    addBillRealm.addGoods(goodsRealm)
                                    mExBaseAdapter.notifyItemInserted(headerCount + count)
                                    mExBaseAdapter.notifyItemChanged(headerCount)
                                    mExBaseAdapter.notifyItemChanged(headerCount + dataCount)
                                    mExBaseAdapter.notifyItemRangeChanged(headerCount + count, itemCount)
                                }
                            }
                        }
                    })
                }

                if (SettingUIView.hideTradePrice) {
                    holder.invisible(R.id.trade_price_control_layout)
                }

                if (!isAddBill) {
                    holder.visible(R.id.bill_status_cb).clickIt { view ->
                        RRealm.exe {
                            addBillRealm.statue = if ((view as CompoundButton).isChecked) 1 else 0
                        }
                    }
                }
            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: GoodsRealm?) {
                super.onBindDataView(holder, posInData, dataBean)
                if (posInData == 0) {
                    holder.tv(R.id.goods_num_tip_view).apply {
                        visibility = View.VISIBLE
                        text = dataCount.toString()
                    }
                } else {
                    holder.gone(R.id.goods_num_tip_view)
                }

                dataBean?.let {
                    holder.tv(R.id.name).text = it.name
                    holder.timeV(R.id.time).time = it.createTime
                    holder.tv(R.id.unit).text = "${it.num}${it.unit}"

                    holder.tv(R.id.price).text = "价格:${it.price}元/${it.unit}"
                    holder.tv(R.id.price_all).text = "合:${it.price * it.num}元"
                    holder.tv(R.id.trade_price).text = "特价:${it.tradePrice}元/${it.unit}"
                    holder.tv(R.id.trade_price_all).text = "合:${it.tradePrice * it.num}元"
                }

                if (SettingUIView.hideTradePrice) {
                    holder.invisible(R.id.trade_price_control_layout)
                }
            }
        }
    }


}