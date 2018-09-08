package com.tylorlilley.talismanrandomizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends BasicActivity {

    private int MIN_PLAYERS = 1;
    private int MAX_PLAYERS = 6;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Random rand = new Random();
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<AlternateEnding> endings = new ArrayList<>();
    private ArrayList<Integer> expansions = new ArrayList<>();
    MediaPlayer bell; // = MediaPlayer.create(getApplicationContext(), R.raw.bell);
    MediaPlayer choir; // = MediaPlayer.create(getApplicationContext(), R.raw.bell);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bell = MediaPlayer.create(getApplicationContext(), R.raw.bell);
        choir = MediaPlayer.create(getApplicationContext(), R.raw.choir);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeArrays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextFields();
        updateExpansions();
    }

    protected void selectedRandomizerOption() {
        closeOptionsMenu();
    }

    protected void selectedSettingsOption() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    protected void selectedAboutOption() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    private void initializeArrays() {

        // Initialize SharedPreferences
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        // Initialize Settings
        if (!pref.getBoolean(getString(R.string.initialized), false)) {
            editor.putBoolean(getString(R.string.base_game), true);
            editor.putBoolean(getString(R.string.reaper_expansion), false);
            editor.putBoolean(getString(R.string.dungeon_expansion), false);
            editor.putBoolean(getString(R.string.frostmarch_expansion), false);
            editor.putBoolean(getString(R.string.highland_expansion), false);
            editor.putBoolean(getString(R.string.sacred_pool_expansion), false);
            editor.putBoolean(getString(R.string.dragon_expansion), false);
            editor.putBoolean(getString(R.string.blood_moon_expansion), false);
            editor.putBoolean(getString(R.string.city_expansion), false);
            editor.putBoolean(getString(R.string.firelands_expansion), false);
            editor.putBoolean(getString(R.string.woodland_expansion), false);
            editor.putBoolean(getString(R.string.harbinger_expansion), false);
            editor.putBoolean(getString(R.string.cataclysm_expansion), false);
            editor.putBoolean(getString(R.string.nether_realm_expansion), false);
            editor.putBoolean(getString(R.string.digital_edition_expansion), false);
            editor.putBoolean(getString(R.string.revealed_only), false);
            editor.putBoolean(getString(R.string.hidden_only), false);
            editor.putBoolean(getString(R.string.ending_is_hidden), false);
            editor.putString("Current Ending", "No Ending");
            editor.putString("Player 1", "No Character");
            editor.putString("Player 2", "No Character");
            editor.putString("Player 3", "No Character");
            editor.putString("Player 4", "No Character");
            editor.putString("Player 5", "No Character");
            editor.putString("Player 6", "No Character");
            editor.putInt(getString(R.string.number_of_players), 4);
            editor.putBoolean(getString(R.string.initialized), true);
            editor.apply();
            setupGameArrays();
            randomizeGameConfiguration();
        }
        else {
            // Set up initial randomization
            setupGameArrays();
            hideAndRevealFields(false);
        }
    }

    public void randomizeButton(View v) {
        editor.putString("Player 1 Name", ((TextView)findViewById(R.id.player1)).getText().toString());
        editor.putString("Player 2 Name", ((TextView)findViewById(R.id.player2)).getText().toString());
        editor.putString("Player 3 Name", ((TextView)findViewById(R.id.player3)).getText().toString());
        editor.putString("Player 4 Name", ((TextView)findViewById(R.id.player4)).getText().toString());
        editor.putString("Player 5 Name", ((TextView)findViewById(R.id.player5)).getText().toString());
        editor.putString("Player 6 Name", ((TextView)findViewById(R.id.player6)).getText().toString());
        editor.apply();
        randomizeGameConfiguration();
    }

    public void killPlayer1(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(1, true);
    }
    public void killPlayer2(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(2, true);
    }
    public void killPlayer3(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(3, true);
    }
    public void killPlayer4(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(4, true);
    }
    public void killPlayer5(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(5, true);
    }
    public void killPlayer6(View v) throws IOException {
        bell.stop();
        bell.prepare();
        bell.start();
        getNextPlayer(6, true);
    }


    public void revealEnding(View v) throws IOException {
        if (pref.getBoolean(getString(R.string.ending_is_hidden), false)) {
            choir.stop();
            choir.prepare();
            choir.start();
            editor.putBoolean(getString(R.string.ending_is_hidden), false);
            editor.apply();
        } else {
            getNextEnding(true);
        }
        hideAndRevealFields(false);
    }

    private void updateExpansions() {
        expansions = new ArrayList<Integer>();
        if (pref.getBoolean(getString(R.string.base_game), true)) { expansions.add(0); }
        if (pref.getBoolean(getString(R.string.reaper_expansion), false)) { expansions.add(1); }
        if (pref.getBoolean(getString(R.string.dungeon_expansion), false)) { expansions.add(2); }
        if (pref.getBoolean(getString(R.string.frostmarch_expansion), false)) { expansions.add(3); }
        if (pref.getBoolean(getString(R.string.highland_expansion), false)) { expansions.add(4); }
        if (pref.getBoolean(getString(R.string.sacred_pool_expansion), false)) { expansions.add(5); }
        if (pref.getBoolean(getString(R.string.dragon_expansion), false)) { expansions.add(6); }
        if (pref.getBoolean(getString(R.string.blood_moon_expansion), false)) { expansions.add(7); }
        if (pref.getBoolean(getString(R.string.city_expansion), false)) { expansions.add(8); }
        if (pref.getBoolean(getString(R.string.firelands_expansion), false)) { expansions.add(9); }
        if (pref.getBoolean(getString(R.string.woodland_expansion), false)) { expansions.add(10); }
        if (pref.getBoolean(getString(R.string.harbinger_expansion), false)) { expansions.add(11); }
        if (pref.getBoolean(getString(R.string.cataclysm_expansion), false)) { expansions.add(12); }
        if (pref.getBoolean(getString(R.string.nether_realm_expansion), false)) { expansions.add(13); }
        if (pref.getBoolean(getString(R.string.digital_edition_expansion), false)) { expansions.add(14); }
    }

    private void randomizeGameConfiguration() {
        // Shuffle game arrays
        Collections.shuffle(characters);
        Collections.shuffle(endings);

        // Interpret the user's chosen settings
        updateExpansions();

        // Select the new ending
        AlternateEnding newEnding = getNextEnding(false);
        if (pref.getBoolean(getString(R.string.hidden_only), true)) {
            editor.putBoolean(getString(R.string.ending_is_hidden), true);
        } else if (pref.getBoolean(getString(R.string.revealed_only), true)) {
            editor.putBoolean(getString(R.string.ending_is_hidden), false);
        } else if (!newEnding.includeInHidden()) {
            editor.putBoolean(getString(R.string.ending_is_hidden), false);
        } else {
            editor.putBoolean(getString(R.string.ending_is_hidden), true);
        }
        editor.apply();

        // Select and reveal new characters
        hideAndRevealFields(true);
    }

    private void hideAndRevealFields(boolean getNewCharacters) {
        ArrayList<View> playerRows = new ArrayList<>();
        playerRows.add(findViewById(R.id.player1Row));
        playerRows.add(findViewById(R.id.player2Row));
        playerRows.add(findViewById(R.id.player3Row));
        playerRows.add(findViewById(R.id.player4Row));
        playerRows.add(findViewById(R.id.player5Row));
        playerRows.add(findViewById(R.id.player6Row));

        for (int i = 1; i <= 6; i++) {
            if (i <= pref.getInt(getString(R.string.number_of_players), 4)) {
                if (getNewCharacters) { getNextPlayer(i, false); }
                playerRows.get(i-1).setVisibility(View.VISIBLE);
            } else {
                String playerString = "Player "+Integer.toString(i);
                editor.putString(playerString, getString(R.string.no_character));
                playerRows.get(i-1).setVisibility(View.GONE);
            }
        }

        // Hide or reveal the ending
        if (pref.getBoolean(getString(R.string.ending_is_hidden), false)) {
            findViewById(R.id.hiddenEnding).setVisibility(View.VISIBLE);
            findViewById(R.id.revealedEnding).setVisibility(View.GONE);
            ((Button) findViewById(R.id.revealButton)).setText(getString(R.string.reveal_button));
        } else {
            findViewById(R.id.hiddenEnding).setVisibility(View.GONE);
            findViewById(R.id.revealedEnding).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.revealButton)).setText(R.string.next_ending_button);
        }
    }

    private void setupGameArrays() {
        // Create shuffled array of characters
        characters = new ArrayList<>();

        // Add Base Game Characters
        characters.add(new Character(getString(R.string.priest), 0));
        characters.add(new Character(getString(R.string.monk), 0));
        characters.add(new Character(getString(R.string.warrior), 0));
        characters.add(new Character(getString(R.string.troll), 0));
        characters.add(new Character(getString(R.string.wizard), 0));
        characters.add(new Character(getString(R.string.sorceress), 0));
        characters.add(new Character(getString(R.string.ghoul), 0));
        characters.add(new Character(getString(R.string.minstrel), 0));
        characters.add(new Character(getString(R.string.thief), 0));
        characters.add(new Character(getString(R.string.assassin), 0));
        characters.add(new Character(getString(R.string.druid), 0));
        characters.add(new Character(getString(R.string.dwarf), 0));
        characters.add(new Character(getString(R.string.elf), 0));
        characters.add(new Character(getString(R.string.prophetess), 0));

        // Add Reaper Characters
        characters.add(new Character(getString(R.string.dark_cultist), 1));
        characters.add(new Character(getString(R.string.knight), 1));
        characters.add(new Character(getString(R.string.merhant), 1));
        characters.add(new Character(getString(R.string.sage), 1));

        // Add Dungeon Characters
        characters.add(new Character(getString(R.string.amazon), 2));
        characters.add(new Character(getString(R.string.gladiator), 2));
        characters.add(new Character(getString(R.string.gypsy), 2));
        characters.add(new Character(getString(R.string.philosopher), 2));
        characters.add(new Character(getString(R.string.swashbuckler), 2));

        // Add Frostmarch Characters
        characters.add(new Character(getString(R.string.leprechaun), 3));
        characters.add(new Character(getString(R.string.ogre_chieftain), 3));
        characters.add(new Character(getString(R.string.necromancer), 3));
        characters.add(new Character(getString(R.string.warlock), 3));

        // Add Highlands Characters
        characters.add(new Character(getString(R.string.alchemist), 4));
        characters.add(new Character(getString(R.string.highlander), 4));
        characters.add(new Character(getString(R.string.rogue), 4));
        characters.add(new Character(getString(R.string.sprite), 4));
        characters.add(new Character(getString(R.string.valkyrie), 4));
        characters.add(new Character(getString(R.string.vampiress), 4));

        // Add Sacred Pool Characters
        characters.add(new Character(getString(R.string.chivalric_knight), 5));
        characters.add(new Character(getString(R.string.dread_knight), 5));
        characters.add(new Character(getString(R.string.cleric), 5));
        characters.add(new Character(getString(R.string.magus), 5));

        // Add Dragon Characters
        characters.add(new Character(getString(R.string.dragon_hunter), 6));
        characters.add(new Character(getString(R.string.dragon_priestess), 6));
        characters.add(new Character(getString(R.string.dragon_rider), 6));
        characters.add(new Character(getString(R.string.conjurer), 6));
        characters.add(new Character(getString(R.string.fire_wizard), 6));
        characters.add(new Character(getString(R.string.minotaur), 6));

        // Add Blood Moon Characters
        characters.add(new Character(getString(R.string.grave_digger), 7));
        characters.add(new Character(getString(R.string.vampire_hunter), 7));
        characters.add(new Character(getString(R.string.doomsayer), 7));

        // Add City Characters
        characters.add(new Character(getString(R.string.tinkerer), 8));
        characters.add(new Character(getString(R.string.tavern_maid), 8));
        characters.add(new Character(getString(R.string.spy), 8));
        characters.add(new Character(getString(R.string.elementalist), 8));
        characters.add(new Character(getString(R.string.bounty_hunter), 8));
        characters.add(new Character(getString(R.string.cat_burglar), 8));

        // Add Firelands Characters
        characters.add(new Character(getString(R.string.dervish), 9));
        characters.add(new Character(getString(R.string.jin_blooded), 9));
        characters.add(new Character(getString(R.string.nomad), 9));
        characters.add(new Character(getString(R.string.warlord), 9));

        // Add Woodland Characters
        characters.add(new Character(getString(R.string.ancient_oak), 10));
        characters.add(new Character(getString(R.string.leywalker), 10));
        characters.add(new Character(getString(R.string.scout), 10));
        characters.add(new Character(getString(R.string.totem_warrior), 10));
        characters.add(new Character(getString(R.string.spider_queen), 10));

        // Add Harbinger Characters
        characters.add(new Character(getString(R.string.ascendant_divine), 11));
        characters.add(new Character(getString(R.string.celestial), 11));
        characters.add(new Character(getString(R.string.possessed), 11));

        // Add Cataclysm Characters
        characters.add(new Character(getString(R.string.barbarian), 12));
        characters.add(new Character(getString(R.string.black_knight), 12));
        characters.add(new Character(getString(R.string.mutant), 12));
        characters.add(new Character(getString(R.string.scavenger), 12));
        characters.add(new Character(getString(R.string.arcane_scion), 12));

        // Add Nether Realms Characters
        //  - None

        // Add Digital Characters
        characters.add(new Character(getString(R.string.pirate), 14));
        characters.add(new Character(getString(R.string.ninja), 14));
        characters.add(new Character(getString(R.string.exorcist), 14));
        characters.add(new Character(getString(R.string.courtesan), 14));
        characters.add(new Character(getString(R.string.devils_minion), 14));
        characters.add(new Character(getString(R.string.genie), 14));
        characters.add(new Character(getString(R.string.martyr), 14));
        characters.add(new Character(getString(R.string.gambler), 14));
        characters.add(new Character(getString(R.string.black_witch), 14));
        characters.add(new Character(getString(R.string.apprentice_mage), 14));
        characters.add(new Character(getString(R.string.shape_shifter), 14));
        characters.add(new Character(getString(R.string.shaman), 14));
        characters.add(new Character(getString(R.string.illusionist), 14));
        characters.add(new Character(getString(R.string.jester), 14));
        characters.add(new Character(getString(R.string.goblin_shaman), 14));

        // Create shuffled array of endings
        endings = new ArrayList<>();

        // Add Base Game Ending
        endings.add(new AlternateEnding(getString(R.string.crown_of_command), false, false, 0));

        // Add Reaper Ending
        endings.add(new AlternateEnding(getString(R.string.danse_macabre), false, false, 1));

        // Add Dungeon Game Ending
        //  - None

        // Add Frostmarch Endings
        endings.add(new AlternateEnding(getString(R.string.crown_and_sceptre), false, false, 3));
        endings.add(new AlternateEnding(getString(R.string.ice_queen), false, false, 3));
        endings.add(new AlternateEnding(getString(R.string.warlock_quests), true, false, 3));

        // Add Highland Endings
        endings.add(new AlternateEnding(getString(R.string.battle_royale), false, false, 4));
        endings.add(new AlternateEnding(getString(R.string.eagle_king), false, false, 4));
        endings.add(new AlternateEnding(getString(R.string.hand_of_doom), false, false, 4));

        // Add Sacred Pool Endings
        endings.add(new AlternateEnding(getString(R.string.demon_lord), false, false, 5));
        endings.add(new AlternateEnding(getString(R.string.judgement_day), false, false, 5));
        endings.add(new AlternateEnding(getString(R.string.sacred_pool), true, false, 5));

        // Add Dragon Endings
        endings.add(new AlternateEnding(getString(R.string.dragon_king), false, false, 6));
        endings.add(new AlternateEnding(getString(R.string.domain_of_dragons), true, false, 6));
        endings.add(new AlternateEnding(getString(R.string.dragon_slayers), true, false, 6));

        // Add Blood Moon Endings
        endings.add(new AlternateEnding(getString(R.string.horrible_black_void), false, true, 7));
        endings.add(new AlternateEnding(getString(R.string.blood_moon_werewolf), false, true, 7));
        endings.add(new AlternateEnding(getString(R.string.Lightbearers), true, false, 7));

        // Add City Endings
        endings.add(new AlternateEnding(getString(R.string.thieves_guild), false, false, 8));
        endings.add(new AlternateEnding(getString(R.string.merchants_guild), true, false, 8));
        endings.add(new AlternateEnding(getString(R.string.assassins_guild), true, false, 8));

        // Add Fireland Endings
        endings.add(new AlternateEnding(getString(R.string.spreading_flames), false, true, 9));
        endings.add(new AlternateEnding(getString(R.string.crown_of_flame), false, true, 9));
        endings.add(new AlternateEnding(getString(R.string.a_hero_rises), true, false, 9));

        // Add Woodland Endings
        endings.add(new AlternateEnding(getString(R.string.war_of_seasons), false, false, 10));
        endings.add(new AlternateEnding(getString(R.string.judged_by_fate), false, true, 10));
        endings.add(new AlternateEnding(getString(R.string.wanderlust), true, false, 10));

        // Add Harbinger Endings
        endings.add(new AlternateEnding(getString(R.string.armageddon_crown), false, false, 11));
        endings.add(new AlternateEnding(getString(R.string.end_of_days), true, false, 11));

        // Add Cataclysm Endings
        endings.add(new AlternateEnding(getString(R.string.the_eternal_crown), false, false, 12));
        endings.add(new AlternateEnding(getString(R.string.cult_of_the_damned), false, true, 12));
        endings.add(new AlternateEnding(getString(R.string.the_one_talisman), true, false, 12));
        endings.add(new AlternateEnding(getString(R.string.lands_of_wonder), true, false, 12));

        // Add Nether Realm Endings
        endings.add(new AlternateEnding(getString(R.string.pandoras_box), false, false, 13));
        endings.add(new AlternateEnding(getString(R.string.the_hunt), true, false, 13));
        endings.add(new AlternateEnding(getString(R.string.the_guantlet), true, false, 13));
    }

    private <T> T cycleArray(ArrayList<T> list) {
        T obj = list.remove(0);
        list.add(obj);
        return obj;
    }

    private void getNextPlayer(int playerNumber, boolean isKilled) {
        // Find a character that matches the chosen preferences
        Character current = null;
        int tries = 0;
        while (current == null && tries <= characters.size()) {
            current = cycleArray(characters);
            if (!expansions.contains(current.getExpansion())) { current = null; }
            else if (isKilled && pref.getString("Player 1", "None").compareTo(current.getName()) == 0) { current = null; }
            else if (isKilled && pref.getString("Player 2", "None").compareTo(current.getName()) == 0) { current = null; }
            else if (isKilled && pref.getString("Player 3", "None").compareTo(current.getName()) == 0) { current = null; }
            else if (isKilled && pref.getString("Player 4", "None").compareTo(current.getName()) == 0) { current = null; }
            else if (isKilled && pref.getString("Player 5", "None").compareTo(current.getName()) == 0) { current = null; }
            else if (isKilled && pref.getString("Player 6", "None").compareTo(current.getName()) == 0) { current = null; }
            tries += 1;
        }

        // Save the new character as the chosen character
        String playerString = "Player "+Integer.toString(playerNumber);
        if (current == null) {
            editor.putString(playerString, getString(R.string.no_character));
        } else {
            editor.putString(playerString, current.getName());
        }
        updateTextFields();
    }

    private AlternateEnding getNextEnding(boolean isNext) {
        // Find an ending that matches the chosen preferences
        AlternateEnding current = null;
        int tries = 0;
        while (current == null && tries <= endings.size()) {
            current = cycleArray(endings);
            if (!expansions.contains(current.getExpansion())) { current = null; }
            else if (pref.getBoolean(getString(R.string.hidden_only), false) && !current.includeInHidden()) { current = null; }
            else if (pref.getBoolean(getString(R.string.revealed_only), false) && !current.includeInRevealed()) { current = null; }
            else if (isNext && pref.getString(getString(R.string.current_ending_label), getString(R.string.no_ending)).compareTo(current.getName()) == 0) { current = null; }
            tries += 1;
        }

        // Save the new ending as the chosen ending
        if (current == null) {
            editor.putString(getString(R.string.current_ending_label), getString(R.string.no_ending));
        } else {
            editor.putString(getString(R.string.current_ending_label), current.getName());
        }
        updateTextFields();
        return current;
    }

    private void updateTextFields() {
        editor.apply();
        ((TextView) findViewById(R.id.player1)).setText(pref.getString("Player 1 Name", "Player 1"));
        ((TextView) findViewById(R.id.player2)).setText(pref.getString("Player 2 Name", "Player 2"));
        ((TextView) findViewById(R.id.player3)).setText(pref.getString("Player 3 Name", "Player 3"));
        ((TextView) findViewById(R.id.player4)).setText(pref.getString("Player 4 Name", "Player 4"));
        ((TextView) findViewById(R.id.player5)).setText(pref.getString("Player 5 Name", "Player 5"));
        ((TextView) findViewById(R.id.player6)).setText(pref.getString("Player 6 Name", "Player 6"));
        ((TextView) findViewById(R.id.character1)).setText(pref.getString("Player 1", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.character2)).setText(pref.getString("Player 2", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.character3)).setText(pref.getString("Player 3", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.character4)).setText(pref.getString("Player 4", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.character5)).setText(pref.getString("Player 5", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.character6)).setText(pref.getString("Player 6", getString(R.string.no_character)));
        ((TextView) findViewById(R.id.revealedEnding)).setText(pref.getString(getString(R.string.current_ending_label), getString(R.string.no_ending)));
    }
}
