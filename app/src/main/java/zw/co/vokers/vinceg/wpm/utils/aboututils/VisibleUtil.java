package zw.co.vokers.vinceg.wpm.utils.aboututils;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Vince G on 2/1/2019
 */

public final class VisibleUtil {
    public static void handle(View v, @Nullable String s) {
        v.setVisibility(s == null || s.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
