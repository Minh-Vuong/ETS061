package inlämning1task1;

import java.util.*;
import java.io.*;

class State {
	public int numberInQueue1 = 0, accumulated = 0, noMeasurements = 0, numberInQueue2 = 0, accumulated2 = 0,
			denied = 0;

	Random slump = new Random();
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);

	public double expDistributed(double mean) {
		double y = (mean) * Math.log(slump.nextDouble());
		return y;
	}

	public void TreatEvent(Event x) {
		switch (x.eventType) {
		case G.ARRIVAL: {
			if (numberInQueue1 == 10) {
				denied++;
				break;
			}
			numberInQueue1++;
			EventList.InsertEvent(G.ARRIVAL, G.time + 1);
			if (numberInQueue1 == 1) {
				EventList.InsertEvent(G.READY, G.time - expDistributed(2.1));
			}

		}

			break;
		case G.READY: {
			numberInQueue1--;
			if (numberInQueue1 > 0) {
				EventList.InsertEvent(G.READY, G.time - expDistributed(2.1));
			}
			EventList.InsertEvent(G.ARRIVAL2, G.time);
		}
			break;

		case G.ARRIVAL2: {
			numberInQueue2++;
			if (numberInQueue1 == 1) {
				EventList.InsertEvent(G.READY2, G.time + 2);
			}
		}
			break;

		case G.READY2: {
			numberInQueue2--;
			if (numberInQueue1 > 0) {
				EventList.InsertEvent(G.READY2, G.time + 2);
			}
		}

		case G.MEASURE: {
			accumulated = accumulated + numberInQueue1;
			accumulated2 = accumulated2 + numberInQueue2;
			noMeasurements++;
			EventList.InsertEvent(G.MEASURE, G.time - expDistributed(5));
			// W.println(String.valueOf(numberInQueue1));
		}
			break;
		}
	}
}
