package com.fujisoft.storage.bean;

/**
 * Goods情報Bean
 */
public class Goods {
    // ID
    private int id;
    // 品名
    private String goodsName;
    // コード
    private String barcode;
    // 説明
    private String description;

    /**
     * コンストラクタ
     * @param goodsName 物品名称
     * @param barcode バーコード
     * @param description 物品詳細
     */
    public Goods(String goodsName, String barcode, String description) {
        this.goodsName = goodsName;
        this.barcode = barcode;
        this.description = description;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 文字列
     */
    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", barcode='" + barcode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}