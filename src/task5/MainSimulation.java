package task5;

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
		QS Q2 = new QS();
		QS Q3 = new QS();
		QS Q4 = new QS();
		QS Q5 = new QS();
		Q1.sendTo = null;
		Q2.sendTo = null;
		Q3.sendTo = null;
		Q4.sendTo = null;
		Q5.sendTo = null;

		Q1.beta = 0.5;
		Q2.beta = 0.5;
		Q3.beta = 0.5;
		Q4.beta = 0.5;
		Q5.beta = 0.5;

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.sendTo1 = Q1;
		dispatcher.sendTo2 = Q2;
		dispatcher.sendTo3 = Q3;
		dispatcher.sendTo4 = Q4;
		dispatcher.sendTo5 = Q5;

		Gen Generator = new Gen();
		Generator.beta = 0.11; // Anger medeltiden mellan anländande kunder
		Generator.sendTo = dispatcher; // De genererade kunderna ska skickas
										// till kösystemet QS // The generated
										// customers shall be sent to Q1

		// Här nedan skickas de första signalerna för att simuleringen ska komma
		// igång.
		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, Generator, time);
		SignalList.SendSignal(MEASURE, Q1, time);
		SignalList.SendSignal(MEASURE, Q2, time);
		SignalList.SendSignal(MEASURE, Q3, time);
		SignalList.SendSignal(MEASURE, Q4, time);
		SignalList.SendSignal(MEASURE, Q5, time);

		// Detta är simuleringsloopen:
		// This is the main loop

		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		// Slutligen skrivs resultatet av simuleringen ut nedan:
		// Finally the result of the simulation is printed below:
		
		double Q1mean, Q2mean, Q3mean, Q4mean, Q5mean;
		double N;
		Q1mean = 1.0 * Q1.accumulated / Q1.noMeasurements;
		Q2mean = 1.0 * Q2.accumulated / Q2.noMeasurements;
		Q3mean = 1.0 * Q3.accumulated / Q3.noMeasurements;
		Q4mean = 1.0 * Q4.accumulated / Q4.noMeasurements;
		Q5mean = 1.0 * Q5.accumulated / Q5.noMeasurements;
		N = Q1mean + Q2mean + Q3mean + Q4mean + Q5mean;

		System.out.println("Mean number of customers in queue1: " + Q1mean);
		System.out.println("Mean number of customers in queue2: " + Q2mean);
		System.out.println("Mean number of customers in queue3: " + Q3mean);
		System.out.println("Mean number of customers in queue4: " + Q4mean);
		System.out.println("Mean number of customers in queue5: " + Q5mean);
		System.out.println("Mean number of customers in queuing system: " + N);
		double Q1sum = 0;
		double Q2sum = 0;
		double Q3sum = 0;
		double Q4sum = 0;
		double Q5sum = 0;

		for (Double v : Q1.queueTime) {
			Q1sum += v;
		}
		for (Double v : Q2.queueTime) {
			Q2sum += v;
		}
		for (Double v : Q3.queueTime) {
			Q3sum += v;
		}
		for (Double v : Q4.queueTime) {
			Q4sum += v;
		}
		for (Double v : Q5.queueTime) {
			Q5sum += v;
		}
		
		double T = (Q1sum + Q2sum + Q3sum + Q4sum + Q5sum) / (Q1.queueTime.size()
				+ Q2.queueTime.size() + Q3.queueTime.size() + Q4.queueTime.size() + Q5.queueTime.size());
		System.out.println("Average queue time in queue 1: " + Q1sum / Q1.queueTime.size());
		System.out.println("Average queue time in queue 2: " + Q2sum / Q2.queueTime.size());
		System.out.println("Average queue time in queue 3: " + Q3sum / Q3.queueTime.size());
		System.out.println("Average queue time in queue 4: " + Q4sum / Q4.queueTime.size());
		System.out.println("Average queue time in queue 5: " + Q5sum / Q5.queueTime.size());
		System.out
				.println("Average queue time in system: " + T);
		System.out.println("Lambda from Little's theorem from simulation: " + N/T);
		System.out.println("Lambda is: " + 1/Generator.beta);

	}
}