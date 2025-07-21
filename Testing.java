// Yash Kulkarni
// 6-6-25
// CSE 123
// C3 Best of the Best
// TA: Srihari
// Prof: Nathan Brunelle

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

public class Testing {
    private CollectionManager manager;
    private PokemonCard pikachu;
    private PokemonCard charizard;
    private PokemonCard blastoise;
    
    @BeforeEach
    public void setUp() {
        manager = new CollectionManager();
        pikachu = new PokemonCard("Pikachu", 60, 50);
        charizard = new PokemonCard("Charizard", 150, 120);
        blastoise = new PokemonCard("Blastoise", 140, 100);
    }
    
    // pokemoncard constructor
    @Test
    public void testPokemonCardConstructor() {
        PokemonCard card = new PokemonCard("Pikachu", 60, 50);
        assertEquals("Pikachu", card.getName());
        assertEquals(60, card.getHp());
        assertEquals(50, card.getAttack());
    }
    
    @Test
    public void testPokemonCardConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokemonCard(null, 60, 50);
        });
    }
    
    @Test
    public void testPokemonCardConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokemonCard("", 60, 50);
        });
    }
    
    @Test
    public void testPokemonCardConstructorNegativeHp() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokemonCard("Pikachu", -1, 50);
        });
    }
    
    @Test
    public void testPokemonCardConstructorZeroHp() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokemonCard("Pikachu", 0, 50);
        });
    }
    
    @Test
    public void testPokemonCardConstructorNegativeAttack() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokemonCard("Pikachu", 60, -1);
        });
    }
    
    @Test
    public void testPokemonCardConstructorZeroAttack() {
        PokemonCard card = new PokemonCard("Magikarp", 20, 0);
        assertEquals(0, card.getAttack());
    }
    
    // Tests for adding feature
    @Test
    public void testAdd() {
        manager.add(pikachu);
        assertTrue(manager.contains(pikachu));
    }
    
    @Test
    public void testAddMultipleCards() {
        manager.add(pikachu);
        manager.add(charizard);
        manager.add(blastoise);
        assertTrue(manager.contains(pikachu));
        assertTrue(manager.contains(charizard));
        assertTrue(manager.contains(blastoise));
    }
    
    @Test
    public void testAddDuplicateCards() {
        manager.add(pikachu);
        manager.add(pikachu);
        assertTrue(manager.contains(pikachu));
    }
    
    @Test
    public void testAddNullCard() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.add(null);
        });
    }
    
    // Tests for contains
    @Test
    public void testContains() {
        manager.add(pikachu);
        assertTrue(manager.contains(pikachu));
        assertFalse(manager.contains(charizard));
    }
    
    @Test
    public void testContainsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.contains(null);
        });
    }
    
    // Tests for the toString override
    @Test
    public void testToStringEmptyCollection() {
        String result = manager.toString();
        assertEquals("Your collection is empty. Add some cards!", result);
    }
    
    @Test
    public void testToStringWithCards() {
        manager.add(pikachu);
        String result = manager.toString();
        assertTrue(result.contains("Pokemon Card Collection"));
        assertTrue(result.contains("Pikachu"));
    }
    
    // Tests for binary tree structure and ordering
    @Test
    public void testTreeOrdering() {
        manager.add(new PokemonCard("Mew", 100, 70));
        manager.add(new PokemonCard("Alakazam", 80, 90));
        manager.add(new PokemonCard("Zapdos", 120, 100));
        
        String result = manager.toString();
        assertTrue(result.contains("Alakazam"));
        assertTrue(result.contains("Mew"));
        assertTrue(result.contains("Zapdos"));
    }
    
    @Test
    public void testCompareTo() {
        PokemonCard alakazam = new PokemonCard("Alakazam", 80, 90);
        PokemonCard pikachu2 = new PokemonCard("Pikachu", 60, 50);
        
        assertTrue(alakazam.compareTo(pikachu2) < 0);
        assertTrue(pikachu2.compareTo(alakazam) > 0);
    }
        
    @Test
    public void testCompareToSameName() {
        PokemonCard pikachu1 = new PokemonCard("Pikachu", 60, 50);
        PokemonCard pikachu2 = new PokemonCard("Pikachu", 80, 50);
        
        assertTrue(pikachu1.compareTo(pikachu2) > 0);
        assertTrue(pikachu2.compareTo(pikachu1) < 0);
    }
    
    // Tests for pokemon card equals
    @Test
    public void testPokemonCardEquals() {
        PokemonCard pikachu2 = new PokemonCard("Pikachu", 60, 50);
        assertTrue(pikachu.equals(pikachu2));
        assertFalse(pikachu.equals(charizard));
        assertFalse(pikachu.equals(null));
        assertFalse(pikachu.equals("not a card"));
    }
    
    // Tests for pokemon card toString
    @Test
    public void testPokemonCardToString() {
        String result = pikachu.toString();
        assertEquals("Pikachu (60, 50)", result);
    }
    
    // Tests for copy constructor
    @Test
    public void testPokemonCardCopyConstructor() {
        PokemonCard copy = new PokemonCard(pikachu);
        assertEquals(pikachu.getName(), copy.getName());
        assertEquals(pikachu.getHp(), copy.getHp());
        assertEquals(pikachu.getAttack(), copy.getAttack());
        assertTrue(pikachu.equals(copy));
    }
    
    // Tests for filter method
    @Test
    public void testFilterByNameOnly() {
        manager.add(pikachu); 
        manager.add(charizard); 
        manager.add(new PokemonCard("Pichu", 30, 20));
        
        List<PokemonCard> filtered = manager.filter("pi", 0);
        assertEquals(2, filtered.size()); 
    }
    
    @Test
    public void testFilterByHpOnly() {
        manager.add(pikachu); 
        manager.add(charizard);
        manager.add(blastoise); 
        
        List<PokemonCard> filtered = manager.filter(null, 100);
        assertEquals(2, filtered.size()); 
    }
    
    @Test
    public void testFilterMultipleCriteria() {
        manager.add(pikachu); 
        manager.add(charizard); 
        manager.add(new PokemonCard("Charmander", 40, 30));
        
        List<PokemonCard> filtered = manager.filter("char", 50);
        assertEquals(1, filtered.size()); 
        assertEquals(charizard, filtered.get(0));
    }
    
    @Test
    public void testFilterNoMatches() {
        manager.add(pikachu);
        
        List<PokemonCard> filtered = manager.filter("xyz", 200);
        assertTrue(filtered.isEmpty());
    }
    
    @Test
    public void testFilterAllNull() {
        manager.add(pikachu);
        manager.add(charizard);
        
        List<PokemonCard> filtered = manager.filter(null, 0);
        assertEquals(2, filtered.size()); 
    }
    
    @Test
    public void testFilterCaseInsensitive() {
        manager.add(pikachu); 
        
        List<PokemonCard> filtered = manager.filter("PIKA", 0);
        assertEquals(1, filtered.size());
        assertEquals(pikachu, filtered.get(0));
    }
    
    // Tests for save method
    @Test
    public void testSaveMethod() {
        manager.add(new PokemonCard("Alakazam", 80, 90));
        manager.add(new PokemonCard("Pikachu", 60, 50));
        manager.add(new PokemonCard("Zapdos", 120, 100));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        
        manager.save(ps);
        
        String output = baos.toString();
        assertTrue(output.contains("Alakazam"));
        assertTrue(output.contains("80"));
        assertTrue(output.contains("90"));
    }
    
    @Test
    public void testSaveNullOutput() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.save(null);
        });
    }
    
    @Test
    public void testSaveEmptyCollection() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        
        manager.save(ps);
        
        String output = baos.toString();
        assertTrue(output.isEmpty());
    }
    
    // Tests for Scanner constructor
    @Test
    public void testScannerConstructor() {
        String input = "Pikachu\n60\n50\nCharizard\n150\n120\n";
        Scanner scanner = new Scanner(input);
        
        CollectionManager loadedManager = new CollectionManager(scanner);
        assertTrue(loadedManager.contains(new PokemonCard("Pikachu", 60, 50)));
        assertTrue(loadedManager.contains(new PokemonCard("Charizard", 150, 120)));
    }
    
    @Test
    public void testScannerConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CollectionManager(null);
        });
    }
    
    @Test
    public void testScannerConstructorInvalidData() {
        String input = "Pikachu\nabc\n50\n"; 
        Scanner scanner = new Scanner(input);
        
        CollectionManager loadedManager = new CollectionManager(scanner);
        assertFalse(loadedManager.contains(new PokemonCard("Pikachu", 60, 50)));
    }
}