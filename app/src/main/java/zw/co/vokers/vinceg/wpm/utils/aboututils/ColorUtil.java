package zw.co.vokers.vinceg.wpm.utils.aboututils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by Vince G on 2/1/2019
 */

public final class ColorUtil {
    public static int get(@NonNull Context context, int res) {
        try {
            return ContextCompat.getColor(context, res);
        } catch (Resources.NotFoundException e) {
            return res;
        }
    }
}
