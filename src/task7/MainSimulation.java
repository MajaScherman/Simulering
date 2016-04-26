package task7;

import java.util.ArrayList;
import java.util.Random;

public class MainSimulation {

	public static void main(String[] args) {

		Random slump = new Random();
		double l1, l3;
		int numberOfSimulations = 1000;
		ArrayList<Double> meanTime = new ArrayList<Double>();
		double meanTimeSum = 0;

		for (int i = 0; i < numberOfSimulations; i++) {
			l1 = 1 + 4 * slump.nextDouble();
			l3 = 1 + 4 * slump.nextDouble();
			meanTime.add(Math.max(l1, l3));
		}
		
		for (Double v : meanTime) {
			meanTimeSum += v;
		}
		System.out.println("Mean time system works: " + meanTimeSum/meanTime.size());
	}

}