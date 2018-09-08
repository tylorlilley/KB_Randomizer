package com.tylorlilley.talismanrandomizer;

/**
 * Created by Tylor on 10/17/2016.
 */
public class Character {

    private String name;
    private int expansion;

    public Character(String givenName, int expansionNumber) {
        name = givenName;
        expansion = expansionNumber;
    }

    public String getName() { return name; }
    public int getExpansion() { return expansion; }
}
