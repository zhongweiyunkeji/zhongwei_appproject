package com.cdhxqh.travel_ticket_app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.cdhxqh.travel_ticket_app.ui.activity.BaseActivity;


/**
 * Created by yw on 2015/5/3.
 */
public class BaseFragment extends Fragment {

    protected boolean mIsLogin;
//    protected BackHandledInterface mBackHandledInterface;

//    protected FootUpdate mFootUpdate = new FootUpdate();

    public static interface BackHandledInterface {
        public abstract void setSelectedFragment(BaseFragment selectedFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mIsLogin = AccountUtils.isLogined(getActivity());
//        if (mIsLogin)
//            mLoginProfile = AccountUtils.readLoginMember(getActivity());
//        AccountUtils.registerAccountListener(this);

//        mBackHandledInterface = (BackHandledInterface) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
//        //告诉FragmentActivity，当前Fragment在栈顶
//        mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.toString());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.toString());
    }

    @Override
    public void onDestroy() {
//        AccountUtils.unregisterAccountListener(this);
        super.onDestroy();
    }


    public boolean onBackPressed() {
        return false;
    }


    final public BaseActivity getBaseActivity() {
        return ((BaseActivity) super.getActivity());
    }

}
