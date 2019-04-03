package BibTeX;

import java.io.*;
import java.util.Scanner;

import static BibTeX.Parser.findRecordsByType;
import static BibTeX.Parser.findRecordsOfAuthor;

public class Main {

    public static void main(String[] args) throws IOException, MandatoryFieldException {
        System.out.println("Naciśnij enter by wyświetlić");
        String instruction;
        //C:\\Users\\Grzegorz\\Desktop\\xampl.bib.txt
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            instruction = in.nextLine();
            switch(instruction){
                case "a":
                case "A":
                    System.out.println("Wpisz rekordy jakich autorów chcesz wyszukać. Oddziel ich przecinkami.");
                    instruction = in.nextLine();
                    String [] authors = instruction.split(" ?, ?");
                    findRecordsOfAuthor(authors);
                    break;
                case "t":
                case "T":
                    System.out.println("Wpisz nazwy typów rekordów, które chciałbyś wyświetlić. Oddziel je przecinkami.");
                    instruction = in.nextLine();
                    String [] types = instruction.split(" ?, ?");
                    findRecordsByType(types);
                    break;
                case "w":
                case "W":
                    Parser.displayAll();
                    break;
                case "p":
                case "P":
                    System.out.println("Wpisz ścieżkę do pliku BibTeX, dla którego chcesz wykonać parsowanie;");
                    instruction = in.nextLine();
                    Parser.parseFile(instruction);
                    break;
                case "k":
                case "K":
                    flag = false;
                    break;
                default:
                    System.out.println("Witaj w programie parsującym pliki BibTeX. Dostępne opcje programu to:");
                    System.out.println("\t- Sparsuj plik. By to zrobić wpisz: p");
                    System.out.println("\t- Wyszukaj rekordy danego autora/autorów. By to zrobić wpisz: a");
                    System.out.println("\t- Wyświetl rekordy danego typu/typów. By to zrobić, wpisz: t");
                    System.out.println("\t- Wyświetl wszystkie rekordy. By to zrobić, wpisz: w");
                    System.out.println("Możesz zakończyć działanie programu, wpisując: k");
                    System.out.println("Udanego parsowania! ;)");
            }
        }
    }
}
