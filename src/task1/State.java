package task1;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	Random slump = new Random(); // This is just a random number generator

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nrOfArrivals = 0, numberInQueue1 = 0, numberInQueue2 = 0, accumulated1 = 0, accumulated2 = 0,
			noMeasurements = 0;
	public double beta1 = 2.1, beta2 = 5;
	public double arrivalSpeed = 2, serviceTimeQ1 = (-beta1) * Math.log(1 - slump.nextDouble()), serviceTimeQ2 = 2,
			measureTime = (-beta2) * Math.log(1 - slump.nextDouble());

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL:
			arrival();
			break;
		case DEPARTURE_FROM_1:
			departureFrom1();
			break;
		case DEPARTURE_FROM_2:
			departureFrom2();
			break;
		case MEASURE:
			measure();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrival() {
		nrOfArrivals++;
		if (numberInQueue1 < 10) {
			if (numberInQueue1 == 0)
				insertEvent(DEPARTURE_FROM_1, time + serviceTimeQ1);
			numberInQueue1++;
		}
		insertEvent(ARRIVAL, time + arrivalSpeed);
	}

	private void departureFrom1() {
		numberInQueue1--;
		if (numberInQueue1 > 0)
			insertEvent(DEPARTURE_FROM_1, time + serviceTimeQ1);
		if (numberInQueue2 == 0)
			insertEvent(DEPARTURE_FROM_2, time + serviceTimeQ2);
		numberInQueue2++;
	}

	private void departureFrom2() {
		numberInQueue2--;
		if (numberInQueue2 > 0)
			insertEvent(DEPARTURE_FROM_2, time + serviceTimeQ2);
	}

	private void measure() {
		accumulated1 += numberInQueue1;
		accumulated2 += numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + measureTime);
	}
}