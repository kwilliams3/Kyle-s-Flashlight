package kyle.flashlight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    protected static Camera myCamera = null;
    protected static Boolean turnOnNow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button flashlightButton = (Button) findViewById(R.id.theOnButton);
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera.Parameters myCameraParameters = myCamera.getParameters();
                String status = myCameraParameters.getFlashMode();
                if (status.equals(Camera.Parameters.FLASH_MODE_OFF)) {
                    flashControl(v, true);
                } else {
                    flashControl(v, false);
                }
            }
        });
        /*
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT);
        toast.show();
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart(){
        super.onStart();
        //Context context = getApplicationContext();
        /*
        Toast toast = Toast.makeText(context, "onStart called", Toast.LENGTH_SHORT);
        toast.show();
        */
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(myCamera == null) {
            myCamera = Camera.open();
            /*Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Camera is opened by onResume1", Toast.LENGTH_SHORT);
            toast.show();*/
        }
        Camera.Parameters myCameraParameters = myCamera.getParameters();
        String status = myCameraParameters.getFlashMode();
        if(status.equals(Camera.Parameters.FLASH_MODE_OFF)) {
            findViewById(R.id.theOnButton).setBackgroundResource(R.drawable.circle_button_normal);
            ((Button) findViewById(R.id.theOnButton)).setText(R.string.on);
        }
        else{
            findViewById(R.id.theOnButton).setBackgroundResource(R.drawable.circle_button_pressed);
            ((Button) findViewById(R.id.theOnButton)).setText(R.string.off);
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean flashOnOpen = sharedPref.getBoolean(SettingsActivity.KEY_FLASHONOPEN, false);
        if(turnOnNow && flashOnOpen){
            flashControl(this.findViewById(android.R.id.content), true);
        }
        turnOnNow = true;
        /*
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "onResume called", Toast.LENGTH_SHORT);
        toast.show();
        */
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Context context = getApplicationContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean flashRemains = sharedPref.getBoolean(SettingsActivity.KEY_FLASHREMAINS, false);
        Camera.Parameters myCameraParameters = myCamera.getParameters();
        String status = myCameraParameters.getFlashMode();
        if(!flashRemains || status.equals(Camera.Parameters.FLASH_MODE_OFF)) {
            flashControl(this.findViewById(android.R.id.content), false);
            myCamera.release();
            myCamera = null;

            /*
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Camera is closed by onPause1", Toast.LENGTH_SHORT);
            toast.show();
            */

        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        /*Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "onStop called", Toast.LENGTH_SHORT);
        toast.show();*/

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        /*
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "onRestart called", Toast.LENGTH_SHORT);
        toast.show();
        */
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        /*
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "onDestroy called", Toast.LENGTH_SHORT);
        toast.show();
        */
    }

    public static void flashControl(View view, Boolean desire) {
        Camera.Parameters myCameraParameters = myCamera.getParameters();
        String status = myCameraParameters.getFlashMode();
        if (desire && (status.equals(Camera.Parameters.FLASH_MODE_OFF))) {
            myCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            view.findViewById(R.id.theOnButton).setBackgroundResource(R.drawable.circle_button_pressed);
            ((Button) view.findViewById(R.id.theOnButton)).setText(R.string.off);
            myCamera.setParameters(myCameraParameters);
        }
        else if(!desire && (status.equals(Camera.Parameters.FLASH_MODE_TORCH))){
            myCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            view.findViewById(R.id.theOnButton).setBackgroundResource(R.drawable.circle_button_normal);
            ((Button) view.findViewById(R.id.theOnButton)).setText(R.string.on);
            myCamera.setParameters(myCameraParameters);
        }
    }

    public void onSettingsClick(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }
}