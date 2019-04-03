package BibTeX;

import java.util.HashMap;
import java.util.EnumSet;
import java.util.Map;

public class TypeInfo {
    private static final Map<Type, EnumSet<Field>> mandatoryFields;
    private static final Map<Type, EnumSet<Field>> optionalFields;

    static {
        mandatoryFields = new HashMap<>();

        mandatoryFields.put(Type.ARTICLE, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.JOURNAL, Field.YEAR));

        mandatoryFields.put(Type.BOOK, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.PUBLISHER,
                Field.YEAR, Field.EDITOR)); // author/editor - pól jest 4

        mandatoryFields.put(Type.BOOKLET, EnumSet.of(Field.TITLE));

        mandatoryFields.put(Type.INBOOK, EnumSet.of(Field.AUTHOR, Field.EDITOR, Field.TITLE,     // author/editor
                Field.CHAPTER, Field.PAGES, Field.PUBLISHER, Field.YEAR));                       // chapter/pages

        mandatoryFields.put(Type.INCOLLECTION, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.BOOKTITLE, Field.PUBLISHER, Field.YEAR));

        mandatoryFields.put(Type.INPROCEEDINGS, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.BOOKTITLE, Field.YEAR));

        mandatoryFields.put(Type.MANUAL, EnumSet.of(Field.TITLE));

        mandatoryFields.put(Type.MASTERSTHESIS, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.SCHOOL, Field.YEAR));

        mandatoryFields.put(Type.MISC, EnumSet.noneOf(Field.class));    //utworzenie pustego EnumSet

        mandatoryFields.put(Type.PHDTHESIS, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.SCHOOL, Field.YEAR));

        mandatoryFields.put(Type.PROCEEDINGS, EnumSet.of(Field.TITLE, Field.YEAR));

        mandatoryFields.put(Type.TECHREPORT, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.INSTITUTION, Field.YEAR));

        mandatoryFields.put(Type.UNPUBLISHED, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.YEAR));


        optionalFields = new HashMap<>();

        optionalFields.put(Type.ARTICLE, EnumSet.of(Field.VOLUME, Field.NUMBER, Field.PAGES, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.BOOK, EnumSet.of(Field.VOLUME, Field.NUMBER, Field.SERIES, Field.ADDRESS,
                Field.EDITION, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.BOOKLET, EnumSet.of(Field.AUTHOR, Field.HOWPUBLISHED, Field.ADDRESS, Field.MONTH,
                Field.YEAR, Field.NOTE, Field.KEY));

        optionalFields.put(Type.INBOOK, EnumSet.of(Field.VOLUME, Field.NUMBER, Field.SERIES, Field.TYPE,
                Field.ADDRESS, Field.EDITION, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.INCOLLECTION, EnumSet.of(Field.EDITOR, Field.VOLUME, Field.NUMBER, Field.SERIES, Field.TYPE,
                Field.CHAPTER, Field.PAGES, Field.ADDRESS, Field.EDITION, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.INPROCEEDINGS, EnumSet.of(Field.EDITOR, Field.VOLUME, Field.NUMBER, Field.SERIES, Field.PAGES,
                Field.ADDRESS, Field.MONTH, Field.ORGANIZATION, Field.PUBLISHER, Field.NOTE, Field.KEY));

        optionalFields.put(Type.MANUAL, EnumSet.of(Field.AUTHOR, Field.ORGANIZATION, Field.ADDRESS, Field.EDITION, Field.MONTH,
                Field.YEAR, Field.NOTE, Field.KEY));

        optionalFields.put(Type.MASTERSTHESIS, EnumSet.of(Field.TYPE, Field.ADDRESS, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.MISC, EnumSet.of(Field.AUTHOR, Field.TITLE, Field.HOWPUBLISHED, Field.MONTH,
                Field.YEAR, Field.NOTE, Field.KEY));

        optionalFields.put(Type.PHDTHESIS, EnumSet.of(Field.TYPE, Field.ADDRESS, Field.MONTH, Field.NOTE, Field.KEY));

        optionalFields.put(Type.PROCEEDINGS, EnumSet.of(Field.EDITOR, Field.VOLUME, Field.NUMBER, Field.SERIES,
                Field.ADDRESS, Field.MONTH, Field.ORGANIZATION, Field.PUBLISHER, Field.NOTE, Field.KEY));

        optionalFields.put(Type.TECHREPORT, EnumSet.of(Field.EDITOR, Field.VOLUME, Field.NUMBER, Field.SERIES, Field.ADDRESS,
                Field.MONTH, Field.ORGANIZATION, Field.PUBLISHER, Field.NOTE, Field.KEY));

        optionalFields.put(Type.UNPUBLISHED, EnumSet.of(Field.MONTH, Field.YEAR, Field.KEY));
    }

    // metoda zwraca listę z obowiązkowymi polami w podanym typie rekordu
    public static EnumSet<Field> getMandatoryFields(Type type) {
        return mandatoryFields.get(type);
    }

    // metoda zwraca listę z opcjonalnymi polami w podanym typie rekordu
    public static EnumSet<Field> getPossibleFields(Type type) { //możliwe pola to połączenie obowiązkowych z opcjonalnymi
        EnumSet<Field> result = optionalFields.get(type);
        result.addAll(mandatoryFields.get(type));
        return result;
    }

    /**
     * Metoda zwraca ilość obowiązkowych elementów (jest różna od wielkości zbiorów EnumSet dla poszczególnych typów
     * ponieważ część nazw pól ma dozwolone dwie nazwy)
     */
    public static int getNumberOfMandatoryFields(Type type){
        switch(type) {
            case ARTICLE:
                return 4;
            case BOOK:
                return 4;
            case BOOKLET:
                return 1;
            case INBOOK:
                return 5;
            case INCOLLECTION:
                return 5;
            case INPROCEEDINGS:
                return 4;
            case MANUAL:
                return 1;
            case MASTERSTHESIS:
                return 4;
            case MISC:
                return 0;
            case PHDTHESIS:
                return 4;
            case PROCEEDINGS:
                return 2;
            case TECHREPORT:
                return 4;
            case UNPUBLISHED:
                return 3;

            default:
                return 0;
        }
    }
    private TypeInfo() {
    }
}
