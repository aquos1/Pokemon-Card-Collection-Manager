// Yash Kulkarni
// 6-6-25
// CSE 123
// C3 Best of the Best
// TA: Srihari
// Prof: Nathan Brunelle

import java.util.*;
import java.io.*;

// This class organizes with a collection of pokemon cards.
// It allows adding cards, removing cards and searching for cards.
// It can also display statistics.
public class CollectionManager {
    private CardNode overallRoot;
    
    // B: Constructs a new empty pokemon card collection
    // E: None
    // R: N/A 
    public CollectionManager() {
        overallRoot = null;
    }
    
    // B: Constructs a pokemon card collection from the input. 
    // B: it reads name, hp, and attack in a 3 line format. 
    // E: If input is null, throws IllegalArgumentException.
    // R: N/A
    // P: input is a scanner that will examine pokemon data.
    public CollectionManager(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        overallRoot = null;
        
        while (input.hasNextLine()) {
            String name = input.nextLine().trim();
            if (!name.isEmpty() && input.hasNextLine()) {
                String hpLine = input.nextLine().trim();
                if (!hpLine.isEmpty() && input.hasNextLine()) {
                    String attackLine = input.nextLine().trim();
                    if (!attackLine.isEmpty() && isValidInteger(hpLine) && isValidInteger(attackLine)) {
                        int hp = Integer.parseInt(hpLine);
                        int attack = Integer.parseInt(attackLine);
                        PokemonCard card = new PokemonCard(name, hp, attack);
                        add(card);
                    }
                }
            }
        }
    }
    
    // B: Adds a pokemon card to the current collection. 
    // E: if the card that is being added is null, throw an illegal argument exception. 
    // R: N/A 
    // P: takes in a card that will be added to new collection. 
    public void add(PokemonCard card) {
        if (card == null) {
            throw new IllegalArgumentException();
        }
        overallRoot = add(overallRoot, card);
    }
    
    // B: Adds a pokemon card to the current collection. 
    // R: Returns the new card node. 
    // P: takes in a card that will be added to new collection.
    // P: curr is the current node in the collection.   
    private CardNode add(CardNode curr, PokemonCard card) {
        if (curr == null) {
            return new CardNode(card);
        }
        
        int comparison = card.compareTo(curr.card);
        if (comparison <= 0) {
            curr.left = add(curr.left, card);
        } else {
            curr.right = add(curr.right, card);
        }
        return curr;
    }
    
    // B: Checks if the collection has the pokemon card. 
    // E: Throws illegal argument exception if the card is null. 
    // R: Returns true if the collection has the card and false if not. 
    // P: takes in a card that will be searched for. 
    public boolean contains(PokemonCard card) {
        if (card == null) {
            throw new IllegalArgumentException();
        }
        return contains(overallRoot, card);
    }

    // B: Checks if the collection has the pokemon card. 
    // R: Returns true if the collection has the card and false if not. 
    // P: takes in a card that will be searched for.  
    // P: curr is the current node in the collection.     
    private boolean contains(CardNode curr, PokemonCard card) {
        if (curr == null) {
            return false;    
        }
        int comparison = card.compareTo(curr.card);
        if (comparison < 0) {
            return contains(curr.left, card);
        } else if (comparison > 0) {
            return contains(curr.right, card);
        } else {
            return true; 
        }
    }
    
    // B: Creates a string representation of all of the collection.  
    // R: Returns a string representation about each card in the collection 
    // and details about the card.   
    public String toString() {
        if (overallRoot == null) {
            return "Your collection is empty. Add some cards!";
        }
        
        String result = "Pokemon Card Collection:\n";
        result += toString(overallRoot, 1);
        return result;
    }
    
    // B: Creates a string representation of all of the collection.  
    // R: Returns a string representation about each card in the collection 
    // and details about the card.   
    // P: Takes in a counter, keeping order of traversals. 
    // P: curr is the current node in the collection.    
    private String toString(CardNode curr, int counter) {
        if (curr == null) {
            return "";
        }
        
        String result = "";
        result += toString(curr.left, counter);
        
        int leftSize = getSize(curr.left);
        result += (counter + leftSize) + ". " + curr.card.toString() + "\n";
        
        result += toString(curr.right, counter + leftSize + 1);
        return result;
    }
    
