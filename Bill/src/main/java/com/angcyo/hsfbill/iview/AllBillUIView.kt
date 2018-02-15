package com.angcyo.hsfbill.iview

import android.os.Bundle
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseSingleRecyclerUIView
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.dialog.UIDialog
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.rsen.RefreshLayout
import io.realm.RealmResults

/**
 * Created by angcyo on 2018/02/15 00:38
 * 所有订单信息
 */
class AllBillUIView : BaseSingleRecyclerUIView<BillRealm>() {

    var billRealmList: RealmResults<BillRealm>? = null

    var canEdit = false

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleString("所有账单")
                .addRightItem(TitleBarPattern.TitleBarItem(R.mipmap.ico_edit) {
                    canEdit = true
                    mExBaseAdapter.notifyDataSetChanged()
                })
    }

    override fun initRefreshLayout(refreshLayout: RefreshLayout?, baseContentLayout: ContentLayout?) {
        super.initRefreshLayout(refreshLayout, baseContentLayout)
        refreshLayout?.setNoNotifyPlaceholder()
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.let {
            //it.layoutManager = GridLayoutManager(mActivity, 2)
        }
    }

    override fun onViewShowNotFirst(bundle: Bundle?) {
        super.onViewShowNotFirst(bundle)
        onBaseLoadData()
    }

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)

        RRealm.where {
            billRealmList = it.where(BillRealm::class.java).sort("userPY").findAll()
//            billRealmList?.sortWith(Comparator { o1, o2 ->
//                o1.userPY.compareTo(o2.userPY, true)
//            })
            onUILoadFinish(billRealmList)
        }
    }

    override fun createAdapter(): RExBaseAdapter<String, BillRealm, String> = object : RExBaseAdapter<String, BillRealm, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.item_bill_info
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: BillRealm?) {
            super.onBindDataView(holder, posInData, dataBean)

            if (canEdit) {
                holder.visible(R.id.delete_view)
            }

            dataBean?.let {
                holder.tv(R.id.name_view).text = it.user

                holder.click(R.id.delete_view) {
                    UIDialog.build()
                            .setDialogContent("删除 ${dataBean.user} 的账单? 删除后不可恢复.")
                            .setOkText("删除")
                            .setOkListener {
                                RRealm.exe {
                                    it.where(BillRealm::class.java)
                                            .equalTo("user", dataBean.user)
                                            .and()
                                            .equalTo("createTime", dataBean.createTime).findAll().deleteAllFromRealm()

                                    val adapterPosition = headerCount + posInData
                                    //dataBean.deleteFromRealm()
                                    //billRealmList?.deleteFromRealm()
                                    //deleteItem(posInData)
                                    notifyItemRemoved(adapterPosition)
                                    notifyItemRangeChanged(adapterPosition, itemCount - adapterPosition)
                                }
                            }
                            .showDialog(mParentILayout)
                }

                var pAll = 0f
                var tpAll = 0f
                for (goods in it.goodsList) {
                    pAll += goods.price * goods.num
                    tpAll += goods.tradePrice * goods.num
                }
                holder.tv(R.id.price_all).text = "合:${pAll}元"
                holder.timeV(R.id.time_view).time = it.createTime

                holder.clickItem {
                    startIView(AddBillUIView(false).apply {
                        addBillRealm = dataBean
                    })
                }
            }
        }
    }

}