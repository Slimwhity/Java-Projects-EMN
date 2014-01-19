package parser;

public abstract class AST {
	public String toString() {
		return "";
	}
	
	public abstract String toString(String offset);
	
	public String align() {
		// Crée une chaine contenant un nombre d'espace suffisant pour aligner les arguments de l'expression
		// au niveau de la première LPar
		int length = this.getClass().getSimpleName().length() + 1;
		return String.format("%" + length + "s", ""); 
	}
}
