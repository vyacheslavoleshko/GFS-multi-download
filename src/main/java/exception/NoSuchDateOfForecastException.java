package exception;

public class NoSuchDateOfForecastException extends RuntimeException{
    public NoSuchDateOfForecastException(String message) {
        super(message);
    }
}
