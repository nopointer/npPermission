package npPermission.nopointer.log;

import android.text.TextUtils;
import android.util.Log;

public class NpPerLog {


    public static void log(String message) {
        if (mNpPerLogPrinter != null) {
            log(mNpPerLogPrinter.initTag(), message);
        } else {
            log("NpPerLog", message);
        }

    }

    public static void log(String tag, String message) {
        if (TextUtils.isEmpty(tag)) {
            tag = "NpPerLog";
        }
        if (mNpPerLogPrinter == null) {
            Log.e(tag, message);
        } else {
            mNpPerLogPrinter.onLogPrint(tag, message);
        }
    }

    private static NpPerLogPrinter mNpPerLogPrinter;

    public static void setNpPerLogPrinter(NpPerLogPrinter NpPerLogPrinter) {
        mNpPerLogPrinter = NpPerLogPrinter;
    }

    public static interface NpPerLogPrinter {
        void onLogPrint(String message);

        void onLogPrint(String tag, String message);

        String initTag();
    }


}
