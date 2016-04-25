package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class Dispatcher extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public int type = 1; // 0 - random, 1 - round-robin, 2 - min
	private int rr = 0;
	public QS sendTo1;
	public QS sendTo2;
	public QS sendTo3;
	public QS sendTo4;
	public QS sendTo5;

	Random slump = new Random();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL: {

			switch (type) {
			case 0: {
				switch (slump.nextInt(5)) {
				case 0:
					SignalList.SendSignal(ARRIVAL, sendTo1, time);
					break;
				case 1:
					SignalList.SendSignal(ARRIVAL, sendTo2, time);
					break;
				case 2:
					SignalList.SendSignal(ARRIVAL, sendTo3, time);
					break;
				case 3:
					SignalList.SendSignal(ARRIVAL, sendTo4, time);
					break;
				case 4:
					SignalList.SendSignal(ARRIVAL, sendTo5, time);
					break;
				}
				break;
			}
			case 1: {
				switch (rr) {
				case 0:
					SignalList.SendSignal(ARRIVAL, sendTo1, time);
					rr++;
					break;
				case 1:
					SignalList.SendSignal(ARRIVAL, sendTo2, time);
					rr++;
					break;
				case 2:
					SignalList.SendSignal(ARRIVAL, sendTo3, time);
					rr++;
					break;
				case 3:
					SignalList.SendSignal(ARRIVAL, sendTo4, time);
					rr++;
					break;
				case 4:
					SignalList.SendSignal(ARRIVAL, sendTo5, time);
					rr = 0;
					break;
				}
				break;
			}
			case 2: {
				int[] a = new int[5];
				a[0] = sendTo1.numberInQueue;
				a[1] = sendTo2.numberInQueue;
				a[2] = sendTo3.numberInQueue;
				a[3] = sendTo4.numberInQueue;
				a[4] = sendTo5.numberInQueue;

				int min = Math.min(a[0], Math.min(a[1], Math.min(a[2], Math.min(a[3], a[4]))));
				ArrayList<Integer> minList = new ArrayList<Integer>();

				for (int i = 0; i < 5; i++) {
					if (a[i] == min)
						minList.add(i);
				}

				int rand = slump.nextInt(minList.size());
				int next = minList.get(rand);

				switch (next) {
				case 0:
					SignalList.SendSignal(ARRIVAL, sendTo1, time);
					break;
				case 1:
					SignalList.SendSignal(ARRIVAL, sendTo2, time);
					break;
				case 2:
					SignalList.SendSignal(ARRIVAL, sendTo3, time);
					break;
				case 3:
					SignalList.SendSignal(ARRIVAL, sendTo4, time);
					break;
				case 4:
					SignalList.SendSignal(ARRIVAL, sendTo5, time);
					break;
				}
				break;
			}
			}

		}
			break;
		}
	}
}