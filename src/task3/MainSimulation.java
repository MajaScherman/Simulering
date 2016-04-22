package task3;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, actState.expDist(actState.beta2));

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean value of arrival speed: " + actState.arrivalSpeed);
		System.out.println("Average number of clients in queue 1: " + 1.0 * actState.accumulated1 / actState.noMeasurements);
		System.out.println("Average number of clients in queue 2: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		System.out.println("Sum of average number of clients: " + 1.0 * (actState.accumulated1 + actState.accumulated2)/actState.noMeasurements);
		
		double sum = 0;
		for (int i = 0; i < actState.meanTimeInQueue.size(); i++) {
			sum += actState.meanTimeInQueue.get(i);
		}
		System.out.println("Average time in queueing network: "+ sum/actState.meanTimeInQueue.size());

	}
}