package xcal.letsfind;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_splash_screen);


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                saveLogin = loginPreferences.getBoolean("saveLogin", false);
                if (saveLogin == true) {
                    i = new Intent(Splash_Screen.this,MainActivity.class);
                }else{
                    i = new Intent(Splash_Screen.this,FirstActivity.class);
                }
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}