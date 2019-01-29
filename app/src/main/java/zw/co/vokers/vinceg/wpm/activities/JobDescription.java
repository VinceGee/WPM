package zw.co.vokers.vinceg.wpm.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.models.ItemAbout;
import zw.co.vokers.vinceg.wpm.utils.AppConfig;
import zw.co.vokers.vinceg.wpm.utils.JsonUtils;
import zw.co.vokers.vinceg.wpm.utils.VinceTextView;

/**
 * Created by Vince G on 10/12/2018
 */

public class JobDescription extends AppCompatActivity {

    VinceTextView employeeName, jobTitle, payNumber;
    ImageView imgAppLogo;
    ArrayList<ItemAbout> mListItem;
    ScrollView mScrollView;
    ProgressBar mProgressBar;
    WebView webView;
    JsonUtils jsonUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        jsonUtils = new JsonUtils(this);
        jsonUtils.forceRTLIfSupported(getWindow());

        employeeName = (VinceTextView) findViewById(R.id.text_emp_name);
        jobTitle = (VinceTextView) findViewById(R.id.job_title);
        payNumber = (VinceTextView) findViewById(R.id.pay_number);
        imgAppLogo = (ImageView) findViewById(R.id.image_app_logo);
        webView = (WebView) findViewById(R.id.jobDescWebView);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mListItem = new ArrayList<>();

        if (JsonUtils.isNetworkAvailable(JobDescription.this)) {
            new MyTaskAbout().execute(AppConfig.ABOUT_US_URL);
        }else {
            showToast(getString(R.string.network_msg));
        }
    }

    private class MyTaskAbout extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
            if (null == result || result.length() == 0) {
                showToast(getString(R.string.no_data_found));
            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray("Description");
                    JSONObject objJson;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        ItemAbout itemAbout = new ItemAbout();
                        itemAbout.setAppName(objJson.getString(AppConfig.EMP_NAME));
                        itemAbout.setAppVersion(objJson.getString(AppConfig.JOB_TITLE));
                        itemAbout.setAppAuthor(objJson.getString(AppConfig.PAY_NUMBER));
                        itemAbout.setAppDescription(objJson.getString(AppConfig.APP_DESC));
                        mListItem.add(itemAbout);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setResult();
            }
        }
    }

    private void setResult() {

        ItemAbout itemAbout = mListItem.get(0);
        employeeName.setText(itemAbout.getAppName());
        jobTitle.setText(itemAbout.getAppVersion());
        payNumber.setText(itemAbout.getAppAuthor());
        Picasso.with(JobDescription.this).load(AppConfig.IMAGE_PATH_URL + itemAbout.getAppLogo()).into(imgAppLogo);

        String mimeType = "text/html;charset=UTF-8";
        String encoding = "utf-8";
        String htmlText = itemAbout.getAppDescription();

        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/myfonts/custom.ttf\")}body{font-family: MyFont;color: #545454;text-align:justify}"
                + "</style></head>"
                + "<body>"
                +  htmlText
                + "</body></html>";

        webView.loadDataWithBaseURL(null,text, mimeType, encoding,null);

    }

    public void showToast(String msg) {
        Toast.makeText(JobDescription.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}

