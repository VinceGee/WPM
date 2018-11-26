package zw.co.vokers.vinceg.wpm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.fragments.AuthAdapter;
import zw.co.vokers.vinceg.wpm.utils.AnimatedViewPager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnimatedViewPager pager= ButterKnife.findById(this,R.id.pager);
        pager.setAdapter(new AuthAdapter(getSupportFragmentManager(),pager));
    }
}

