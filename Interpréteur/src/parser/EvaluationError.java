package parser;

public class EvaluationError extends Exception {
	/*
	 * Exception représentant une erreur lors de l'évaluation de l'arbre syntaxique.
	 */
	private String message;

	public EvaluationError(String string) {
		this.message = string;
	}
	
	public String toString() {
		return "Exception : " + message;
	}

	private static final long serialVersionUID = -6230247955701814493L;

}
