package BibTeX;

public class MandatoryFieldException extends Exception {

    private String key;
    private Type type;

    public MandatoryFieldException(String key, Type type) {
        this.key = key;
        this.type = type;
    }

    public void displayWhereIsProblem(){
        System.out.println("Rekord nie zawiera wszystkich obowiązkowych pól w rekordzie o następującym kluczu:" + key);
        System.out.print("Wymagane w nim pola to:");
        System.out.println(TypeInfo.getMandatoryFields(type).toString());
    }
}