package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;
import sonjb.phillit.ime_counter.View.MainActivity;

public class TextWatcherManager implements TextWatcher, View.OnClickListener {
    private final String TAG = "IME_COUNTER";
    private Activity activity;
    private boolean isDebug = true;
    boolean isSpace = false;
    private int currentCnt, prevCount, lastedStrLength, lastedStart = 0;
    private char inputStr, lastedInputStr;
    private Button currentBtn, prevBtn, resetBtn, clearBtn;
    private EditText editText;
    private Context context;
    private MainActivity.iD callback;




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

        try {
            inputStr = (charSequence.charAt(charSequence.length() -1));

            // 스페이스바 입력
            if(inputStr == ' '){
                // (추천단어)스페이스바 전 문자가 기호인지 체크
                if(charSequence.charAt(charSequence.length()-2) == '.'){
                    prevCount += 1;
                    prevBtn.setText("Prev : " + prevCount);
                }else{
                    Toast.makeText(context, "Spacebar", Toast.LENGTH_SHORT).show();
                    prevCount = currentCnt + 1;
                    prevBtn.setText("Prev : " + prevCount);
                }

                currentCnt = 0;

            }else{
                if(inputStr != ' '){
                    currentCnt += 1;
                }
            }

            if (isDebug) {
                Log.d(TAG, "* start : " + start + "/ before : " + before + "/ count : " + count + "/ lastedStrLength : " + charSequence.length() + "/ inputStr : " + inputStr);
            }















            //Log.d(TAG, " / lastedInputStr : " + lastedInputStr);
            //inputStr = charSequence.charAt(count - 1);
//
            //if (inputStr != ' ') {
            //    currentCnt += 1;
            //} else {
            //    if (inputStr == ' ') {
            //        prevCount = currentCnt + 1;
            //        prevBtn.setText("Prev : " + prevCount);
            //        currentCnt = 0;
            //        Toast.makeText(context, "SpaceBar", Toast.LENGTH_SHORT).show();
            //    }
            //}
            //lastedInputStr = inputStr;
            //if (isDebug) Log.d(TAG, "inputStr : " + inputStr);

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "Null value...");
            e.printStackTrace();
        } finally {
            currentBtn.setText("Cur : " + currentCnt);
            lastedStrLength = charSequence.length();
            lastedStart = start;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void resetCount(){
        currentCnt = 0;
        lastedStrLength = 0;
        currentBtn.setText("Cur : " + currentCnt);
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