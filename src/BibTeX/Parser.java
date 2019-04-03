package BibTeX;

import java.util.*;
import java.io.*;

public class Parser {

    private static Map<String,String> bibStrings = new HashMap<>();
    private static SetOfBibRecords bibObject = new SetOfBibRecords();
    private static Tokenizer tokenizer;

    public static void parseFile(String filePath) throws IOException, MandatoryFieldException {
        Parser.setTokenizer(filePath);
        int current_token;
        String current_string;
        do {
            current_token = tokenizer.findCharacter('@');
            if (current_token == StreamTokenizer.TT_EOF) {
                System.out.println("Parsowanie zakończone");
                return;
            }
            current_string = tokenizer.nextTokenAsString().toLowerCase();
            switch (current_string) {
                case "string":
                    Parser.parseString();               //dodaj zmienną string
                    break;
                case "comment":                         //w tych przypadkach po prostu omiń tekst aż do kolejnej @
                case "preamble":
                    break;
                default:                                //utwórz konkretny typ rekordu
                    Parser.parseRecord(current_string);
                    break;
            }
        } while (true);
    }

    private static void setTokenizer (String filePath) throws IOException, MandatoryFieldException {
        try {
            Reader r = new BufferedReader(new FileReader(new File(filePath)));
            tokenizer = new Tokenizer(r);
        }
        catch (FileNotFoundException ex){
            System.out.println("Proszę podaj poprawną ścieżkę pliku");
            Scanner in = new Scanner(System.in);
            String newFilePath = in.nextLine();
            Parser.parseFile(newFilePath);
        }
    }


    private static void parseString() throws IOException{
        tokenizer.findCharacter('{');
        String shortForm = tokenizer.nextTokenAsString();
        tokenizer.nextToken();
        int token;
        StringBuilder longForm = new StringBuilder();
        while(!tokenizer.isNextToken('}')) {
            token = tokenizer.nextToken();
            switch (token) {
                case '#': //wyjdź do kolejnej pętli pierwszego while
                    break;
                case '"':                                               // np @String {email = firstname # "." # lastname # "@imag.fr"}
                    while (!tokenizer.isNextToken('"')){
                        longForm.append(tokenizer.nextTokenAsString());
                        longForm.append(" ");
                    }
                    tokenizer.nextToken(); // przejście na ostatnią klamerkę
                    break;
                default:
                    longForm.append(bibStrings.get(tokenizer.sval));
                    longForm.append(" ");
                    break;
            }
        }
        longForm.delete(longForm.length() - 1, longForm.length()); // usunięcie ostatniej spacji
        bibStrings.put(shortForm,longForm.toString());
    }


    private static BibRecord parseRecord(String type) throws IOException, MandatoryFieldException{

        BibRecord result = new BibRecord(type);
        Type currentType = Type.fromString(type);

        EnumSet<Field> currentMandatoryFields = TypeInfo.getMandatoryFields(currentType);
        tokenizer.nextToken();                      //do metody wchodzimy z tokenizerem ustawionym na typ recordu,
                                                    // przechodzimy na 1-szą {

        String key = tokenizer.nextTokenAsString(); //ustawiamy klucz

        tokenizer.nextToken();                      //będzie to przecinek, zakładam że nie ma zupełnie pustych rekordów
        String currentName;
        String currentValue;
        int countOfRightFields = 0;
        while (!tokenizer.isNextToken('}')) {
            currentName = tokenizer.nextTokenAsString().toLowerCase();
            tokenizer.nextToken(); //czyli na znak =
            currentValue = setValueOfField();
            switch (currentName) {
                case "author":
                    String[] authors = currentValue.split("and ");
                    for (String a : authors) {
                        Person author = new Person(a);
                        result.addAuthor(author);
                        bibObject.addAuthor(author);
                        bibObject.putToMapByAuthors(author, key);
                    }
                    break;
                case "editor":
                    String[] editors = currentValue.split("and");
                    for (String e : editors) {
                        Person editor = new Person(e);
                        result.addEditor(editor);
                    }
                    break;
                default:
                    if(TypeInfo.getPossibleFields(currentType).contains(Field.fromString(currentName))) { //sprawdzamy czy dla danego typu istnieje takie pole
                        result.putValue(currentName, currentValue);
                    }
                    break;
            }
            if(currentMandatoryFields.contains(Field.fromString(currentName))){
                countOfRightFields++;
            }
            if(tokenizer.isNextToken(','))
                tokenizer.nextToken();              //na przecinek, na końcu jest opcjonalny stąd warunek
        }
        try {
            if(countOfRightFields < TypeInfo.getNumberOfMandatoryFields(currentType))
                throw new MandatoryFieldException(key, currentType);
        }
        catch (MandatoryFieldException mfe){
            mfe.displayWhereIsProblem();
        }
        bibObject.putRecord(key, result);
        bibObject.putToMapByType(currentType, key);
        return result;
    }

