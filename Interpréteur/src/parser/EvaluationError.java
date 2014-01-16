package parser;

public class EvaluationError extends Exception {
	private String message;

	public EvaluationError(String string) {
		this.message = string;
	}
	
	public String toString() {
		return "Exception : " + message;
	}

	private static final long serialVersionUID = -6230247955701814493L;

}
