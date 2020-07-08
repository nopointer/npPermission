package demo.nopointer.npPermission;

import android.Manifest;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import demo.nopointer.npPermission.base.BasePermissionCheckFragment;
import npPermission.nopointer.log.NpPerLog;
import simaple.ycPermission.R;
import npPermission.nopointer.core.RequestPermissionInfo;
import npPermission.nopointer.utils.PermissionPageUtils;

public class MainFragment extends BasePermissionCheckFragment {

    @Override
    protected int loadLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        super.initView();
//        requestPermission(loadPermissionsConfig());

        rootView.findViewById(R.id.permission_setting_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionPageUtils.jumpPermissionPage(getActivity());
            }
        });
    }


    @Override
    protected RequestPermissionInfo loadPermissionsConfig() {
        RequestPermissionInfo requestPermissionInfo = new RequestPermissionInfo();
        requestPermissionInfo.setPermissionMessage("请授权这些权限");
        requestPermissionInfo.setPermissionCancelText("取消");
        requestPermissionInfo.setPermissionSureText("确定");
        requestPermissionInfo.setAgainPermissionMessage("需要授权啊");
        requestPermissionInfo.setAgainPermissionTitle("11");
        requestPermissionInfo.setAgainPermissionCancelText("取消");
        requestPermissionInfo.setAgainPermissionSureText("确定");

        requestPermissionInfo.setPermissionArr(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
        return requestPermissionInfo;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        NpPerLog.log("获取到了部分的权限" + new Gson().toJson(perms) + this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        NpPerLog.log("拒绝了部分的权限" + new Gson().toJson(perms) + this);
    }

    @Override
    public void onGetAllPermission() {
        super.onGetAllPermission();
        NpPerLog.log("获取到了所有的权限" + this);
    }


}
