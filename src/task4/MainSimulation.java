package task4;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, actState.T);

		// The main simulation loop
		while (actState.noMeasurements < actState.M) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		
		try {
			ProcessBuilder process = new ProcessBuilder("/bin/rm", "/Users/krlun/git/Simulering/src/task4/a.m");
			Process p = process.start();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			PrintWriter writer = new PrintWriter("/Users/krlun/git/Simulering/src/task4/a.m", "UTF-8");
			StringBuilder outputLine = new StringBuilder();
			outputLine.append("b = [");
			writer.println(outputLine);
			
			for (int i = 0; i < actState.noMeasurements; i++) {
				outputLine = new StringBuilder();
				outputLine.append(actState.timeList.get(i) + " " + actState.numberBeingServicedList.get(i) + ";");
				writer.println(outputLine);
			}
			outputLine = new StringBuilder();
			outputLine.append("];");
			writer.println(outputLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Printing the result of the simulation
		System.out.println("Average number of clients being serviced: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println("Probability of rejection: " + (1.0 * actState.rejected) / (1.0 * actState.nrOfArrivals));

	}
}