    private static String setValueOfField() throws IOException{
        int token;
        StringBuilder sb = new StringBuilder();

        // przecinek jako separator pól, oraz } jako koniec danego rekordu (ostatni przecinek jest opcjonalny)
        while(!(tokenizer.isNextToken(',') || tokenizer.isNextToken('}'))) {
            token = tokenizer.nextToken();
            switch (token) {
                case '#': //wyjdź do kolejnej pętli pierwszego while
                    break;
                case '"':                                               // np @String {email = firstname # "." # lastname # "@imag.fr"}
                    while (!tokenizer.isNextToken('"')){
                        token = tokenizer.nextToken();
                        if(token == '|') {
                            sb.append((char)token);
                        }
                        else {
                            tokenizer.pushBack();
                            sb.append(tokenizer.nextTokenAsString());
                        }
                        sb.append(" ");
                    }
                    tokenizer.nextToken(); // przejście na zamykający cudzysłów
                    break;
                default:
                    sb.append(bibStrings.get(tokenizer.sval));
                    sb.append(" ");
                    break;
            }
        }
        if(sb.length() != 0) {
            sb.delete(sb.length() - 1, sb.length()); // usunięcie ostatniej spacji
        }
        return sb.toString();
    }
    public static void noOfRecords(){
        System.out.println(bibObject.getBibRecords().size());
    }

    public static void displayAll(){
        Map<String,BibRecord> bibRecordMap = bibObject.getBibRecords();
        for (Map.Entry<String,BibRecord> entry : bibRecordMap.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().display();
            System.out.println();
        }
    }

    public static void findRecordsOfAuthor(String [] authors){
        Set<Person> foundAuthours = new HashSet<>();       //gdy szukamy po imieniu możemy znaleźć więcej niż jednego autora
        for (Person iterator : bibObject.getAllAutors()) {
            for (String i : authors) {
                if (iterator.isAuthor(i)) {
                    foundAuthours.add(iterator);
                }
            }
        }
        Map<Person, LinkedList<String>> map = bibObject.getRecordsByAuthors();
        Map<String, BibRecord> bibRecordMap = bibObject.getBibRecords();
        for (Person iterator : foundAuthours) {
            LinkedList<String> bibRecordsByAuthor = map.get(iterator);
            for (String entry : bibRecordsByAuthor) {
                System.out.println(entry);              //wydrukuj klucz
                bibRecordMap.get(entry).display();      //i resztę
                System.out.println();
            }
        }
        System.out.println("\nTo wszystkie znalezione rekordy ;)\n");
    }

    public static void findRecordsByType(String [] types){
        Map<String, BibRecord> bibRecordMap = bibObject.getBibRecords();
        for(String iterator : types) {
            if(!Type.getEntryTypesAsStrings().contains(iterator)){
                System.out.println("Nie ma takiego typu! \n");
                return;
            }
            Type type = Type.fromString(iterator);
            Map<Type, LinkedList<String>> map = bibObject.getRecordsByType();
            LinkedList<String> bibRecordsByType = map.get(type);
            if(bibRecordsByType == null){
                System.out.println("Brak rekordów typu " + type.toString()+ " :(");
                continue;
            }
            for (String entry : bibRecordsByType) {
                System.out.println(entry);
                bibRecordMap.get(entry).display();
                System.out.println();
            }
        }
        System.out.println("\nTo wszystkie znalezione rekordy.\n");
    }

    private Parser(){
    }

}
