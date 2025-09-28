package shiroha.exceptions;


public class UnknownCommandException extends RuntimeException {
    private static final String HEAD = "Emm. I don't quite get what you are saying. ";
    /**
     * Constructor for UnknownCommandException
     * @param message the message to be displayed by the chatbot
     */
    public UnknownCommandException(String message){
        super(HEAD + message);
    }
}
