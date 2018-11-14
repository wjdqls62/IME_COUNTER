package sonjb.phillit.ime_counter.View;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;
import sonjb.phillit.ime_counter.common.TestCaseManager;
import sonjb.phillit.ime_counter.common.TextWatcherManager;


public class MainActivity extends Activity {
    private final String TAG = "IME_COUNTER";
    private TextWatcherManager textWatcherManager;
    private Activity activity;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        activity = MainActivity.this;
        textWatcherManager = new TextWatcherManager(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select_menu = item.getItemId();

        switch (select_menu){
            case R.id.menu_rst_cnt :
                textWatcherManager.resetCount();
                break;
            case R.id.menu_clear :
                textWatcherManager.clearEditText();
                break;
            case R.id.menu_settings :
                moveSettingActivity();
                break;
            case R.id.menu_nxt_word :
                textWatcherManager.nextWord();
                break;
            case R.id.menu_prv_word :
                textWatcherManager.prevWord();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - time > 2000){
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textWatcherManager.getKeyboardType();
        textWatcherManager.getTestType();
    }
}
