import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;

import java.util.HashSet;

public class FlightManager {

	String[] cities = { "Dallas", "New York", "London", "Paris", "Tokyo" };
	final int DALLAS = 0;
	final int NEWYORK = 1;
	final int LONDON = 2;
	final int PARIS = 3;
	final int TOKYO = 4;

	int[] flightTimes = { 3, // Dallas
			1, // New York
			7, // London
			8, // Paris
			16,// Tokyo
	};

	ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
	Random random = new Random();
	// store flight with key flight number, value flight
	TreeMap<String, Flight> flightMap = new TreeMap<String, Flight>();
	// store crew member
	HashSet<CrewMember> crewMembers = new HashSet<CrewMember>();

	public FlightManager() {
		// Create some aircraft types with max seat capacities
		airplanes.add(new Aircraft(4, "Bombardier 5000"));
		airplanes.add(new Aircraft(36, "Dash-8 100"));
		airplanes.add(new Aircraft(84, "Boeing 737"));
		airplanes.add(new Aircraft(180, "Airbus 320"));
		// change 592,16 to 200,16
		airplanes.add(new Aircraft(200, 16, "Boeing 747"));
		// Crate new crew members
		crewMembers.add(new CrewMember("Herbert", "ADKFJG7", "pilot"));
		crewMembers.add(new CrewMember("Baron", "KICKS88", "pilot"));
		crewMembers.add(new CrewMember("Amber", "KINSCY9", "navigator"));
		crewMembers.add(new CrewMember("Darren", "LOKDUI0", "navigator"));
		crewMembers.add(new CrewMember("Elijah", "PALKYC7", "attendant"));
		crewMembers.add(new CrewMember("August", "QUIDMH8", "attendant"));
		crewMembers.add(new CrewMember("Philip", "PALDJU5", "attendant"));
		crewMembers.add(new CrewMember("Jack", "ALDSIO7", "attendant"));
		crewMembers.add(new CrewMember("Maxwell", "AMDJSU1", "attendant"));
		crewMembers.add(new CrewMember("Frank", "MIDLKS4", "attendant"));
		// Populate the list of flights with some random test flights
		try {
			Scanner scanner = new Scanner(new File("flights.txt"));
			String airline;
			String destination;
			int flightTime = 0;
			String departureTime;
			int minNum;
			Aircraft flightCraft = airplanes.get(0);
			while (scanner.hasNextLine()) {
				Scanner newFlight = new Scanner(scanner.nextLine());
				airline = newFlight.next();
				destination = newFlight.next();
				departureTime = newFlight.next();
				minNum = newFlight.nextInt();
				String flightNum = generateFlightNumber(airline);
				// get the flight time
				if (destination.equalsIgnoreCase("Dallas"))
					flightTime = flightTimes[DALLAS];
				else if (destination.equalsIgnoreCase("New_york"))
					flightTime = flightTimes[NEWYORK];
				else if (destination.equalsIgnoreCase("LONDON"))
					flightTime = flightTimes[LONDON];
				else if (destination.equalsIgnoreCase("PARIS"))
					flightTime = flightTimes[PARIS];
				else if (destination.equalsIgnoreCase("TOKYO"))
					flightTime = flightTimes[TOKYO];
				// find the appropriate aircraft
				for (Aircraft a : airplanes) {
					if (a.getTotalSeats() >= minNum) {
						flightCraft = a;
						break;
					}
				}
				// if destination is tokyo, assign the aircraft with first class seats
				if (destination.equalsIgnoreCase("TOKYO"))
					flightCraft = airplanes.get(4);
				// check if the destination is tokyo, if not tokyo it is normal flight
				if (!destination.equalsIgnoreCase("TOKYO")) {
					Flight flight = new Flight(flightNum, airline, destination, departureTime, flightTime, flightCraft);
					flightMap.put(flightNum, flight);
				} else { // if tokyo it is long haul flight
					Flight flight = new LongHaulFlight(flightNum, airline, destination, departureTime, flightTime,
							flightCraft);
					flightMap.put(flightNum, flight);
				}

			}
		} catch (FileNotFoundException exception) {
			exception.getMessage();
		}

	}

	// gemerate Flight number
	private String generateFlightNumber(String airline) {
		String words, word1, word2;
		Scanner scanner = new Scanner(airline);
		words = scanner.next();
		// spilt the words
		String[] parts = words.split("_");
		word1 = parts[0];
		word2 = parts[1];
		// get the first letter
		String letter1 = word1.substring(0, 1);
		String letter2 = word2.substring(0, 1);
		letter1.toUpperCase();
		letter2.toUpperCase();

		// Generate random number between 101 and 300
		boolean duplicate;
		String flightNum;
		String flight = letter1 + letter2;
		// check for duplicate flight number
		do {
			flightNum = flight + Integer.toString(random.nextInt(200) + 101);
			duplicate = false;
			for (String air : flightMap.keySet()) {
				if (flightNum.equalsIgnoreCase(air)) {
					duplicate = true;
				}
			}
		} while (duplicate == true);

		return flightNum;
	}

	// print all flights
	public void printAllFlights() {
		for (String f : flightMap.keySet())
			System.out.println(flightMap.get(f));
	}

