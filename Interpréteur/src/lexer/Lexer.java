package lexer;
import java.util.*;
import java.io.*;

import errors.UnexpectedCharacter;
import lexer.OPERAND.OP;

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
	
	protected Token getToken() throws UnexpectedCharacter, IOException {
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
			if(i == '=') {
				i = in.read();
				return new OPERAND(OP.EQUALS);
			}
			else return new Equal();
		case '+' :
			i = in.read();
			return new OPERAND(OP.PLUS);
		case '-' :
			i = in.read();
			return new OPERAND(OP.MINUS);
		case '*' :
			i = in.read();
			return new OPERAND(OP.TIMES);
		case '/' : 
			i = in.read();
			return new OPERAND(OP.DIV);
		case '<' :
			i = in.read();
			return new OPERAND(OP.LOWER);
		case -1 : 
			in.close();
			return new EOF();
		case '0' :
			i = in.read();
			if (Character.toString((char) i).matches("[0-9]")) throw new UnexpectedCharacter(i);
			return new LITERAL("0");
		default : 
			return getKeyWord();
		}
	}
	
	private Token getKeyWord() throws UnexpectedCharacter, IOException {
		if(Character.toString((char) i).matches("[0-9]")) 
			return new LITERAL(lookForKeyword(TokenType.LITERAL));
		else if(Character.toString((char) i).matches("[a-z]")) {
			String sKwWord = lookForKeyword(TokenType.IDENTIFIER);
			if (sKwWord.equals("define")) return new KWDEFINE();
			else if (sKwWord.equals("if")) return new KWIF();
			else return new IDENTIFIER(sKwWord);
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
	
	protected FileReader getFileReader() {
		return this.in;
	}
}


