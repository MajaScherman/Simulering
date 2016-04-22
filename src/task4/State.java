package task4;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	Random slump = new Random(); // This is just a random number generator

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nrOfArrivals = 0, numberBeingServiced = 0, accumulated = 0, noMeasurements = 0, rejected = 0;
	public int n = 100, x = 10, M = 4000;
	public double lambda = 4, T = 4;
	public ArrayList<Double> timeList = new ArrayList<Double>();
	public ArrayList<Integer> numberBeingServicedList = new ArrayList<Integer>();
	public ArrayList<Double> meanTimeInQueue = new ArrayList<Double>();

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
		if (numberBeingServiced < n) {
			insertEvent(DEPARTURE_FROM_1, time + x);
			numberBeingServiced++;
		} else {
			rejected++;
		}
		insertEvent(ARRIVAL, time + expDist(lambda));
	}

	private void departureFrom1() {
		numberBeingServiced--;
	}

	private void measure() {
		accumulated += numberBeingServiced;
		noMeasurements++;
		insertEvent(MEASURE, time + T);
		timeList.add(time);
		numberBeingServicedList.add(numberBeingServiced);
	}

	public double expDist(double lambda) {
		return (-1/lambda) * Math.log(1 - slump.nextDouble());
	}
}