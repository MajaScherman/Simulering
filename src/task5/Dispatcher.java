package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class Dispatcher extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public int type = 0; // 0 - random, 1 - round-robin, 2 - min
	public QS sendTo1;
	public QS sendTo2;
	public QS sendTo3;
	public QS sendTo4;
	public QS sendTo5;
	
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				numberInQueue++;
				if (numberInQueue == 1) {
					SignalList.SendSignal(READY, this, time);
				}
			} break;

			case READY:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0) {
					SignalList.SendSignal(READY, this, time);
				}
			} break;

		}
	}
}