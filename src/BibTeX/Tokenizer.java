package BibTeX;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Tokenizer extends  StreamTokenizer{

    private final int [] Special_Char = new int []{'{','}',',','"','@','#', '|'}; //znaki ważne w składni BibTeXa
    /**
     * W konstruktorze ustawiamy obiekt typu StreamTokenizer
     * żeby był zgodny z naszymi potrzebami
     **/
    Tokenizer (Reader r){
        super(r);
        resetSyntax();
        wordChars(0,Character.MAX_CODE_POINT); //ustawiamy wszystkie znaki jako znaki tworzące słowa
        whitespaceChars(0, ' '); //typowy zakres białych znaków
        for(int c : Special_Char){ //ustawiamy nasz Tokenizer by traktował specjalne znaki jako osobne tokeny
            ordinaryChar(c);
        }
    }
    /**
     * Zwraca następny token po podanym znaku
     */
    public int findCharacter(int character) throws IOException {
        int token;
        do {
            token = nextToken();
        } while (token != StreamTokenizer.TT_EOF && token != character);
        return token;
    }

    public String nextTokenAsString() throws IOException{
        nextToken();
        return sval;
    }

    public boolean isNextToken(int character) throws IOException{
        int token = nextToken();
        pushBack();
        return token == character;
    }
}
