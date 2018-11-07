package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;

public class TextWatcherManager implements TextWatcher, View.OnClickListener {
    private final String TAG = "IME_COUNTER";
    private Activity activity;
    private boolean isDebug = true;
    private int currentCnt, prevCount, lastedStrLength = 0;
    private char inputStr, lastedInputStr;
    private Button currentBtn, prevBtn, resetBtn, clearBtn;
    private EditText editText;
    private Context context;


    public TextWatcherManager(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        initView();
    }

    private void initView(){
        editText = activity.findViewById(R.id.edit_text);
        currentBtn = activity.findViewById(R.id.current_cnt_btn);
        prevBtn = activity.findViewById(R.id.prev_cnt_btn);
        resetBtn = activity.findViewById(R.id.reset_btn);
        clearBtn = activity.findViewById(R.id.clear_btn);

        //리스너 등록
        editText.addTextChangedListener(this);
        resetBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (isDebug) {
            Log.d(TAG, "* start : " + start + "/ before : " + before + "/ count : " + count);
            Log.d(TAG, "* lastedStrLength : " + charSequence.length());
        }

        try {
            Log.d(TAG, " / lastedInputStr : " + lastedInputStr);
            inputStr = charSequence.charAt(count - 1);

            if (inputStr != ' ') {
                currentCnt += 1;
            } else {
                if (inputStr == ' ') {
                    prevCount = currentCnt + 1;
                    prevBtn.setText("Prev : " + prevCount);
                    currentCnt = 0;
                    Toast.makeText(context, "SpaceBar", Toast.LENGTH_SHORT).show();
                }else if(){

                }
            }
            lastedInputStr = inputStr;
            if (isDebug) Log.d(TAG, "inputStr : " + inputStr);

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "Null value...");
            e.printStackTrace();
        } finally {
            currentBtn.setText("Current : " + currentCnt);
            lastedStrLength = charSequence.length();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private boolean isBackspace(CharSequence charSequence){
        String temp = String.valueOf(inputStr);
        if(temp != null){
            if(lastedStrLength > charSequence.length()){
                if(!temp.equals(lastedInputStr)){
                    return true;
                }
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    private void resetCount(){
        currentCnt = 0;
        lastedStrLength = 0;
        currentBtn.setText("Current : " + currentCnt);
        prevBtn.setText("Prev : 0");
    }

    private void clearEditText(){
        editText.setText(null);
        resetCount();
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
}