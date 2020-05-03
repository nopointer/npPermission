package npPermission.nopointer.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by nopointer on 2018/8/20.
 * 原驰网络权限的请求
 */

public class NpPermissionRequester extends AbsPermsRequester {

    public NpPermissionRequester(RequestPermissionInfo permissionInfo) {
        super(permissionInfo);
    }



    @Override
    protected void cfgPermissionInfoDialog(final Activity activity, final RequestPermissionInfo permissionInfo) {
        AlertDialog firstDialog = new AlertDialog.Builder(activity)
                .setPositiveButton(permissionInfo.getPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executePermissionsRequest(activity, permissionInfo.getPermissionArr(), permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(false);
                        }
                    }
                }).create();
        Log.e("fuck,firstDialog", firstDialog.toString());
        Log.e("fuck,permissionInfo", permissionInfo +"///"+ permissionInfo.toString());
        firstDialog.setCancelable(false);
        firstDialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(permissionInfo.getPermissionTitle())) {
            firstDialog.setTitle(permissionInfo.getPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getPermissionMessage())) {
            firstDialog.setMessage(permissionInfo.getPermissionMessage());
        }
        firstDialog.show();
    }

    @Override
    protected void cfgPermissionInfoDialogForNeverAsk(final Activity activity, final RequestPermissionInfo permissionInfo, List<String> permissionArr) {
        AlertDialog aginDialog = new AlertDialog.Builder(activity)
                .setPositiveButton(permissionInfo.getAgainPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        startAppSettingsScreen(activity, intent, permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(true);
                        }
                    }
                })
//                    .setNegativeButton(permissionInfo.getAgainPermissionCancelText(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
//                                permissionInfo.getPermissionDialogCallback().onCancel(true);
//                            }
//
//                        }
//                    })
                .create();
        aginDialog.setCancelable(false);
        aginDialog.setCanceledOnTouchOutside(false);
        aginDialog.show();
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionTitle())) {
            aginDialog.setTitle(permissionInfo.getAgainPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionMessage())) {
            aginDialog.setMessage(permissionInfo.getAgainPermissionMessage());
        }
    }


    @Override
    protected void cfgPermissionInfoDialog(final Fragment fragment, final RequestPermissionInfo permissionInfo) {
        AlertDialog firstDialog = new AlertDialog.Builder(fragment.getActivity())
                .setPositiveButton(permissionInfo.getPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executePermissionsRequest(fragment, permissionInfo.getPermissionArr(), permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(false);
                        }
                    }
                })
//                    .setNegativeButton(permissionInfo.getPermissionCancelText(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            // act as if the permissions were denied
//                            if (fragment instanceof PermissionCallback) {
//                                ((PermissionCallback) fragment).onPermissionsDenied(permissionInfo.getRequestCode(), Arrays.asList(permissionInfo.getPermissionArr()));
//                            }
//                            if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
//                                permissionInfo.getPermissionDialogCallback().onCancel(false);
//                            }
//                        }
//                    })
                .create();
        if (!TextUtils.isEmpty(permissionInfo.getPermissionTitle())) {
            firstDialog.setTitle(permissionInfo.getPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getPermissionMessage())) {
            firstDialog.setMessage(permissionInfo.getPermissionMessage());
        }
        firstDialog.setCancelable(false);
        firstDialog.setCanceledOnTouchOutside(false);
        firstDialog.show();
    }

    @Override
    protected void cfgPermissionInfoDialogForNeverAsk(final Fragment fragment, final RequestPermissionInfo permissionInfo, List<String> permissionArr) {
        AlertDialog aginDialog = new AlertDialog.Builder(fragment.getActivity())
                .setPositiveButton(permissionInfo.getAgainPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startAppSettingsScreen(fragment, intent, permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(true);
                        }
                    }
                })
//                    .setNegativeButton(permissionInfo.getAgainPermissionCancelText(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
//                                permissionInfo.getPermissionDialogCallback().onCancel(true);
//                            }
//                        }
//                    })
                .create();
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionTitle())) {
            aginDialog.setTitle(permissionInfo.getAgainPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionMessage())) {
            aginDialog.setMessage(permissionInfo.getAgainPermissionMessage());
        }
        aginDialog.setCancelable(false);
        aginDialog.setCanceledOnTouchOutside(false);
        aginDialog.show();
    }


    @Override
    protected void cfgPermissionInfoDialog(final android.app.Fragment fragment, final RequestPermissionInfo permissionInfo) {
        AlertDialog firstDialog = new AlertDialog.Builder(fragment.getActivity())
                .setPositiveButton(permissionInfo.getPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executePermissionsRequest(fragment, permissionInfo.getPermissionArr(), permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(false);
                        }
                    }
                })
//                    .setNegativeButton(permissionInfo.getPermissionCancelText(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            // act as if the permissions were denied
//                            if (fragment instanceof PermissionCallback) {
//                                ((PermissionCallback) fragment).onPermissionsDenied(permissionInfo.getRequestCode(), Arrays.asList(permissionInfo.getPermissionArr()));
//                            }
//                            if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
//                                permissionInfo.getPermissionDialogCallback().onCancel(false);
//                            }
//                        }
//                    })
                .create();
        if (!TextUtils.isEmpty(permissionInfo.getPermissionTitle())) {
            firstDialog.setTitle(permissionInfo.getPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getPermissionMessage())) {
            firstDialog.setMessage(permissionInfo.getPermissionMessage());
        }
        firstDialog.setCancelable(false);
        firstDialog.setCanceledOnTouchOutside(false);
        firstDialog.show();
    }

    @Override
    protected void cfgPermissionInfoDialogForNeverAsk(final android.app.Fragment fragment, final RequestPermissionInfo permissionInfo, List<String> permissionArr) {
        AlertDialog aginDialog = new AlertDialog.Builder(fragment.getActivity())
                .setPositiveButton(permissionInfo.getAgainPermissionSureText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startAppSettingsScreen(fragment, intent, permissionInfo.getRequestCode());
                        if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
                            permissionInfo.getPermissionDialogCallback().onSure(true);
                        }
                    }
                })
//                    .setNegativeButton(permissionInfo.getAgainPermissionCancelText(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            if (permissionInfo != null && permissionInfo.getPermissionDialogCallback() != null) {
//                                permissionInfo.getPermissionDialogCallback().onCancel(true);
//                            }
//                        }
//                    })
                .create();
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionTitle())) {
            aginDialog.setTitle(permissionInfo.getAgainPermissionTitle());
        }
        if (!TextUtils.isEmpty(permissionInfo.getAgainPermissionMessage())) {
            aginDialog.setMessage(permissionInfo.getAgainPermissionMessage());
        }
        aginDialog.setCancelable(false);
        aginDialog.setCanceledOnTouchOutside(false);
        aginDialog.show();
    }


}