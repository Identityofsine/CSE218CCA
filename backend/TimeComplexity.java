package backend;
import frontend.GUIBackEnd;
import java.util.concurrent.TimeUnit;



public abstract class TimeComplexity {
	public static void checkTime(TimeTaken function) {
		long start1 = System.nanoTime();
		function.function();
		long end1 = System.nanoTime();
		System.out.println("This Expression took : " + TimeUnit.NANOSECONDS.toMillis(end1 - start1) + " milliseconds");
	}




}
