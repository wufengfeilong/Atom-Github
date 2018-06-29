package com.fujisoft.card;

public class Card {

    /**
     * id : 7
     * interest_id : 6
     * img_name : sltds.PNG
     * content : 酸辣土豆丝
     * isLook : false
     * interest : {"id":6,"interest_name":"美食","level":200}
     */

    private int id;
    private int interest_id;
    private String img_name;
    private String content;
    private boolean isLook;
    private InterestBean interest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(int interest_id) {
        this.interest_id = interest_id;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsLook() {
        return isLook;
    }

    public void setIsLook(boolean isLook) {
        this.isLook = isLook;
    }

    public InterestBean getInterest() {
        return interest;
    }

    public void setInterest(InterestBean interest) {
        this.interest = interest;
    }

    public static class InterestBean {
        /**
         * id : 6
         * interest_name : 美食
         * level : 200
         */

        private int id;
        private String interest_name;
        private int level;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInterest_name() {
            return interest_name;
        }

        public void setInterest_name(String interest_name) {
            this.interest_name = interest_name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
