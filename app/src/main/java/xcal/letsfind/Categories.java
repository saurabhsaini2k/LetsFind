package xcal.letsfind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Categories extends Activity implements AdapterView.OnItemClickListener{
    ListView lv;
    Intent in;
    String myitems[]={"Electronics","Accessories","Wearables","Wallets/Bags","Books","Sports Gears","Locks/Keys","Cards"};
    ArrayAdapter<String> adp;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_categories);
        adp = new ArrayAdapter<String>(Categories.this,android.R.layout.simple_list_item_1,myitems);
        lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);

        pref = getApplicationContext().getSharedPreferences("Mode",MODE_PRIVATE);
        editor = pref.edit();
        loginPreferences = getApplicationContext().getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        //Log.d("da", "ds");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
            {
                editor.putString("Cstegory","Electronics");
                editor.commit();
                in = new Intent(Categories.this,Electronics.class);
                startActivity(in);
                break;
            }


        }

    }
}

