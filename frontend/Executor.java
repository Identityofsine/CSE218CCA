package frontend;

public interface Executor {
	void execute( );
}

class JavaFXLambda {
	   
	   public static void execute(Executor r) {
	       r.execute();
	   }
}