package fcn.co.jp.park.model.me;
/**
 * 查看个人报修列表Model
 */
public class MeRepairModel {
    /**
     * 报修ID
     * 报修电话
     * 报修姓名
     * 报修地址
     * 报修内容
     * 报修时间
     * 报修状态
     */
    private String repairPhone;
    private String repairName;
    private String repairId;
    private String repairAddress;
    private String repairContent;
    private String repairTime;
    private String repairState;

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairState() {
        return repairState;
    }

    public void setRepairState(String repairState) {
        this.repairState = repairState;
    }

    public String getRepairPhone() {
        return repairPhone;
    }

    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }
}
