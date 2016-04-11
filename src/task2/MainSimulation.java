package task2;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL_A, 0);
		insertEvent(MEASURE, 5);

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEventPrioB();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println(
				"Average number of clients in queue 1: " + 1.0 * actState.accumulated1 / actState.noMeasurements);
		System.out.println(
				"Average number of clients in queue 2: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		System.out.println("Probability of rejection: " + (1.0 * actState.rejected) / (1.0 * actState.nrOfArrivals));
		System.out.println("Elapsed time: " + actState.time);
	}
}