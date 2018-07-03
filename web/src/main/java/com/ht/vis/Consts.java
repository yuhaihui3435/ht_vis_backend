package com.ht.vis;

import org.jsoup.safety.Whitelist;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * 常量类
 */
public interface Consts {

    Whitelist basicWithImages = Whitelist.basicWithImages();

    String ENCRYPT_KEY = "ht_vis-never-late";

    String USER_ACCESS_TOKEN = "uic-ht_vis";

    int COOKIE_TIMEOUT = 4 * 60 * 60;

    int COOKIE_FOREVER = 24 * 60 * 60 * 6 * 365 * 50;

    String CURR_USER = "currUser";

    String CURR_USER_CINFO = "currUserCInfo";

    String CURR_USER_MER = "currUserMer";

    String CURR_USER_ROLES = "currUserRoles";

    String CURR_USER_RESES = "currUserReses";

    String CURR_USER_SERS = "currUserSers";

    String BLANK = "";

    String APP_TOKEN="系统操作";

    BigDecimal ZERO = new BigDecimal(0);

    String CURR_USER_COOKIE="currUserCookie";

    String NON_SET="未设置";

    String UN_KNOW="未知";

    String USER_ONLINE_TACTICS_LOGIN_FIRST="loginFirst";

    String USER_ONLINE_TACTICS_AFTER_LOGGING_IN="afterLoggingIn";

    DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.00");


    enum YORN {
        yes(true), no(false);
        boolean val;

        private YORN(boolean val) {
            this.val = val;
        }

        public String getLabel() {
            return (val) ? "否" : "是";
        }

        public boolean isVal() {
            return val;
        }
    }

    enum YORN_STR {
        yes("0"), no("1");
        String val;

        private YORN_STR(String val) {
            this.val = val;
        }

        public String getLabel() {
            return (val.equals("0")) ? "是" : "否";
        }

        public String getVal() {
            return val;
        }
    }
    enum STATUS {
        enable("0"), forbidden("1");
        String val;

        private STATUS(String val) {
            this.val = val;
        }

        public String getVal() {
            return this.val;
        }

        public String getValTxt() {
            return (val.equals("0") ? "正常" : "禁用");
        }
    }

    enum REQ_JSON_CODE {
        success, fail, unauthorized;
    }

    enum CHECK_STATUS {
        normal("00"), waitingCheck("01"), revokeCheck("02");
        String val;

        private CHECK_STATUS(String val) {
            this.val = val;
        }

        public String getVal() {
            return this.val;
        }

        public String getValTxt() {
            if (val.equals("00")) {
                return "正常";
            } else if (val.equals("01")) {
                return "等待审批";
            } else if (val.equals("02")) {
                return "未通过审批";
            }
            return "";
        }
    }

    enum CACHE_NAMES {
        paramCache, userRoles, user, userReses, dd, login,userSers,allSer,cInfo
    }

}