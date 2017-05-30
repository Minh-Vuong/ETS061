package inl�mning1task5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MainSimSmallestQueue extends Global {

	public static void main(String[] args) throws IOException {

		// Signallistan startas och actSignal deklareras. actSignal �r den
		// senast utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is
		// the latest signal that has been fetched from the
		// signal list in the main loop below.

		Random rand = new Random();
		Signal actSignal;
		new SignalList();
		ArrayList<String> available = new ArrayList<String>();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();

		// H�r nedan skapas de processinstanser som beh�vs och parametrar i dem
		// ges v�rden.
		// Here process instances are created (two queues and one generator) and
		// their parameters are given values.

		QS dispatcher = new QS();
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

		dispatcher.sendTo = null;

		Gen Generator = new Gen();
		Generator.lambda = (1.0 / 2.0); // Generar med 0.12 sekund f�r att
										// bekr�fta little's teori.
										// //Generator shall
										// generate 0.12 to verify the
										// program.
		Generator.sendTo = dispatcher; // De genererade kunderna ska skickas
										// till k�systemet dipatcher // The
										// generated customers shall be sent to
										// dispatcher

		// H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma
		// ig�ng.
		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, Generator, time);
		SignalList.SendSignal(MEASURE, dispatcher, time);
		SignalList.SendSignal(MEASURE, Q1, time);
		SignalList.SendSignal(MEASURE, Q2, time);
		SignalList.SendSignal(MEASURE, Q3, time);
		SignalList.SendSignal(MEASURE, Q4, time);
		SignalList.SendSignal(MEASURE, Q5, time);

		// Detta �r simuleringsloopen:
		// This is the main loop

		while (time < 100000) {
			// l�gger till och j�mf�r de olika k�erna mot varandra med hj�lp av
			// en lista och en treemap
			map.put("Q1", Q1.numberInQueue);
			map.put("Q2", Q2.numberInQueue);
			map.put("Q3", Q3.numberInQueue);
			map.put("Q4", Q4.numberInQueue);
			map.put("Q5", Q5.numberInQueue);
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue().equals(Collections.min(map.values()))) {
					available.add(entry.getKey());
				}
			}

			int index = rand.nextInt(available.size());
			switch (available.get(index)) {
			case "Q1":
				dispatcher.sendTo = Q1;
				break;
			case "Q2":
				dispatcher.sendTo = Q2;
				break;
			case "Q3":
				dispatcher.sendTo = Q3;
				break;
			case "Q4":
				dispatcher.sendTo = Q4;
				break;
			case "Q5":
				dispatcher.sendTo = Q5;
				break;
			}
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
			map.clear();
			available.clear();
		}

		// Slutligen skrivs resultatet av simuleringen ut nedan:
		// Finally the result of the simulation is printed below:
		double meanCustomers = (1.0 * Q1.accumulated / Q1.noMeasurements) + (1.0 * Q2.accumulated / Q2.noMeasurements)
				+ (1.0 * Q3.accumulated / Q3.noMeasurements) + (1.0 * Q4.accumulated / Q4.noMeasurements)
				+ (1.0 * Q5.accumulated / Q5.noMeasurements);

		double T = (Q1.totalTime / Q1.departures + Q2.totalTime / Q2.departures + Q3.totalTime / Q3.departures
				+ Q4.totalTime / Q4.departures + Q5.totalTime / Q5.departures) / 5;

		System.out.println("Mean number of customers in queuing system 1: " + 1.0 * Q1.accumulated / Q1.noMeasurements);
		System.out.println("Mean number of customers in queuing system 2: " + 1.0 * Q2.accumulated / Q2.noMeasurements);
		System.out.println("Mean number of customers in queuing system 3: " + 1.0 * Q3.accumulated / Q3.noMeasurements);
		System.out.println("Mean number of customers in queuing system 4: " + 1.0 * Q4.accumulated / Q4.noMeasurements);
		System.out.println("Mean number of customers in queuing system 5: " + 1.0 * Q5.accumulated / Q5.noMeasurements);
		System.out.println("Mean number N of customers in the whole system: " + meanCustomers);
		System.out.println("Mean time T of customer in system: " + T);
		System.out.println("Theoretical lambda: " + Generator.lambda);
		System.out.println("Measured Lambda': " + meanCustomers / T);

	}
}
