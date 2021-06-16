import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Scanner;

public class Flight {
	public enum Status {
		DELAYED, ONTIME, ARRIVED, INFLIGHT
	};

	public static enum Type {
		SHORTHAUL, MEDIUMHAUL, LONGHAUL
	};

	public static enum SeatType {
		ECONOMY, FIRSTCLASS, BUSINESS
	};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	// key is seat number, value is passenger
	protected TreeMap<String, Passenger> seatMap;
	// store piority queue
	protected TreeMap<Integer, String> priorityQueue = new TreeMap<Integer, String>();
	// set to store crew menmebrs
	protected HashSet<CrewMember> crewMembers = new HashSet<CrewMember>();

	protected Random random = new Random();

	public Flight() {
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		// map seats to passenger
		seatMap = new TreeMap<String, Passenger>();
	}

	public Flight(String flightNum) {
		this.flightNum = flightNum;
	}

	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration,
			Aircraft aircraft) {
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}

	// get flight type
	public Type getFlightType() {
		return type;
	}

	// get flight number
	public String getFlightNum() {
		return flightNum;
	}

	// change flight number
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	// get airline
	public String getAirline() {
		return airline;
	}

	// change airline
	public void setAirline(String airline) {
		this.airline = airline;
	}

	// get origin
	public String getOrigin() {
		return origin;
	}

	// change origin
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	// get destination
	public String getDest() {
		return dest;
	}

	// change destination
	public void setDest(String dest) {
		this.dest = dest;
	}

	// get departure time
	public String getDepartureTime() {
		return departureTime;
	}

	// change departure time
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	// get aircraft
	public Aircraft getAircraft() {
		return aircraft;
	}

	// get flight status
	public Status getStatus() {
		return status;
	}

	// change flight status
	public void setStatus(Status status) {
		this.status = status;
	}

	// get flight duration
	public int getFlightDuration() {
		return flightDuration;
	}

	// change flight duration
	public void setFlightDuration(int dur) {
		this.flightDuration = dur;
	}

	// get the number of passengers
	public int getNumPassengers() {
		return numPassengers;
	}

	// change the number of passengers
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	// cancel seat for the flight
	public boolean cancelSeat(String name, String passport, String seat, String seatType)
			throws SeatTypeException, PassengerNotFoundException {
		// check if the seat is economy class
		if (!seatType.equalsIgnoreCase("ECO")) {
			throw new SeatTypeException("Seat Type Incorrect");
		}
		Passenger p = new Passenger(name, passport);
		// check if the passenger is on the flight
		if (manifest.indexOf(p) == -1)
			throw new PassengerNotFoundException("Passenger " + name + " " + passport + " Not Found");

		// check if the seat is taken
		if (!seatMap.containsKey(seat))
			throw new PassengerNotFoundException("Passenger " + name + " " + passport + " Not Found");
		// remove the passenger from the manifest
		manifest.remove(p);
		// remove the passenger from the seat map
		seatMap.remove(seat);
		if (numPassengers > 0)
			numPassengers--;
		return true;
	}

	// reserve seat for the flight
	public boolean reserveSeat(String name, String passport, String seat, String seatType)
			throws FlightFullException, SeatOccupiedException, SeatTypeException, DuplicatePassengerException {
		boolean seatAva = false;
		// check if the seats is full
		if (numPassengers >= aircraft.getNumSeats())
			throw new FlightFullException("Flight " + flightNum + " Full");
		// check if the seat is eco
		if (!seatType.equalsIgnoreCase("ECO"))
			throw new SeatTypeException("Flight " + flightNum + " Invalid Seat Type Request");

		// check if the seat exist
		for (int i = 0; i < aircraft.seatLayout.length; i++) {
			for (String s : aircraft.seatLayout[i]) {
				if (s.equalsIgnoreCase(seat))
					seatAva = true;
			}
		}
		if (!seatAva)
			throw new SeatOccupiedException(seat + " Does Not Exist");
		Passenger p = new Passenger(name, passport, seat, seatType);
		// Check for duplicate passenger
		if (manifest.indexOf(p) >= 0)
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
		// check if the seat is taken
		if (seatMap.containsKey(seat))
			throw new SeatOccupiedException("Seat Occupied");
		// add passenger to seatmap
		seatMap.put(seat, p);
		// add passenget to manifest
		manifest.add(p);
		numPassengers++;
		return true;
	}

	// override the equals method
	public boolean equals(Object other) {
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}

	// print out the flight info
	public String toString() {
		return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime
				+ "\t Duration: " + flightDuration + "\t Status: " + status;
	}

	// prints all the passenger on the flight
	public void printPassengerManifest() {
		for (Passenger p : manifest)
			System.out.println(p.toString());
	}

	// prints the seat layout
	public void printSeats() {

		for (int i = 0; i < aircraft.seatLayout.length; i++) {

			// print the middle walk way before the thrid row
			if (i == 2) {
				System.out.println(" ");
				for (String s : aircraft.seatLayout[i]) {
					// print XX if seat is taken
					if (seatMap.containsKey(s))
						System.out.print("XX ");
					else
						System.out.print(s + " ");
				}
			} else {
				for (String s : aircraft.seatLayout[i]) {
					// print XX if seat is taken
					if (seatMap.containsKey(s))
						System.out.print("XX ");
					else
						System.out.print(s + " ");
				}
			}
			System.out.println(" ");

		}

	}

	// print manifest
	public void printManifest() {
		for (Passenger p : manifest)
			System.out.println(p.toString());
	}

	// crate priority queue
	public void preBoard(String flightNum) {
		// add the seats in the queue
		for (String s : seatMap.keySet()) {
			// set the keys
			int key = Integer.parseInt(s.replaceAll("\\D", "")) * 10;
			String temps = s.replaceAll("[0-9]", "");
			if (Character.compare(temps.charAt(0), 'A') == 0)
				key += 1;
			if (Character.compare(temps.charAt(0), 'B') == 0)
				key += 2;
			if (Character.compare(temps.charAt(0), 'C') == 0)
				key += 3;
			if (Character.compare(temps.charAt(0), 'D') == 0)
				key += 4;
			// add the seat to the queue
			priorityQueue.put(key, s);
		}

	}

	// print priority queue
	public String queue() throws QueueNotCrateException {
		String queue = "";
		// check if the queue exist
		if (priorityQueue.isEmpty())
			throw new QueueNotCrateException("Priority Queue does not exist");
		for (int s : priorityQueue.keySet())
			queue = queue + " " + priorityQueue.get(s);
		return queue;
	}

	// board the passenger
	public void board(String startRow, String endRow) throws QueueNotCrateException, RowOutOfRangeException {
		int startrow;
		int endrow;
		int row;
		// check if the priority queue is crated
		if (priorityQueue.isEmpty())
			throw new QueueNotCrateException("Priority Queue does not exist");
		// initialize start row and end row
		startrow = Integer.parseInt(startRow);
		endrow = Integer.parseInt(endRow);
		// check if start row and end row is out of range
		if (startrow <= 0)
			throw new RowOutOfRangeException("Start Row Should Be Larger Than 0");
		if (endrow <= 0)
			throw new RowOutOfRangeException("End Row Should Be Larger Than 0");
		if (startrow > aircraft.getTotalSeats() / 4)
			throw new RowOutOfRangeException("Start Row Should Be Smaller Than Max Row Of The Aircraft");
		// store seat needed to be removed
		HashSet<Integer> itemRemove = new HashSet<Integer>();
		// go through priority queue
		for (int i : priorityQueue.keySet()) {
			// get the value
			String s = priorityQueue.get(i);
			// get the row number
			row = Integer.parseInt(s.replaceAll("\\D", ""));
			// check if the row number is in the range
			if (startrow <= row && row <= endrow) {
				itemRemove.add(i);
				// board the passenger in seat map
				seatMap.get(s).passBoard();
				// board the passenger in manifest
				manifest.get(manifest.indexOf(seatMap.get(s))).passBoard();
			}
		}
		// remove the seats
		for (int i : itemRemove)
			priorityQueue.remove(i);
		itemRemove.clear();
		// if the queue is empty change the status to inflight
		if (priorityQueue.isEmpty()) {
			System.out.println("All Passenger Has Been Boarded");
			status = Status.INFLIGHT;
		}
	}

	// get flight crew
	public HashSet<CrewMember> getCrew() {
		return crewMembers;
	}

	// add crew member to flight
	public void newCrew(CrewMember member) {
		crewMembers.add(member);
		System.out.println(member.getName() + " Added To Flight");
	}
}
