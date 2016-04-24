package xcal.letsfind;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Login extends Fragment implements View.OnClickListener {


    Button bt_login;
    EditText et_login_email,et_login_password;

    String reg_id="";
    String REGISTER_URL = ipClass.REGISTER_URL+"/gcm_server_php/login.php";
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    Intent i;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        bt_login = (Button) view.findViewById(R.id.button_login);
        bt_login.setOnClickListener(this);

        et_login_email = (EditText) view.findViewById(R.id.et_login_email);
        et_login_password = (EditText) view.findViewById(R.id.et_login_password);


        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        return view;
    }

    private void register(String password, String email) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("password",params[0]);
                data.put("email",params[1]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                if(result=="FAILED"){

                }else{
                    try {
                        JSONObject jObj = new JSONObject(result);
                        if(jObj.getString("success")=="1"){
                            reg_id=jObj.getString("reg_id");
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("email", params[1]);
                            loginPrefsEditor.putString("password", params[0]);
                            loginPrefsEditor.commit();
                            i = new Intent(getActivity(),MainActivity.class);
                            startActivity(i);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(password,email);

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void onClick(View v) {
        String email="";
        String password="";
        if(v==bt_login) {
            email = et_login_email.getText().toString().trim().toLowerCase();
            if(!isValidEmail(email)){
                Toast.makeText(getActivity(),"Invalid EMail",Toast.LENGTH_LONG).show();
                return;
            }
            password = et_login_password.getText().toString().trim().toLowerCase();
        }

        register(password, email);


    }
}