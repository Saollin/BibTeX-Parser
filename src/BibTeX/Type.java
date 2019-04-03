package BibTeX;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public enum Type {
    // Standardowe typy rekordów
    ARTICLE, BOOK, BOOKLET, INBOOK,  INCOLLECTION, INPROCEEDINGS, MANUAL, MASTERSTHESIS, MISC, PHDTHESIS,
    PROCEEDINGS, TECHREPORT, UNPUBLISHED;

    public String toString() {
        return name().toLowerCase();
    }

    public static Set<Type> getEntryTypes() {
        return EnumSet.of(ARTICLE, BOOK, BOOKLET, INBOOK,  INCOLLECTION, INPROCEEDINGS, MANUAL, MASTERSTHESIS, MISC, PHDTHESIS,
                PROCEEDINGS, TECHREPORT, UNPUBLISHED);
    }

    public static Set<String> getEntryTypesAsStrings(){
        Set<String> result = new HashSet<>();
        for(Type types : getEntryTypes()){
            result.add(types.toString());
        }
        return result;
    }

    public static Type fromString (String type) throws IllegalArgumentException {
        Type result = null;
        if(type.equalsIgnoreCase("conference")){ //nazwy conference i inproceeding są równoważne
            return INPROCEEDINGS;
        }
        try {
            result = Type.valueOf(type.toUpperCase());
        }
        catch(IllegalArgumentException ex){
            return MISC;                // gdy nazwa nie należy do zbioru nazw, wtedy traktujemy typ jako MISC
        }
        return result;
    }
}
