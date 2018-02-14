package com.angcyo.hsfbill.dialog

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.angcyo.hsfbill.R
import com.angcyo.hsfbill.realm.BillRealm
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.dialog.UIInputExDialog
import com.angcyo.uiview.recycler.RBaseItemDecoration
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.github.promeg.pinyinhelper.Pinyin
import java.util.*

/**
 * Created by angcyo on 2018/02/14 17:01
 * 收货单位输入对话框
 */
class UserInputDialog : UIInputExDialog() {
    override fun inflateDialogView(dialogRootLayout: FrameLayout, inflater: LayoutInflater): View {
        return inflate(R.layout.dialog_user_input)
    }

    override fun initDialogContentView() {
        super.initDialogContentView()
        mViewHolder.reV(R.id.recycler_view).apply {
            addItemDecoration(RBaseItemDecoration(getDimensionPixelOffset(R.dimen.base_line), getColor(R.color.base_chat_bg_color)).apply {
                setDrawLastHLine(true)
            })
            adapter = object : RBaseAdapter<BillRealm>(mActivity) {
                override fun getItemLayoutId(viewType: Int): Int {
                    return R.layout.item_user_info
                }

                override fun onBindView(holder: RBaseViewHolder, position: Int, bean: BillRealm?) {
                    bean?.let {
                        holder.tv(R.id.text_view).text = "${it.userPY}  ${it.user}"

                        holder.clickItem {
                            setInputText(bean.user)
                        }
                    }
                }
            }.apply {
                RRealm.where {
                    val users = mutableListOf<BillRealm>()
                    val usersNameList = mutableListOf<String>()
                    for (bill in it.where(BillRealm::class.java).findAll()) {
                        if (!usersNameList.contains(bill.user)) {
                            if (bill.userPY.isEmpty()) {
                                bill.userPY = Pinyin.toPinyin(bill.user[0])[0].toString().toUpperCase()
                                it.insertOrUpdate(bill)
                            }
                            users.add(bill)
                            usersNameList.add(bill.user)
                        }
                    }

                    if (users.isEmpty()) {
                        mViewHolder.reV(R.id.recycler_view).visibility = View.INVISIBLE
                    } else {
                        users.sortWith(Comparator { o1, o2 ->
                            o1.userPY.compareTo(o2.userPY, true)
                        })
                        resetData(users)
                    }
                }
            }
        }
    }
}

