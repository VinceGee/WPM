package zw.co.vokers.vinceg.wpm.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.models.UserFxns;
import zw.co.vokers.vinceg.wpm.utils.InputValidation;

/**
 * Created by Vince G on 28/11/2018
 */

public class ResetPassword extends Activity {

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private TextInputLayout inputLayoutEmail;
    private TextInputEditText email;
    private InputValidation inputValidation;
    Button resetpass;
    private LinearLayout linearLayout;
    private String testEmail;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passwordreset);

        Button login = (Button) findViewById(R.id.bktolog);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });

        linearLayout = (LinearLayout) findViewById(R.id.resetParentLayout);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputForgot);
        inputValidation = new InputValidation(this);
        email = (TextInputEditText) findViewById(R.id.forpas);
        resetpass = (Button) findViewById(R.id.respass);

        testEmail = email.getText().toString().trim();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validEmail(testEmail)) {
                    Toast.makeText(ResetPassword.this,"Please enter a valid e-mail!",Toast.LENGTH_LONG).show();
                }else {
                    NetAsync(view);
                }
            }



        });}

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>

    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(ResetPassword.this);
            nDialog.setMessage("Loading...");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){



            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
                Toast.makeText(getBaseContext(),"Error in Network Connection",Toast.LENGTH_LONG).show();

            }
        }
    }





    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {


        private ProgressDialog pDialog;

        String forgotpassword;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            forgotpassword = email.getText().toString();

            pDialog = new ProgressDialog(ResetPassword.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Getting Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


            UserFxns userFunction = new UserFxns();
            JSONObject json = userFunction.forPass(forgotpassword);
            return json;


        }


        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks if the Password Change Process is sucesss
             **/
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    //alert.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    String red = json.getString(KEY_ERROR);


                    if(Integer.parseInt(res) == 1){
                        pDialog.dismiss();
                        Toast.makeText(getBaseContext(),"A recovery email is sent to you, see it for more details.",Toast.LENGTH_LONG).show();
                    }

                    else if (Integer.parseInt(red) == 2)
                    {    pDialog.dismiss();
                        Toast.makeText(getBaseContext(),"Your email does not exist in our database.",Toast.LENGTH_LONG).show();

                    }
                    else {
                        pDialog.dismiss();
                        Toast.makeText(getBaseContext(),"Error occured in resetting your password",Toast.LENGTH_LONG).show();

                    }




                }}
            catch (JSONException e) {
                e.printStackTrace();


            }
        }}
    public void NetAsync(View view){
        new NetCheck().execute();
    }}