    // B: Saves this collection and prints in preorder traversal. 
    // E: If output is null, throw an illegal argument exception. 
    // R: N/A 
    // P: Takes in output, a printStream which is where the collection is saved.
    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException();
        }
        save(overallRoot, output);
    }
    
    // B: Saves this collection and prints in preorder traversal. 
    // E: N/A
    // R: N/A 
    // P: Takes in output, a printStream which is where the collection is saved.
    // P: curr is the current node in the collection. 
    private void save(CardNode curr, PrintStream output) {
        if (curr != null) {
            output.println(curr.card.getName());
            output.println(curr.card.getHp());
            output.println(curr.card.getAttack());
            
            save(curr.left, output);
            save(curr.right, output);
        }
    }
    
    // B: Filters through the collection to find cards matching criteria.  
    // E: N/A
    // R: Returns a list of cards meeting the inputted criteria. 
    // P: nameContains is a substring that the searched pokemon has to have. 
    // P: minHp is the minimum HP value. 
    public List<PokemonCard> filter(String nameContains, int minHp) {
        List<PokemonCard> result = new ArrayList<PokemonCard>();
        filter(overallRoot, nameContains, minHp, result);
        return result;
    }
    
    // B: Filters through the collection to find cards matching criteria.  
    // E: N/A
    // P: nameContains is a substring that the searched pokemon has to have. 
    // P: minHp is the minimum HP value. 
    // P: curr is the current node in the collection. 
    // P: result is the list where the matching cards are added. 
    private void filter(CardNode curr, String nameContains, int minHp, List<PokemonCard> result) {
        if (curr != null) {
            filter(curr.left, nameContains, minHp, result);
            
            boolean matches = true;
            
            if (nameContains != null && !nameContains.isEmpty()) {
                if (!curr.card.getName().toLowerCase().contains(nameContains.toLowerCase())) {
                    matches = false;
                }
            }
            
            if (minHp > 0 && curr.card.getHp() < minHp) {
                matches = false;
            }
            
            if (matches) {
                result.add(curr.card);
            }
            
            filter(curr.right, nameContains, minHp, result);
        }
    }
    
    // B: Counts nodes in the tree. 
    // E: N/A
    // R: Returns the number of nodes in the tree. 
    // P: curr is the current node in the collection. 
    private int getSize(CardNode curr) {
        if (curr == null) {
            return 0;
        }
        return 1 + getSize(curr.left) + getSize(curr.right);
    }

    // B: Checks if a string can be turned into an integer. 
    // E: None
    // R: Returns true if it can parse as an integer and false otherwise. 
    // P: Takes in the string that should be checked. 
    private boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        int start = 0;
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            start = 1;
        }
        
        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    // This class represents a node in the collection that stores information about
    // pokemon cards. It has references to left and right nodes. 
    private static class CardNode {
        public PokemonCard card;
        public CardNode left;
        public CardNode right;
        
        // B: Constructs a node based off of the given pokemonc ard.
        // E: N/A
        // R: N/A 
        // P: card: ard is the pokemon card that will be stored in the node. 
        public CardNode(PokemonCard card) {
            this(card, null, null);
        }
        
        // B: Constructs a new pokemon card node with given card info and child nodes. 
        // E: None
        // R: N/A 
        // P: card: takes in a card that the new card will be based off of. 
        // P: left is a reference to another node. 
        // p: right is a reference to a another node. 
        public CardNode(PokemonCard card, CardNode left, CardNode right) {
            this.card = card;
            this.left = left;
            this.right = right;
        }
        
        // B: Returns a string representation of the node. 
        // E: None
        // R: Returns a string representation of the pokemon card node. 
        // P: N/A
        public String toString() {
            return card.toString();
        }
    }
}