package Ligatabell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class FileHandler implements IFileHandler{

    @Override
    public LigaSamling readFile(String filename) throws FileNotFoundException, NoSuchFileException {
        
        try(Scanner scanner = new Scanner(getFile(filename))){
            Collection<Liga> ligaSamlingListe = new ArrayList<>();
            String ligaSamlingNavn = scanner.nextLine();
            
            while (scanner.hasNextLine()){
                    String lineLigaNavn = scanner.nextLine();
                    if(!lineLigaNavn.isBlank()){
                        Liga ligaSamlingLiga = new Liga(lineLigaNavn.replace("$", ""));
                        if(scanner.hasNextLine()){
                            String lineKlubber = scanner.nextLine();
                            if(!lineKlubber.isBlank()){
                                String[] klubber = lineKlubber.split(";");
                                Collection<Klubb> alleKlubbObjekter = new ArrayList<>();
                                for (String string : klubber) {
                                    Klubb ligaSamlingKlubb = new Klubb(string, ligaSamlingLiga);
                                    alleKlubbObjekter.add(ligaSamlingKlubb);
                                }
                                if(scanner.hasNextLine()){
                                    String lineKamper = scanner.nextLine();
                                    if(!lineKamper.isBlank()){
                                        String[] kamper = lineKamper.split(";");
                                        for (String string : kamper) {
                                            String[] kamp = string.split("-");
                                            new Kamp(alleKlubbObjekter.stream().filter(klubb->klubb.getKlubbnavn().equals(kamp[0])).toList().get(0),
                                            alleKlubbObjekter.stream().filter(klubb->klubb.getKlubbnavn().equals(kamp[1])).toList().get(0),kamp[2].charAt(0));
                                        }
                                    }
                                }
                            }
                        }
                        ligaSamlingListe.add(ligaSamlingLiga);  
                    }         
             }
             return new LigaSamling(ligaSamlingListe, ligaSamlingNavn);
        }
         catch (FileNotFoundException e) {
            throw new FileNotFoundException("Finner ingen fil med dette navnet");
        }
    }

    @Override
    public void writeFile(String filename, LigaSamling ligaSamling) {
        if(ligaSamling.equals(null)){
            throw new NullPointerException("LigaSamling cannot be null");
        }
        try(PrintWriter writer = new PrintWriter(getFile(filename))){
            writer.println(ligaSamling.getName());
            for (Liga liga: ligaSamling.getSamling()) {
                writer.println("$"+liga.getName());
                String klubberPrintLine="";
                String kamoerPrintLine="";
                for (Klubb klubb : liga.getKlubber()) {
                    klubberPrintLine+=klubb.getKlubbnavn()+";";
                }
                if(!klubberPrintLine.isBlank()){
                    writer.println(klubberPrintLine);
                }
                for (Kamp kamp : liga.getAlleKamper()) {
                    kamoerPrintLine+= (kamp.getHjemmelag().getKlubbnavn()+"-"+kamp.getBortelag().getKlubbnavn()+"-"+kamp.getResultat()+";");
                }
                if(!kamoerPrintLine.isBlank()){
                    writer.println(kamoerPrintLine);
                }
                writer.println();
            }
        }catch (Exception e) {
            
        } 
    }
    
    private File getFile(String filename) throws NoSuchFileException,IllegalArgumentException{
        if(filename.isBlank()||filename.isEmpty()){
            throw new IllegalArgumentException("Filens navn kan ikke være tomt");
        }
        return new File(filename+".txt");
    }

    public Path getFilePath(String filename) throws NoSuchFileException, IllegalArgumentException{
        return getFile(filename).toPath();
    }
    public static void main(String[] args) throws FileNotFoundException, NoSuchFileException {
        FileHandler fileHandler = new FileHandler();
        Liga testLiga = new Liga("testliga");
        Klubb k1 = new Klubb("k1", testLiga);
        Klubb k2 = new Klubb("k2", testLiga);
        Klubb k3 = new Klubb("k3", testLiga);
        Kamp ka1 = new Kamp(k1, k2, 'H');
        Kamp ka2 = new Kamp(k2, k3, 'U');
        

        Liga testLiga2 = new Liga("testliga2");
        Klubb k4 = new Klubb("k4", testLiga2);
        Klubb k5 = new Klubb("k5", testLiga2);
        Klubb k6 = new Klubb("k6", testLiga2);
        Kamp ka3 = new Kamp(k4, k5, 'H');
        Kamp ka4 = new Kamp(k5,k6,'B');

        Collection<Liga> ligaer = new ArrayList<>();
        ligaer.add(testLiga);
        ligaer.add(testLiga2);

        LigaSamling ligaSamling = new LigaSamling(ligaer, "testsamling");
        fileHandler.writeFile("testFil7", ligaSamling);

        fileHandler.readFile("testFil7");
    }
}
