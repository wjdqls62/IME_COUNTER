package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;

public class TextWatcherManager implements TextWatcher {
    private final String TAG = "IME_COUNTER";
    private String keyboardType;
    private Activity activity;

    private int currentCnt, prevCount = 0;
    private int lastedStrLength, lastedStart= 0;
    private char inputStr;
    private TextView current, prev, typingWord, typingHit;
    private EditText editText;

    private boolean isDebug = true;

    public TextWatcherManager(Activity activity) {
        this.activity = activity;
        initView();
    }

    private void initView() {
        editText = activity.findViewById(R.id.edit_text);
        current = activity.findViewById(R.id.current_cnt_txv);
        prev = activity.findViewById(R.id.prev_cnt_txv);
        typingWord = activity.findViewById(R.id.typing_word);
        typingHit = activity.findViewById(R.id.typing_hit);

        editText.addTextChangedListener(this);

        typingWord.setVisibility(View.INVISIBLE);
        typingHit.setVisibility(View.INVISIBLE);

        getKeyboardType();


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        try {
            if(keyboardType != "null"){
                inputStr = (charSequence.charAt(charSequence.length() - 1));
                // 스페이스바 입력
                if (inputStr == ' ') {
                    if(!keyboardType.equals("SAMSUNG")){
                        prevCount += 1;
                        prev.setText("Prev : " + prevCount);
                    }
                    prevCount = currentCnt + 1;
                    prev.setText("Prev : " + prevCount);
                    currentCnt = 0;

                } else {
                    if (inputStr != ' ') {
                        currentCnt += 1;
                    }
                }

                if (isDebug) Log.d(TAG, "* start : " + start + "/ before : " + before + "/ count : " + count + "/ lastedStrLength : " + charSequence.length() + "/ inputStr : " + inputStr);

            }else{
                Toast.makeText(activity, "키보드 타입이 설정되지 않습니다.\n 설정에서 키보드 타입을 설정하세요", Toast.LENGTH_SHORT).show();
            }

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "Null value...");
            e.printStackTrace();
        } finally {
            if(keyboardType != "null"){
                current.setText("Cur : " + currentCnt);
                lastedStrLength = charSequence.length();
                lastedStart = start;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void resetCount() {
        currentCnt = 0;
        lastedStrLength = 0;
        current.setText("Cur : " + currentCnt);
        prev.setText("Prev : 0");
    }

    public void clearEditText() {
        editText.setText(null);
        resetCount();
    }

    public String getKeyboardType() {
        keyboardType = PreferenceManager.getDefaultSharedPreferences(activity).getString("keyboard_type", "null");
        return keyboardType;
    }

}