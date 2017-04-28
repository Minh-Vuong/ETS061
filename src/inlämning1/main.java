package inlämning1;

import java.util.Random;

public class main {

	public static void main(String[] args) {
		Random slump = new Random();
		Event actEvent;
		State actState = new State();
		new EventList();
		EventList.InsertEvent(G.ARRIVAL, 1);
		EventList.InsertEvent(G.MEASURE, 5);
		while (G.time < 10000000) {
			actEvent = EventList.FetchEvent();
			G.time = actEvent.eventTime;
			actState.TreatEvent(actEvent);
		}
		System.out.println("Mean value1: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println("Mean value2: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		System.out.println("Number accumulated: " + actState.accumulated);
		System.out.println("Number of accumulated2: " + actState.accumulated2);
		System.out.println("Number of measurements: " + actState.noMeasurements);
		System.out.println("Denied: " + actState.denied);

		actState.W.close();
	}
}
