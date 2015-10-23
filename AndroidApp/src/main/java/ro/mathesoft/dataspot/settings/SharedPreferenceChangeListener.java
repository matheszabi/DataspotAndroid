package ro.mathesoft.dataspot.settings;

import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.TextView;


/**
 * Created by matheszabi on Oct/23/2015 0023.
 */
public class SharedPreferenceChangeListener implements
        SharedPreferences.OnSharedPreferenceChangeListener, ISettingsKey {

    private Handler mHandler;
    private SharedPreferences mSharedPreferences;
    private TextView mTextViewPhone;

    public SharedPreferenceChangeListener(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mHandler = new Handler();
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {

        if (KEY_BASE_URL.equals(key)) {
            // reload data:
        } else if (KEY_PHONE_NR.equals(key)) {
            if (mTextViewPhone != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reloadPhoneNumber();
                    }
                });
            }
        }
    }

    public void reloadPhoneNumber() {
        if (mTextViewPhone == null) {
            return;
        }
        String telValue = mSharedPreferences.getString(KEY_PHONE_NR, "0357.442.442");
        mTextViewPhone.setText(telValue);
    }

    public void setTextViewPhone(TextView textViewPhone) {
        this.mTextViewPhone = textViewPhone;
    }
}
