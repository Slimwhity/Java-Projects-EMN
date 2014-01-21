package errors;

public class UnexpectedCharacter extends Exception {
	private static final long serialVersionUID = 412257913973525333L;

	public UnexpectedCharacter(int i){
		super("unexpected character : ascii " + i + " - " + (char)i);
	}

}
