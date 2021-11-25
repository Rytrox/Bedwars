package de.rytrox.bedwars.database.enums;

public enum SignSortValue {
    WINS, KILLS, GAMES;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
