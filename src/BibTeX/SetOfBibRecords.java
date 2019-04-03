package BibTeX;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class SetOfBibRecords {

    /**
     * mapa pozwalająca dostać się do konkretnego rekordu po jego kluczu
     * <klucz, BibRecord>
     */
    private Map<String, BibRecord> bibRecords;

    /**
     * mapa trzymająca listę kluczy dla rekordów każdego typu
     */
    private Map<Type, LinkedList<String>> recordsByType;

    /**
     * mapa trzymająca listę kluczy do rekordów z książkami danego autora
     */
    private Map<Person, LinkedList<String>> recordsByAuthors;

    /**
     * zbiór wszystkich autorów
     */
    private Set<Person> setOfAuthors;

    SetOfBibRecords(){
        bibRecords = new HashMap<>();
        recordsByType = new HashMap<>();
        recordsByAuthors = new HashMap<>();
        setOfAuthors = new HashSet<>();
    }

    //gettery
    public Map<Type, LinkedList<String>> getRecordsByType() {
        return recordsByType;
    }

    public Map<Person, LinkedList<String>> getRecordsByAuthors() {
        return recordsByAuthors;
    }

    public Map<String, BibRecord> getBibRecords() {
        return bibRecords;
    }

    public Set<Person> getAllAutors(){
        return setOfAuthors;
    }

    //settery
    public void putRecord(String key, BibRecord bibRecord){
        bibRecords.put(key, bibRecord);
    }

    public void putType (Type type){
        LinkedList<String> keysOfBibRecords = new LinkedList<>();
        recordsByType.put(type,keysOfBibRecords);
    }

    public void putToMapByType(Type type, String keyOfBibRecord) {
        if(!recordsByType.containsKey(type))
            putType(type);
        recordsByType.get(type).add(keyOfBibRecord);
    }

    public void putAuthor (Person author){
        LinkedList<String> keysOfBibRecords = new LinkedList<>();
        recordsByAuthors.put(author, keysOfBibRecords);
    }

    public void putToMapByAuthors (Person author, String keyOfBibRecord){
        if(!recordsByAuthors.containsKey(author))
            putAuthor(author);
        recordsByAuthors.get(author).add(keyOfBibRecord);
    }

    public void addAuthor (Person author){
        setOfAuthors.add(author);
    }
}
