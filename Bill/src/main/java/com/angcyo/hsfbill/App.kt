package com.angcyo.hsfbill

import com.angcyo.library.utils.L
import com.angcyo.rrealm.RRealm
import com.angcyo.uiview.RApplication

/**
 * Created by angcyo on 2018/02/13 23:02
 */
class App : RApplication() {
    override fun onInit() {
        super.onInit()
        RRealm.init(this, false, packageName + ".realm", 4) { realm, oldVersion, newVersion ->
            L.e("数据库更新:$oldVersion->$newVersion")
            val schema = realm.schema
            var version = oldVersion
            if (version == 0L) {
                schema.get("BillRealm")?.addField("userPY", String::class.java)
                version++
            }
            if (version == 3L) {
                schema.get("BillRealm")?.addField("ext4", String::class.java)
                version++
            }
            L.e("数据库更新完成:$oldVersion->$newVersion $version")
        }
    }
}