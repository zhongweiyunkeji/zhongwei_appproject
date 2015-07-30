package com.cdhxqh.travel_ticket_app.utils;

import android.content.Context;

import com.cdhxqh.travel_ticket_app.model.Ec_user;
import com.cdhxqh.travel_ticket_app.model.PersistenceHelper;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 登录帐号管理Created by yw on 2015/5/5.
 */
public class AccountUtils {

    public static final int REQUEST_LOGIN = 0;

    private static final String key_login_member = "logined@member";
    private static final String key_fav_nodes = "logined@fav_nodes";

    /**
     * 帐号登陆登出监听接口
     */
    public static interface OnAccountListener{
        abstract public void onLogout();

        abstract public void onLogin(Ec_user member);
    }

    private static HashSet<OnAccountListener> listeners = new HashSet<OnAccountListener>();

    /**
     * 注册登录接口
     * @param listener
     */
    public static void registerAccountListener(OnAccountListener listener){
        listeners.add(listener);
    }

    /**
     * 取消登录接口的注册
     * @param listener
     */
    public static void unregisterAccountListener(OnAccountListener listener){
        listeners.remove(listener);
    }

    /**
     * 用户是否已经登录
     * @param cxt
     * @return
     */
    public static boolean isLogined(Context cxt) {
        return FileUtils.isExistDataCache(cxt, key_login_member);
    }

    /**
     * 保存登录用户资料
     * @param cxt
     * @param profile
     */
    public static void writeLoginMember(Context cxt, Ec_user profile) {
        PersistenceHelper.saveModel(cxt, profile, key_login_member);

        //通知所有页面,登录成功,更新用户信息
        Iterator<OnAccountListener> iterator = listeners.iterator();
        while(iterator.hasNext()){
            OnAccountListener listener = iterator.next();
            listener.onLogin(profile);
        }
    }

    /**
     * 获取登录用户信息
     * @param cxt
     * @return
     */
    public static Ec_user readLoginMember(Context cxt) {
        return PersistenceHelper.loadModel(cxt, key_login_member);
    }

    /**
     * 删除登录用户资料
     * @param cxt
     */
    public static void removeLoginMember(Context cxt) {
        File data = cxt.getFileStreamPath(key_login_member);
        data.delete();
    }



    /**
     * 删除节点信息
     * @param cxt
     */
    public static void removeFavNodes(Context cxt) {
        File data = cxt.getFileStreamPath(key_fav_nodes);
        data.delete();
    }

    /**
     * 清除所有用户相关资料
     * @param cxt
     */
    public static void removeAll(Context cxt) {
        removeLoginMember(cxt);
        removeFavNodes(cxt);

        //通知所有页面退出登录了,清除登录痕迹
        Iterator<OnAccountListener> iterator = listeners.iterator();
        while(iterator.hasNext()){
            OnAccountListener listener = iterator.next();
            listener.onLogout();
        }
    }





    public static interface OnAccountNotificationCountListener{
        public void onAccountNotificationCount(int count);
    }


}
