package com.moongoal.golfscore;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class Preferences extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
	}

}
