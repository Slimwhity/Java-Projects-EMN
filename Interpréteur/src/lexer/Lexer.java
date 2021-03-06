package lexer;
import java.util.*;
import java.io.*;

public class Lexer {
	private FileReader in;
	private int i; // current ASCII character (coded as an integer)
	public enum TokenType {LITERAL, IDENTIFIER};
	
	public Lexer(String filename) throws IOException {
		File file = new File(filename);
		try {
			in = new FileReader(file);
			i = in.read(); // initialize lexer
		} catch (FileNotFoundException e) {
			System.err.println("File : " + filename + " not found");
			throw e; // pass the exception up the stack
		} catch (IOException e){
			in.close(); // close the reader
			throw e; // pass the exception up the stack
		}
	}
	
	public List<Token> lex() throws UnexpectedCharacter, IOException {
		// return the list of tokens recorded in the file
		List<Token> tokens = new ArrayList<Token>();
		
		try {
			Token token = getToken();
	
			while (! (token instanceof EOF)) {
				tokens.add(token);
				token = getToken();
			}
			tokens.add(token); // this is the EOF token
		} catch (IOException e){
				in.close(); // close the reader
				throw e; // pass the exception up the stack
		}
		return tokens;
	}
	
	private Token getToken() throws UnexpectedCharacter, IOException {
		switch (i){
		case '\n' :
			i = in.read();
			return getToken();
		case '\t' : 
			i = in.read();
			return getToken();
		case ' ' :
			i = in.read();
			return getToken();
		case '\r' : 
			i = in.read();
			return getToken();
		case '(' :
			i = in.read();
			return new Lpar();
		case ')' :
			i = in.read();
			return new Rpar();
		case '=' :
			i = in.read();
			if(i == '=') return new OpEqual();
			else return new Equal();
		case '+' :
			i = in.read();
			return new OpPlus();
		case '-' :
			i = in.read();
			return new OpMoins();
		case '*' :
			i = in.read();
			return new OpMult();
		case '/' : 
			i = in.read();
			return new OpDiv();
		case '<' :
			i = in.read();
			return new OpInf();
		case -1 : 
			in.close();
			return new EOF();
		case '0' :
			i = in.read();
			return new Literal("0");
		default : 
			return getKeyWord();
		}
	}
	
	private Token getKeyWord() throws UnexpectedCharacter, IOException {
		if(Character.toString((char) i).matches("[0-9]")) 
			return new Literal(lookForKeyword(TokenType.LITERAL));
		else if(Character.toString((char) i).matches("[a-z]")) {
			String sKwWord = lookForKeyword(TokenType.IDENTIFIER);
			if (sKwWord.equals("define")) return new KwDefine();
			else if (sKwWord.equals("if")) return new KwIf();
			else return new Identifier(sKwWord);
		}
		throw new UnexpectedCharacter(i);
	}
	
	private String lookForKeyword(TokenType type) throws IOException {
		String tmpKw = Character.toString((char) i);
		if((tmpKw.matches("[a-z0-9]") && type == TokenType.IDENTIFIER) || 
				(tmpKw.matches("[0-9]") && type == TokenType.LITERAL)) {
			i = in.read();
			return tmpKw + lookForKeyword(type);
		} else return "";
	}
}


