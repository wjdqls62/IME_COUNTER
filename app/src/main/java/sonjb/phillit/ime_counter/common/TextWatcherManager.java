package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sonjb.phillit.ime_counter.R;

public class TextWatcherManager implements TextWatcher {
    private final String TAG = "IME_COUNTER";
    private String keyboardType, testType;
    private Activity activity;
    private ArrayList<Word_Hit> wordList;

    private TestCaseManager testCaseManager;
    private int currentCnt, prevCount, wordNum = 0;
    private int lastedStrLength, lastedStart= 0;
    private char inputStr;
    private TextView current, prev;
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

        editText.addTextChangedListener(this);

        getKeyboardType();

        testCaseManager = new TestCaseManager(activity);
        testCaseManager.initWord();
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
                    testCaseManager.nextWord();

                } else {
                    if (inputStr != ' ') {
                        currentCnt += 1;
                    }
                }

                if (isDebug) Log.d(TAG, "* start : " + start + "/ before : " + before + "/ count : " + count + "/ lastedStrLength : " + charSequence.length() + "/ inputStr : " + inputStr);

            }else{
                if(keyboardType.equals("null")){
                    Toast.makeText(activity, "키보드 타입이 설정되지 않습니다.\n 설정에서 키보드 타입을 설정하세요", Toast.LENGTH_SHORT).show();
                }else if(testType.equals("null")){
                    Toast.makeText(activity, "테스트 타입이 설정되지 않았습니다.\n 설정에서 테스트 타입을 설정하세요", Toast.LENGTH_SHORT).show();
                }

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

    public void getTestType(){
        testType = PreferenceManager.getDefaultSharedPreferences(activity).getString("test_type", "KSR_Conversation");
        testCaseManager.refreshTestType(testType);

    }

    public void nextWord(){
        testCaseManager.nextWord();
    }
    public void prevWord(){
        testCaseManager.prevWord();
    }

}