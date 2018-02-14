package com.angcyo.hsfbill.realm;

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
     * 单位
     */
    private String unit = "";

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
    private String ext1 = "";
    private String ext2 = "";
    private String ext3 = "";

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
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}
