package zw.co.vokers.vinceg.wpm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import zw.co.vokers.vinceg.wpm.AppController;
import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.db.DatabaseHelper;
import zw.co.vokers.vinceg.wpm.db.DbConnection;
import zw.co.vokers.vinceg.wpm.db.SQLiteHandler;
import zw.co.vokers.vinceg.wpm.db.SessionManager;
import zw.co.vokers.vinceg.wpm.utils.AppConfig;
import zw.co.vokers.vinceg.wpm.utils.InputValidation;

/**
 * Created by Vince G on 27/11/2018
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutPNumber;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText inputPNumber;
    private TextInputEditText inputPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private AppCompatTextView textViewResetPassword;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.loginNestedScrollView);

        textInputLayoutPNumber = (TextInputLayout) findViewById(R.id.textInputLayoutPnumber);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        inputPNumber = (TextInputEditText) findViewById(R.id.textInputEditTextPNumber);
        inputPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
        textViewResetPassword = (AppCompatTextView) findViewById(R.id.resetPassword);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paynumber = inputPNumber.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!paynumber.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(paynumber, password);

                     //new checkLogin().execute("");
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter your credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        textViewLinkRegister.setOnClickListener(this);
        textViewResetPassword.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.resetPassword:
                // Navigate to RegisterActivity
                Intent intentReset = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(intentReset);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(inputPNumber, textInputLayoutPNumber, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(inputPNumber, textInputLayoutPNumber, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(inputPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(inputPNumber.getText().toString().trim()
                , inputPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            accountsIntent.putExtra("EMAIL", inputPNumber.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        inputPNumber.setText(null);
        inputPassword.setText(null);
    }

    /*class checkLogin extends AsyncTask<String,String,String> {
        String z="";
        Boolean IsSuccess=false;
        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        *//*protected void onPreExecute() {
            this.dialog.setMessage("Logging in...");
            this.dialog.show();
        }*//*

        @Override
        protected String doInBackground(String... strings) {

            DbConnection connect=new DbConnection();
            String payNumber=inputPNumber.getText().toString();
            String password=inputPassword.getText().toString();
            if(payNumber.trim().equals("")||password.trim().equals("")) {
                z="Please enter username and password";
            }
            else {
                try {
                    conn=connect.connectionclass();
                    if(conn==null) {
                        z="Please check your internet connection.";
                    }
                    else {
                        //"SELECT * FROM users WHERE pay_number = '" . $pay_number. "' and password = '" . md5($password) . "'"
                        String query="SELECT * FROM users WHERE pay_number='"+payNumber+"' AND password='"+password+"';";
                        Statement stmt=conn.createStatement();
                        ResultSet rs=stmt.executeQuery(query);
                        Log.e("RRRRRRRR",query);
                        Log.e("TTTTTTTT",stmt.toString());
                        Log.e("YYYYYYYY",rs.toString());

                        if(rs.next()) {
                            z="Login Successful";
                            IsSuccess=true;
                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            z="Invalid Credientals";
                            IsSuccess=false;

                        }
                    }

                }
                catch (Exception ex) {
                    IsSuccess=false;
                    z=ex.getMessage();

                }
            }

            return null;
        }

        protected void onPostExecute(final Boolean result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (result.booleanValue()) {
                //also show register success dialog
            }
        }

    }*/

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String paynumber, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        //String uid = jObj.getString("id");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String payNumber = user.getString("pay_number");
                        String userPassword = user.getString("password");
                        String jobTitle = user.getString("job_title");
                        String dept = user.getString("department");
                        String email = user.getString("email");
                        String mobile = user.getString("mobile");
                        String extension = user.getString("extension");
                        String created_at = user.getString("created_at");
Log.e("UUUUUUUUUUU",dept);
                        // Inserting row in users table
                        db.addUser(name, payNumber, userPassword,jobTitle, dept, email,mobile,extension, created_at);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pay_number", paynumber);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

