package com.tylorlilley.kbrandomizer;

/**
 * Created by Tylor on 6/4/2016.
 */
public class BoardConfiguration {

    int CONFIG_SIZE = 59;
    int[] config;

    // Constructors

    public BoardConfiguration(String rawInput) {
        config = new int[CONFIG_SIZE];
        if (rawInput.compareTo("") == 0) {
            for (int i = 0; i < CONFIG_SIZE; i++) {
                config[i] = 0;
            }
        }
        else {
            for (int i = 0; i < CONFIG_SIZE; i++) {
                config[i] = Character.getNumericValue(rawInput.charAt(i));
            }
        }
    }

    public BoardConfiguration(int[] input) {
        config = input;
    }

    @Override
    public String toString() {
        String configAsString = "";
        for (int i = 0; i < CONFIG_SIZE; i++) {
            configAsString = configAsString.concat( Integer.toString(config[i]) );
            config[i] = Character.getNumericValue(configAsString.charAt(i));
        }
        return configAsString;
    }

    // Convert number to array position

    private int getCardPosition(int card) {
        return 27+(card*2);
    }

    private int getBoardPosition(int quadrant) {
        return 2+(quadrant*5);
    }

    private int getNomadPosition(int space) { return 44+(space*2); }

    // Convert number to identifying number
    private int getNomadSpace(int space) {
        return config[getNomadPosition(space)]+config[getNomadPosition(space)+1];
    }

    public int getCardSet(int card) {
        return config[getCardPosition(card)];
    }

    public int getCardName(int card) {
        return config[getCardPosition(card)+1];
    }

    public int getQuadrantSet(int quadrant) {
        return config[getBoardPosition(quadrant)];
    }

    public int getQuadrantName(int quadrant) {
        return config[getBoardPosition(quadrant)+1];
    }

    public int getQuadrantFlipped(int quadrant) {
        return config[getBoardPosition(quadrant)+2];
    }

    public int getQuadrantCapitols(int quadrant) {
        return config[getBoardPosition(quadrant)+3];
    }

    public int getQuadrantCaves(int quadrant) {
        return config[getBoardPosition(quadrant)+4];
    }

    public int getTotalCards() {
        return config[1];
    }

    public int getTotalBoards() {
        return config[0];
    }

    public int getQuadrantCastles(int quadrant) {
       if (getQuadrantSet(quadrant) == 0) {
            if (getQuadrantName(quadrant) < 2) return 2;
            else return 1;
        }
        else if (getQuadrantSet(quadrant) == 2 || getQuadrantSet(quadrant) == 5) return 1;
        else return 0;
    }

    public int getQuadrantNomads(int quadrant) {
        if (getQuadrantSet(quadrant) == 1) {
            if (getQuadrantName(quadrant) == 0) return 3;
            else return 1;
        }
        else return 0;
    }

    public int getQuadrantNomadSpace(int quadrant) {
        int usedNomads = 0;
        for (int i = 0; i < quadrant; i++) {
            usedNomads += getQuadrantNomads(i);
        }
        return usedNomads;
    }

    // Parse identifying number to string

    public String parseQuadrant(int quadrant) {
        String placement = parseBoard(getQuadrantSet(quadrant), getQuadrantName(quadrant));
        if (getQuadrantFlipped(quadrant) == 1) { placement = placement.concat(" ↷"); }
        placement = placement.concat(parseQuadrantCapitols(quadrant));
        for (int i = 0; i < getQuadrantNomads(quadrant); i++) {
            placement = placement.concat(" - ");
            placement = placement.concat(parseNomadSpace(getQuadrantNomadSpace(quadrant)+i));
        }
        if (getQuadrantCaves(quadrant) == 1) { placement = placement.concat(" - Cave"); }
        return placement;
    }

    private String parseQuadrantCapitols (int quadrant) {
        switch (getQuadrantCapitols(quadrant)) {
            case 0: return "";
            case 1: return " - Capitol";
            case 2: return " - Capitol (first)";
            case 3: return " - Capitol (second)";
            case 4: return " - Capitol (both)";
        }
        return "ERROR";
    }

