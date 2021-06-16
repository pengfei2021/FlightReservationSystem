// customized exception
public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException() {
        super("Reservation is Not Found");
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }

}
