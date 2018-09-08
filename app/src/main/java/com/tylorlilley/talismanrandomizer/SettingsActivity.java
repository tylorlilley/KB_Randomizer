package com.tylorlilley.talismanrandomizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

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
        updateSeekBar();
    }

    private void updateSeekBar() {
        SeekBar bar = (SeekBar)findViewById(R.id.seekBar);
        final TextView label = (TextView)findViewById(R.id.playerNumberLabel);
        bar.setProgress(pref.getInt(getString(R.string.number_of_players), 4)-1);
        label.setText(getString(R.string.number_of_players)+" "+Integer.toString(bar.getProgress()+1));
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                label.setText(getString(R.string.number_of_players)+Integer.toString(progress+1));
                editor.putInt(getString(R.string.number_of_players), progress+1);
                editor.apply();
            }
        });
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

    private boolean isLastBox(String boxName) {
        if (boxName.equals(getString(R.string.dungeon_expansion)) || boxName.equals(getString(R.string.nether_realm_expansion)) || boxName.equals(getString(R.string.digital_edition_expansion))) {
            return false;
        }
        int boxesChecked = 0;
        if (pref.getBoolean(getString(R.string.base_game), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.reaper_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.frostmarch_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.highland_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.sacred_pool_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.dragon_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.blood_moon_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.city_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.firelands_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.woodland_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.harbinger_expansion), false)) boxesChecked++;
        if (pref.getBoolean(getString(R.string.cataclysm_expansion), false)) boxesChecked++;
        return (boxesChecked < 2);
    }

    private void updateCheckBoxes() {
        // Expansion Check Boxes
        ((CheckBox)findViewById(R.id.baseGameBox)).setChecked(pref.getBoolean(getString(R.string.base_game), true));
        ((CheckBox)findViewById(R.id.reaperBox)).setChecked(pref.getBoolean(getString(R.string.reaper_expansion), false));
        ((CheckBox)findViewById(R.id.dungeonBox)).setChecked(pref.getBoolean(getString(R.string.dungeon_expansion), false));
        ((CheckBox)findViewById(R.id.frostmarchBox)).setChecked(pref.getBoolean(getString(R.string.frostmarch_expansion), false));
        ((CheckBox)findViewById(R.id.highlandBox)).setChecked(pref.getBoolean(getString(R.string.highland_expansion), false));
        ((CheckBox)findViewById(R.id.sacredPoolBox)).setChecked(pref.getBoolean(getString(R.string.sacred_pool_expansion), false));
        ((CheckBox)findViewById(R.id.dragonBox)).setChecked(pref.getBoolean(getString(R.string.dragon_expansion), false));
        ((CheckBox)findViewById(R.id.bloodMoonBox)).setChecked(pref.getBoolean(getString(R.string.blood_moon_expansion), false));
        ((CheckBox)findViewById(R.id.cityBox)).setChecked(pref.getBoolean(getString(R.string.city_expansion), false));
        ((CheckBox)findViewById(R.id.firelandsBox)).setChecked(pref.getBoolean(getString(R.string.firelands_expansion), false));
        ((CheckBox)findViewById(R.id.woodlandBox)).setChecked(pref.getBoolean(getString(R.string.woodland_expansion), false));
        ((CheckBox)findViewById(R.id.harbingerBox)).setChecked(pref.getBoolean(getString(R.string.harbinger_expansion), false));
        ((CheckBox)findViewById(R.id.cataclysmBox)).setChecked(pref.getBoolean(getString(R.string.cataclysm_expansion), false));
        ((CheckBox)findViewById(R.id.netherRealmBox)).setChecked(pref.getBoolean(getString(R.string.nether_realm_expansion), false));
        ((CheckBox)findViewById(R.id.digitalBox)).setChecked(pref.getBoolean(getString(R.string.digital_edition_expansion), false));

        // Settings Check Boxes
        ((CheckBox)findViewById(R.id.hiddenOnlyBox)).setChecked(pref.getBoolean(getString(R.string.hidden_only), true));
        ((CheckBox)findViewById(R.id.revealedOnlyBox)).setChecked(pref.getBoolean(getString(R.string.revealed_only), false));
    }


    public void updateExpansionSettings(View v) {
        CheckBox currentBox = (CheckBox)v;
        if (!currentBox.isChecked() && isLastBox((currentBox.getText().toString()))) {
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
