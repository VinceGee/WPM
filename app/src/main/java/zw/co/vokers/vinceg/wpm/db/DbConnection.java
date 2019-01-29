package zw.co.vokers.vinceg.wpm.db;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Vince G on 29/11/2018
 */

public class DbConnection {
    @SuppressLint("NewApi")
    public Connection connectionclass() {
        String user="sa";
        String database="wpm_db";
        String password="W3lcom3";
        String server="192.168.1.18:3306";

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection=null;
        String ConnectionURL=null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jdbc:jtds:sqlserver://"+server+";"+"DatabaseName="+database+";user="+user+";password="+password+";"+"integratedSecurity=true;";
            connection= DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1:",se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2:",e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3:",e.getMessage());
        }
        return connection;

    }
}
