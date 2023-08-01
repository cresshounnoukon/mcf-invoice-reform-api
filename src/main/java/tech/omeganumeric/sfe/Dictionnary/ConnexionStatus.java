package tech.omeganumeric.sfe.Dictionnary;

public enum ConnexionStatus {



    USERNAME_EXIST(1, "Cette nom d'utilisateur est déjà disponible."),
    EMAIL_EXIST(2, "Cette adresse emaile st déjà disponible."),
    USER_REGISTERED(3, "Utilisateur enregistré avec succès.");

    private final int value;
    private final String reason;

    ConnexionStatus(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
