package demo.nopointer.npPermission.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import npPermission.nopointer.core.RequestPermissionInfo;
import npPermission.nopointer.core.NpPermissionRequester;
import npPermission.nopointer.core.callback.PermissionCallback;
import npPermission.nopointer.log.NpPerLog;


/**
 * Created by nopointer on 2018/9/1.
 * 权限检测器
 */

public abstract class BasePermissionCheckActivity extends BaseActivity implements PermissionCallback {

    private NpPermissionRequester ycPermissionRequester = null;

    public void requestPermission(RequestPermissionInfo requestPermissionInfo) {
        if (requestPermissionInfo == null) {
            NpPerLog.log("权限列表为空，不请求");
            return;
        }
        if (ycPermissionRequester == null) {
            ycPermissionRequester = new NpPermissionRequester(requestPermissionInfo);
        } else {
            ycPermissionRequester.setPermissionInfo(requestPermissionInfo);
        }

        ycPermissionRequester.requestPermission(this, this);
    }

    @Override
    public void initView() {
//        requestPermission(loadPermissionsConfig());
    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ycPermissionRequester == null) return;
        ycPermissionRequester.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }


    @Override
    public void onGetAllPermission() {
        NpPerLog.log("所有权限得到");
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        NpPerLog.log("部分权限得到" + new Gson().toJson(perms));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        NpPerLog.log("部分权限拒绝" + new Gson().toJson(perms));
        if (ycPermissionRequester == null) {
            NpPerLog.log("ycPermissionRequester==null:" + (ycPermissionRequester == null));
            return;
        }
        RequestPermissionInfo requestPermissionInfo = ycPermissionRequester.getPermissionInfo();
        if (requestPermissionInfo != null && !TextUtils.isEmpty(requestPermissionInfo.getAgainPermissionMessage())) {
            ycPermissionRequester.checkDeniedPermissionsNeverAskAgain(this, Arrays.asList(requestPermissionInfo.getPermissionArr()));
            NpPerLog.log("继续请求");
        }
    }
}
