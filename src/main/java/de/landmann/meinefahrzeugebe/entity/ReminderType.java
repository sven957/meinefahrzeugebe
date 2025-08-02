package de.landmann.meinefahrzeugebe.entity;

public enum ReminderType {
    LEASE_END("Leasingrückgabe"),
    LICENSE_CHECK("Führerscheinüberprüfung"),
    TUV("TÜV/HU"),
    INSURANCE("Versicherung"),
    MAINTENANCE("Wartung"),
    OTHER("Sonstiges");
    
    private final String displayName;
    
    ReminderType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}