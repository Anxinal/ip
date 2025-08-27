package shiroha.exceptions;


public class UnknownCommandException extends RuntimeException {
    private static final String HEAD = "Emm. I don't quite get what you are saying. ";
    public UnknownCommandException(String message){
        super(HEAD + message);
    }
}
