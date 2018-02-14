package com.angcyo.hsfbill.iview

import android.os.Bundle
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.base.BaseSingleRecyclerUIView
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.hsfbill.realm.GoodsRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.rsen.RefreshLayout

/**
 * Created by angcyo on 2018/02/15 00:38
 * 所有商品 出售情况
 */
class AllGoodsUIView : BaseSingleRecyclerUIView<AllGoodsUIView.GoodsInfoBean>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("所有商品")
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
            val datas = mutableListOf<AllGoodsUIView.GoodsInfoBean>()
            val nameList = mutableListOf<String>()
            val map = hashMapOf<String, GoodsInfoBean>()
            for (bill in it.where(BillRealm::class.java).findAll()) {
                for (goods in bill.goodsList) {
                    if (goods.name.isNotEmpty()) {
                        if (nameList.contains(goods.name)) {
                            map[goods.name]?.let {
                                it.num += goods.num
                            }
                        } else {
                            nameList.add(goods.name)
                            map.put(goods.name, GoodsInfoBean().apply {
                                goodsRealm = goods
                                name = goods.name
                                num += goods.num

                                datas.add(this)
                            })
                        }

                    }
                }
            }
            datas.sortWith(Comparator { o1, o2 ->
                o1.goodsRealm!!.namePY.compareTo(o2.goodsRealm!!.namePY, true)
            })
            onUILoadFinish(datas)
        }
    }

    override fun createAdapter(): RExBaseAdapter<String, AllGoodsUIView.GoodsInfoBean, String> = object : RExBaseAdapter<String, AllGoodsUIView.GoodsInfoBean, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.item_bill_info
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: AllGoodsUIView.GoodsInfoBean?) {
            super.onBindDataView(holder, posInData, dataBean)
            dataBean?.let {
                holder.tv(R.id.name_view).text = it.name
                holder.tv(R.id.price_all).text = "合:${it.num}${it.goodsRealm!!.unit}"
                holder.timeV(R.id.time_view).time = it.goodsRealm!!.createTime
            }
        }
    }

    class GoodsInfoBean {
        var name = ""
        var num = 0L
        var goodsRealm: GoodsRealm? = null
    }

}