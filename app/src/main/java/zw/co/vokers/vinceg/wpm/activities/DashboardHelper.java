package zw.co.vokers.vinceg.wpm.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.utils.aboututils.AboutBuilder;
import zw.co.vokers.vinceg.wpm.utils.aboututils.AboutView;

/**
 * Created by Vince G on 2/1/2019
 */

public class DashboardHelper implements View.OnClickListener {

    private Activity activity;
    private int theme = R.style.AppThemeDark;

    private DashboardHelper(Activity activity) {
        this.activity = activity;
    }

    public static DashboardHelper with(Activity activity) {
        return new DashboardHelper(activity);
    }

    public DashboardHelper init() {
        activity.setTheme(theme);

       /* activity.findViewById(R.id.dark).setOnClickListener(this);
        activity.findViewById(R.id.light).setOnClickListener(this);
        activity.findViewById(R.id.custom).setOnClickListener(this);
*/
        return this;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.dark:
                if (theme != R.style.AppThemeDark) {
                    theme = R.style.AppThemeDark;
                    activity.recreate();
                }
                break;
            case R.id.light:
                if (theme != R.style.AppThemeLight) {
                    theme = R.style.AppThemeLight;
                    activity.recreate();
                }
                break;

            case R.id.custom:
                if (theme != R.style.AppThemeCustom) {
                    theme = R.style.AppThemeCustom;
                    activity.recreate();
                }
                break;

            default:
                break;*/
        }
    }
}

