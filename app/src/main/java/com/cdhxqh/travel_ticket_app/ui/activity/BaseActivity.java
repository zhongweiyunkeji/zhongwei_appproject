package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.cdhxqh.travel_ticket_app.app.AppManager;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Ec_user;
import com.cdhxqh.travel_ticket_app.task.AsyncCallable;
import com.cdhxqh.travel_ticket_app.task.Callback;
import com.cdhxqh.travel_ticket_app.task.EMobileTask;
import com.cdhxqh.travel_ticket_app.task.ProgressCallable;
import com.cdhxqh.travel_ticket_app.utils.AccountUtils;

import java.util.Locale;
import java.util.concurrent.Callable;

public abstract class BaseActivity extends Activity implements AccountUtils.OnAccountListener {

    public static final String TAG = BaseActivity.class.getSimpleName();

    protected Handler mHandler = null;
    protected InputMethodManager imm;
    private TelephonyManager tManager;

    protected boolean mIsLogin;
    protected Ec_user ec_user;

    protected SharedPreferences myshared;


    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        mIsLogin = AccountUtils.isLogined(this);
        if (mIsLogin)
            ec_user = AccountUtils.readLoginMember(this);
        AccountUtils.registerAccountListener(this);

        SDKInitializer.initialize(getApplicationContext());
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        init();
    }

    public void init() {
        myshared = this.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
    }

    @Override
    public void onLogout() {
        mIsLogin = false;
    }

    @Override
    public void onLogin(Ec_user member) {
        mIsLogin = true;
        ec_user = member;
    }


    /**
     * 绑定控件id
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * 加载进度条
     */
    public void showProgressDialog() {
        ProgressDialog progressDialog = null;

        if (progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = new ProgressDialog(this);
//        Drawable drawable=getResources().getDrawable(R.drawable.loading_animation);
//        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("请稍候，正在努力加载。。");
        progressDialog.show();
    }


    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void hideOrShowSoftInput(boolean isShowSoft, EditText editText) {
        if (isShowSoft) {
            imm.showSoftInput(editText, 0);
        } else {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    //获得当前程序版本信息
    protected String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }


    //獲得設備信息
    protected String getDeviceId() throws Exception {
        String deviceId = tManager.getDeviceId();

        return deviceId;

    }


    /**
     * @param <T>       模板参数，操作时要返回的内容
     * @param pCallable 需要异步调用的操作
     * @param pCallback 回调
     */
    protected <T> void doAsync(final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback, final boolean showDialog, String message) {
        EMobileTask.doAsync(this, null, message, pCallable, pCallback, pExceptionCallback, false, showDialog);
    }

    protected <T> void doAsync(final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback, final boolean showDialog) {
        EMobileTask.doAsync(this, pTitle, pMessage, pCallable, pCallback, null, false, showDialog);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog},
     * while the {@link Callable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable, final Callback<T> pCallback) {
        this.doAsync(pTitleResID, pMessageResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a indeterminate
     * {@link ProgressDialog}, while the {@link Callable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doAsync(this, pTitleResID, pMessageResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog} with
     * an ProgressBar, while the {@link AsyncCallable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pCallback
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
        this.doProgressAsync(pTitleResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog} with
     * a ProgressBar, while the {@link AsyncCallable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doProgressAsync(this, pTitleResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing an indeterminate
     * {@link ProgressDialog}, while the {@link AsyncCallable} is being
     * processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pAsyncCallable
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doAsync(this, pTitleResID, pMessageResID, pAsyncCallable, pCallback, pExceptionCallback);
    }


    /**
     * 退出登陆*
     */
    public void exit(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(context);
        }
    }

}
