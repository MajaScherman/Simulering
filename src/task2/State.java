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
	public int task = 1; // Change here for the different assignments
	public ArrayList<Double> timeList = new ArrayList<Double>();
	public ArrayList<Integer> numberInQueueAList = new ArrayList<Integer>();
	public ArrayList<Integer> numberInQueueBList = new ArrayList<Integer>();

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL_A:
			arrivalA();
			break;
		case READY_A:
			readyA();
			break;
		case ARRIVAL_B:
			arrivalB();
			break;
		case READY_B:
			readyB();
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
		if ((numberInQueueA == 0) && (numberInQueueB == 0))
			insertEvent(READY_A, time + x_a);
		numberInQueueA++;
		insertEvent(ARRIVAL_A, time + expDist(lambda));
	}

	private void readyA() {
		numberInQueueA--;
		if (task == 1) {
			insertEvent(ARRIVAL_B, time + d);
			task1();
		} else if (task == 2) {
			insertEvent(ARRIVAL_B, time + expDist(d));
			task1();
		} else if (task == 3) {
			insertEvent(ARRIVAL_B, time + d);
			task3();
		}

	}

	private void arrivalB() {
		if ((numberInQueueB == 0) && (numberInQueueA == 0))
			insertEvent(READY_B, time + x_b);
		numberInQueueB++;
	}

	private void readyB() {
		numberInQueueB--;
		if (task == 1 || task == 2) {
			task1();
		} else if (task == 3) {
			task3();
		}

	}

	private void task1() {
		if (numberInQueueB > 0)
			insertEvent(READY_B, time + x_b);
		else if (numberInQueueA > 0)
			insertEvent(READY_A, time + x_a);
	}

	private void task3() {
		if (numberInQueueA > 0)
			insertEvent(READY_A, time + x_a);
		else if (numberInQueueB > 0)
			insertEvent(READY_B, time + x_b);
	}

	private void measure() {
		accumulated1 += numberInQueueA;
		accumulated2 += numberInQueueB;
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
		// Experimenting with exporting data to Matlab
		timeList.add(time);
		numberInQueueAList.add(numberInQueueA);
		numberInQueueBList.add(numberInQueueB);
	}

	public double expDist(double lambda) {
		return (-1 / lambda) * Math.log(1 - slump.nextDouble());
	}
}