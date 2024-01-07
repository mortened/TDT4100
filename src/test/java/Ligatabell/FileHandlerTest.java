package Ligatabell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)

public class FileHandlerTest {
    private static final String test_minligasamling_file_content = """
    MinLigaSamling
    $La Liga
    Real Madrid;Sevilla;Valencia;Athletic Bilbao;Barcelona;Real Sociedad;Atletico Madrid;Real Betis;Villareal;
    Barcelona-Real Sociedad-U;Sevilla-Athletic Bilbao-H;Real Betis-Valencia-B;Real Madrid-Villareal-H;Athletic Bilbao-Atletico Madrid-H;

    """;
    private static final String test_minvarierteligasamling_file_content = """
    Min varierte ligasamling
    $Eliteserien
    Bodø/Glimt;Brann;Lillestrøm;Molde;Rosenborg;Viking;Vålerenga;

    $Obosligaen

    $Allsvenskan
    Hacken;Hammarby;IFK Goteborg;Mjallby;Malmo FF;Sirius;AIK;Djurgården;Kalmar;Valbergs BolS;Elfsborg;IFK Varnamo;Degerfors IF;Helsingborg;Norrkoping;Sundsvall;
    Hammarby-Helsingborg-H;Hacken-AIK-H;IFK Goteborg-IFK Varnamo-H;Sirius-Sundsvall-H;Kalmar-Malmo FF-B;Norrkoping-Valbergs BolS-B;Elfsborg-Mjallby-B;Djurgården-Degerfors IF-H;Sundsvall-Hammarby-B;Degerfors IF-Hacken-B;Valbergs BolS-Kalmar-B;Helsingborg-IFK Goteborg-B;AIK-Norrkoping-H;Mjallby-Djurgården-H;IFK Varnamo-Sirius-U;Malmo FF-Elfsborg-U;

    $MLS - Eastern Conference
    Toronto;Philadelphia Union;NY Red Bulls;Orlando City;Atlanta United;
    Toronto-Atlanta United-H;Philadelphia Union-Toronto-U;

    $MLS - Western Conference
    LA Galaxy;

    """;


    private IFileHandler getFileHandler(){
        return new FileHandler();
    }

    private LigaSamling getExpectedMinLigaSamling(){
        Liga laliga = new Liga("La Liga");
        new Kamp(new Klubb("Barcelona", laliga), new Klubb("Real Sociedad", laliga), 'U');
        new Kamp(new Klubb("Sevilla", laliga), new Klubb("Athletic Bilbao", laliga), 'H');
        new Kamp(new Klubb("Real Betis", laliga),new Klubb("Valencia", laliga),'B');
        new Kamp(new Klubb("Real Madrid", laliga), new Klubb("Villareal", laliga), 'H');
        new Kamp(laliga.getKlubber().stream().filter(k->k.getKlubbnavn()=="Athletic Bilbao").toList().get(0),new Klubb("Atletico Madrid", laliga),'H');
        Collection<Liga> ligaList = new ArrayList<Liga>();
        ligaList.add(laliga);
        return new LigaSamling(ligaList, "MinLigaSamling");
    }

