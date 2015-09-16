package kyle.flashlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by kyle on 9/15/15.
 */
public class SettingsActivity extends PreferenceActivity{
    public static final String KEY_FLASHREMAINS = "flashRemains";
    public static final String KEY_FLASHONOPEN = "flashOnOpen";

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Context context = getApplicationContext();
        if(MainActivity.myCamera == null) {
            MainActivity.myCamera = Camera.open();
            //Toast toast2 = Toast.makeText(context, "Camera is opened by onResume2", Toast.LENGTH_SHORT);
            //toast2.show();
        }
        MainActivity.turnOnNow = false;
    }

    @Override
    public void onPause(){
        super.onPause();
        //Context context = getApplicationContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean remainOn = sharedPref.getBoolean(SettingsActivity.KEY_FLASHREMAINS, false);
        if(!remainOn) {
            MainActivity.myCamera.release();
            MainActivity.myCamera = null;
            //Toast toast = Toast.makeText(context, "Camera is closed  by onPause2", Toast.LENGTH_SHORT);
            //toast.show();
        }
    }
}
