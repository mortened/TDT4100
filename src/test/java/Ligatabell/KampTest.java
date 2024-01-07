package Ligatabell;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KampTest {
    private Klubb k1;
    private Klubb k2;
    private Klubb k3;
    private Liga l1;
    private Liga l2;

    @BeforeEach
    public void setup(){
        l1 = new Liga("l1");
        l2 = new Liga("l2");
        k1 = new Klubb("k1", l1);
        k2 = new Klubb("k2", l1);
        k3 = new Klubb("k3", l2);
    }

    
    @Test
    public void testSjekkKamp(){
        assertThrows(IllegalStateException.class, ()->new Kamp(k1, k3, 'H'), "Tester om klubber fra forskjellige ligaer kan spille mot hverandre");
        assertThrows(IllegalArgumentException.class, ()->new Kamp(k1, k2, 'S'));
        assertThrows(IllegalArgumentException.class, ()->new Kamp(k1, k2, '\s'));
        assertDoesNotThrow(()->new Kamp(k1, k2, 'B'));
        assertThrows(IllegalArgumentException.class, ()->new Kamp(k1, k2, 'H'));
        assertDoesNotThrow(()->new Kamp(k2, k1, 'U'));
        assertThrows(IllegalArgumentException.class, ()->new Kamp(k2, k1, 'H'));
        assertThrows(IllegalArgumentException.class,()->new Kamp(k1, k1, 'B'));
    }
}
