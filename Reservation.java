
public class Reservation {
	private String flightNum;
	private String flightInfo;
	private boolean firstClass;
	private String passengerName;
	private String passengerPassport;
	private String seat;

	// constructor
	public Reservation(String flightNum, String info) {
		this.flightNum = flightNum;
		this.flightInfo = info;
	}

	// overload constructor
	public Reservation(String flightNum, String name, String passport) {
		this.flightNum = flightNum;
		this.passengerName = name;
		this.passengerPassport = passport;
	}

	// overload constructor
	public Reservation(String flightNum, String info, String name, String passport, String seat, String seatType) {
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.passengerName = name;
		this.passengerPassport = passport;
		this.seat = seat;
		this.firstClass = seatType.equalsIgnoreCase("FCL");

	}

	// get flight number
	public String getFlightNum() {
		return flightNum;
	}

	// get flight info
	public String getFlightInfo() {
		return flightInfo;
	}

	// get the class type of the reservation
	public String getFirstClass() {
		if (firstClass)
			return "FCL";
		else
			return "ECO";
	}

	// get passenger name
	public String getPassName() {
		return passengerName;
	}

	// get passenger passport
	public String getPassPass() {
		return passengerPassport;
	}

	// get passenger seat
	public String getSeat() {
		return seat;
	}

	// change flight info
	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	// override equals method
	public boolean equals(Reservation other) {
		return other.flightNum.equalsIgnoreCase(this.flightNum)
				&& other.passengerName.equalsIgnoreCase(this.passengerName)
				&& other.passengerPassport.equalsIgnoreCase(this.passengerPassport);
	}

	// print reservation info
	public void print() {
		System.out.println(flightInfo + " " + passengerName + " " + passengerPassport + " " + seat);
	}
}
