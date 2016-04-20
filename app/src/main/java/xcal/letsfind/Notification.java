package xcal.letsfind;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    //private String mActivityTitle;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    //private String mActivityTitle;
    final Context context = this;

    private Toolbar mToolbar;
    private ListView listView;
    private List<Message> listMessages = new ArrayList<>();
    private MessageAdapter adapter;
    DatabaseHelper databaseHelper;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        listView = (ListView) findViewById(R.id.list_view);
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mActivityTitle = getTitle().toString();

        adapter = new MessageAdapter(this);
        pref = new PrefManager(getApplicationContext());



        addDrawerItems();
        setupDrawer();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getData();
        String[] fromFieldNames = new String[]{"EMAIL","TITLE","AUTHENTICATION"};
        int[] toViewIds = new int[]{R.id.text_view_email,R.id.text_title,R.id.message};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(this,R.layout.list_row,cursor,fromFieldNames,toViewIds,0);
        ListView myList = (ListView) findViewById(R.id.list_view);
        myList.setItemsCanFocus(true);
        myList.setOnItemClickListener(onClickBeer);
        myList.setAdapter(myCursorAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Notification");

    }

    private AdapterView.OnItemClickListener onClickBeer = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {

            Intent newActivity = new Intent(getApplicationContext(), Screen3.class);
            newActivity.putExtra("com.example.checkmybeer.DetailsBeer", ""
                    + arg3);
            startActivityForResult(newActivity, 0);
        }
    };
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");

        Message m = new Message(message, System.currentTimeMillis());
        listMessages.add(0, m);
        adapter.notifyDataSetChanged();
    }


    private class MessageAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public MessageAdapter(Activity activity) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return listMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_row, null);
            }

            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            //TextView txtTimestamp = (TextView) view.findViewById(R.id.timestamp);
            TextView title = (TextView) view.findViewById(R.id.text_title);

            Message message = listMessages.get(position);
            txtMessage.setText(message.getMessage());

            CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            //txtTimestamp.setText(String.valueOf(ago));

            return view;
        }
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


    private void addDrawerItems() {
        String[] osArray = { "Home", "MyAccount", "About Us", "Logout" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent b = new Intent(Notification.this, MainActivity.class);
                        startActivity(b);
                        break;
                    case 1:
                        Intent c = new Intent(Notification.this, Screen4.class);
                        startActivity(c);
                        break;
                    case 2:
                        Intent a = new Intent(Notification.this, Screen3.class);
                        startActivity(a);
                        break;
                    case 3:
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.logout_dialog);
                        dialog.setTitle("Logout");


                        // set the custom dialog components - text, image and button


                        Button yes = (Button) dialog.findViewById(R.id.bt_logout_dialog_yes);
                        Button no = (Button) dialog.findViewById(R.id.bt_logout_dialog_no);
                        // if button is clicked, close the custom dialog

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginPreferences = getApplicationContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                loginPrefsEditor = loginPreferences.edit();
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                                Intent i = new Intent(Notification.this, FirstActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Notification.this, MainActivity.class);
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

    private void populateListView(){

    }


}
