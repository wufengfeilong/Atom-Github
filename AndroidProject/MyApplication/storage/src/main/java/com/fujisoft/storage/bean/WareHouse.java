package com.fujisoft.storage.bean;

/**
 * 出入庫情報Bean
 */
public class WareHouse {
    // ID
    private int id;
    // 物品コード
    private int goodsId;
    // 数
    private int amount;
    // 価格
    private int price;
    // 出入庫フラッグ
    private int flg;
    // 生産日
    private String productDate;
    // 出入庫日
    private String dateTime;
    // 単位
    private String unit;
    /**
     * コンストラクタ
     * @param goodsId 物品ID
     * @param amount 件数
     * @param price 価格
     * @param flg フラグ
     * @param productDate 生産日付
     * @param dateTime 日付
     * @param unit 単位
     */
    public WareHouse(int goodsId, int amount, int price, int flg,
                     String productDate, String dateTime, String unit) {
        this.goodsId = goodsId;
        this.amount = amount;
        this.price = price;
        this.flg = flg;
        this.productDate = productDate;
        this.dateTime = dateTime;
        this.unit = unit;
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
     * @return goodsId
     */
    public int getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return flg
     */
    public int getFlg() {
        return flg;
    }

    /**
     * @param flg
     */
    public void setFlg(int flg) {
        this.flg = flg;
    }

    /**
     * @return productDate
     */
    public String getProductDate() {
        return productDate;
    }

    /**
     * @param productDate
     */
    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    /**
     * @return dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * 文字列
     */
    @Override
    public String toString() {
        return "WareHouse{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", amount=" + amount +
                ", price=" + price +
                ", flg=" + flg +
                ", productDate='" + productDate + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
