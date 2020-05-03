package npPermission.nopointer.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import npPermission.nopointer.core.callback.PermissionCallback;
import npPermission.nopointer.log.ycPerLog;

/**
 * Created by nopointer on 2018/8/21.
 * 抽象的权限管理方法
 */

abstract class AbsPermsRequester {

    private RequestPermissionInfo permissionInfo = null;

    public AbsPermsRequester(RequestPermissionInfo permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    public void setPermissionInfo(RequestPermissionInfo permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    public RequestPermissionInfo getPermissionInfo() {
        return permissionInfo;
    }

    protected abstract void cfgPermissionInfoDialog(Activity activity, RequestPermissionInfo permissionInfo);

    protected abstract void cfgPermissionInfoDialogForNeverAsk(Activity activity, RequestPermissionInfo permissionInfo, List<String> permissionArr);

    protected abstract void cfgPermissionInfoDialog(Fragment fragment, RequestPermissionInfo permissionInfo);

    protected abstract void cfgPermissionInfoDialogForNeverAsk(Fragment fragment, RequestPermissionInfo permissionInfo, List<String> permissionArr);

    protected abstract void cfgPermissionInfoDialog(android.app.Fragment fragment, RequestPermissionInfo permissionInfo);

    protected abstract void cfgPermissionInfoDialogForNeverAsk(android.app.Fragment fragment, RequestPermissionInfo permissionInfo, List<String> permissionArr);


    //在activty里面请求权限
    public <T extends Activity> void requestPermission(T activity, PermissionCallback permissionCallback) {
        if (permissionInfo == null || permissionInfo.getPermissionArr() == null) {
            ycPerLog.e("权限请求的内容为空！！！");
            return;
        }
        if (hasPermissions(activity, permissionInfo.getPermissionArr())) {
            if (permissionCallback != null) {
                permissionCallback.onGetAllPermission();
            }
        } else {
            requestPermissions(activity, permissionInfo);
        }
    }

    //在fragment里面请求权限
    public <T extends Fragment> void requestPermission(T fragment, PermissionCallback permissionCallback) {
        ycPerLog.e("permissionInfo==>" + permissionInfo.toString());

        if (permissionInfo == null || permissionInfo.getPermissionArr() == null) {
            ycPerLog.e("权限请求的内容为空！！！");
            return;
        }
        if (hasPermissions(fragment, permissionInfo.getPermissionArr())) {
            if (permissionCallback != null) {
                permissionCallback.onGetAllPermission();
            }
        } else {
            requestPermissions(fragment, permissionInfo);
        }
    }

    /**
     * 请求权限
     */
    protected void requestPermissions(final Object object, RequestPermissionInfo permissionInfo) {
        checkCallingObjectSuitability(object);
        boolean shouldShowRationale = false;
        for (String perm : permissionInfo.getPermissionArr()) {
            shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
        }
        if (shouldShowRationale) {

            if (object instanceof Activity) {
                cfgPermissionInfoDialog((Activity) object, permissionInfo);
            } else if (object instanceof Fragment) {
                cfgPermissionInfoDialog((Fragment) object, permissionInfo);
            } else if (object instanceof android.app.Fragment) {
                cfgPermissionInfoDialog((android.app.Fragment) object, permissionInfo);
            }
        } else {
            executePermissionsRequest(object, permissionInfo.getPermissionArr(), permissionInfo.getRequestCode());
        }
    }


    public boolean checkDeniedPermissionsNeverAskAgain(final Object object, List<String> deniedPerms) {
        boolean shouldShowRationale;
        for (String perm : deniedPerms) {
            shouldShowRationale = shouldShowRequestPermissionRationale(object, perm);
            if (!shouldShowRationale) {

                if (object == null) return true;
                if (object instanceof Activity) {
                    cfgPermissionInfoDialogForNeverAsk((Activity) object, permissionInfo, deniedPerms);
                } else if (object instanceof Fragment) {
                    cfgPermissionInfoDialogForNeverAsk((Fragment) object, permissionInfo, deniedPerms);
                } else if (object instanceof android.app.Fragment) {
                    cfgPermissionInfoDialogForNeverAsk((android.app.Fragment) object, permissionInfo, deniedPerms);
                }
                return true;
            }
        }

        return false;
    }


    /**
     * 判断是否有该项或者多项权限
     *
     * @param context
     * @param perms
     * @return
     */
    public static boolean hasPermissions(Context context, String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否有该项或者多项权限
     *
     * @param context
     * @param perms
     * @return
     */
    public static boolean hasPermissions(Fragment context, String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context.getContext(), perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }


    @TargetApi(23)
    protected static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }


//    @TargetApi(11)
//    protected static Activity getActivity(Object object) {
//        if (object instanceof Activity) {
//            return ((Activity) object);
//        } else if (object instanceof Fragment) {
//            return ((Fragment) object).getActivity();
//        } else if (object instanceof android.app.Fragment) {
//            return ((android.app.Fragment) object).getActivity();
//        } else {
//            return null;
//        }
//    }


    @TargetApi(11)
    protected static void startAppSettingsScreen(Object object, Intent intent, int requestCode) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 发起权限申请
     *
     * @param object
     * @param perms
     * @param requestCode
     */
    @TargetApi(23)
    protected static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    //检查当前申请权限的activity或者fragment是否合法
    protected static void checkCallingObjectSuitability(Object object) {
        // Make sure Object is an Activity or Fragment
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    /**
     *
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults, PermissionCallback permissionCallback) {
        checkCallingObjectSuitability(activity);
        // Make a collection of granted and denied permissions from the request.
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // Report granted permissions, if any.
        if (!granted.isEmpty()) {
            // Notify callbacks
            if (permissionCallback != null) {
                permissionCallback.onPermissionsGranted(requestCode, granted);
            }
        }

        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            if (permissionCallback != null) {
                permissionCallback.onPermissionsDenied(requestCode, denied);
            }
        }

        // If 100% successful, call annotated methods
        if (!granted.isEmpty() && denied.isEmpty()) {
            if (permissionCallback != null)
                permissionCallback.onGetAllPermission();
        }
    }

    /**
     *
     */
    public void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults, PermissionCallback permissionCallback) {
        checkCallingObjectSuitability(fragment);
        // Make a collection of granted and denied permissions from the request.
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // Report granted permissions, if any.
        if (!granted.isEmpty()) {
            // Notify callbacks
            if (permissionCallback != null) {
                permissionCallback.onPermissionsGranted(requestCode, granted);
            }
        }

        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            if (permissionCallback != null) {
                permissionCallback.onPermissionsDenied(requestCode, denied);
            }
        }

        // If 100% successful, call annotated methods
        if (!granted.isEmpty() && denied.isEmpty()) {
            if (permissionCallback != null)
                permissionCallback.onGetAllPermission();
        }
    }
}
