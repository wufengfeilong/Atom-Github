package woxingwoxiu.com.message.bean;

/**
 * Created by 860117066 on 2018/05/15.
 * 类描述：好友举报原因
 */

public class MessageReportBean {
    /*举报id，举报原因*/
    private int reportId;
    private String reportReason;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }
}
