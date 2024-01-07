package Ligatabell;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class LigaSamlingTest {


    @Test
    public void testConstructor(){
        Collection<Liga> testSamling = new ArrayList<Liga>();
        assertThrows(IllegalArgumentException.class, ()->new LigaSamling(testSamling, "emptyLigaSamling"));
        Liga testLiga1 = new Liga("TestLiga1");
        testSamling.add(testLiga1);
        assertThrows(IllegalArgumentException.class, ()->new LigaSamling(testSamling, ""));
        assertThrows(NullPointerException.class, ()-> new LigaSamling(null, "test"));
    }

    @Test
    public void testAddLigaa(){
        Liga liga1 = new Liga("liga1");
        Collection<Liga> ligaer = new ArrayList<Liga>();
        ligaer.add(liga1);
        LigaSamling testLigaSamling = new LigaSamling(ligaer, "testLigaSamling");
        assertThrows(NullPointerException.class, ()->testLigaSamling.addLiga(null));
        assertThrows(IllegalArgumentException.class, ()->testLigaSamling.addLiga(liga1));
        
    }
    
}
