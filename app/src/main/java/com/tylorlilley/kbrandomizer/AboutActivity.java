package com.tylorlilley.kbrandomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_about);
    }

    protected void selectedRandomizerOption() {
        finish();
    }

    protected void selectedSettingsOption() {
        finish();
        startActivity(new Intent(this, SettingsActivity.class));
    }

    protected void selectedAboutOption() {
        closeOptionsMenu();
    }

}
