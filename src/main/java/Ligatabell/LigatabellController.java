package Ligatabell;

import javafx.fxml.FXML;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.util.Pair;


public class LigatabellController {
    

    @FXML
    public TextField ligaNavn, klubbNavn, resultat;

    @FXML 
    private Text ligaOverskrift;

    @FXML
    private Button nyLiga, nyKlubb, spill, tilbake, lagreKnapp, simulerKnapp;

    @FXML
    private SplitMenuButton ligaOversikt, hjemmelag, bortelag;

    @FXML
    private ListView<String> posisjonListview, lagListView, kamperSpiltListView, tapListView, uavgjortListView,seierListView,poengListview;
   
    
    private Collection<Liga> ligaListe = new ArrayList<>();
    private Liga activeLiga;
    private Klubb valgtHjemmelag;
    private Klubb valgtBortelag;

    @FXML
    private Liga handleNyLiga(ActionEvent event){
        try {
            Liga activeLiga = new Liga(ligaNavn.getText());
            oppdaterLigaVisning(activeLiga);
            ligaListe.add(activeLiga);
            handleNyLigaIOversikt(activeLiga);
            bortelag.getItems().clear();
            hjemmelag.getItems().clear();
            return activeLiga;
        } catch (IllegalArgumentException e) {
            visFeilmelding(e.getMessage());
        }
        return null;
    }

    @FXML
    private void handleNyLigaIOversikt(Liga liga){
        if(!ligaListe.contains(liga)){
            ligaListe.add(liga);
        }
        MenuItem nyttLigaValg = new MenuItem(liga.getName());
        ligaOversikt.getItems().add(nyttLigaValg);
        nyttLigaValg.setOnAction(a->{
            bortelag.getItems().clear();
            hjemmelag.getItems().clear();
            oppdaterLigaVisning(liga);
            for (Klubb klubb : activeLiga.getKlubber()) {
                oppdaterHjemmeOgBorteLag(klubb);
            }
        });
           ligaOversiktVisning();
    }

    @FXML
    private void handleMenuNyLiga(ActionEvent event){
        posisjonListview.getItems().clear();
        lagListView.getItems().clear();
        kamperSpiltListView.getItems().clear();
        tapListView.getItems().clear();
        uavgjortListView.getItems().clear();
        seierListView.getItems().clear();
        poengListview.getItems().clear();
        hjemmelag.getItems().clear();
        bortelag.getItems().clear();
        ligaOverskrift.setText("");
        nyLigaVisning();
    }

    @FXML
    private Pair<Klubb,Klubb> handleNyKlubb(ActionEvent event){
        try {
            Klubb klubb = new Klubb(klubbNavn.getText(),activeLiga);
            oppdaterHjemmeOgBorteLag(klubb);
            oppdaterLigaVisning(activeLiga);
            return new Pair<Klubb,Klubb>(valgtBortelag,valgtHjemmelag);
        } catch (IllegalArgumentException e) {
            visFeilmelding(e.getMessage());
        } catch(NullPointerException e){
            visFeilmelding(e.getMessage());
        }
        return null;
    }


    private void oppdaterHjemmeOgBorteLag(Klubb klubb){
        MenuItem menuitem = new MenuItem(klubb.getKlubbnavn());
        MenuItem menuitem2 = new MenuItem(klubb.getKlubbnavn());
        menuitem.setOnAction(a->{
            valgtHjemmelag = klubb;
            hjemmelag.setText(klubb.getKlubbnavn());
        });
        menuitem2.setOnAction(a->{
            valgtBortelag = klubb;
            bortelag.setText(klubb.getKlubbnavn());
        });
        hjemmelag.getItems().add(menuitem);
        bortelag.getItems().add(menuitem2);
    }

