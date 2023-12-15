package fractalzoomer.utils;

public class StopSuccessiveRefinementException extends Exception {
    public StopSuccessiveRefinementException() {
        super("Early Stop");
    }
}
