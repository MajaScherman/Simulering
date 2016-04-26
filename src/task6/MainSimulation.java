package task6;

import java.util.*;
import java.io.*;

//Denna klass ärver Global så att man kan använda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {

		// Signallistan startas och actSignal deklareras. actSignal är den
		// senast utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is
		// the latest signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		// Här nedan skapas de processinstanser som behövs och parametrar i dem
		// ges värden.
		// Here process instances are created (two queues and one generator) and
		// their parameters are given values.

		QS Q1 = new QS();
		Q1.sendTo = null;

		Gen Generator = new Gen();
		Generator.lambda = 1.0 / 15; // Generator ska generera nio kunder per
										// sekund //Generator shall generate 9
										// customers per second
		Generator.sendTo = Q1; // De genererade kunderna ska skickas till
								// kösystemet QS // The generated customers
								// shall be sent to Q1

		// Här nedan skickas de första signalerna för att simuleringen ska komma
		// igång.
		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, Generator, time);
		SignalList.SendSignal(MEASURE, Q1, time);

		// Detta är simuleringsloopen:
		// This is the main loop
		
		int numberOfDays = 1000;

		for (int i = 0; i < numberOfDays; i++) {
			new SignalList();
			time = 0;
			SignalList.SendSignal(READY, Generator, time);
			SignalList.SendSignal(MEASURE, Q1, time);
			while (time < 1000) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}
		}
		
		double closingTimeSum = 0;
		for (Double v : Q1.closingTime) {
			closingTimeSum += v;
		}
		
		double avgClosingTime = (closingTimeSum/Q1.closingTime.size() + (9*60))/60;
		int hour = (int) Math.floor(avgClosingTime);
		int minute = (int) Math.floor((avgClosingTime % hour)*60);
		
		StringBuilder avgClosingTimeString = new StringBuilder();
		avgClosingTimeString.append(Integer.toString(hour));
		avgClosingTimeString.append(":");
		if (minute < 10) {
			avgClosingTimeString.append("0").append(Integer.toString(minute));
		} else {
			avgClosingTimeString.append(Integer.toString(minute));
		}

		double recipeTimeSum = 0;
		for (Double v : Q1.queueTime) {
			recipeTimeSum += v;
		}
		double avgRecipeTime = recipeTimeSum/Q1.queueTime.size();

		// Slutligen skrivs resultatet av simuleringen ut nedan:
		// Finally the result of the simulation is printed below:

		System.out.println("Mean number of recipes in queuing system: " + 1.0 * Q1.accumulated / Q1.noMeasurements);
		System.out.println("Average closing time: " + avgClosingTimeString.toString());
		System.out.println("Average time for a recipe in the system in minutes: " + avgRecipeTime);
		System.out.println("test");
	}
}