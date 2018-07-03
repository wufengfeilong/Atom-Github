package sdwxwx.com.me.bean;

/**
 * create by 860115039
 * date      2018/6/5
 * time      11:43
 */
public class RecommendWealthBean {

    String total_wealth;    //	推荐财富的总值。
    String today_wealth;    //	今日增长的财富值。
    String initial_wealth;    //	原始财富值。
    String recommend1_wealth;    //	发展一级下线得到的财富值总和。
    String recommend2_wealth;    //	发展二级下线得到的财富值总和。

    public String getTotal_wealth() {
        return total_wealth;
    }

    public void setTotal_wealth(String total_wealth) {
        this.total_wealth = total_wealth;
    }

    public String getToday_wealth() {
        return today_wealth;
    }

    public void setToday_wealth(String today_wealth) {
        this.today_wealth = today_wealth;
    }

    public String getInitial_wealth() {
        return initial_wealth;
    }

    public void setInitial_wealth(String initial_wealth) {
        this.initial_wealth = initial_wealth;
    }

    public String getRecommend1_wealth() {
        return recommend1_wealth;
    }

    public void setRecommend1_wealth(String recommend1_wealth) {
        this.recommend1_wealth = recommend1_wealth;
    }

    public String getRecommend2_wealth() {
        return recommend2_wealth;
    }

    public void setRecommend2_wealth(String recommend2_wealth) {
        this.recommend2_wealth = recommend2_wealth;
    }
}
