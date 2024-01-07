package Ligatabell;

import java.util.ArrayList;
import java.util.Collection;

public class Klubb implements LigaLytter{
    private String klubbnavn;
    private int poeng;
    private Liga liga;
    private Collection<Kamp> kamper = new ArrayList<>();

    public Klubb(String klubbnavn, Liga liga){
        if(klubbnavn.isBlank()){
            throw new IllegalArgumentException("Klubben må ha et navn");
        }else if(liga == null){
            throw new NullPointerException("Klubben må tilhøre en gyldig liga");
        }else if(klubbnavn.equals(null)){
            throw new NullPointerException("Klubben må ha et navn");
        }
        this.klubbnavn = klubbnavn;
        liga.addKlubb(this);
        this.liga = liga;
        
    }

    public String getKlubbnavn() {
        return klubbnavn;
    }

    public int getPoeng() {
        return poeng;
    }
    
    public Collection<Kamp> getKamper() {
        return kamper;
    }

    public Liga getLiga(){
        return this.liga;
    }

    public void addKamp(Kamp kamp){
        if(this.kamper.contains(kamp)){
            throw new IllegalArgumentException("Kampen kan ikke legges til to ganger");
        }
        kamper.add(kamp);
        /* if(!this.liga.getAlleKamper().contains(kamp)){
            this.liga.addKamp(kamp);
        } */
    }
    
    public void updateState(Kamp kamp, boolean add){
        /* if(poeng<=0){
            throw new IllegalArgumentException("Kan ikke legge til poeng som er mindre eller lik null");
        } */
        if(add){
            if(this.kamper.contains(kamp)){
                throw new IllegalArgumentException("Poengene for denne kampen er allerede delt ut.");
            }
            if(kamp.getHjemmelag().equals(this)||kamp.getBortelag().equals(this)){
                addKamp(kamp);
                if(kampVunnet(kamp)){
                    updatePoeng(3);
                }else if(kampUavgjort(kamp)){
                    updatePoeng(1); 
                }
        }
        }else{
            if(this.kamper.contains(kamp)){
                kamper.remove(kamp);
                if(kampVunnet(kamp)){
                    updatePoeng(-3);
                }else if(kampUavgjort(kamp)){
                    updatePoeng(-1); 
                }
            }  
        }        
    }

    private boolean kampVunnet(Kamp kamp){
        if(kamp.getHjemmelag().equals(this)&&kamp.getResultat()=='H'||kamp.getBortelag().equals(this)&&kamp.getResultat()=='B'){
            return true;
        }
        return false;
    }

    private boolean kampUavgjort(Kamp kamp){
        if(kamp.getResultat()=='U'){
            return true;
        }
        return false;
    }

    private void updatePoeng(int poeng){
        this.poeng+=poeng;
    }

    @Override
    public String toString() {
        return klubbnavn;
    }
    public static void main(String[] args) {
        Liga premierLeague = new Liga("Premier League");
        Collection<String> nyeKlubber = new ArrayList<>();
        Klubb v = new Klubb("Vålerenga", premierLeague);
        Klubb f = new Klubb("Follo", premierLeague);
        nyeKlubber.add("Chelsea");
        nyeKlubber.add("Manchester City");
        nyeKlubber.add("Wolves");
        nyeKlubber.add("Tottenham");
        nyeKlubber.add("Southampton");
        nyeKlubber.add("Everton");
        nyeKlubber.add("Manchester United");
        nyeKlubber.add("Liverpool");
        nyeKlubber.add("Brighton");
        nyeKlubber.add("Arsenal");
        nyeKlubber.add("West Ham United");
        nyeKlubber.add("Crystal Palace");
        nyeKlubber.add("Leicester");
        nyeKlubber.add("Aston Villa");  
        nyeKlubber.add("Brentford"); 
        nyeKlubber.add("Leeds");
        nyeKlubber.add("Newcastle");
        nyeKlubber.add("Burnley");   
        nyeKlubber.add("Watford"); 
        nyeKlubber.add("Norwich"); 
        
        Kamp k = new Kamp(v, f, 'H');
        System.out.println(premierLeague);
        
    }
    
}