    private  String parseNomadSpace(int space) {
        switch (getNomadSpace(space)) {
            case 0: return "Sword";
            case 1: return "Relocation";
            case 2: return "Treasure";
            case 3: return "Outpost";
            case 4: return "Grass";
            case 5: return "Forest";
            case 6: return "Flowers";
            case 7: return "Desert";
            case 8: return "Canyon";
            case 9: return "Water";
            case 10: return "Mountain";
            case 11: return "Sword";
            case 12: return "Relocation";
            case 13: return "Treasure";
            case 14: return "Outpost";
        }
        return "ERROR";
    }

    public String parseCard(int card) {
        switch (getCardSet(card)) {
            case 0: {
                switch (getCardName(card)) {
                    case 0:
                        return "Farmers";
                    case 1:
                        return "Citizens";
                    case 2:
                        return "Lords";
                    case 3:
                        return "Hermits";
                    case 4:
                        return "Knights";
                    case 5:
                        return "Discoverers";
                    case 6:
                        return "Workers";
                    case 7:
                        return "Merchants";
                    case 8:
                        return "Miners";
                    case 9:
                        return "Fishermen";
                }
                break;
            }
            case 1: {
                switch (getCardName(card)) {
                    case 0:
                        return "Families";
                    case 1:
                        return "Ambassadors";
                    case 2:
                        return "Shepherds";
                }
                break;
            }
            case 2: {
                switch (getCardName(card)) {
                    case 0:
                        return "Place of Refuge";
                    case 1:
                        return "Home Country";
                    case 2:
                        return "Road";
                    case 3:
                        return "Advance";
                    case 4:
                        return "Compass Points";
                    case 5:
                        return "Fortress";
                }
                break;
            }
            case 3: {
                switch (getCardName(card)) {
                    case 0:
                        return "Geologists";
                    case 1:
                        return "Messengers";
                    case 2:
                        return "Noblewomen";
                    case 3:
                        return "Vassals";
                    case 4:
                        return "Captains";
                    case 5:
                        return "Scouts";
                }
                break;
            }
            case 4: {
                switch (getCardName(card)) {
                    case 0:
                        return "Mayors";
                    case 1:
                        return "Halflings";
                    case 2:
                        return "Adventurers";
                    case 3:
                        return "Rangers";
                    case 4:
                        return "Innkeepers";
                    case 5:
                        return "Rovers";
                }
                break;
            }
        }
        return "ERROR";
    }

    private String parseBoard(int set, int board) {
        switch (set) {
            case 0: {
                switch (board) {
                    case 0: return "Harbor";
                    case 1: return "Oracle";
                    case 2: return "Tavern";
                    case 3: return "Oasis";
                    case 4: return "Farmland";
                    case 5: return "Barn";
                    case 6: return "Tower";
                    case 7: return "Paddock";
                }
                break;
            }
            case 1: {
                switch (board) {
                    case 0: return "Quarry";
                    case 1: return "Caravan";
                    case 2: return "Gardens";
                    case 3: return "Village";
                }
                break;
            }
            case 2: {
                switch (board) {
                    case 0: return "Lighthouse / Forester's Lodge";
                    case 1: return "Barracks / Crossroads";
                    case 2: return "City Hall / Fort";
                    case 3: return "Wagon / Monastery";
                }
                break;
            }
            case 3: {
                switch (board) {
                    case 0: return "Temple";
                    case 1: return "Refuge";
                    case 2: return "Canoe";
                    case 3: return "Fountain";
                }
                break;
            }
            case 4: {
                switch (board) {
                    case 0: return "Cabin / Aerie";
                    case 1: return "Bazaar / Mill";
                    case 2: return "Bastion / Hospital";
                    case 3: return "Cathedrals / Border Tower";
                }
                break;
            }
            case 5: {
                switch (board) {
                    case 0: return "Island - Bridge";
                    case 1: return "Island - Treehouse";
                }
            }
        }
        return "ERROR";
    }
}
