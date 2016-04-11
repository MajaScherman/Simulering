package task2;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	Random slump = new Random(); // This is just a random number generator

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nrOfArrivals = 0, rejected = 0, numberInQueueA = 0, numberInQueueB = 0, accumulated1 = 0,
			accumulated2 = 0, noMeasurements = 0;
	public double lambda = 150, x_a = 0.002, x_b = 0.004, d = 1;
	public double arrivalSpeed = 5, serviceTimeQ2 = 2;// change these parameters
														// to test different
														// situations

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL_A:
			arrivalA();
			break;
		case DEPARTURE_A:
			departureA();
			break;
		case ARRIVAL_B:
			arrivalB();
			break;
		case DEPARTURE_B:
			departureB();
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

	private void arrivalA() {
		nrOfArrivals++;
		if (numberInQueueA == 0)
			insertEvent(DEPARTURE_A, time + x_a);
		numberInQueueA++;
		insertEvent(ARRIVAL_A, time + expDist(lambda));
	}

	private void departureA() {
		numberInQueueA--;
		if (numberInQueueA > 0)
			insertEvent(DEPARTURE_A, time + x_a);
		insertEvent(ARRIVAL_B, time + d);
	}

	private void arrivalB() {
		if (numberInQueueB == 0)
			insertEvent(DEPARTURE_B, time + x_b);
		numberInQueueB++;
	}

	private void departureB() {
		numberInQueueB--;
		if (numberInQueueB > 0)
			insertEvent(DEPARTURE_B, time + x_b);
	}

	private void measure() {
		accumulated1 += numberInQueueA;
		accumulated2 += numberInQueueB;
		noMeasurements++;
		insertEvent(MEASURE, time + expDist(lambda));
	}

	private double expDist(double lambda) {
		return (-1 / lambda) * Math.log(1 - slump.nextDouble());
	}
}