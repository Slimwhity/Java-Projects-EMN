package errors;

public class SyntacticError extends RuntimeException {
	/*
	 * Exception représentant une erreur lors de la construction de l'arbre syntaxique. 
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
