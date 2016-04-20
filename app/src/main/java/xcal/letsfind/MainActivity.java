package xcal.letsfind;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Intent i;
            Button btn_lost,btn_found;

    final Context context = this;
    //private String mActivityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
      //  mActivityTitle = getTitle().toString();

        btn_found = (Button) findViewById(R.id.btn_found);
        btn_lost = (Button) findViewById(R.id.btn_lost);
        btn_found.setOnClickListener(this);
        btn_lost.setOnClickListener(this);
        pref = getApplicationContext().getSharedPreferences("Mode",MODE_PRIVATE);
        editor = pref.edit().clear();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        i = new Intent(MainActivity.this,Categories.class);


    }

      public void newActivity(View view){
          Intent startnewActivity=new Intent(this,Categories.class);
          startActivity(startnewActivity);
      }
    private void addDrawerItems() {
        String[] osArray = { "MyAccount", "Notification", "About Us", "Logout" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent b=new Intent(MainActivity.this, Screen4.class);
                        startActivity(b);
                        break;
                    case 1:
                        Intent c=new Intent(MainActivity.this, Notification.class);
                        startActivity(c);
                        break;
                    case 2:
                        Intent a=new Intent(MainActivity.this, Screen3.class);
                        startActivity(a);
                        break;
                    case 3:
                        final Dialog dialog = new Dialog(context);
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
                                Intent i = new Intent(MainActivity.this, FirstActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MainActivity.this,MainActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onClick(View v) {
        editor = pref.edit();
        editor.clear();
        switch (v.getId()){
            case R.id.btn_lost:
                Log.v("TAGGAGAGAGAGA","LOSTTTTTTTTTTTTTTTTTTTtt");
                editor.putString("type","lost");
                break;
            case R.id.btn_found :

                editor.putString("type", "found");
                Log.v("TAGGAGAGAGAGA", pref.getString("type","null"));
                break;
        }

        startActivity(i);
        editor.commit();
    }
}
