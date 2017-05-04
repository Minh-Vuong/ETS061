package inlämning1task6;

public class G{
	public static final int ARRIVAL = 1, READY = 2, MEASURE = 3, END = 4;
	public static double time = 0;
	public static EventList eventList = new EventList();
	
	public static void insertEvent(int type, double TimeOfEvent) {
		eventList.InsertEvent(type, TimeOfEvent);
	}
}