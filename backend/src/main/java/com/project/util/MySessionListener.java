package com.project.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by DongBaishun on 2017/8/2.
 */
public class MySessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
        //OnlineCounter.raise();
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //OnlineCounter.reduce();
    }
}
