/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight {
	int firstClassPassengers;

	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration,
			Aircraft aircraft) {
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight() {
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}

	// reserve seat on long haul flight
	public boolean reserveSeat(String name, String passport, String seat, String seatType)
			throws DuplicatePassengerException, SeatOccupiedException, SeatTypeException, FlightFullException {
		// check if it is first class
		if (seatType.equalsIgnoreCase("FCL")) {
			boolean seatAva = false;
			// check if the first class seats is avaiable

			if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
				throw new SeatOccupiedException("No First Class Seats Available");
			// check if seat is in the seat array
			for (int i = 0; i < aircraft.seatLayout.length; i++) {
				for (String s : aircraft.seatLayout[i]) {
					if (s.equalsIgnoreCase(seat))
						seatAva = true;
				}
			}
			if (!seatAva)
				throw new SeatTypeException("Seat " + seat + " Does Not Exist");
			Passenger p = new Passenger(name, passport, seat, seatType);
			// Check for duplicate passenger
			if (manifest.indexOf(p) >= 0)
				throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
			// check if the seat is taken
			if (seatMap.containsKey(seat))
				throw new SeatOccupiedException("Seat Occupied");
			// add the passenger into seat map
			seatMap.put(seat, p);
			// add the passenget into manifest
			manifest.add(p);
			firstClassPassengers++;
			return true;
		} else // economy passenger
			return super.reserveSeat(name, passport, seat, seatType);
	}

	// cancel seat for long haul flight
	public boolean cancelSeat(String name, String passport, String seat, String seatType)
			throws SeatTypeException, PassengerNotFoundException {
		// check the seat type if first class
		if (seatType.equalsIgnoreCase("FCL")) {
			Passenger p = new Passenger(name, passport);
			// check if the passenger is in the manifest
			if (manifest.indexOf(p) == -1)
				throw new PassengerNotFoundException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");
			// check if the seat is in the seat map
			if (!seatMap.containsKey(seat))
				throw new PassengerNotFoundException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");
			seatMap.remove(seat);
			manifest.remove(p);
			if (firstClassPassengers > 0)
				firstClassPassengers--;
			return true;
		} else
			return super.cancelSeat(name, passport, seat, seatType);
	}

	// return the number of total passenger
	public int getPassengerCount() {
		return getNumPassengers() + firstClassPassengers;
	}

	// print out the flight
	public String toString() {
		return super.toString() + "\t LongHaul";
	}

	// get the
	public Type getFlightType() {
		return type;
	}
}
