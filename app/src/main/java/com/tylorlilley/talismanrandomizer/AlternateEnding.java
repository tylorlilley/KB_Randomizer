package com.tylorlilley.talismanrandomizer;

/**
 * Created by Tylor on 10/17/2016.
 */
public class AlternateEnding {

    private String name;
    private boolean revealed;
    private boolean hidden;
    private int expansion;

    public AlternateEnding(String givenName, boolean isRevealed, boolean isHidden, int expansionNumber) {
        name = givenName;
        revealed = isRevealed;
        hidden = isHidden;
        expansion = expansionNumber;
    }

    public String getName() { return name; }
    public int getExpansion() { return expansion; }
    public boolean includeInHidden() { return !revealed; }
    public boolean includeInRevealed() { return !hidden; }

}
