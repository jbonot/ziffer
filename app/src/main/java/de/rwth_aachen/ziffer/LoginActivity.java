package de.rwth_aachen.ziffer;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.FacebookSdk;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity);

        LoginFragment loginFragment = new LoginFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(android.R.id.content, loginFragment).commit();
        }else {
            // Or set the fragment from restored state info
            getFragmentManager().findFragmentById(android.R.id.content);
        }
    }
}
