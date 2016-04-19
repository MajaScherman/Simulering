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
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		// Printing the result of the simulation, in this case a mean value
		System.out.println(
				"Average number of packets in queue A: " + 1.0 * actState.accumulated1 / actState.noMeasurements);
		System.out.println(
				"Average number of packets in queue B: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		System.out.println("Average number of packets in queue: "
				+ 1.0 * (actState.accumulated1 + actState.accumulated2) / actState.noMeasurements);
		
		try {
			ProcessBuilder process = new ProcessBuilder("/bin/rm", "/Users/krlun/git/Simulering/src/task2/a.m");
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
			PrintWriter writer = new PrintWriter("/Users/krlun/git/Simulering/src/task2/a.m", "UTF-8");
			StringBuilder outputLine = new StringBuilder();
			outputLine.append("b = [");
			writer.println(outputLine);
			
			for (int i = 0; i < actState.noMeasurements; i++) {
				outputLine = new StringBuilder();
				outputLine.append(actState.timeList.get(i) + " " + actState.numberInQueueAList.get(i) + " " + actState.numberInQueueBList.get(i) + ";");
				writer.println(outputLine);
			}
			outputLine = new StringBuilder();
			outputLine.append("];");
			writer.println(outputLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}