package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import sonjb.phillit.ime_counter.R;

public class TestCaseManager {
    private final String TAG = "IME_COUNTER";
    private ArrayList<Word_Hit> wordList;
    private TextView typingWord, typingHit;
    private Activity activity;

    private String testType;
    private Workbook workbook;
    private Sheet sheet;
    private int sRow, sColumns, currentNum = 0;

    public TestCaseManager(Activity activity){
        this.activity = activity;
        initView();
    }

    private void initView(){

        wordList = new ArrayList<>();
        wordList = loadExcelFile();

        typingWord = activity.findViewById(R.id.typing_word);
        typingHit = activity.findViewById(R.id.typing_hit);

    }
    public void refreshTestType(String type){
        wordList = null;
        currentNum = 0;
        testType = type;
        loadExcelFile();
        initWord();
    }

    public void initWord(){
        if(wordList != null){
            typingWord.setText(wordList.get(currentNum).getWord());
            typingHit.setText(wordList.get(currentNum).gethit());
        }

    }

    public void nextWord(){
        if(wordList != null){
            currentNum += 1;
            typingWord.setText(wordList.get(currentNum).getWord());
            typingHit.setText(wordList.get(currentNum).gethit());
        }

    }
    public void prevWord(){
        if(wordList != null){
            currentNum -= 1;
            typingWord.setText(wordList.get(currentNum).getWord());
            typingHit.setText(wordList.get(currentNum).gethit());
        }

    }

    private File getXls(){
        File[] fileList = new File("/sdcard/QA/").listFiles();
        if(fileList == null || fileList.length == 0){
            Toast.makeText(activity, "파일이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(fileList.length >= 2){
            Toast.makeText(activity, "파일이 2개이상 존재합니다\n 엑셀의 단어와 타수는 보여지지 않습니다", Toast.LENGTH_SHORT).show();
            return null;
        }
        return fileList[0];
    }

    public ArrayList<Word_Hit> loadExcelFile(){
        try {
            File file = getXls();
            if(file != null){
                if(wordList == null){
                    wordList = new ArrayList<>();
                }
                testType = PreferenceManager.getDefaultSharedPreferences(activity).getString("test_type", "KSR_Conversation");

                workbook = Workbook.getWorkbook(file);
                sheet = workbook.getSheet(testType);
                sRow = sheet.getRows();
                sColumns = sheet.getColumns();

                for(int i=0; i<sRow; i++){

                    Word_Hit word_hit = new Word_Hit();
                    String numCell = sheet.getCell(0, i).getContents();
                    String wordCell = sheet.getCell(1, i).getContents();
                    String hitCell = sheet.getCell(2, i).getContents();

                    try{
                        if(Integer.parseInt(numCell) >= 1 && !numCell.equals("") && !numCell.equals("#")){
                            Log.d(TAG, "Num : " + numCell + " / Word : " + wordCell + " / Hit : " + hitCell);
                            word_hit.add(numCell, wordCell, hitCell);
                            wordList.add(word_hit);
                        }
                    }catch (NumberFormatException e){
                        continue;
                    }
                }
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "파일 또는 디렉토리가 생성되어 있지 않습니다", Toast.LENGTH_SHORT).show();
        } catch (BiffException e) {
            e.printStackTrace();
            Toast.makeText(activity, ".xls 형식의 확장자만 가능합니다", Toast.LENGTH_SHORT).show();
        }finally {
            if(workbook != null){
                workbook.close();
            }
        }
        return wordList;
    }
}
