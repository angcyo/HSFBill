package com.angcyo.hsfbill.dialog

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.realm.GoodsRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.kotlin.onFocusChange
import com.angcyo.uiview.recycler.RBaseItemDecoration
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.widget.ExEditText

/**
 * Created by angcyo on 2018/02/14 19:31
 */
class GoodsInputDialog : UserInputDialog() {

    /**历史商品名称列表*/
    private val goodsNameList = mutableListOf<GoodsRealm>()
    /**历史商品单位列表*/
    private val goodsUnitList = mutableListOf<GoodsRealm>()

    private var isShowUnitList = false

    override fun inflateDialogView(dialogRootLayout: FrameLayout, inflater: LayoutInflater): View {
        return inflate(R.layout.dialog_goods_input)
    }

    override fun initDialogContentView() {
        super.initDialogContentView()
        mViewHolder.exV(R.id.unit).onFocusChange {
            if (it) {
                changeListToUnit(true)
            }
        }
        mViewHolder.exV(R.id.base_edit_text_view).onFocusChange {
            if (it) {
                changeListToUnit(false)
            }
        }
    }

    private fun setInputText(editText: ExEditText, text: String) {
        if (editText.isEmpty) {
            editText.setInputText(text)
        }
    }

    override fun initRecyclerView() {
        mViewHolder.reV(R.id.recycler_view).apply {
            addItemDecoration(RBaseItemDecoration(getDimensionPixelOffset(R.dimen.base_line), getColor(R.color.base_chat_bg_color)).apply {
                setDrawLastHLine(true)
            })
            adapter = object : RBaseAdapter<GoodsRealm>(mActivity) {
                override fun getItemLayoutId(viewType: Int): Int {
                    return R.layout.item_user_info
                }

                override fun onBindView(holder: RBaseViewHolder, position: Int, bean: GoodsRealm?) {
                    bean?.let {
                        if (isShowUnitList) {
                            holder.tv(R.id.text_view).text = "${it.unit}"
                            holder.tv(R.id.text_view1).text = ""
                            holder.tv(R.id.text_view2).text = ""
                        } else {
                            holder.tv(R.id.text_view).text = "${it.name}"
                            holder.tv(R.id.text_view1).text = "${it.namePY}"
                            holder.tv(R.id.text_view2).text = "${it.unit}"
                        }

                        holder.clickItem {
                            if (isShowUnitList) {
                                mViewHolder.exV(R.id.unit).setInputText(bean.unit)
                            } else {
                                setInputText(mViewHolder.exV(R.id.num), "0")
                                setInputText(mViewHolder.exV(R.id.unit), bean.unit)
                                setInputText(mViewHolder.exV(R.id.price), bean.price.toString())
                                setInputText(mViewHolder.exV(R.id.trade_price), bean.tradePrice.toString())
                                setInputText(editText!!, bean.name)
                            }
                        }
                    }
                }
            }.apply {
                RRealm.where {
                    val nameList = mutableListOf<String>()
                    val unitList = mutableListOf<String>()
                    for (goods in it.where(GoodsRealm::class.java).findAll()) {
                        if (goods.name.isNotEmpty() && !nameList.contains(goods.name)) {
                            nameList.add(goods.name)
                            goodsNameList.add(goods)
                        }
                        if (goods.unit.isNotEmpty() && !unitList.contains(goods.unit)) {
                            unitList.add(goods.unit)
                            goodsUnitList.add(goods)
                        }
                    }

                    goodsNameList.sortWith(Comparator { o1, o2 ->
                        o1.namePY.compareTo(o2.namePY, true)
                    })
                    goodsUnitList.sortWith(Comparator { o1, o2 ->
                        o1.unitPY.compareTo(o2.unitPY, true)
                    })

                    post {
                        changeListToUnit(false)
                    }
                }
            }
        }
    }

    /**显示单位历史列表*/
    fun changeListToUnit(unit: Boolean) {
        isShowUnitList = unit

        val recyclerView = mViewHolder.reV(R.id.recycler_view)
        if (unit) {
            if (goodsUnitList.isEmpty()) {
                recyclerView.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapterRaw.resetData(goodsUnitList)
            }
        } else {
            if (goodsNameList.isEmpty()) {
                recyclerView.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapterRaw.resetData(goodsNameList)
            }
        }
    }

    fun getGoodsRealm() = GoodsRealm().apply {
        name = mViewHolder.exV(R.id.base_edit_text_view).string()
        ext1 = mViewHolder.exV(R.id.ex_edit_text_view).string()
        num = mViewHolder.exV(R.id.num).inputNumber.toInt()
        unit = mViewHolder.exV(R.id.unit).string()
        price = mViewHolder.exV(R.id.price).inputNumber
        tradePrice = mViewHolder.exV(R.id.trade_price).inputNumber
    }
}