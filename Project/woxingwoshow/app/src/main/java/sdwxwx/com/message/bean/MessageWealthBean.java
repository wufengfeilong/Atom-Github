package sdwxwx.com.message.bean;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageWealthBean {
    int total_wealth;    //	推荐财富的总值。
    int today_wealth;    //	今日增长的财富值。
    String initial_wealth;    //	原始财富值。
    int recommend1_wealth;    //	发展一级下线得到的财富值总和。
    int recommend2_wealth;    //	发展二级下线得到的财富值总和。

    public int getTotal_wealth() {
        return total_wealth;
    }

    public void setTotal_wealth(int total_wealth) {
        this.total_wealth = total_wealth;
    }

    public int getToday_wealth() {
        return today_wealth;
    }

    public void setToday_wealth(int today_wealth) {
        this.today_wealth = today_wealth;
    }

    public String getInitial_wealth() {
        return initial_wealth;
    }

    public void setInitial_wealth(String initial_wealth) {
        this.initial_wealth = initial_wealth;
    }

    public int getRecommend1_wealth() {
        return recommend1_wealth;
    }

    public void setRecommend1_wealth(int recommend1_wealth) {
        this.recommend1_wealth = recommend1_wealth;
    }

    public int getRecommend2_wealth() {
        return recommend2_wealth;
    }

    public void setRecommend2_wealth(int recommend2_wealth) {
        this.recommend2_wealth = recommend2_wealth;
    }
}
