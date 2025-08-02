package de.landmann.meinefahrzeugebe.entity;

public enum ReminderStatus {
    PENDING("Offen"),
    COMPLETED("Erledigt"),
    OVERDUE("Überfällig");
    
    private final String displayName;
    
    ReminderStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}