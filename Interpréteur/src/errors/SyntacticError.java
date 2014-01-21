package errors;

public class SyntacticError extends RuntimeException {
	/*
	 * Exception repr�sentant une erreur lors de la construction de l'arbre syntaxique. 
	 */
	
	private String message;
	private static final long serialVersionUID = 1L;
	
	public SyntacticError(String message) {
		this.message = message;
	}

	public String toString() {
		return "Exception : " + message;
	}

}
