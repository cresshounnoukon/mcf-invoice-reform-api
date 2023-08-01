package tech.omeganumeric.sfe.Dictionnary;

public enum ResponseMessage {

    /*
        LIST RESERVED AREA
     */

    INTERNAL_SERVER_ERROR(1, String.format("UNE ERREUR INTERNE EST SURVENNUE DONT LA CAUSE EST INCONNUE.")),




    LIST_LOAD_SUCCESSFULLY(2, String.format("LISTE TROUVEE AVEC SUCCES.")),
    LIST_EMPTY(3, String.format("LISTE EST VIDE. AUCUNE DONNEE DISPONIBLE."));


    private final int value;
    private final String reason;

    ResponseMessage(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for property 'reason'.
     *
     * @return Value for property 'reason'.
     */
    public String getReason() {
        return reason;
    }
}
