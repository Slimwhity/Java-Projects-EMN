package parser;

public class SyntacticError extends RuntimeException {
	
	private String message;
	private static final long serialVersionUID = 1L;
	
	public SyntacticError(String message) {
		this.message = message;
	}
	
	public SyntacticError() {
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Exception : " + message;
	}

}
