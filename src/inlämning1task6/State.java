package inlämning1task6;

import java.util.*;
import java.io.*;

public class State extends G {
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, noCustomers = 0;
	public double temp = 0, customerServiceTime = 0;
	Random rand = new Random();
	boolean open = true;


	public void TreatEvent(Event x) {
		switch (x.eventType) {
		case G.ARRIVAL: 
			numberInQueue++;
			noCustomers++;
			double tempRand = 10 * rand.nextDouble(); 
			customerServiceTime = customerServiceTime + numberInQueue * (10 + tempRand);
			if (numberInQueue == 1) {
				EventList.InsertEvent(G.READY, G.time + (10 + tempRand));
			}

			if (G.time < 480) {
				EventList.InsertEvent(G.ARRIVAL, G.time + exp(15));
			} else {
				EventList.InsertEvent(G.END, G.time);
			}
		
			break;

		case G.READY:
			temp = (10 + 10 * rand.nextDouble()); // slumpar en betjänningstid
													// mellan 10-20
			//customerServiceTime += temp;
			numberInQueue--;
			if (numberInQueue > 0) {
				EventList.InsertEvent(G.READY, G.time + temp);
			}
			break;
		case G.MEASURE: 
			accumulated = accumulated + numberInQueue;
			noMeasurements++;
			EventList.InsertEvent(G.MEASURE, G.time + rand.nextDouble() * 10);
		
			break;

		case G.END: 
			if (numberInQueue == 0) {
				open = false;
			}
			EventList.InsertEvent(G.END, G.time + 1);
		
			break;
		}
	}

	private double exp(double mean) {
		return -(mean) * Math.log(rand.nextDouble());
	}
}