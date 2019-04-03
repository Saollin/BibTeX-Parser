package BibTeX;

import java.util.*;

public class BibRecord {
    private final Type type;
    private final HashMap<String, String> values;
    private final List<Person> authors;
    private final List<Person> editors;

    /**
     * Konstruktor objektu przechowującego konkretne rekordy pliku BibTeX
     *
     */
    public BibRecord(String type) {
        this.type = Type.fromString(type);
        values = new LinkedHashMap<>();
        authors = new ArrayList<>();
        editors = new ArrayList<>();
    }
    //gettery
    public Type getType() {
        return type;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public List<Person> getEditors() {
        return editors;
    }

    //settery
    public void putValue(String name, String value){
        values.put(name,value);
    }

    public void addAuthor(Person author){
        this.authors.add(author);
    }

    public void addEditor(Person editor){
        this.editors.add(editor);
    }

    public void display(){
        List<Person> aut;
        if(getAuthors().size()!=0){
            aut = getAuthors();
            displayInGoodPlace("Author");
            for(int i = 0; i < aut.size(); i++) {
                System.out.println(aut.get(i));
                if(i+1 != aut.size())
                    displayInGoodPlace("");
            }
        }
        if(getEditors().size() != 0){
            aut = getEditors();
            displayInGoodPlace("Editor");
            for(int i = 0; i < aut.size(); i++) {
                System.out.println(aut.get(i));
                if(i+1 != aut.size())
                    displayInGoodPlace("");
            }
        }
        HashMap<String, String> val = getValues();
        for(Map.Entry<String,String> entry : val.entrySet()){
            displayInGoodPlace(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    private void displayInGoodPlace(String str) {
        if(str.length() >= 11) {
            System.out.print(str);
            System.out.print("\t|");
        }
        else if(str.length() >= 8) {
            System.out.print(str);
            System.out.print("\t\t|");
        }
        else if(str.length() <= 3){
            System.out.print(str);
            System.out.print("\t\t\t\t|");
        }
        else {
            System.out.print(str);
            System.out.print("\t\t\t|");
        }
    }
    }
