package xcal.letsfind;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class Signup extends Fragment implements View.OnClickListener {

    Button bt_signup;
    ProgressDialog pDialog;

    Intent i;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    EditText et_signup_email,et_signup_password,et_signup_contact,et_signup_name;
    static String name,email,number,password;
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager


    // Connection detector
    ConnectionDetector cd;
    public Signup() {
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
        View view = inflater.inflate(R.layout.signup_fragment,container,false);
        bt_signup = (Button) view.findViewById(R.id.button_signup);
        bt_signup.setOnClickListener(this);

        et_signup_name = (EditText) view.findViewById(R.id.et_signup_name);
        et_signup_email = (EditText) view.findViewById(R.id.et_signup_email);
        et_signup_contact = (EditText) view.findViewById(R.id.et_signup_contact);
        et_signup_password = (EditText) view.findViewById(R.id.et_signup_password);
        i=new Intent(getActivity(),MainActivity.class);

        loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        return view;
    }


    @Override
    public void onClick(View v) {
        name = et_signup_name.getText().toString();
        email = et_signup_email.getText().toString();
        number = et_signup_contact.getText().toString();
        password = et_signup_password.getText().toString();
        getActivity().registerReceiver(mHandleMessageReceiver,new IntentFilter());
        final String regId = GCMRegistrar.getRegistrationId(getActivity());

        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(getActivity(), "228417489761");
            Log.i("TAggggggggggggggggg", "aaaaaaaaaa" + " " + name + " " + email + " " + password + " " + number + " ");
            Log.i("iiiiiiiiii", regId);

            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("email", email);
            loginPrefsEditor.putString("password", email);
            loginPrefsEditor.commit();

            startActivity(i);
            getActivity().finish();

        }else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(getContext())) {
                // Skips registration.
                Log.i("TAggggggggggggggggg","bbbbbbbbbbbbbbbqqqqqqqqqqqqq");
                Toast.makeText(getContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                Log.i("TAggggggggggggggggg","bbbbbbbbbbbbbbbbbb");
                final Context context = getContext();
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        Log.i("TAggggggggggggggggg","ccccccccccccccccc");
                        ServerUtilities.register(context, name, email,number, password, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                        Log.i("TAggggggggggggggggg","ddddddddddddddddd");
                    }

                };
                Log.i("TAggggggggggggggggg","eeeeeeeeeeeee");
                mRegisterTask.execute(null, null, null);
            }
        }

    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
            //lblMessage.append(newMessage + "\n");
            Toast.makeText(getContext(), "New Message", Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    public void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            getActivity().unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(getContext());
        } catch (Exception e) {
            Log.e("UnRegister Receiver", "> " + e.getMessage());
        }
        super.onDestroy();
    }
}

