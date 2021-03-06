package com.angcyo.hsfbill.realm;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by angcyo on 2018/02/14 16:17
 * 账单
 */
public class BillRealm extends RealmObject {

    /**
     * 账单单位名称
     */
    private String user = "";

    /**
     * 汉子拼音首字母
     */
    private String userPY = "";

    /**
     * 备用扩展属性
     */
    private String ext1 = "";
    private String ext2 = "";
    private String ext3 = "";
    private String ext4 = "";

    /**
     * 创建时间
     */
    private long createTime = System.currentTimeMillis();

    /**
     * 状态: 1完成 0未完成 2进行中...
     */
    private int statue = 0;

    /**
     * 订购的所有商品
     */
    private RealmList<GoodsRealm> goodsList;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        if (!TextUtils.isEmpty(user)) {
            userPY = String.valueOf(Pinyin.toPinyin(user.charAt(0)).charAt(0)).toUpperCase();
        }
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public RealmList<GoodsRealm> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(RealmList<GoodsRealm> goodsList) {
        this.goodsList = goodsList;
    }

    public void addGoods(GoodsRealm goods) {
        if (goodsList == null) {
            goodsList = new RealmList<>();
        }
        this.goodsList.add(goods);
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getUserPY() {

        return userPY;
    }

    public void setUserPY(String userPY) {
        this.userPY = userPY;
    }
}
