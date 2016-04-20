package xcal.letsfind;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Screen2 extends MainActivity implements OnItemClickListener{

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    ListView lv;
    Intent in;
    String myitems[]={"Electronics","Accessories","Wearables","Wallets/Bags","Jewellery","Books","Sports Gears","Locks/Keys","Cards"};
    ArrayAdapter<String> adp;

    //private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //  mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        adp=new ArrayAdapter<String>(Screen2.this,android.R.layout.simple_list_item_1,myitems);
        lv=(ListView)findViewById(R.id.listView);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        in=new Intent();
        Log.d("da", "ds");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen2, menu);
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

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:            {
                Log.d("d", "ds");
                in.setClass(Screen2.this,Electronics.class);
                startActivity(in);
                break;
            }
           /* case 1:
            {
                in.setClass(MainActivity.this,Wearables.class);
                startActivity(in);
                break;
            }*/
            case 2:
            {
                in.setClass(Screen2.this, Wearables.class);
                startActivity(in);
                break;
            }

        }

    }

    private void addDrawerItems() {
        String[] osArray = { "MyAccount", "Settings", "About Us", "Logout" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent b = new Intent(Screen2.this, Screen4.class);
                        startActivity(b);
                        break;
                    case 1:
                        Intent c = new Intent(Screen2.this, Notification.class);
                        startActivity(c);
                        break;
                    case 2:
                        Intent a = new Intent(Screen2.this, Screen3.class);
                        startActivity(a);
                        break;
                    case 3:
                        Intent d = new Intent(Screen2.this, Logout.class);
                        startActivity(d);
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
