package inlämning1task6;

import java.util.*;
import java.io.*;

public class MainSim extends G {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		int totalTime = 0, extraTime = 0;
		String endTime;
		double meanServiceTime = 0;

		for (int i = 0; i < 1000; i++) {
			State actState = new State();
			EventList.InsertEvent(G.ARRIVAL, 0);
			EventList.InsertEvent(G.MEASURE, 5);
			while (actState.open == true) {
				actEvent = EventList.FetchEvent();
				time = actEvent.eventTime;
				actState.TreatEvent(actEvent);
			}
			totalTime += time;
			meanServiceTime += (actState.customerServiceTime / actState.noCustomers);
		}

		endTime = "17.00";
		extraTime = totalTime / 1000 - 480;

		if (extraTime < 60) {
			if (extraTime < 10) {
				endTime = "17.0" + extraTime;
			} else {
				endTime = "17." + extraTime;
			}

		} else if (extraTime == 60) {
			endTime = "18.00";
		} else if (extraTime < 120 && extraTime > 60) {
			endTime = "18.0" + (extraTime - 60);
		} else {
			endTime = "18." + (extraTime - 60);
		}

		if (extraTime == 120) {
			endTime = "19.00";
		} else if (extraTime < 180 && extraTime > 120) {
			if (extraTime - 120 < 10) {
				endTime = "19:0" + (extraTime - 120);
			} else {
				endTime = "19:" + (extraTime - 120);
			}
		}

		System.out.println("The mean closing time per day is: " + endTime);
		//System.out.println("The mean time for a prescription in the system is: " + 1000);
		System.out.println("The mean time for a prescription is: " + meanServiceTime / 1000);
		// System.out.println(1.0 * actState.accumulated /
		// actState.noMeasurements);
		// System.out.println(actState.accumulated);
		// System.out.println(actState.noMeasurements);
		// actState.W.close();
	}
}