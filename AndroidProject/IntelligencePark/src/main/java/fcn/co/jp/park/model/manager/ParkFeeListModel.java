package fcn.co.jp.park.model.manager;

public class ParkFeeListModel {

    private String carNumber;  // 车牌号
    private String company;  // 公司名称
    private String parkPay_id;  // 月租车辆id

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getParkPay_id() {
        return parkPay_id;
    }

    public void setParkPay_id(String parkPay_id) {
        this.parkPay_id = parkPay_id;
    }


}
