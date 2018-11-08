package sonjb.phillit.ime_counter.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;
import sonjb.phillit.ime_counter.common.TextWatcherManager;


public class MainActivity extends Activity {
    interface iTouchEventCallback {
        public void getEvent(MotionEvent event);
        public void setCallback(iTouchEventCallback callback);
    }

    iTouchEventCallback callback;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        callback.getEvent(ev);
        return super.dispatchTouchEvent(ev);
    }




}
