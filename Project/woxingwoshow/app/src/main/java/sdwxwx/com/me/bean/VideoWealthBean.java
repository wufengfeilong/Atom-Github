package sdwxwx.com.me.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/6/5
 * time      11:43
 */
public class VideoWealthBean {
    String total_wealth;    // 视频财富的总值。
    String today_wealth;    //	今日增长的财富值。
    List<WealthHistoryBean> history = new ArrayList<>();    //	财富记录数组。

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

    public List<WealthHistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<WealthHistoryBean> history) {
        this.history = history;
    }

    public static class WealthHistoryBean {
        String action;    //	行为描述。
        String wealth;    //	财富值。
        String create_time;    //	创建时间，格式为yyyy-MM-dd HH:mm:ss。


        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getWealth() {
            return wealth;
        }

        public void setWealth(String wealth) {
            this.wealth = wealth;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
