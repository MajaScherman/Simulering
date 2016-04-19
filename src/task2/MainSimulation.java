package task2;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL_A, 0);
		insertEvent(MEASURE, 0.1);

		// The main simulation loop
/*		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
*/
		double sum = 0;
		double a = 0;
		int n = 100000;
		for (int i = 0; i < n; i++) {
			a = actState.expDist(1);
			System.out.println(a);
			sum += a;
		}
		System.out.println("Average: " + a/sum);
		// Printing the result of the simulation, in this case a mean value
		System.out.println("Average number of packets in queue: "
				+ 1.0 * (actState.accumulated1 + actState.accumulated2) / actState.noMeasurements);
		//System.out.println("Probability of rejection: " + 1.0 * actState.rejected / actState.nrOfArrivals);
	}
}