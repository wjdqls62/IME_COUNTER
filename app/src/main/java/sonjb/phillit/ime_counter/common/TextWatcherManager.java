package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;

public class TextWatcherManager implements TextWatcher{
    private final String TAG = "IME_COUNTER";
    private Activity activity;
    private boolean isDebug = true;
    private int currentCnt, prevCount, lastedStrLength, lastedStart = 0;
    private char inputStr, lastedInputStr;
    private TextView currentBtn, prevBtn;
    private EditText editText;
    private Context context;
    private boolean isAutoExcelWrite = true;
    private TestCaseManager testCaseManager;

    public TextWatcherManager(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        initView();
    }

    private void initView(){
        editText = activity.findViewById(R.id.edit_text);
        currentBtn = activity.findViewById(R.id.current_cnt_btn);
        prevBtn = activity.findViewById(R.id.prev_cnt_btn);

        //리스너 등록
        editText.addTextChangedListener(this);

        if(isAutoExcelWrite){
            testCaseManager = new TestCaseManager(activity);
            testCaseManager.init();
        }


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
                prevCount += 1;
                prevBtn.setText("Prev : " + prevCount);
                Toast.makeText(context, "Spacebar", Toast.LENGTH_SHORT).show();
                prevCount = currentCnt + 1;
                prevBtn.setText("Prev : " + prevCount);
                currentCnt = 0;
                }
            else{
                if(inputStr != ' '){
                    currentCnt += 1;
                }
            }

            if (isDebug) {
                Log.d(TAG, "* start : " + start + "/ before : " + before + "/ count : " + count + "/ lastedStrLength : " + charSequence.length() + "/ inputStr : " + inputStr);
            }
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

    public void resetCount(){
        currentCnt = 0;
        lastedStrLength = 0;
        currentBtn.setText("Cur : " + currentCnt);
        prevBtn.setText("Prev : 0");
    }

    public void clearEditText(){
        editText.setText(null);
        resetCount();
    }
}