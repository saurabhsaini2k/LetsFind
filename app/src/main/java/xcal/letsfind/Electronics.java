package xcal.letsfind;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import xcal.letsfind.ipClass.*;

import java.util.SimpleTimeZone;

public class Electronics extends FragmentActivity implements OnItemSelectedListener, View.OnClickListener {

    Spinner sp;

    Intent in;
    String sp_state;
    final Context context = this;
    EditText brand,model,color,specs,location,date,time;
    String REGISTER_URL = ipClass.REGISTER_URL+"/gcm_server_php/categories.php";
    private SharedPreferences pref;
    double latt,lngg;
    String lat,lng;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor editor;
    private String[] items = {"No item selected","Mobile","Watch","Tablets","Laptops","Power Banks","Routers","Kettle","Trimmers","Camera","Calculator"};
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private GoogleMap mMap;// Might be null if Google Play services APK is not available.


    Button one;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
        brand=(EditText)findViewById(R.id.editText);
        model=(EditText)findViewById(R.id.editText2);
        color=(EditText)findViewById(R.id.editText3);
        one = (Button) findViewById(R.id.bt_Submit);
        location=(EditText)findViewById(R.id.editText5);
        date=(EditText)findViewById(R.id.editText6);
        date.setInputType(InputType.TYPE_NULL);
        //date.requestFocus();

        time= (EditText) findViewById(R.id.editText7);
        time.setInputType(InputType.TYPE_NULL);
        //time.requestFocus();
        sp=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter_state=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        sp.setAdapter(adapter_state);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(this);
        Log.i("aaaaaaaaaaaaaaaaaaaa", "aaa");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        one.setOnClickListener(this);
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        pref = getApplicationContext().getSharedPreferences("Mode",MODE_PRIVATE);

        setDateTimeField();
        setUpMapIfNeeded();
        Button two = (Button) findViewById(R.id.button);
        two.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void setDateTimeField() {
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        Log.i("1 Date pick Dialog VAVA", "jashdj");
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.i("2 Date pick Dialog VAVA","jashdj");

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);
        Log.i("3 Date pick Dialog VAVA","jashdj");

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.i("4 Date pick Dialog VAVA","jashdj");

                time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(29.9657, 76.8370)).title("Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DAVAO, 15));
        gotoLocation(29.9657, 76.8370, "Kurukshetra");
        // animate the zoom process
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }


    private void gotoLocation(double lat, double lng, String location){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat, lng)).zoom(15).build();
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(location));
    }

    public void geoLocate(View v) throws IOException {
        EditText e = (EditText) findViewById(R.id.editText5
        );
        String location = e.getText().toString();
        Geocoder g = new Geocoder(this);
        List<Address> list = g.getFromLocationName(location,1);
        Address add = list.get(0);
        String locality = add.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        latt = add.getLatitude();
        lngg = add.getLongitude();
        lat = String.valueOf(add.getLatitude());
        lng = String.valueOf(add.getLongitude());
        gotoLocation(latt, lngg, location);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText6 :

                    datePickerDialog.show();
                    break;
            case R.id.editText7:
                    timePickerDialog.show();
                    break;

            case R.id.bt_Submit:
                    if(brand.getText().toString().trim().equals("")){
                        brand.setError("Enter brand name.");
                    }else{
                        if(color.getText().toString().trim().equals("")){
                            color.setError("Enter color.");
                        }else{
                            if(sp.getSelectedItemPosition()==0){
                                color.setError("Select a item.");
                            }else{

                                if(pref.getString("type","null")=="lost"){
                                    Intent i = new Intent(Electronics.this,Authentication.class);

                                    i.putExtra("brand",brand.getText().toString());
                                    i.putExtra("model",model.getText().toString());
                                    i.putExtra("color",color.getText().toString());
                                    i.putExtra("date",date.getText().toString());
                                    i.putExtra("time",time.getText().toString());
                                    i.putExtra("item",sp.getSelectedItem().toString());
                                    i.putExtra("lat",lat);
                                    i.putExtra("lng",lng);
                                    startActivity(i);
                                }
                                else{
                                    String em = loginPreferences.getString("email","null");

                                   // Log.v("QWERTTYYY","");
                                    register(brand.getText().toString(), model.getText().toString(), color.getText().toString(), date.getText().toString(), time.getText().toString(), sp.getSelectedItem().toString(),lat,lng,em);
                                    final Dialog dialog = new Dialog(Electronics.this);
                                    dialog.setContentView(R.layout.input_dialog);
                                    dialog.setTitle("Finding Owner!!");
                                    Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i = new Intent(Electronics.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    dialog.show();

                                }



                            }
                        }
                    }


                break;

            case R.id.button:
                try {


                    geoLocate(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        }


    private void register(String brand,String color,String model,String date,String time,String item,String lat,String lng,String em) {
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

                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {


                HashMap<String, String> data = new HashMap<String,String>();
                //Log.i("TAGGGGG"," : "+params[0]+" : "+params[1]+" : "+params[2]+" : "+params[3]+" : "+params[4]+" : "+params[5]+" : "+params[6]+" : "+params[7]+" : "+pref.getString("type","null"));
                data.put("company",params[0]);
                data.put("color",params[2]);
                data.put("model",params[1]);
                data.put("date",params[3]);
                data.put("time",params[4]);
                data.put("item",params[5]);
                data.put("lat",params[6]);
                data.put("lng",params[7]);
                data.put("email",params[8]);
                data.put("type",pref.getString("type","null"));


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(brand, model, color, date, time, item,lat,lng,em);

    }



}
