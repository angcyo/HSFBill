package com.angcyo.hsfbill.realm;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import io.realm.RealmObject;

/**
 * Created by angcyo on 2018/02/14 16:13
 * <p>
 * 每个商品的属性
 */
public class GoodsRealm extends RealmObject {
    /**
     * 商品名称
     */
    private String name = "";
    /**
     * 首字母拼音
     */
    private String namePY = "";
    /**
     * 单位
     */
    private String unit = "";
    private String unitPY = "";

    /**
     * 数量
     */
    private int num = 0;

    /**
     * 价格
     */
    private float price = 0f;
    /**
     * 批发价
     */
    private float tradePrice = 0f;

    /**
     * 备用扩展属性
     */
    private String ext1 = ""; //商品备注信息
    private String ext2 = "";
    private String ext3 = "";

    /**
     * 创建时间
     */
    private long createTime = System.currentTimeMillis();

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (!TextUtils.isEmpty(name)) {
            namePY = String.valueOf(Pinyin.toPinyin(name.charAt(0)).charAt(0)).toUpperCase();
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        if (!TextUtils.isEmpty(unit)) {
            unitPY = String.valueOf(Pinyin.toPinyin(unit.charAt(0)).charAt(0)).toUpperCase();
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(float tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getNamePY() {
        return namePY;
    }

    public void setNamePY(String namePY) {
        this.namePY = namePY;
    }

    public String getUnitPY() {
        return unitPY;
    }

    public void setUnitPY(String unitPY) {
        this.unitPY = unitPY;
    }
}
