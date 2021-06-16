import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!

public class FlightReservationSystem {
	public static void main(String[] args) {

		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>(); // my flight reservations

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) {
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) {
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			// print out the list of the flights
			else if (action.equalsIgnoreCase("LIST")) {
				manager.printAllFlights();
			}
			// make reservation
			else if (action.equalsIgnoreCase("RES")) {
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatNum = "";
				// get the flight number
				try {
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// get the passenger name
					if (commandLine.hasNext())
						passengerName = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// get the passport number
					if (commandLine.hasNext())
						passport = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// get the seat number
					if (commandLine.hasNext())
						seatNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// make the reservation
					Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatNum);
					// print out the reservation info
					if (res != null) {
						myReservations.add(res);
						res.print();
					}
				} catch (FlightFullException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (DuplicatePassengerException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (SeatOccupiedException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (SeatTypeException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (ReservationNotFoundException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (MissingInformationException expcetion) {
					System.out.println(expcetion.getMessage());
				} catch (FlightNotfoundException expcetion) {
					System.out.println(expcetion.getMessage());
				}
			}
			// cancel seats
			else if (action.equalsIgnoreCase("CANCEL")) {
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				Reservation res = null;

				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// get the passenger name
					if (commandLine.hasNext())
						passengerName = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// get the passenger passport
					if (commandLine.hasNext())
						passport = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException();
					// find the reservation
					for (Reservation r : myReservations) {
						if (r.getPassName().equalsIgnoreCase(passengerName)
								&& r.getPassPass().equalsIgnoreCase(passport)
								&& r.getFlightNum().equalsIgnoreCase(flightNum))
							res = r;
					}
					// cancel the reservation if it is found
					if (res != null) {
						manager.cancelReservation(res);
						myReservations.remove(res);
					} else
						throw new ReservationNotFoundException();
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());

				} catch (ReservationNotFoundException exception) {
					System.out.println(exception.getMessage());
				} catch (SeatTypeException exception) {
					System.out.println(exception.getMessage());
				} catch (PassengerNotFoundException exception) {
					System.out.println(exception.getMessage());
				}

			}
			// print out the seat arrangement
			else if (action.equalsIgnoreCase("SEATS")) {
				String flightNum = "";

				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// print out the seats array
					manager.printSeats(flightNum);
				} catch (MissingInformationException expception) {
					System.out.println(expception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				}

			}
			// print out all reservation
			else if (action.equalsIgnoreCase("MYRES")) {
				for (Reservation myres : myReservations)
					myres.print();
			}
			// print out manifest
			else if (action.equalsIgnoreCase("PASMAN")) {
				String flightNum;
				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// call the method
					manager.printManifest(flightNum);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				}
			}
			// preboard method
			else if (action.equalsIgnoreCase("PREBOARD")) {
				String flightNum;
				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// call in method
					manager.preBoard(flightNum);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				}
			}
			// print out the priority queue
			else if (action.equalsIgnoreCase("queue")) {
				String flightNum;
				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// call in method
					System.out.println(manager.queue(flightNum));
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				} catch (QueueNotCrateException exception) {
					System.out.println(exception.getMessage());
				}
			}
			// board the flight
			else if (action.equalsIgnoreCase("board")) {
				String flightNum;
				String startRow;
				String endRow;
				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// get the start row
					if (commandLine.hasNext())
						startRow = commandLine.next();
					else
						throw new MissingInformationException("Missing Start Row Or End Row");
					// get the end row
					if (commandLine.hasNext())
						endRow = commandLine.next();
					else
						throw new MissingInformationException("Missing Start Row Or End Row");
					manager.board(flightNum, startRow, endRow);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				} catch (QueueNotCrateException exception) {
					System.out.println(exception.getMessage());
				} catch (RowOutOfRangeException exception) {
					System.out.println(exception.getMessage());
				}

			}
			// add new crew member
			else if (action.equalsIgnoreCase("crew")) {
				String name;
				String passport;
				String title;
				try {
					// get the name
					if (commandLine.hasNext())
						name = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Title");
					// get thepassport
					if (commandLine.hasNext())
						passport = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Title");
					// get the title
					if (commandLine.hasNext())
						title = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Title");
					// call method in manager
					manager.newCrew(name, passport, title);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (CrewMemberException exception) {
					System.out.println(exception.getMessage());
				}

			}
			// add crew member to flight
			else if (action.equalsIgnoreCase("addcrew")) {
				String flightNum;
				String name;
				String passport;
				String title;
				try {
					// get the flight number
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Title or FLight Number");
					// get the name
					if (commandLine.hasNext())
						name = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Titleor FLight Number");
					// get the passport
					if (commandLine.hasNext())
						passport = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Titleor FLight Number");
					// get the title
					if (commandLine.hasNext())
						title = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Name or Passport or Titleor FLight Number");
					// call in method
					manager.addCrewToFlight(flightNum, name, passport, title);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				} catch (CrewMemberException exception) {
					System.out.println(exception.getMessage());
				}
			}
			// print all crew members not in flight
			else if (action.equalsIgnoreCase("pcrew")) {
				// call method
				try {
					manager.printCrews();
				} catch (CrewMemberException exception) {
					System.out.println(exception.getMessage());
				}
			}
			// p rint all crew members in a flight
			else if (action.equalsIgnoreCase("cflight")) {
				String flightNum;
				try {
					if (commandLine.hasNext())
						flightNum = commandLine.next().toUpperCase();
					else
						throw new MissingInformationException("Missing Flight Number");
					// call the methond in manage
					manager.crewFlight(flightNum);
				} catch (MissingInformationException exception) {
					System.out.println(exception.getMessage());
				} catch (FlightNotfoundException exception) {
					System.out.println(exception.getMessage());
				} catch (CrewMemberException exception) {
					System.out.println(exception.getMessage());
				}
			}
			System.out.print("\n>");
		}

	}

}
