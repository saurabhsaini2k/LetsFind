package xcal.letsfind;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Screen4 extends AppCompatActivity {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    TextView name,email,contact;
    String REGISTER_URL = "http://192.168.1.100/gcm_server_php/getData.php";
    //private String mActivityTitle;
    final Context context = this;
    //private String mActivityTitle;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //  mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Account Details");

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        name= (TextView) findViewById(R.id.txt_name);
        email = (TextView) findViewById(R.id.txt_email);
        contact  = (TextView) findViewById(R.id.txt_contact);


        email.setText(loginPreferences.getString("email","null"));
        if(loginPreferences.getString("name","null")=="null"){
            getData();
        }else{
            name.setText(loginPreferences.getString("name","null"));
            contact.setText(loginPreferences.getString("contact","null"));
        }




    }

    public void getData(){
        flag=1;
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                try {
                    JSONObject jObj = new JSONObject(s);

                    name.setText(jObj.getString("name"));
                    contact.setText(jObj.getString("contact"));
                    loginPrefsEditor.putString("name", jObj.getString("name"));
                    loginPrefsEditor.putString("contact",jObj.getString("contact"));
                    loginPrefsEditor.commit();
                    Intent i = new Intent(context,Screen4.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("email",params[0]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(loginPreferences.getString("email","null"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void addDrawerItems() {
        String[] osArray = { "Home", "Notification", "About Us", "Logout" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent b = new Intent(Screen4.this, MainActivity.class);
                        startActivity(b);
                        break;
                    case 1:
                        Intent c = new Intent(Screen4.this, Notification.class);
                        startActivity(c);
                        break;
                    case 2:
                        Intent a = new Intent(Screen4.this, Screen3.class);
                        startActivity(a);
                        break;
                    case 3:final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.logout_dialog);
                        dialog.setTitle("Logout");


                        // set the custom dialog components - text, image and button


                        Button yes = (Button) dialog.findViewById(R.id.bt_logout_dialog_yes);
                        Button no = (Button)dialog.findViewById(R.id.bt_logout_dialog_no);
                        // if button is clicked, close the custom dialog

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginPreferences = getApplicationContext().getSharedPreferences("loginPrefs",MODE_PRIVATE);
                                loginPrefsEditor = loginPreferences.edit();
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                                Intent i = new Intent(Screen4.this, FirstActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Screen4.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        dialog.show();

                        break;
                    default:
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Lets-Find");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
