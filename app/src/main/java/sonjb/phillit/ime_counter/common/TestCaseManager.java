package sonjb.phillit.ime_counter.common;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sonjb.phillit.ime_counter.R;

public class TestCaseManager {
    private Activity activity;
    private TextView originalWord, typingHit;

    private FileInputStream fileInputStream;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;

    public TestCaseManager(Activity activity){
        this.activity = activity;
    }


    public void init(){
        originalWord = activity.findViewById(R.id.original_word);
        typingHit = activity.findViewById(R.id.typing_hit);

        loadExcelFile();
    }

    public void nextRow(){

    }

    private boolean loadExcelFile(){
        try {
            fileInputStream = new FileInputStream("/sdcard/qa_prediction/test.xlsx");

            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet("KSR_LG");


        } catch (FileNotFoundException e) {
            Toast.makeText(activity.getApplicationContext(), "/sdcard/qa_prediction/ 에 파일이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
