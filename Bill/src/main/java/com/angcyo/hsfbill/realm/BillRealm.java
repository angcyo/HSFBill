package com.angcyo.hsfbill.realm;

import io.realm.RealmList;

/**
 * Created by angcyo on 2018/02/14 16:17
 * 账单
 */
public class BillRealm {

    /**
     * 账单单位名称
     */
    private String user = "";

    /**
     * 备用扩展属性
     */
    private String ext1 = "";
    private String ext2 = "";
    private String ext3 = "";

    /**
     * 创建时间
     */
    private long createTime = System.currentTimeMillis();

    /**
     * 状态: 完成 未完成 进行中...
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
}