    @FXML
    private void handleSpill(ActionEvent event){
        try {
            new Kamp(valgtHjemmelag, valgtBortelag, resultat.getText().charAt(0));
            oppdaterLigaVisning(activeLiga);
            valgtBortelag=null;
            valgtBortelag=null;
        }
         catch (IllegalArgumentException e) {
            visFeilmelding(e.getMessage());
        }
        catch(NullPointerException e){
            visFeilmelding("For å spille en kamp må gyldig hjemme- og bortelag velges");
        }
        catch(IndexOutOfBoundsException e){
            visFeilmelding("Legg inn resultat");
        }
    }

    private void oppdaterLigaVisning(Liga liga){
        ligaOverskrift.setText(liga.getName());
        activeLiga = liga;
        bortelag.setText("Bortelag");
        hjemmelag.setText("Hjemmelag");
        klubbNavn.clear();
        resultat.clear();

        posisjonListview.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(0)).toList());
        lagListView.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(1)).toList());
        kamperSpiltListView.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(2)).toList());
        tapListView.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(3)).toList());
        uavgjortListView.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(4)).toList());
        seierListView.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(5)).toList());
        poengListview.getItems().setAll(liga.toList().stream().map(innerList->innerList.get(6)).toList());
    }

    @FXML
    private void handleTilbake(){
        ligaOversiktVisning();
        oppdaterLigaVisning(activeLiga);
        for (Klubb klubb : activeLiga.getKlubber()) {
            oppdaterHjemmeOgBorteLag(klubb);
        }
    }

    @FXML
    private void handleSimuler(ActionEvent event){
        try {
            activeLiga.simulate();
            oppdaterLigaVisning(activeLiga);
        } catch (NullPointerException e) {
            visFeilmelding("Ligaen må eksistere for å kunne simuleres.");
        }
        
    }

    @FXML
    private void handleLagre(ActionEvent event){
        TextInputDialog lagreVindu = new TextInputDialog();
        lagreVindu.setHeaderText("Lagre oversikt");
        lagreVindu.setContentText("Lagre som:");
        Optional<String> filNavnInput = lagreVindu.showAndWait();
        if(filNavnInput.isPresent()){ 
            try {
                FileHandler fileWriter = new FileHandler();
                LigaSamling ligaSamling = new LigaSamling(ligaListe, filNavnInput.get());
                fileWriter.writeFile(filNavnInput.get(), ligaSamling);
            } catch (Exception e) {
                visFeilmelding(e.getMessage());
            }
            
         } 
        
    }

    @FXML
    private void handleHentLigaer(ActionEvent event) throws FileNotFoundException, NoSuchFileException{
        TextInputDialog hentLigaerVindu = new TextInputDialog();
        hentLigaerVindu.setHeaderText("Hent ligaer");
        hentLigaerVindu.setContentText("Skriv inn navnet på filen du ønsker å hente oversikten fra:");
        Optional<String> hentFilnavn = hentLigaerVindu.showAndWait();
        if(hentFilnavn.isPresent()){
            try {
                FileHandler fileReader = new FileHandler();
                LigaSamling hentetLigaSamling = fileReader.readFile(hentFilnavn.get());
                for (Liga liga : hentetLigaSamling.getSamling()){
                    
                    handleNyLigaIOversikt(liga);
                }
            } catch (FileNotFoundException e) {
                visFeilmelding(e.getMessage());
            }
        }
    }
    
    private void ligaOversiktVisning(){
        ligaNavn.setVisible(false);
        nyLiga.setVisible(false);
        ligaOversikt.setVisible(true);
        tilbake.setVisible(false);
        simulerKnapp.setVisible(true);
    }

    private void nyLigaVisning(){
        ligaNavn.clear();
        ligaNavn.setVisible(true);
        nyLiga.setVisible(true);
        ligaOversikt.setVisible(false);
        tilbake.setVisible(true);
        simulerKnapp.setVisible(false);
    }
    
    private void visFeilmelding(String melding){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Feilmelding");
        alert.setContentText(melding);
        alert.showAndWait();
    }

}
