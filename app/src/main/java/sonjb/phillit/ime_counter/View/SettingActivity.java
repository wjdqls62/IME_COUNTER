package sonjb.phillit.ime_counter.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import sonjb.phillit.ime_counter.R;

public class SettingActivity extends PreferenceActivity {

    private SharedPreferences sharedPreferences;
    private ListPreference keyboard_type, test_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        initView();


    }

    private void initView(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        test_type = (ListPreference) findPreference("test_type");
        keyboard_type = (ListPreference) findPreference("keyboard_type");
        keyboard_type.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                keyboard_type.setSummary(newValue.toString());
                return true;
            }
        });
        test_type.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                test_type.setSummary(newValue.toString());
                return true;
            }
        });

        setSummary();

    }

    private void setSummary(){
        String keyboardType, testType;
        keyboardType = PreferenceManager.getDefaultSharedPreferences(this).getString("keyboard_type", "null");
        testType = PreferenceManager.getDefaultSharedPreferences(this).getString("test_type", "KSR_Conversation");

        if(keyboardType != "null"){
            keyboard_type.setSummary(keyboardType);
        }
        test_type.setSummary(testType);
    }
}
