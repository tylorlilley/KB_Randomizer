package com.tylorlilley.kbrandomizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends BasicActivity {

    private int MAX_BOARDS = 5;
    private int MAX_CARDS = 8;
    private int MIN_BOARDS = 4;
    private int MIN_CARDS = 3;
    private BoardConfiguration currentConfiguration;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean useBaseGame;
    private boolean useNomads;
    private boolean useCrossroads;
    private boolean useMarshlands;
    private boolean useHarvest;
    private boolean useCapitols;
    private boolean useCaves;
    private boolean useIsland;
    private boolean alwaysUsePromos;
    private boolean usePromosRandomly;
    private Random rand = new Random();
    private ArrayList<TextView> quadrants = new ArrayList<>();
    private ArrayList<TextView> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeArrays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSettings();
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

        // Initialize TextView references
        quadrants.add((TextView)findViewById(R.id.quadrant1));
        quadrants.add((TextView)findViewById(R.id.quadrant2));
        quadrants.add((TextView)findViewById(R.id.quadrant3));
        quadrants.add((TextView)findViewById(R.id.quadrant4));
        quadrants.add((TextView)findViewById(R.id.quadrant5));
        cards.add((TextView)findViewById(R.id.card1));
        cards.add((TextView)findViewById(R.id.card2));
        cards.add((TextView)findViewById(R.id.card3));
        cards.add((TextView)findViewById(R.id.card4));
        cards.add((TextView)findViewById(R.id.card5));
        cards.add((TextView)findViewById(R.id.card6));
        cards.add((TextView)findViewById(R.id.card7));
        cards.add((TextView)findViewById(R.id.card8));

        // Initialize Settings
        if (!pref.getBoolean(getString(R.string.initialized), false)) {
            editor.putBoolean(getString(R.string.base_game), true);
            editor.putBoolean(getString(R.string.nomads), false);
            editor.putBoolean(getString(R.string.crossroads), false);
            editor.putBoolean(getString(R.string.marshlands), false);
            editor.putBoolean(getString(R.string.harvest), false);
            editor.putBoolean(getString(R.string.capitols), false);
            editor.putBoolean(getString(R.string.caves), false);
            editor.putBoolean(getString(R.string.island), false);
            editor.putBoolean(getString(R.string.use_with_assigned_board), true);
            editor.putBoolean(getString(R.string.always_use), false);
            editor.apply();
        }
        updateSettings();

        // Construct initial arrays and perform initial randomization
        if (pref.getBoolean(getString(R.string.initialized), false)) {
            currentConfiguration = new BoardConfiguration(pref.getString(getString(R.string.current_configuration), ""));
            displayCurrentBoardConfiguration();
        }
        else {
            randomizeBoardConfiguration();
            displayCurrentBoardConfiguration();
            editor.putBoolean(getString(R.string.initialized), true);
            editor.apply();
        }
    }

    private void updateSettings() {
        useBaseGame = pref.getBoolean(getString(R.string.base_game), true);
        useNomads = pref.getBoolean(getString(R.string.nomads), false);
        useCrossroads = pref.getBoolean(getString(R.string.crossroads), false);
        useMarshlands = pref.getBoolean(getString(R.string.marshlands), false);
        useHarvest = pref.getBoolean(getString(R.string.harvest), false);
        useCapitols = pref.getBoolean(getString(R.string.capitols), false);
        useCaves = pref.getBoolean(getString(R.string.caves), false);
        useIsland = pref.getBoolean(getString(R.string.island), false);
        alwaysUsePromos = pref.getBoolean(getString(R.string.always_use), false);
        usePromosRandomly = !pref.getBoolean(getString(R.string.use_with_assigned_board), false);
    }

    public void randomizeButton(View v) {
        randomizeBoardConfiguration();
        displayCurrentBoardConfiguration();
    }

    private void randomizeBoardConfiguration() {

        // Create shuffled array of boards
        ArrayList<int[]> boards = new ArrayList<>();
        if (useBaseGame) {
            for (int i = 0; i < 8; i++) {
                int[] board = new int[2];
                board[0] = 0;
                board[1] = i;
                boards.add(board);
            }
        }
        if (useNomads) {
            for (int i = 0; i < 4; i++) {
                int[] board = new int[2];
                board[0] = 1;
                board[1] = i;
                boards.add(board);
            }
        }
        if (useCrossroads) {
            for (int i = 0; i < 4; i++) {
                int[] board = new int[2];
                board[0] = 2;
                board[1] = i;
                boards.add(board);
            }
        }
        if (useMarshlands) {
            for (int i = 0; i < 4; i++) {
                int[] board = new int[2];
                board[0] = 3;
                board[1] = i;
                boards.add(board);
            }
        }
        if (useHarvest) {
            for (int i = 0; i < 4; i++) {
                int[] board = new int[2];
                board[0] = 4;
                board[1] = i;
                boards.add(board);
            }
        }
        Collections.shuffle(boards);

        // Create shuffled array of kingdom cards
        ArrayList<int[]> cards = new ArrayList<>();
        if (useBaseGame) {
            for (int i = 0; i < 10; i++) {
                int[] card = new int[2];
                card[0] = 0;
                card[1] = i;
                cards.add(card);
            }
        }
        if (useNomads) {
            for (int i = 0; i < 3; i++) {
                int[] card = new int[2];
                card[0] = 1;
                card[1] = i;
                cards.add(card);
            }
        }
        if (useMarshlands) {
            for (int i = 0; i < 6; i++) {
                int[] card = new int[2];
                card[0] = 3;
                card[1] = i;
                cards.add(card);
            }
        }
        if (useHarvest) {
            for (int i = 0; i < 6; i++) {
                int[] card = new int[2];
                card[0] = 4;
                card[1] = i;
                cards.add(card);
            }
        }
        Collections.shuffle(cards);

        // Create shuffled array of task cards
        ArrayList<int[]> tasks = new ArrayList<>();
        if (useCrossroads) {
            for (int i = 0; i < 6; i++) {
                int[] card = new int[2];
                card[0] = 2;
                card[1] = i;
                tasks.add(card);
            }
        }
        Collections.shuffle(tasks);

        // Create shuffled array of nomad spaces
        ArrayList<int[]> spaces = new ArrayList<>();
        if (useNomads) {
            for (int i = 0; i < 15; i++) {
                int[] space = new int[2];
                if (i < 10) {
                    space[0] = 0;
                    space[1] = i;
                } else {
                    space[0] = i;
                    space[1] = 9;
                }
                spaces.add(space);
            }
        }
        Collections.shuffle(spaces);


        // Determine if the Island will be used
        boolean usingIsland = alwaysUsePromos && useIsland;
        if (useIsland) {
            if (usePromosRandomly) {
                if (rand.nextInt(boards.size()) < 2) {
                    usingIsland = true;
                }
            } else {
                for (int i = 0; i < MIN_BOARDS; i++) {
                    if ((boards.get(i)[0] == 0 && boards.get(i)[1] == 0) ||
                            boards.get(i)[0] == 2 && boards.get(i)[1] == 0) {
                        usingIsland = true;
                    }
                }
            }
        }

        // Determine if Caves will be used
        boolean usingCaves = alwaysUsePromos && useCaves;
        if (useCaves) {
            if (usePromosRandomly) {
                if (rand.nextInt(boards.size()) == 0) {
                    usingCaves = true;
                }
            } else {
                for (int i = 0; i < MIN_BOARDS; i++) {
                    if (boards.get(i)[0] == 0 && boards.get(i)[1] == 2) {
                        usingCaves = true;
                    }
                }
            }
        }

        // Determine if Capitols will be used
        boolean usingCapitols = alwaysUsePromos && useCapitols && (useBaseGame || useCrossroads || usingIsland);
        if (useCapitols) {
            if (usePromosRandomly) {
                if (rand.nextInt(boards.size()) < 2) {
                    usingCapitols = true;
                }
            } else {
                for (int i = 0; i < MIN_BOARDS; i++) {
                    if (boards.get(i)[0] == 0 && boards.get(i)[1] < 2) {
                        usingCapitols = true;
                    }
                }
            }
        }

        // Determine how many boards there will be
        int totalBoards = 4;
        if (usingIsland) { totalBoards++; }

        // Determine how many cards there will be
        int totalCards = 3;
        for (int i = 0; i < MIN_BOARDS; i++) {
            if (boards.get(i)[0] == 2) { totalCards++; }
        }
        if (!useBaseGame && !useNomads && !useMarshlands && !useHarvest) { totalCards -= 3; }

        // Determine which side of island will be used
        if (usingIsland) {
            int[] newBoard = new int[2];
            newBoard[0] = 5;
            if (rand.nextBoolean()) { newBoard[1] = 1; }
            else { newBoard[1] = 0; }
            boards.add(4, newBoard);
        }

        // Determine which boards will have caves
        int noCaveBoard = 4;
        if (usingCaves) {
            // No Cave on Oasis
            for (int i = 0; i < MIN_BOARDS; i++) {
                if (boards.get(i)[0] == 0 && boards.get(i)[1] == 3) { noCaveBoard = i; }
            }
            if (usingIsland && boards.get(4)[1] == 1) {
                // Five possible Island locations
                noCaveBoard = rand.nextInt(5);
            }
        }

        // Determine which boards will have capitols
        ArrayList<int[]> capitolBoards = new ArrayList<>();
        if (usingCapitols) {
            if (usePromosRandomly) {
                for (int i = 0; i < totalBoards; i++) {
                    // Add board if it has one castle
                    if (boards.get(i)[0] == 0 || boards.get(i)[0] == 2 || boards.get(i)[0] == 5) {
                        capitolBoards.add(boards.get(i));
                    }
                    // Add board again if it has two castles
                    if (boards.get(i)[0] == 0 && (boards.get(i)[1] == 0 || boards.get(i)[1] == 1)) {
                        capitolBoards.add(boards.get(i));
                    }
                }
                Collections.shuffle(capitolBoards);
            }
            else {
                int[] doubleBoard = new int[2];
                doubleBoard[0] = 0;
                doubleBoard[1] = 0;
                capitolBoards.add(doubleBoard);
                doubleBoard = new int[2];
                doubleBoard[0] = 0;
                doubleBoard[1] = 1;
                capitolBoards.add(doubleBoard);
            }
        }

        // Construct new string for new BoardConfiguration object
        String newConfig = "";
        newConfig = newConfig.concat(Integer.toString(totalBoards));
        newConfig = newConfig.concat(Integer.toString(totalCards));

        // Add Boards to new string
        for (int i = 0; i < MAX_BOARDS; i++) {
            // Hide any boards in excess of those used this time
            if (i < totalBoards) {
                newConfig = newConfig.concat(Integer.toString(boards.get(i)[0]));
                newConfig = newConfig.concat(Integer.toString(boards.get(i)[1]));
                // Determine if flipped
                if (boards.get(i)[0] < 5 && rand.nextBoolean()) { newConfig = newConfig.concat("1"); }
                else { newConfig = newConfig.concat("0"); }
                // Determine if capitol is used
                if (usingCapitols) {
                    if (capitolBoards.get(0)[0] == boards.get(i)[0] &&
                            capitolBoards.get(0)[1] == boards.get(i)[1]) {
                        if (capitolBoards.size() > 1 &&
                                capitolBoards.get(1)[0] == boards.get(i)[0] &&
                                capitolBoards.get(1)[1] == boards.get(i)[1]) {
                            // Both capitols on this double board
                            newConfig = newConfig.concat("4");
                        }
                        else if (boards.get(i)[0] == 0 && (boards.get(i)[1] == 0 || boards.get(i)[1] == 1)) {
                                // One capitol on this double board
                                if (rand.nextBoolean()) { newConfig = newConfig.concat("2"); }
                                else { newConfig = newConfig.concat("3"); }
                        }
                        else {
                            newConfig = newConfig.concat("1");
                        }
                    }
                    else if (capitolBoards.size() > 1 &&
                            capitolBoards.get(1)[0] == boards.get(i)[0] &&
                            capitolBoards.get(1)[1] == boards.get(i)[1]) {
                        if (boards.get(i)[0] == 0 && (boards.get(i)[1] == 0 || boards.get(i)[1] == 1)) {
                            // One capitol on this double board
                            if (rand.nextBoolean()) { newConfig = newConfig.concat("2"); }
                            else { newConfig = newConfig.concat("3"); }
                        }
                        else {
                            newConfig = newConfig.concat("1");
                        }
                    }
                    else { newConfig = newConfig.concat("0"); }
                }
                else { newConfig = newConfig.concat("0"); }
                // Determine if cave is used
                if (usingCaves && i != noCaveBoard){ newConfig = newConfig.concat("1"); }
                else { newConfig = newConfig.concat("0"); }
            }
            else {
                // Add a dummy board
                newConfig = newConfig.concat("00000");
            }
        }

        // Add Cards to new string
        for (int i = 0; i < MAX_CARDS; i++) {
            // Hide any boards in excess of those used this time
            if (i < totalCards) {
                if (i < cards.size()) {
                    if (i < MIN_CARDS) {
                        // Add a normal card
                        newConfig = newConfig.concat(Integer.toString(cards.get(i)[0]));
                        newConfig = newConfig.concat(Integer.toString(cards.get(i)[1]));
                    } else {
                        // Add a task card
                        newConfig = newConfig.concat(Integer.toString(tasks.get(i - MIN_CARDS)[0]));
                        newConfig = newConfig.concat(Integer.toString(tasks.get(i - MIN_CARDS)[1]));
                    }
                }
                else if (i < tasks.size()) {
                    // Add a task card
                    newConfig = newConfig.concat(Integer.toString(tasks.get(i)[0]));
                    newConfig = newConfig.concat(Integer.toString(tasks.get(i)[1]));
                }
                else {
                    // Add a dummy card
                    newConfig = newConfig.concat("00");
                }
            }
            else {
                // Add a dummy card
                newConfig = newConfig.concat("00");
            }
        }

        // Add Nomad Spaces to new string
        if (useNomads) {
            for (int i = 0; i < MAX_CARDS; i++) {
                // Hide any boards in excess of those used this time
                newConfig = newConfig.concat(Integer.toString(spaces.get(i)[0]));
                newConfig = newConfig.concat(Integer.toString(spaces.get(i)[1]));
            }
        }
        else {
            // Add dummy spaces
            newConfig = newConfig.concat("00000000");
            newConfig = newConfig.concat("00000000");
        }

        // Save needed information to shared preferences
        putCurrentConfiguration(new BoardConfiguration(newConfig));
    }

    private void displayCurrentBoardConfiguration() {
        // Set the display based on the saved information
        BoardConfiguration config = getCurrentConfiguration();
        int pos = 0;

        // Set Quadrant Text
        for (int i = 0; i < 5; i++) {
            if (i >= config.getTotalBoards()) quadrants.get(i).setVisibility(View.GONE);
            else {
                quadrants.get(i).setVisibility(View.VISIBLE);
                quadrants.get(i).setText(config.parseQuadrant(i));
                format(quadrants.get(i), config.getQuadrantSet(i));
            }
        }

        // Set Card Text
        for (int i = 0; i < 8; i++) {
            if (i >= config.getTotalCards()) cards.get(i).setVisibility(View.GONE);
            else {
                cards.get(i).setVisibility(View.VISIBLE);
                cards.get(i).setText(config.parseCard(i));
                format(cards.get(i), config.getCardSet(i));
            }
        }
    }

    private BoardConfiguration getCurrentConfiguration() {
        String currentConfig = pref.getString(getString(R.string.current_configuration), "");
        return new BoardConfiguration(currentConfig);
    }

    private void putCurrentConfiguration(BoardConfiguration config) {
        editor.putString(getString(R.string.current_configuration), config.toString());
        editor.apply();
    }

    private void format(TextView object, int set) {
        switch (set) {
            case 0: {
                object.setBackgroundColor(Color.rgb(224, 224, 224));
                break;
            }
            case 1: {
                object.setBackgroundColor(Color.rgb(229, 115, 115));
                break;
            }
            case 2: {
                object.setBackgroundColor(Color.rgb(100, 181, 246));
                break;
            }
            case 3: {
                object.setBackgroundColor(Color.rgb(161, 136, 127));
                break;
            }
            case 4: {
                object.setBackgroundColor(Color.rgb(255, 213, 79));
                break;
            }
            case 5: {
                object.setBackgroundColor(Color.rgb(178, 223, 219));
                break;
            }
        }
    }

}
