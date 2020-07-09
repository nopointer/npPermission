package demo.nopointer.npPermission;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import demo.nopointer.npPermission.base.BasePermissionCheckActivity;
import npPermission.nopointer.core.RequestPermissionInfo;
import npPermission.nopointer.core.callback.PermissionDialogCallback;
import npPermission.nopointer.log.NpPerLog;
import simaple.ycPermission.R;


public class MainActivity extends BasePermissionCheckActivity {


    @Override
    public int loadLayout() {
        return R.layout.activity_main;
    }

    //适配器
    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    public void initView() {
        super.initView();

        //viewpager
        ycViewPager = findViewById(R.id.main_viewPage);

        initViewPager();

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return progressPageList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return progressPageList.get(position);
            }
        };
        ycViewPager.setAdapter(fragmentPagerAdapter);
        ycViewPager.setOffscreenPageLimit(3);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        requestPermission();

    }



    protected void requestPermission() {
        RequestPermissionInfo requestPermissionInfo = new RequestPermissionInfo();
        requestPermissionInfo.setPermissionTitle("title");
        requestPermissionInfo.setPermissionMessage("请授权这些权限");
        requestPermissionInfo.setPermissionCancelText("取消");
        requestPermissionInfo.setPermissionSureText("确定");

        requestPermissionInfo.setAgainPermissionMessage("需要授权啊 agin");
        requestPermissionInfo.setAgainPermissionTitle("11 agin" );
        requestPermissionInfo.setAgainPermissionCancelText("取消");
        requestPermissionInfo.setAgainPermissionSureText("确定");

        requestPermissionInfo.setPermissionArr(new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG
        });

        requestPermissionInfo.setPermissionDialogCallback(new PermissionDialogCallback() {
            @Override
            public void onCancel(boolean isAgainAsk) {
                NpPerLog.log("取消确认授权的dialog");
            }
        });
        requestPermission(requestPermissionInfo);
    }



    //自定义Viewpager，不可左右滑动
    private ViewPager ycViewPager;
    //所有的Fragment
    private ArrayList<Fragment> progressPageList = new ArrayList<>();


    /**
     * 初始化子页面
     */
    private void initViewPager() {
        progressPageList.clear();
        //添加子组件
        progressPageList.add(new MainFragment());
//        progressPageList.add(new MainFragment());
//        progressPageList.add(new MainFragment());
    }


}
