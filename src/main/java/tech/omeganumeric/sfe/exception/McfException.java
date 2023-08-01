package tech.omeganumeric.sfe.exception;


public class McfException extends  RuntimeException{
    private int error;
    private String message;

    public McfException( int error, String message) {
        super(message);
        this.error = error;
        this.message = message;
    }
}
