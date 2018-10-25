package sonjb.phillit.ime_counter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements TextWatcher, View.OnClickListener {
    private String TAG = "IME_COUNTER";
    private char inputResult, lastedInputChar;
    private int currentCnt, prevCount, strLength = 0;
    private long time;


    private Button currentBtn, prevBtn, resetBtn, clearBtn = null;
    private EditText editText = null;

    private boolean isDebug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResource();
    }

    private void resetCount(){
        currentCnt = 0;
        currentBtn.setText("Current : " + currentCnt);
        prevBtn.setText("Prev : 0");
    }

    private void clearEditText(){
        editText.setText(null);
        resetCount();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if(isDebug){
            Log.d(TAG, "\nstart : " + start + "\nbefore : " + before + "\ncount : " + count);
            Log.d(TAG, "strLength : " + charSequence.length());
        }

        try {
            if(!isBackspace(charSequence)){
                inputResult = charSequence.charAt(start);
            }

            if (inputResult != ' ') {
                currentCnt += 1;
            }else{
                if (inputResult == ' ') {
                    // Swift keyboard의 경우 +1 제거해야 정상적인 Count 표시됨
                    prevCount = currentCnt;
                    prevBtn.setText("Prev : " + prevCount);
                    currentCnt = 0;
                    Toast.makeText(getApplicationContext(), "SpaceBar", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "Null value...");
            e.printStackTrace();
        } finally {
            currentBtn.setText("Current : " + currentCnt);
            strLength = charSequence.length();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {


    }

    private boolean isBackspace(CharSequence charSequence){
        if(strLength >= charSequence.length() || String.valueOf(charSequence.charAt(charSequence.length()-1)).equals(lastedInputChar)){
            return true;
        }else{
            return false;
        }
    }

    private void initResource(){
        // Resource 초기화
        editText = findViewById(R.id.edit_text);
        currentBtn = findViewById(R.id.current_cnt_btn);
        prevBtn = findViewById(R.id.prev_cnt_btn);
        resetBtn = findViewById(R.id.reset_btn);
        clearBtn = findViewById(R.id.clear_btn);

        //리스너 등록
        editText.addTextChangedListener(this);
        resetBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // RST CNT 버튼
            case R.id.reset_btn:
                resetCount();
                break;
            // Clear 버튼
            case R.id.clear_btn :
                clearEditText();
                break;
        }
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
