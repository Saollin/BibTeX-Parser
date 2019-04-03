package BibTeX;

//import java.util.Set;
//import java.util.EnumSet;

public enum Field {
    //typy pól
    AUTHOR, ADDRESS, BOOKTITLE, EDITOR, EDITION, HOWPUBLISHED, JOURNAL, TITLE, MONTH, YEAR, PUBLISHER, CHAPTER,
    PAGES, SCHOOL, INSTITUTION, VOLUME, NUMBER, NOTE, SERIES, KEY, ORGANIZATION, TYPE,
    NO_FIELD; // gdy wzywamy jakąś metodę dla czegoś co nie jest polem

    public String toString() {
        return name().toLowerCase();
    }

//    public static Set<Field> getEntryTypes() {
//        return EnumSet.of(AUTHOR, ADDRESS, BOOKTITLE, EDITOR, EDITION, HOWPUBLISHED, JOURNAL, TITLE, MONTH, YEAR,
//                PUBLISHER, CHAPTER, PAGES, SCHOOL, INSTITUTION, VOLUME, NUMBER, NOTE, SERIES, KEY, ORGANIZATION, TYPE, NO_FIELD);
//    }

    public static Field fromString (String field) throws IllegalArgumentException{
        Field result;
        try{
            result = Field.valueOf(field.toUpperCase());  //utworzenie odpowiedniego pola
        }
        catch (IllegalArgumentException ex){
            result = Field.NO_FIELD;
        }
        return result;
    }
}