    private LigaSamling getExpectedMinVarierteLigaSamling(){
        Liga eliteserien = new Liga("Eliteserien");
        Liga obosligaen = new Liga("Obosligaen");
        Liga allsvenskan = new Liga("Allsvenskan");
        Liga MLSEastern = new Liga("MLS - Eastern Conference");
        Liga MLSWestern = new Liga("MLS - Western Conference");
        Collection<Liga> variertLigaList = new ArrayList<Liga>();

        new Klubb("Bodø/Glimt", eliteserien);
        new Klubb("Brann", eliteserien);
        new Klubb("Lillestrøm", eliteserien);
        new Klubb("Molde", eliteserien);
        new Klubb("Rosenborg", eliteserien);
        new Klubb("Viking", eliteserien);
        new Klubb("Vålerenga", eliteserien);
        
        Klubb aik = new Klubb("AIK", allsvenskan);
        Klubb hacken = new Klubb("Hacken", allsvenskan);
        Klubb hammarby = new Klubb("Hammarby", allsvenskan);
        Klubb ifkGoteborg = new Klubb("IFK Goteborg", allsvenskan);
        Klubb mjallby = new Klubb("Mjallby", allsvenskan);
        Klubb malmoff = new Klubb("Malmo FF", allsvenskan);
        Klubb sirius = new Klubb("Sirius", allsvenskan);
        Klubb djurgarden = new Klubb("Djurgården", allsvenskan);
        Klubb kalmar = new Klubb("Kalmar", allsvenskan);
        Klubb valbergsBols = new Klubb("Valbergs BolS", allsvenskan);
        Klubb elfsborg = new Klubb("Elfsborg", allsvenskan);
        Klubb ifkVarnamo = new Klubb("IFK Varnamo", allsvenskan);
        Klubb degerforsif = new Klubb("Degerfors IF", allsvenskan);
        Klubb helsingborg = new Klubb("Helsingborg", allsvenskan);
        Klubb norrkoping = new Klubb("Norrkoping", allsvenskan);
        Klubb sundsvall = new Klubb("Sundsvall", allsvenskan);

        Klubb toronto = new Klubb("Toronto", MLSEastern);
        Klubb philadelphiaUnion = new Klubb("Philadelphia Union", MLSEastern);
        new Klubb("NY Red Bulls", MLSEastern);
        new Klubb("Orlando City", MLSEastern);
        Klubb atlantaUnited = new Klubb("Atlanta United", MLSEastern);

        new Klubb("LA Galaxy", MLSWestern);

        new Kamp(hammarby, helsingborg, 'H');
        new Kamp(hacken, aik, 'H');
        new Kamp(ifkGoteborg, ifkVarnamo, 'H');
        new Kamp(sirius, sundsvall, 'H');
        new Kamp(kalmar, malmoff, 'B');
        new Kamp(norrkoping, valbergsBols, 'B');
        new Kamp(elfsborg, mjallby, 'B');
        new Kamp(djurgarden, degerforsif, 'H');
        new Kamp(sundsvall, hammarby, 'B');
        new Kamp(degerforsif, hacken, 'B');
        new Kamp(valbergsBols, kalmar, 'B');
        new Kamp(helsingborg, ifkGoteborg, 'B');
        new Kamp(aik, norrkoping, 'H');
        new Kamp(mjallby, djurgarden, 'H');
        new Kamp(ifkVarnamo, sirius, 'U');
        new Kamp(malmoff, elfsborg, 'U');
        
        new Kamp(toronto, atlantaUnited, 'H');
        new Kamp(philadelphiaUnion, toronto, 'U');
        
        variertLigaList.add(eliteserien);
        variertLigaList.add(obosligaen);
        variertLigaList.add(allsvenskan);
        variertLigaList.add(MLSEastern);
        variertLigaList.add(MLSWestern);
        LigaSamling variertSamling = new LigaSamling(variertLigaList, "Min varierte ligasamling");
        return variertSamling;
    }

    @BeforeAll
    public void lagTestFiler() throws IOException{
        Files.write(getFileHandler().getFilePath("min_ligasamling"), test_minligasamling_file_content.getBytes());
        Files.write(getFileHandler().getFilePath("min_varierte_ligasamling"),test_minvarierteligasamling_file_content.getBytes());
    }

    @Test
    public void testReadFileDoesNotExcist(){
        assertThrows(FileNotFoundException.class, ()->{
            getFileHandler().readFile("does_not_excist");
        });
    }

    @Test
    public void testWriteInvalidFile(){
        assertThrows(NullPointerException.class, ()->{
            getFileHandler().writeFile("test", null);
        });
    }

