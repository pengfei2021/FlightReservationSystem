
public class Passenger extends Person {
	private String seat;
	private String seatType;
	private boolean board;

	public Passenger(String name, String passport, String seat, String seatType) {
		super(name, passport);
		this.seat = seat.toUpperCase();
		this.seatType = seatType.toUpperCase();
		board = false;
	}

	public Passenger(String name, String passport) {
		super(name, passport);
		board = false;
	}

	// override equals method
	public boolean equals(Object other) {
		Passenger otherP = (Passenger) other;
		return this.getName().equalsIgnoreCase(otherP.getName())
				&& this.getPassport().equalsIgnoreCase(otherP.getPassport());
	}

	// get seat type
	public String getSeatType() {
		return seatType;
	}

	// change seat type
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	// get passenger seat
	public String getSeat() {
		return seat;
	}

	// change passenger seat
	public void setSeat(String seat) {
		this.seat = seat;
	}

	// print passenger
	public String toString() {
		String boardStatus;
		if (board)
			boardStatus = "BOARD";
		else
			boardStatus = "NOT_BOARD";
		return super.toString() + "\t " + seat + "\t " + boardStatus;
	}

	// board the passenger
	public void passBoard() {
		board = true;
	}
}
