package Ligatabell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LigaSimulatorTest {

    private Liga getEmptyTestPremierLeague(){
        Liga premierLeague = new Liga("Premier League");
        new Klubb("Southampton", premierLeague);
        new Klubb("Crystal Palace", premierLeague);
        new Klubb("Arsenal", premierLeague);
        new Klubb("Manchester United", premierLeague);
        new Klubb("Leeds United", premierLeague);
        new Klubb("Manchester City", premierLeague);
        new Klubb("Liverpool", premierLeague);
        new Klubb("Brighton", premierLeague);
        new Klubb("Everton", premierLeague);
        new Klubb("Burnley", premierLeague);
        new Klubb("Watford", premierLeague);
        new Klubb("Norwich", premierLeague);
        new Klubb("Brentford", premierLeague);
        new Klubb("Newcastle", premierLeague);
        new Klubb("West Ham", premierLeague);
        new Klubb("Leicester", premierLeague);
        new Klubb("Wolves", premierLeague);
        new Klubb("Tottenham", premierLeague);
        new Klubb("Chelsea", premierLeague);
        new Klubb("Aston Villa", premierLeague);
        return premierLeague;
    }

    private Liga getEptyTestOddNumberLiga(){
        Liga oddNumberLiga = new Liga("Premier League");
        new Klubb("Odd1", oddNumberLiga);
        new Klubb("Odd2", oddNumberLiga);
        new Klubb("Odd3", oddNumberLiga);
        new Klubb("Odd4", oddNumberLiga);
        new Klubb("Odd5", oddNumberLiga);
        new Klubb("Odd6", oddNumberLiga);
        new Klubb("Odd7", oddNumberLiga);
        new Klubb("Odd8", oddNumberLiga);
        new Klubb("Odd9", oddNumberLiga);
        new Klubb("Odd10", oddNumberLiga);
        new Klubb("Odd11", oddNumberLiga);
        return oddNumberLiga;
    }


    @Test
    @DisplayName("Tester simulering for ligaer som ikke kan simuleres")
    public void testIllegalSimulation(){
        assertThrows(NullPointerException.class, ()->new LigaSimulator().simulate(null));
        Liga enKlubbLiga = new Liga("enKlubb");
        new Klubb("testKlubb", enKlubbLiga);
        assertThrows(IllegalArgumentException.class, ()->enKlubbLiga.simulate());
        assertThrows(IllegalArgumentException.class, ()->new Liga("empty").simulate());
    }

    @Test
    @DisplayName("Tester at alle kamper er spilt når simulering er over for ligaer av ulik størrelse, samt at ingen er duplikat")
    public void testAllMatchesPlayedWithNoDuplicates(){
        Liga tjueKlubberLiga = getEmptyTestPremierLeague();
        Liga ellveKlubberLiga = getEptyTestOddNumberLiga();
        tjueKlubberLiga.simulate();
        ellveKlubberLiga.simulate();
        int totalAntallKamperTjueKlubberLiga = 380;
        int totalAntallKamperElleveKlubberLiga = 110;
        assertEquals(tjueKlubberLiga.getAlleKamper().size(), totalAntallKamperTjueKlubberLiga, "Antall kamper spilt samsvarer ikke med en ferdigspilt sesong");
        assertEquals(ellveKlubberLiga.getAlleKamper().size(), totalAntallKamperElleveKlubberLiga, "Antall kamper spilt samsvarer ikke med en ferdigspilt sesong");

        List<String> tjueKlubberKamperStringListe = tjueKlubberLiga.getAlleKamper().stream().map(kamp->kamp.getHjemmelag()+"-"+kamp.getBortelag()).toList();
        assertTrue(tjueKlubberKamperStringListe.stream().distinct().count()==totalAntallKamperTjueKlubberLiga, "Den samme kampen er spilt flere enn én gang");
        assertTrue(tjueKlubberLiga.getAlleKamper().stream().distinct().count()==totalAntallKamperTjueKlubberLiga, "Det same kamp-objektet er lagt til flere enn én gang");

        List<String> elleveKlubberKamperStringListe = ellveKlubberLiga.getAlleKamper().stream().map(kamp->kamp.getHjemmelag()+"-"+kamp.getBortelag()).toList();
        assertTrue(elleveKlubberKamperStringListe.stream().distinct().count()==totalAntallKamperElleveKlubberLiga, "Den samme kampen er spilt flere enn én gang");
        assertTrue(ellveKlubberLiga.getAlleKamper().stream().distinct().count()==totalAntallKamperElleveKlubberLiga, "Det same kamp-objektet er lagt til flere enn én gang");
        
    }

    @Test
    @DisplayName("Tester at resultatene genereres tilfeldig - i teorien kan denne feile, men svært usannsynlig")
    public void testRandomNess(){
        Liga simulatedLiga1 = getEmptyTestPremierLeague();
        simulatedLiga1.simulate();
        List<Character> simulation1Results =simulatedLiga1.getAlleKamper().stream().map(Kamp::getResultat).toList();
        Liga simulatedLiga2 = getEmptyTestPremierLeague();
        simulatedLiga2.simulate();
        List<Character> simulation2Results = simulatedLiga2.getAlleKamper().stream().map(Kamp::getResultat).toList();
        assertNotEquals(simulation1Results.toString(), simulation2Results.toString());
    }

    @Test
    @DisplayName("Tester at kamper man legger inn før simuleringen fortsatt er med etter simulering")
    public void testSimulationDoesnNotOverWrite(){
        Liga eliteserien = new Liga("Eliteserien");
        Klubb bodo = new Klubb("Bodø/Glimt", eliteserien);
        Klubb brann = new Klubb("Brann", eliteserien);
        Klubb lillestrom = new Klubb("Lillestrøm", eliteserien);
        Klubb molde = new Klubb("Molde", eliteserien);
        Klubb rbk = new Klubb("Rosenborg", eliteserien);
        Klubb viking = new Klubb("Viking", eliteserien);
        Klubb vif = new Klubb("Vålerenga", eliteserien);
        Kamp rbkVif = new Kamp(rbk, vif, 'B');
        Kamp moldeBrann = new Kamp(molde, brann, 'H');
        Kamp bodoLillestrom = new Kamp(bodo, lillestrom, 'H');
        Kamp vikingRbk = new Kamp(viking, rbk, 'U');
        eliteserien.simulate();
        assertTrue(eliteserien.getAlleKamper().contains(rbkVif));
        assertTrue(eliteserien.getAlleKamper().contains(moldeBrann));
        assertTrue(eliteserien.getAlleKamper().contains(bodoLillestrom));
        assertTrue(eliteserien.getAlleKamper().contains(vikingRbk));
    }

    @Test
    @DisplayName("Tester at ny simulering skjer dersom alle kampene er spilt")
    public void testNewSimulationOccurs(){
        Liga simulationLiga = getEmptyTestPremierLeague();
        simulationLiga.simulate();
        String oldLiga = simulationLiga.toString();
        List<String> oldResults = simulationLiga.getAlleKamper().stream().map(kamp->kamp.getHjemmelag()+"-"+kamp.getBortelag()+"-"+kamp.getResultat()).toList();
        simulationLiga.simulate();
        String newLiga = simulationLiga.toString();
        List<String> newResults = simulationLiga.getAlleKamper().stream().map(kamp->kamp.getHjemmelag()+"-"+kamp.getBortelag()+"-"+kamp.getResultat()).toList();
        assertNotEquals(oldResults, newResults);
        assertNotEquals(oldLiga, newLiga);
    }
}
