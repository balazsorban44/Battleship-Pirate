package application;

@SuppressWarnings("serial")

public class OutOfBoardException extends IndexOutOfBoundsException{
	public OutOfBoardException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
