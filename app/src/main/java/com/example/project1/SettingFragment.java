package com.example.project1;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // below line is used to add preference
        // fragment from our xml folder.
        addPreferencesFromResource(R.xml.prefences);
    }
}