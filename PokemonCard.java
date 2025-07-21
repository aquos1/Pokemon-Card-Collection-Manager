// Yash Kulkarni
// 6-6-25
// CSE 123
// C3 Best of the Best
// TA: Srihari
// Prof: Nathan Brunelle

import java.util.*;

// This class represents a pokemon card with descriptions about it.
// This class can compare cards to other cards based on their descriptions. 
public class PokemonCard implements Comparable<PokemonCard> {
    private String name;
    private int hp;
    private int attack;
       
    // B: Constructs a new pokemon card key descriptions. 
    // E: Throws IllegalArgumentException if : 
    // E: name is null, if name is empty. if attack is negative or HP is 0 or negative. 
    // R: N/A (constructor)
    // P: Takes in a name: describing the name of the pokemon.
    // P: takes in HP: describes the health points a pokemon has.
    // P: takes in attack: describes the amount of damage a pokemon can do.  
    public PokemonCard(String name, int hp, int attack) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (attack < 0) {
            throw new IllegalArgumentException();
        }
        if (hp <= 0) {
            throw new IllegalArgumentException();
        }
        
        this.name = name;
        this.attack = attack;
        this.hp = hp;
    }

    // B: Constructs a pokemon card based off of another pokemon card. 
    // E: None
    // R: N/A 
    // P: takes in a card that the new card will be based off of. 
    public PokemonCard(PokemonCard card) {
        this(card.name, card.hp, card.attack);
    }

    // B: Uses a input to create a new pokemon card.
    // E: None
    // R: Returns a new card with attributes from the input.
    // P: Input is the scanner that parses the input. 
    public static PokemonCard parse(Scanner input) {
        System.out.print("What is your created Pokemon's name?");
        String name = input.nextLine();
        
        System.out.print("What is the health points stat of your Pokemon?");
        int hp = Integer.parseInt(input.nextLine());
        
        System.out.print("What is the attack stat of your Pokemon?");
        int attack = Integer.parseInt(input.nextLine());
        
        return new PokemonCard(name, hp, attack);
    }
    
    // B: Gets name of the pokemon card. 
    // E: None
    // R: Returns name of the pokemon card. 
    // P: None
    public String getName() {
        return name;
    }

    // B: Gets attack of the pokemon card. 
    // E: None
    // R: Returns attack of the pokemon card. 
    // P: None
    public int getAttack() {
        return attack;
    }
    
    // B: Gets HP of the pokemon card. 
    // E: None
    // R: Returns HP of the pokemon card. 
    // P: None
    public int getHp() {
        return hp;
    }
    
    // B: Makes string representation of the pokemon card.
    // E: None
    // R: Returns String representation of the pokemon card with key attributes.  
    // P: None
    public String toString() {
        return name + " (" + hp + ", " + attack + ")";
    }
    
    // B: Compares "this" Pokemon card to the "other" Pokemon card by
    // B: name, HP, and attack in order.
    // E: None
    // R: Returns a negative integer if this card is considered less than the other
    // R: a positive integer if this card is considered greater than the other
    // R: zero if the two are equal.
    // P: Takes in another Pokemon card.
    public int compareTo(PokemonCard other) {
        if (!this.name.equals(other.name)) {
            return this.name.compareTo(other.name);
        } else if (this.hp != other.hp) {
            return Integer.compare(other.hp, this.hp);
        } else {
            return Integer.compare(other.attack, this.attack);
        }
    }

    // B: Checks if this pokemon card is equal to the other object.
    // E: None
    // R: Returns true if the pokemon cards are equal, and false if not.  
    // P: Other is the object that will be compared to this pokemon card.
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PokemonCard)) {
            return false;
        }
        PokemonCard otherCard = (PokemonCard) other;
        return this.name.equals(otherCard.name)
                && this.hp == otherCard.hp
                && this.attack == otherCard.attack;
    }
    
    // B: Creates a hash code value for this pokemon card. 
    // E: N/A
    // R: Returns a hashcode value for this pokemon card.
    // P: N/A
    public int hashCode() {
        return 31 * (31 * this.name.hashCode() + this.hp) + this.attack;
    }
}