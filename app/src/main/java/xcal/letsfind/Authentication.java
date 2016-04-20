package xcal.letsfind;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;

import java.util.HashMap;

public class Authentication extends AppCompatActivity implements View.OnClickListener {

    Intent  i;
    EditText et;
    String ss;
    Button bt;
    final Context context = this;
    String REGISTER_URL = ipClass.REGISTER_URL+"/gcm_server_php/categories.php";
    private SharedPreferences pref;
    private SharedPreferences loginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        i=getIntent();
        et = (EditText) findViewById(R.id.et_authentication);
        bt = (Button) findViewById(R.id.bt_start);
        bt.setOnClickListener(this);
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        pref = getApplicationContext().getSharedPreferences("Mode",MODE_PRIVATE);
    }



    @Override
    public void onClick(View v) {
        String brand,model,color,date,time,item,lat,lng;
        brand = i.getStringExtra("brand");
        model = i.getStringExtra("model");
        color = i.getStringExtra("color");
        date = i.getStringExtra("date");
        time = i.getStringExtra("time");
        item = i.getStringExtra("item");

        lat = i.getStringExtra("lat");
        lng = i.getStringExtra("lng");

        ss = et.getText().toString();
        register(brand, model, color, date, time, item, lat, lng);

        final Dialog dialog = new Dialog(Authentication.this);
        dialog.setContentView(R.layout.input_dialog);
        dialog.setTitle("Finding Founder!!");
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Authentication.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        dialog.show();
    }
    private void register(String brand,String color,String model,String date,String time,String item, String lat, String lng) {
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

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("company",params[0]);
                data.put("color",params[2]);
                data.put("model",params[1]);
                data.put("date",params[3]);
                data.put("time",params[4]);
                data.put("item",params[5]);
                data.put("lat",params[6]);
                data.put("lng",params[7]);
                data.put("email",loginPreferences.getString("email","null"));
                data.put("type",pref.getString("type","null"));


                if(pref.getString("type","null")=="lost"){
                    data.put("auth", ss);
                }

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(brand, model, color, date, time, item, lat, lng);

    }
}
