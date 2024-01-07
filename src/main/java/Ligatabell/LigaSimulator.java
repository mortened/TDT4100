package Ligatabell;


import java.util.Random;

public class LigaSimulator {

    public Liga simulate(Liga liga){
        
        if(liga == null){
            throw new NullPointerException("Liga må eksistere for å kunne simuleres");
        }
        if(liga.getKlubber().isEmpty()||liga.getKlubber().size()==1){
            throw new IllegalArgumentException("Kan ikke simulere liga med færre lag enn to");
        }
        if(seasonIsOver(liga)){
            liga.getAlleKamper().stream().toList().forEach(kamp->liga.slettKamp(kamp)); 
        }
        
        while(!seasonIsOver(liga)){
            for (Klubb hjemmelag : liga.getKlubber()) {
                for (Klubb bortelag : liga.getKlubber()) {
                    if(!hjemmelag.equals(bortelag)){
                        if(liga.getAlleKamper().stream().noneMatch(kamp->kamp.getHjemmelag().equals(hjemmelag)&&kamp.getBortelag().equals(bortelag))){
                            new Kamp(hjemmelag, bortelag, randChar());  
                        }
                    }
                }
            }
        }
        return liga;
    }
    private boolean seasonIsOver(Liga liga) {
        for (Klubb klubb : liga.getKlubber()) {
            if(!(klubb.getKamper().size()==((liga.getKlubber().size()-1)*2))){
                return false;
            }
        } 
        return true;
    }
    private char randChar(){
        Random rand = new Random();
        int randInt = rand.nextInt(3)+1;
        if(randInt==1){
            return 'H';
        }else if(randInt==2){
            return 'B';
        }
        return 'U';
    }
}