    @Test
    @DisplayName("Tester om readfile fungerer som tenkt for en samling med én liga")
    public void testReadMinSamling() throws NoSuchFileException, FileNotFoundException{
        LigaSamling expectedMinSamling = getExpectedMinLigaSamling();
        LigaSamling actualMinSamling = getFileHandler().readFile("min_ligasamling");
        Collection<Liga> expectedLigaer = expectedMinSamling.getSamling();
        Collection<Liga> actualLigaer = actualMinSamling.getSamling();
        assertEquals(expectedMinSamling.getName(), actualMinSamling.getName(), "Feil navn på ligasamling");
        assertEquals(expectedLigaer.stream().map(Liga::getName).toList(),actualLigaer.stream().map(Liga::getName).toList(),"Feil ligaer i ligasamling");
        Collection<String> expectedKlubberNavn = new ArrayList<String>();
        Collection<String> actualKlubberNavn = new ArrayList<String>();
        for (Liga liga : expectedLigaer) {
            for (Klubb klubb : liga.getKlubber()) {
                expectedKlubberNavn.add(klubb.getKlubbnavn());
            }    
        }
        for (Liga liga : actualLigaer) {
            for (Klubb klubb : liga.getKlubber()) {
                actualKlubberNavn.add(klubb.getKlubbnavn());
            }
        }
        assertTrue(actualKlubberNavn.containsAll(expectedKlubberNavn),"Inneholder ikke alle/riktige klubbnavn");
        assertEquals(expectedMinSamling.getSamling().stream().map(Liga::toList).toList(), actualMinSamling.getSamling().stream().map(Liga::toList).toList(),
        "Ligatabellene i samlingene stemmer ikke overens");
    }

    @Test
    @DisplayName("Tester om readFile fungerer som tenkt på en samling med flere varierte ligaer av forskjellige størrelser, både med og uten klubber og/eller kamer")
    public void testReadMinVarierteLigaSamling() throws NoSuchFileException, FileNotFoundException{
        LigaSamling expectedMinVarierteSamling = getExpectedMinVarierteLigaSamling();
        LigaSamling actualMinVarierteSamling = getFileHandler().readFile("min_varierte_ligasamling");

        assertEquals(expectedMinVarierteSamling.getName(), actualMinVarierteSamling.getName(),
        "Navn på samlingen stemmer ikke");
        assertEquals(expectedMinVarierteSamling.getSamling().stream().map(Liga::getName).toList(), actualMinVarierteSamling.getSamling().stream().map(Liga::getName).toList(),
        "Liganavn stemmer ikke");
        assertEquals(expectedMinVarierteSamling.getSamling().stream().map(Liga::toList).toList(), actualMinVarierteSamling.getSamling().stream().map(Liga::toList).toList(),
        "Ligatabeller stemmer ikke");
    }

    @Test
    @DisplayName("Tester writeFile på samling med én liga")
    public void testWriteMinSamling() throws NoSuchFileException, FileNotFoundException, IOException{
        getFileHandler().writeFile("test_write_minsamling", getExpectedMinLigaSamling());
        Path expectedMinSamlingFile = getFileHandler().getFilePath("min_ligasamling");
        Path actualMinSamlingFile = getFileHandler().getFilePath("test_write_minsamling");
        assertEquals(-1, Files.mismatch(expectedMinSamlingFile, actualMinSamlingFile));
    }

    @Test
    @DisplayName("Tester writeFile på samling med flere varierte ligaer")
    public void testWriteMinVarierteSamling() throws IllegalArgumentException, IOException{
        getFileHandler().writeFile("test_write_min_varierte_ligasamling", getExpectedMinVarierteLigaSamling());
        Path expectedMinVarierteSamlingFile = getFileHandler().getFilePath("min_varierte_ligasamling");
        Path actualMinVarierteSamlingFile = getFileHandler().getFilePath("test_write_min_varierte_ligasamling");
        assertEquals(-1, Files.mismatch(expectedMinVarierteSamlingFile, actualMinVarierteSamlingFile));
    }


    @AfterAll
    public void slettTestFiler() throws NoSuchFileException, IllegalArgumentException{
        getFileHandler().getFilePath("min_ligasamling").toFile().delete();
        getFileHandler().getFilePath("min_varierte_ligasamling").toFile().delete();
        getFileHandler().getFilePath("test_write_min_varierte_ligasamling").toFile().delete();
        getFileHandler().getFilePath("test_write_minsamling").toFile().delete();
    }  


}
