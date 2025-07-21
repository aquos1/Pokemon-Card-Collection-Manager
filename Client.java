// Yash Kulkarni
// 6-6-25
// CSE 123
// C3 Best of the Best
// TA: Srihari
// Prof: Nathan Brunelle
import java.util.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the CSE 123 Collection Manager! " +
                "To begin, enter your desired mode of operation:");
        System.out.println();
        System.out.println("1) Start with an empty collection manager");
        System.out.println("2) Load collection from file");
        System.out.print("Enter your choice here: ");
        int choice = Integer.parseInt(console.nextLine());
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice! Try again");
            choice = Integer.parseInt(console.nextLine());
        }
        CollectionManager collectionManager = null;
        if (choice == 1) {
            collectionManager = new CollectionManager();
        } else if (choice == 2) {
            System.out.print("Enter file to read: ");
            String inFileName = console.nextLine();
            File inFile = new File(inFileName);
            while (!inFile.exists()) {
                System.out.println(" File does not exist. Please try again.");
                System.out.print("Enter file to read: ");
                inFileName = console.nextLine();
                inFile = new File(inFileName);
            }
            collectionManager = new CollectionManager(new Scanner(inFile));
            System.out.println("Collection manager created!");
            System.out.println();
        }
        menu(console);
        String option = console.nextLine();
        while (!option.equalsIgnoreCase("quit")) {
            System.out.println();
            if (option.equalsIgnoreCase("add")) {
                PokemonCard card = PokemonCard.parse(console);
                collectionManager.add(card);
                System.out.println();
            } else if (option.equalsIgnoreCase("contains")) {
                PokemonCard card = PokemonCard.parse(console);
                System.out.println(collectionManager.contains(card));
                System.out.println();
            } else if (option.equalsIgnoreCase("print")) {
                System.out.println(collectionManager.toString());
                System.out.println();
            } else if (option.equalsIgnoreCase("creative")) {
                System.out.print("Enter name filter (or press enter to skip): ");
                String nameFilter = console.nextLine();
                if (nameFilter.isEmpty()) {
                    nameFilter = null;
                }
                System.out.print("Enter minimum HP (or 0 to skip): ");
                int minHp = Integer.parseInt(console.nextLine());
                List<PokemonCard> filtered = collectionManager.filter(nameFilter, minHp);
                for (PokemonCard card : filtered) {
                    System.out.println(card);
                }
                System.out.println();
            } else if (option.equalsIgnoreCase("save")) {
                System.out.print("Enter file to save to: ");
                String outFileName = console.nextLine();
                PrintStream outFile = new PrintStream(new File(outFileName));
                collectionManager.save(outFile);
                System.out.println("Collection Manager exported!");
                System.out.println();
            } else if (!option.equalsIgnoreCase("quit")) {
                System.out.println(" Invalid choice. Please try again.");
                System.out.println();
            }
            menu(console);
            option = console.nextLine();
        }
    }
    
    private static void menu(Scanner console) {
        System.out.println("What would you like to do? Choose an option in brackets.");
        System.out.println(" [add] item");
        System.out.println(" [contains] item");
        System.out.println(" [print] my collection");
        System.out.println(" [save] my collection");
        System.out.println(" [creative] extension");
        System.out.println(" [quit] program");
    }
}