
public class Aircraft implements Comparable<Aircraft> {
	int numEconomySeats;
	int numFirstClassSeats;

	String model;

	String[][] seatLayout;

	String[] s = { "A", "B", "C", "D" };

	// constructor
	public Aircraft(int seats, String model) {
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;
		if (seats > 4)
			this.seatLayout = new String[4][(int) Math.ceil((double) seats / 4.0)];
		else
			this.seatLayout = new String[2][(int) Math.ceil((double) seats / 2.0)];
		// initialize seatLayout
		for (int i = 0; i < seatLayout.length; i++) {
			for (int j = 0; j < (int) Math.ceil((double) seats / (double) seatLayout.length); j++) {
				seatLayout[i][j] = (j + 1) + s[i];
			}
		}
	}

	// overload constructor
	public Aircraft(int economy, int firstClass, String model) {
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
		this.seatLayout = new String[4][(int) Math.ceil(((double) economy + (double) firstClass) / 4.0)];
		// initialize seatLayout for first class
		for (int i = 0; i < seatLayout.length; i++) {
			for (int j = 0; j < (int) Math.ceil((double) firstClass / (double) seatLayout.length); j++) {
				seatLayout[i][j] = (j + 1) + s[i] + "+";
			}
		}
		// initialize seatLayout for economy class
		for (int i = 0; i < seatLayout.length; i++) {
			for (int j = (int) Math.ceil((double) firstClass / (double) seatLayout.length); j < (int) Math
					.ceil(((double) economy + (double) firstClass) / (double) seatLayout.length); j++) {
				seatLayout[i][j] = (j + 1) + s[i];
			}
		}
	}

	// get the number of economy seats
	public int getNumSeats() {
		return numEconomySeats;
	}

	/**
	 * get the bumber of total seast
	 * 
	 * @return the number of total seats
	 */
	public int getTotalSeats() {
		return numEconomySeats + numFirstClassSeats;
	}

	// get the number of first class seats
	public int getNumFirstClassSeats() {
		return numFirstClassSeats;
	}

	// get the model
	public String getModel() {
		return model;
	}

	/**
	 * change the model name of the aircraft
	 * 
	 * @param model the new model name
	 */
	public void setModel(String model) {
		this.model = model;
	}

	// print out the aircraft info
	public void print() {
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: "
				+ numFirstClassSeats);
	}

	// compare two aircraft
	public int compareTo(Aircraft other) {
		if (this.numEconomySeats == other.numEconomySeats)
			return this.numFirstClassSeats - other.numFirstClassSeats;

		return this.numEconomySeats - other.numEconomySeats;
	}
}
