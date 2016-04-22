package task3;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	Random slump = new Random(); // This is just a random number generator

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nrOfArrivals = 0, numberInQueue1 = 0, numberInQueue2 = 0, accumulated1 = 0,
			accumulated2 = 0, noMeasurements = 0;
	public double beta1 = 1, beta2 = 5;
	public double arrivalSpeed = 1.1, serviceTimeQ2 = 2;
	public ArrayList<Double> timeList = new ArrayList<Double>();
	public ArrayList<Integer> numberInQueue1List = new ArrayList<Integer>();
	public ArrayList<Integer> numberInQueue2List = new ArrayList<Integer>();
	public ArrayList<Double> meanTimeInQueue = new ArrayList<Double>();

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL:
			arrival(time);
			break;
		case DEPARTURE_FROM_1:
			//System.out.println(x.arrivalTime);
			departureFrom1(x.arrivalTime);
			break;
		case DEPARTURE_FROM_2:
			departureFrom2(x.arrivalTime);
			//System.out.println(x.arrivalTime);
			meanTimeInQueue.add(time-x.arrivalTime);
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

	private void arrival(double arrivalTime) {
		nrOfArrivals++;
		if (numberInQueue1 == 0)
			insertEvent(DEPARTURE_FROM_1, time + expDist(beta1), arrivalTime);
		numberInQueue1++;
		insertEvent(ARRIVAL, time + expDist(arrivalSpeed), time);
	}

	private void departureFrom1(double arrivalTime) {
		numberInQueue1--;
		if (numberInQueue1 > 0)
			insertEvent(DEPARTURE_FROM_1, time + expDist(beta1), arrivalTime);
		if (numberInQueue2 == 0)
			insertEvent(DEPARTURE_FROM_2, time + expDist(beta1), arrivalTime);
		numberInQueue2++;
	}

	private void departureFrom2(double arrivalTime) {
		numberInQueue2--;
		if (numberInQueue2 > 0)
			insertEvent(DEPARTURE_FROM_2, time + expDist(beta1), arrivalTime);
	}

	private void measure() {
		accumulated1 += numberInQueue1;
		accumulated2 += numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + expDist(beta2), 0);
		timeList.add(time);
		numberInQueue1List.add(numberInQueue1);
		numberInQueue2List.add(numberInQueue2);
	}

	public double expDist(double beta) {
		return (-beta) * Math.log(1 - slump.nextDouble());
	}
}