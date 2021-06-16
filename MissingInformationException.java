// customized exception
public class MissingInformationException extends Exception {
    public MissingInformationException() {
        super("Missing FlightNumber or Name or PassPortNumber or SeatNumber");
    }

    public MissingInformationException(String message) {
        super(message);
    }
}
