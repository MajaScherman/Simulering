package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public double beta;
	public Proc sendTo;
	Random slump = new Random();
	public LinkedList<Double> arrivalTime = new LinkedList<Double>();
	public ArrayList<Double> queueTime = new ArrayList<Double>();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL: {
			arrivalTime.add(time);
			numberInQueue++;
			if (numberInQueue == 1) {
				SignalList.SendSignal(READY, this, time + expDist(beta));
			}
		}
			break;

		case READY: {
			queueTime.add(time - arrivalTime.pop());
			numberInQueue--;
			if (sendTo != null) {
				SignalList.SendSignal(ARRIVAL, sendTo, time);
			}
			if (numberInQueue > 0) {
				SignalList.SendSignal(READY, this, time + expDist(beta));
			}
		}
			break;

		case MEASURE: {
			noMeasurements++;
			accumulated = accumulated + numberInQueue;
			SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble());
		}
			break;
		}
	}

	public double expDist(double beta) {
		return (-beta) * Math.log(1 - slump.nextDouble());
	}
}