package task6;

import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

class Gen extends Proc{

	//The random number generator is started:
	Random slump = new Random();

	//Generatorn har två parametrar:
	//There are two parameters:
	public Proc sendTo;		//Where to send customers
	public double lambda;   //How many to generate per second
	private double customerTime;

	//What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:{
				SignalList.SendSignal(ARRIVAL, sendTo, time);
				customerTime = time + expDist(lambda);
				if (customerTime < 480) {
					SignalList.SendSignal(READY, this, customerTime);
				}
				break;
			}
		}
	}
	
	public double expDist(double lambda){
		return (-1/lambda) * Math.log(1 - slump.nextDouble());
	}
}