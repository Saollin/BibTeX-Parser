package BibTeX;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String name) {
        String [] tab = name.split(" \\| ");
        if(tab.length == 2){
            this.firstName = tab[1];
            this.lastName = tab[0];
        }
        else {
            tab = name.split(" ");
            this.firstName = tab[0];
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < tab.length; i++){
                sb.append(tab[i]);
                sb.append(" ");
        }
            this.lastName = sb.toString();
        }
    }

    /**
     * metoda dostaje string z imieniem i/lub nazwiskiem autora
     * sprawdza czy to on
     */
    public boolean isAuthor(String partOfName){
        String fullName = this.firstName + " " + this.lastName;
        String [] words = partOfName.split(" +");
        for(String w : words){
            if(!fullName.contains(w)){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}
