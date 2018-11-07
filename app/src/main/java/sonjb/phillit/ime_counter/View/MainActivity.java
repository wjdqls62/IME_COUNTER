package sonjb.phillit.ime_counter.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;
import sonjb.phillit.ime_counter.common.TextWatcherManager;


public class MainActivity extends Activity {

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
        textWatcherManager = new TextWatcherManager(
                getApplicationContext(),
                activity);
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
}
