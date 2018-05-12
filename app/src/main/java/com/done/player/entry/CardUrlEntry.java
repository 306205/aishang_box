package com.done.player.entry;

/**
 * Created by XDONE on 2018/1/24.
 */

public class CardUrlEntry {


    /**
     * code : 1
     * card_url : {"monthCard":"https://www.baidu.com","quarterCard":"https://www.baidu.com","half_yearCard":"https://www.baidu.com","yearCard":"https://www.baidu.com","longCard":"https://www.baidu.com"}
     * msg : 获取发卡链接成功
     */

    private int code;
    private CardUrlBean card_url;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CardUrlBean getCard_url() {
        return card_url;
    }

    public void setCard_url(CardUrlBean card_url) {
        this.card_url = card_url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class CardUrlBean {
        /**
         * monthCard : https://www.baidu.com
         * quarterCard : https://www.baidu.com
         * half_yearCard : https://www.baidu.com
         * yearCard : https://www.baidu.com
         * longCard : https://www.baidu.com
         */

        private String monthCard;
        private String quarterCard;
        private String half_yearCard;
        private String yearCard;
        private String longCard;

        public String getMonthCard() {
            return monthCard;
        }

        public void setMonthCard(String monthCard) {
            this.monthCard = monthCard;
        }

        public String getQuarterCard() {
            return quarterCard;
        }

        public void setQuarterCard(String quarterCard) {
            this.quarterCard = quarterCard;
        }

        public String getHalf_yearCard() {
            return half_yearCard;
        }

        public void setHalf_yearCard(String half_yearCard) {
            this.half_yearCard = half_yearCard;
        }

        public String getYearCard() {
            return yearCard;
        }

        public void setYearCard(String yearCard) {
            this.yearCard = yearCard;
        }

        public String getLongCard() {
            return longCard;
        }

        public void setLongCard(String longCard) {
            this.longCard = longCard;
        }
    }
}