	// reserve seats
	public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat)
			throws FlightFullException, DuplicatePassengerException, SeatOccupiedException, SeatTypeException,
			ReservationNotFoundException, FlightNotfoundException {
		// check if the flight exist
		if (!flightMap.containsKey(flightNum))
			return null;

		Flight flight = flightMap.get(flightNum);
		String seatType;
		// check for seat type
		if (seat.contains("+"))
			seatType = "FCL";
		else
			seatType = "ECO";
		// make sure the flight is in status ONTIME
		if (flight.getStatus() == Flight.Status.INFLIGHT)
			throw new FlightNotfoundException(flightNum + " is In Flight");
		// reserve the seat
		if (!flight.reserveSeat(name, passport, seat, seatType))
			throw new ReservationNotFoundException("Reservation Can Not Be Done");
		// store the reservation
		return new Reservation(flightNum, flight.toString(), name, passport, seat, seatType);
	}

	// cancel reservation
	public boolean cancelReservation(Reservation res) throws SeatTypeException, PassengerNotFoundException {
		// get the flight number
		String flightnum = res.getFlightNum();
		Flight flight = flightMap.get(flightnum);
		flight.cancelSeat(res.getPassName(), res.getPassPass(), res.getSeat(), res.getFirstClass());
		return true;
	}

	// print seats
	public void printSeats(String flightnum) throws FlightNotfoundException {
		if (flightMap.containsKey(flightnum))
			flightMap.get(flightnum).printSeats();
		else
			throw new FlightNotfoundException("Flight " + flightnum + " not found");

	}

	// print manifest
	public void printManifest(String flightnum) throws FlightNotfoundException {
		// check if the flight number exist
		if (flightMap.containsKey(flightnum))
			// call the method
			flightMap.get(flightnum).printManifest();
		else
			throw new FlightNotfoundException("Flight " + flightnum + " not found");
	}

	// crate priority queue
	public void preBoard(String flightNum) throws FlightNotfoundException {
		// check if the flight number exist
		if (!flightMap.containsKey(flightNum))
			throw new FlightNotfoundException("Flight Number " + flightNum + " is Not Found");
		// call in method
		flightMap.get(flightNum).preBoard(flightNum);
	}

	// print the priority queue
	public String queue(String flightNum) throws FlightNotfoundException, QueueNotCrateException {
		// check if the flight number exist
		if (!flightMap.containsKey(flightNum))
			throw new FlightNotfoundException("Flight Number " + flightNum + " is Not Found");
		return flightMap.get(flightNum).queue();
		// call in method

	}

	// board the passenger
	public void board(String flightNum, String startRow, String endRow)
			throws FlightNotfoundException, QueueNotCrateException, RowOutOfRangeException {
		// check if the flight number exist
		if (!flightMap.containsKey(flightNum))
			throw new FlightNotfoundException("Flight Number " + flightNum + " is Not Found");
		// board rows
		flightMap.get(flightNum).board(startRow, endRow);
	}

	// add new crew
	public void newCrew(String name, String passport, String title) throws CrewMemberException {
		// check if the title is correct
		if (title.equalsIgnoreCase("pilot") || title.equalsIgnoreCase("navigator")
				|| title.equalsIgnoreCase("attendant")) {
			// create new crew member
			CrewMember crew = new CrewMember(name, passport, title);
			// add the member to the set
			crewMembers.add(crew);
			System.out.println(crew.toString());
		} else
			throw new CrewMemberException("Title Not Found");
	}

	// add crew to flight
	public void addCrewToFlight(String flightNum, String name, String passport, String title)
			throws FlightNotfoundException, CrewMemberException {
		// check if the fligh exist
		if (!flightMap.containsKey(flightNum))
			throw new FlightNotfoundException(flightNum + " Not Found");
		// create new crewmember with give param
		CrewMember member = new CrewMember(name, passport, title);
		// check if the member is on the crewlist
		if (!crewMembers.contains(member))
			throw new CrewMemberException("Crew " + member.toString() + " Does Not Exist");
		HashSet<CrewMember> existCrew = flightMap.get(flightNum).getCrew();
		// check if the member exist in the flight
		if (!existCrew.isEmpty()) {
			if (existCrew.contains(member))
				throw new CrewMemberException("Duplicate Crew Member In Same Flight");
		}
		// check if the flight has a poilt if the new crew is pilot
		if (title.equalsIgnoreCase("pilot")) {
			for (CrewMember c : existCrew) {
				if (c.isPilot())
					throw new CrewMemberException("Flight Has Poilt");

			}
		}
		// check if the flight has navigator if the new crew is nagigator
		if (title.equalsIgnoreCase("navigator")) {
			for (CrewMember c : existCrew) {
				if (c.isNavi())
					throw new CrewMemberException("Flight Has Navigator");
			}
		}
		// check if the crew menmber is in multiple flight
		for (String s : flightMap.keySet()) {
			if (flightMap.get(s).getCrew().contains(member))
				throw new CrewMemberException("Crew Is On Another Flight");
		}

		// remove the member
		crewMembers.remove(member);
		// add the member in flight
		flightMap.get(flightNum).newCrew(member);
	}

	// print all crews
	public void printCrews() throws CrewMemberException {
		if (crewMembers.isEmpty())
			throw new CrewMemberException("Crew Member List Is Empty");
		for (CrewMember c : crewMembers)
			System.out.println(c.toString());
	}

	// print all crews in a flight
	public void crewFlight(String flightNum) throws FlightNotfoundException, CrewMemberException {
		// check the flight nunmber
		if (!flightMap.containsKey(flightNum))
			throw new FlightNotfoundException(flightNum + " Is Not Found");
		// check if the flight has crew
		if (flightMap.get(flightNum).getCrew().isEmpty())
			throw new CrewMemberException(flightNum + " Has No Crew");
		for (CrewMember c : flightMap.get(flightNum).getCrew())
			System.out.println(c.toString());
	}

}
