package Ligatabell;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class LigaTest {
    private Liga premierLeague;
    private Klubb k1; 
    private Klubb k2;
    private Klubb k3;
    private Klubb k4;
    private Klubb k5;
    private Klubb k6;
    private Klubb k7;
    private Klubb k8;
    private Klubb k9;
    private Klubb k10;
    private Klubb k11;
    private Klubb k12;
    private Klubb k13;
    private Klubb k14;
    private Klubb k15;
    private Klubb k16;
    private Klubb k17;
    private Klubb k18;
    private Klubb k19;
    private Klubb k20;

    @BeforeEach
    public void setup(){
        premierLeague = new Liga("Premier League");
        k1 = new Klubb("Southampton", premierLeague);
        new Klubb("Crystal Palace", premierLeague);
        k3 = new Klubb("Arsenal", premierLeague);
        k4 = new Klubb("Manchester United", premierLeague);
        k5 = new Klubb("Leeds United", premierLeague);
        k6 = new Klubb("Manchester City", premierLeague);
        k7 = new Klubb("Liverpool", premierLeague);
        k8 = new Klubb("Brighton", premierLeague);
        k9 = new Klubb("Everton", premierLeague);
        k10 = new Klubb("Burnley", premierLeague);
        k11 = new Klubb("Watford", premierLeague);
        k12 = new Klubb("Norwich", premierLeague);
        k13 = new Klubb("Brentford", premierLeague);
        k14 = new Klubb("Newcastle", premierLeague);
        k15 = new Klubb("West Ham", premierLeague);
        k16 = new Klubb("Leicester", premierLeague);
        k17 = new Klubb("Wolves", premierLeague);
        k18 = new Klubb("Tottenham", premierLeague);
        k19 = new Klubb("Chelsea", premierLeague);
        k20 = new Klubb("Aston Villa", premierLeague);
    }

    @Test
    public void testLigaConstructor(){
        assertThrows(IllegalArgumentException.class, ()->new Liga(""), "Kan ikke lage liga uten navn");
        assertThrows(IllegalArgumentException.class, ()->new Liga("   "), "Kan ikke lage liga uten navn");
    }
    @Test
    public void testAddKlubb(){
        Liga liga = new Liga("TestLiga");
        Klubb klubb = new Klubb("TestKlubb", liga);
        Klubb klubb2 = new Klubb("TestKlubb2", liga);
        assertTrue(liga.getKlubber().contains(klubb), "Klubb blir ikke lagt til i liga ved opprettelse");
        assertTrue(liga.getKlubber().size()==2, "Flere klubber i liga enn ønsket");
        assertFalse(liga.getKlubber().stream().filter(k->k.equals(klubb2)).toList().size()>=2, "Klubb blir lagt til flere ganger");
        assertThrows(IllegalArgumentException.class, ()->liga.addKlubb(klubb2), "Kan legge til samme klubb flere ganger");
        assertThrows(IllegalArgumentException.class, ()->liga.addKlubb(new Klubb("TestKlubb", liga)), "Kan legge til klubb med samme navn som annen i ligaen");
        
    }

    @Test 
    public void testAlfabetiskPlassering(){
        
        assertTrue(premierLeague.getKlubber().stream()
                                .map(Klubb::getKlubbnavn)
                                .collect(Collectors.toList())
                                .toString().equals("[Arsenal, Aston Villa, Brentford, Brighton, Burnley, Chelsea, Crystal Palace, Everton, Leeds United, Leicester, Liverpool, Manchester City, Manchester United, Newcastle, Norwich, Southampton, Tottenham, Watford, West Ham, Wolves]"),
                                "Sortert feil alfabetisk");
        
        new Kamp(k4, k19, 'B');
        new Kamp(k19,k4,'B');
        new Kamp(k9, k20, 'U');
        
        assertTrue(premierLeague.getKlubber().stream()
                                .map(Klubb::getKlubbnavn)
                                .collect(Collectors.toList())
                                .toString().equals("[Chelsea, Manchester United, Aston Villa, Everton, Arsenal, Brentford, Brighton, Burnley, Crystal Palace, Leeds United, Leicester, Liverpool, Manchester City, Newcastle, Norwich, Southampton, Tottenham, Watford, West Ham, Wolves]"),
                                "Dersom to eller flere klubber har lik poengsum, antall seiere og tap, skal klubbene sorteres alfabetisk.");
    }

    @Test
    @DisplayName("Tester at tabellen oppdaterer seg som ønsket")
    public void testPlassering(){
        new Kamp(k3,k6,'B');
        
        assertTrue(premierLeague.getKlubber().stream()
                                .map(Klubb::getKlubbnavn)
                                .collect(Collectors.toList())
                                .toString().equals("[Manchester City, Aston Villa, Brentford, Brighton, Burnley, Chelsea, Crystal Palace, Everton, Leeds United, Leicester, Liverpool, Manchester United, Newcastle, Norwich, Southampton, Tottenham, Watford, West Ham, Wolves, Arsenal]"),
                                "Like mange poeng og færre kamper spilt rangeres over.");
        
        new Kamp(k9, k10,'U');
        new Kamp(k9, k11,'U');
        new Kamp(k9,k13,'U');
        new Kamp(k12,k14,'H');
        
        assertTrue(premierLeague.getKlubber().stream()
                                .map(Klubb::getKlubbnavn)
                                .collect(Collectors.toList())
                                .toString().equals("[Manchester City, Norwich, Everton, Brentford, Burnley, Watford, Aston Villa, Brighton, Chelsea, Crystal Palace, Leeds United, Leicester, Liverpool, Manchester United, Southampton, Tottenham, West Ham, Wolves, Arsenal, Newcastle]"),
                                "Like mange poeng samt kamper spilt -> flere seiere rangeres over.");         
        new Kamp(k6,k3,'H');
        
        assertTrue(premierLeague.getKlubber().stream()
                                .map(Klubb::getKlubbnavn)
                                .collect(Collectors.toList())
                                .toString().equals("[Manchester City, Norwich, Everton, Brentford, Burnley, Watford, Aston Villa, Brighton, Chelsea, Crystal Palace, Leeds United, Leicester, Liverpool, Manchester United, Southampton, Tottenham, West Ham, Wolves, Newcastle, Arsenal]"),
                                "Like mange poeng samt kamper spilt og seiere -> flere tap rangeres under");
    }

    @Test
    @DisplayName("Sjekker om toList fungerer som tenkt")
    public void toList(){
        assertEquals("[Pos, Lag, S, T, U, V, P]", premierLeague.toList().get(0).toString(), "Første rad i tabell feil");
        List <String> kolonne1 = new ArrayList<String>();
        List <String> kolonne2 = new ArrayList<String>();
        List <String> kolonne3 = new ArrayList<String>();
        List <String> kolonne4 = new ArrayList<String>();
        List <String> kolonne5 = new ArrayList<String>();
        List <String> kolonne6 = new ArrayList<String>();
        List <String> kolonne7 = new ArrayList<String>();

        for (List<String> l : premierLeague.toList()) {
            kolonne1.add(l.get(0));
            kolonne2.add(l.get(1));
        }

        assertEquals(kolonne1.toString(), "[Pos, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]", "Første kolonne i tabell feil");
        assertEquals(kolonne2.toString(), "[Lag, Arsenal, Aston Villa, Brentford, Brighton, Burnley, Chelsea, Crystal Palace, Everton, Leeds United, Leicester, Liverpool, Manchester City, Manchester United, Newcastle, Norwich, Southampton, Tottenham, Watford, West Ham, Wolves]",
        "Kolonne to i tabell feil");

        new Kamp(k1, k16, 'B');
        new Kamp(k3,k16,'H');
        new Kamp(k4,k18,'U');
        for (List<String> l : premierLeague.toList()) {
            kolonne3.add(l.get(2));
            kolonne4.add(l.get(3));
            kolonne5.add(l.get(4));
            kolonne6.add(l.get(5));
            kolonne7.add(l.get(6));
        }
        
        assertEquals(kolonne3.toString(), "[S, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]",
        "Feil antall kamper spilt i tabell");
        assertEquals(kolonne4.toString(), "[T, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]",
        "Feil antall kamper tapt i tabell");
        assertEquals(kolonne5.toString(), "[U, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
        "Feil antall kamper uavgjort i tabell");
        assertEquals(kolonne6.toString(), "[V, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
        "Feil antall kamper vunnet i tabell");
        assertEquals(kolonne7.toString(), "[P, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
        "Feil antall poeng i tabell");
    }

    @Test
    public void testSlettKamp(){
        Kamp testKamp = new Kamp(k5,k8,'H');
        premierLeague.slettKamp(testKamp);
        assertTrue(premierLeague.getAlleKamper().isEmpty());
        assertTrue(k5.getKamper().isEmpty());
        assertTrue(k8.getKamper().isEmpty());
        Kamp testKamp2 = new Kamp(k4,k8,'B');
        assertThrows(IllegalArgumentException.class, ()->new Liga("test").slettKamp(testKamp2));
    }


    
    
}
