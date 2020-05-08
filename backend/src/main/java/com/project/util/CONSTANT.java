package com.project.util;

/**
 * Created by DongBaishun on 2017/7/13.
 */
public class CONSTANT {
    //public static String FILE_PATH = "C:\\Users\\zheng\\Desktop";//"/usr/local/Java/apache-tomcat-7.0.81/upload/";
    public static String FILE_PATH = "/usr/local/upload/";
    public static String JURA_URI ="http://166.111.80.117:8443";
    public static String JURA_QUERY ="whoami=jura";
    public static String NGINX_BASE_PATH = "http://localhost/";
    public static String WS_HOST = "http://localhost";
    public static String CONN_HOST = "127.0.0.1";
    public static int CONN_PORT = 8888;
    //public static String WS_HOST = "http://166.111.80.117";
    public static String LOG_PATH = "/usr/local/Java/log/";
    public static String LOG_FILENAME = "log.log";
    public static String NGINX_PATH = "http://localhost/lora/";
    // public static String FILE_PATH = "D:\\upload\\";
    public static String MQTT_PATH = "/usr/bin/mosquitto_sub";
    public static String PING_PATH = "ping";
    // public static String PING_PATH = "C:\\Windows\\System32\\ping.exe";

    public static String GD_SOCKET_HOST = "127.0.0.1";
    public static String pattern = "4786e6ed1138115a";
    public static String LINUX_PATH_SLASH = "/";

    public static String UNIX_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String UNIX_ZERO_TIMESTAMP = "1970-01-01 08:00:00";

    /**
     * node state
     */
    public static final int STATE_CLOSE = 0; //关闭
    public static final int STATE_AVIVALABLE = 1; //可用
    public static final int STATE_ORDER = 2; //被用户预定的
    public static final int STATE_RUNNING = 3; //运行的
    public static final int STATE_SUSPEND = 4; //暂停的
    public static final int STATE_BURNING = 5; //烧录中
    public static final int STATE_GATEWAY = -1; //网关

    /**
     * Web Info id
     */
    public static final int WEBINFO_ID = 1;
    public static final int DATA_PAGE_ITEMS = 10;

    /**
     * HTTP Session中的用户名 Key
     */
    public static final String DEFAULT_SESSION_USERNAME = "username";
    /**
     * WEBSOCKET Session中的用户名 Key
     */
    public static String DEFAULT_WEBSOCKET_USERNAME = "ws_username";

    public static String BURN_FAILURE(int count) {
        return "很抱歉，只成功烧录" + count + "个节点";
    }

    public static String BURN_SUCCESS(int num) {
        return "成功烧录" + num + "个节点！";
    }

    public static final int TIMEORDER_AVAILABLE = 1; //预订通过
    public static final int TIMOEORDER_PASSING = 0; //待审核

    /**
     * WEBSOCKET Node的运行状态
     */

    public static final int NODE_OFFLINE = 0; // node离线
    public static final int NODE_CLEAN = 1; // node擦除
    public static final int NODE_RUNNING = 2; // node运行
    public static final int NODE_IDLE = 3; // node静止
    public static final int NODE_BURNING = 4; // node烧录

    /**
     * WEBSOCKET node的初始化经纬度
     */

    public static final double NODE_LONGITUDE = 116.3413;
    public static final double NODE_INIT_LATITUDE = 40.0066;

    /**
     * WEBSOCKET timeOrder的状态
     */

    public static final int ORDER_VERIFY = 0; // 预约待审批
    public static final int ORDER_SUCCESS = 1; // 预约成功
    public static final int ORDER_FAILED = 2; // 预约失败
    public static final int ORDER_OUTTIME = 3; // 预约过期

}
