package lexer;

public class IDENTIFIER extends Token {
	public String value;
	
	public IDENTIFIER(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "ID" + value;
	}

}
