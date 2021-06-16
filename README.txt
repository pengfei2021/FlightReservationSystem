I blieve everything works
My program is not case sensitive
Connect first and last name with _
There are 10 crew added in the flight manager for testing purpose, can be removed.
If the flight in in INFLIGHT status, then no reservation can be made, other status are ok

preboard a flight:
preboard flightnumber

print the queue:
queue flightnumber

board different rows:
board flightnumber startrow endrow

add new crew:
crew name passport title

add crew to a flight:
addcrew flightnumber name passport title
after the crew is added to a flight, all crew list will delete the crew, but the crew will appear on crewlist in flight

print all crew available:
pcrew

print all crew in a flightl:
cflight flightnumber