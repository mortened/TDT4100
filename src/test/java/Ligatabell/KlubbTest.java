package Ligatabell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlubbTest {
    private Liga liga;
    private Klubb klubb;
    private Klubb klubb2;

    @BeforeEach
    public void setup(){
        liga = new Liga("testLiga");
        klubb = new Klubb("testKlubb", liga);
        klubb2 = new Klubb("testKlubb2", liga);
    }
    
    @Test
    @DisplayName("Tester konstruktør")
    public void testConstructor(){
        assertThrows(IllegalArgumentException.class, ()-> new Klubb("", new Liga("test")),"Klubbens navn kan ikke være tomt");
        assertThrows(NullPointerException.class, ()-> new Klubb("Klubb", null),"Klubben må tilhøre en gyldig liga.");
        Assertions.assertTrue(liga.getKlubber().contains(klubb), "Klubben ble ikke lagt til i ligaen");
    }

    @Test
    @DisplayName("Tester at riktig menge poeng blir utdelt, samt at en klubb ikke kan få poeng fra samme kampen flere ganger.")
    public void testUpdateState(){
        Kamp kamp1 = new Kamp(klubb, klubb2, 'H');
        Kamp kamp2 = new Kamp(klubb2, klubb, 'U');
        assertThrows(IllegalArgumentException.class, ()->klubb.updateState(kamp1, true));
        assertThrows(IllegalArgumentException.class, ()->klubb2.updateState(kamp2, true));
        assertEquals(1, klubb2.getPoeng());
        assertEquals(4, klubb.getPoeng());
        
    }

    @Test
    @DisplayName("Tester at kamp blir lagt til, kun én gang")
    public void testAddKamp(){
        Kamp kamp1 = new Kamp(klubb, klubb2, 'H');
        assertTrue(klubb.getKamper().contains(kamp1));
        assertTrue(liga.getAlleKamper().contains(kamp1));
        assertThrows(IllegalArgumentException.class, ()->klubb.addKamp(kamp1));
    }


}
