package com.tylorlilley.kbrandomizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends BasicActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_settings);

        // Initialize SharedPreferences
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        updateCheckBoxes();
    }

    protected void selectedRandomizerOption() {
        finish();
    }

    protected void selectedSettingsOption() {
        closeOptionsMenu();
    }

    protected void selectedAboutOption() {
        finish();
        startActivity(new Intent(this, AboutActivity.class));
    }

    private boolean isLastBox() {
        int boxesChecked = 0;
        if (pref.getBoolean(getString(R.string.base_game), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.nomads), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.crossroads), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.marshlands), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.harvest), false)) boxesChecked++;
        return (boxesChecked < 2);
    }

    private void updateCheckBoxes() {
        ((CheckBox)findViewById(R.id.baseGameBox)).setChecked(pref.getBoolean(getString(R.string.base_game), false));
        ((CheckBox)findViewById(R.id.nomadsBox)).setChecked(pref.getBoolean(getString(R.string.nomads), false));
        ((CheckBox)findViewById(R.id.crossroadsBox)).setChecked(pref.getBoolean(getString(R.string.crossroads), false));
        ((CheckBox)findViewById(R.id.marshlandsBox)).setChecked(pref.getBoolean(getString(R.string.marshlands), false));
        ((CheckBox)findViewById(R.id.harvestBox)).setChecked(pref.getBoolean(getString(R.string.harvest), false));
        ((CheckBox)findViewById(R.id.capitolsBox)).setChecked(pref.getBoolean(getString(R.string.capitols), false));
        ((CheckBox)findViewById(R.id.cavesBox)).setChecked(pref.getBoolean(getString(R.string.caves), false));
        ((CheckBox)findViewById(R.id.islandBox)).setChecked(pref.getBoolean(getString(R.string.island), false));
        ((CheckBox)findViewById(R.id.useWithAssignedBox)).setChecked(pref.getBoolean(getString(R.string.use_with_assigned_board), false));
        ((CheckBox)findViewById(R.id.alwaysUseBox)).setChecked(pref.getBoolean(getString(R.string.always_use), false));
    }


    public void updateExpansionSettings(View v) {
        CheckBox currentBox = (CheckBox)v;
        if (!currentBox.isChecked() && isLastBox()) {
            // TODO: Display Error Message
            currentBox.setChecked(true);
        }
        updateGenericSettings(v);
    }

    public void updateGenericSettings(View v) {
        CheckBox currentBox = (CheckBox)v;
        editor.putBoolean(currentBox.getText().toString(), (currentBox.isChecked()));
        editor.apply();
    }

}
