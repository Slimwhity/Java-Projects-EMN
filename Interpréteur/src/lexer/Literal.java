package lexer;

public class Literal extends Token {
	private String value;
	
	public Literal(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "LT"+ value;
	}
}
