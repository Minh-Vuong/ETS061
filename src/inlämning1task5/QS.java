package inlämning1task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random rand = new Random();
	double service = 0.5;

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + exponentialDist(service));
				}
			} break;
			
			case ARRIVALDISPATCHER:{
				numberInQueue++;
				if (numberInQueue == 1) {
					SignalList.SendSignal(READYDISPATCHER, this, time);
				}
			}

			case READY:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0){
					double tempRand = exponentialDist(service);
					SignalList.SendSignal(READY, this, time + tempRand);
				}
			} break;
			
			case READYDISPATCHER:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0) {
					SignalList.SendSignal(READYDISPATCHER, sendTo, time);
				}
			}

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*rand.nextDouble());
			} break;
		}
	}
	private double exponentialDist(double mean){
		return -(mean)*Math.log(rand.nextDouble());
	}
	
}