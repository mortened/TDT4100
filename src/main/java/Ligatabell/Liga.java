package Ligatabell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Liga{
    private String name;
    private List<Klubb> klubber = new ArrayList<>();
    private Collection<Kamp> alleKamper= new ArrayList<>();
    private Collection<LigaLytter> lyttere = new ArrayList<>();
    private LigaSimulator ligaSimulator = new LigaSimulator();

    public Liga(String name) {
        if(name.equals(null)){
            throw new NullPointerException("Name cannot be null");
        }
        else if(name.isBlank()){
            throw new IllegalArgumentException("Navnet kan ikke være tomt");
        } 
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void addKlubb(Klubb klubb){
        Collection<String> alleKlubbnavn = new ArrayList<>();
        for (Klubb klubb1 : klubber) {
            alleKlubbnavn.add(klubb1.getKlubbnavn());
        }
        if(alleKlubbnavn.contains(klubb.getKlubbnavn())||klubber.contains(klubb)){
            throw new IllegalArgumentException("Klubben er allerede med i ligaen");
        }
        klubber.add(klubb);
        lyttere.add(klubb);
        oppdaterTabell2();
    }

    public void addLigaLytter(LigaLytter lytter){
        if(this.lyttere.contains(lytter)){
            throw new IllegalArgumentException("Lytteren finnes allerede");
        }
        lyttere.add(lytter);
    }

    public void addKamp(Kamp kamp){
        if(!alleKamper.contains(kamp)){
            alleKamper.add(kamp);
        }
        this.lyttere.forEach(klubb->klubb.updateState(kamp, true));
        oppdaterTabell2();
    }

    public void slettKamp(Kamp kamp){
            if(!getAlleKamper().contains(kamp)){
                throw new IllegalArgumentException("Kan ikke slette kamp spilt i en annen liga");
            }
            alleKamper.remove(kamp);
            this.lyttere.forEach(klubb->klubb.updateState(kamp, false));
    }

    public List<Klubb> getKlubber(){
        return this.klubber;
    }

    public Collection<Kamp> getAlleKamper(){
        return alleKamper;
    }
    public void oppdaterTabell2(){
    
        List<Klubb> temp = new ArrayList<Klubb>(klubber);
        temp.sort((klubb1,klubb2)->{
            if(klubb1.getPoeng()!=klubb2.getPoeng()){
                return klubb2.getPoeng()-klubb1.getPoeng();
            }
            int klubb1Seiere = klubb1.getKamper()
                            .stream().filter(kamp -> 
                            (kamp.getHjemmelag().equals(klubb1)&&kamp.getResultat()=='H')
                            ||kamp.getBortelag().equals(klubb1)&&kamp.getResultat()=='B')
                            .toList().size();
            int klubb1Tap = klubb1.getKamper()
                            .stream().filter(kamp -> 
                            (kamp.getHjemmelag().equals(klubb1)&&kamp.getResultat()=='B')
                            ||kamp.getBortelag().equals(klubb1)&&kamp.getResultat()=='H')
                            .toList().size();
            int klubb2Seiere = klubb2.getKamper()
                            .stream()
                            .filter(kamp -> 
                            (kamp.getBortelag().equals(klubb2)&&kamp.getResultat()=='B')
                            ||kamp.getHjemmelag().equals(klubb2)&&kamp.getResultat()=='H')
                            .toList().size();
            int klubb2Tap = klubb2.getKamper()
                        .stream().filter(kamp -> 
                        (kamp.getHjemmelag().equals(klubb2)&&kamp.getResultat()=='B')
                        ||kamp.getBortelag().equals(klubb2)&&kamp.getResultat()=='H')
                        .toList().size();
    
            if(klubb1.getKamper().size()==klubb2.getKamper().size()){
                if(klubb1Seiere!=klubb2Seiere){
                    return klubb2Seiere-klubb1Seiere;
                }else if(klubb1Tap!=klubb2Tap){
                    return klubb1Tap-klubb2Tap;
                }
                return klubb1.getKlubbnavn().compareTo(klubb2.getKlubbnavn());
            }
            return klubb1.getKamper().size()-klubb2.getKamper().size();
        }); 
        klubber = temp;
    }

    public List<List<String>> toList(){
        int i = 1;
        List<List<String>> utskrift = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add("Pos");
        s.add("Lag");
        s.add("S");
        s.add("T");
        s.add("U");
        s.add("V");
        s.add("P");
        utskrift.add(s);
        for (Klubb klubb : klubber) {
            List<String> element = new ArrayList<>();
            element.add(Integer.toString(i));
            element.add(klubb.getKlubbnavn());
            element.add(Integer.toString(klubb.getKamper().size()));
            element.add(Integer.toString(
                klubb.getKamper().stream().filter(kamp->(kamp.getHjemmelag().equals(klubb)&&kamp.getResultat()=='B')
                ||(kamp.getBortelag().equals(klubb)&&kamp.getResultat()=='H')).toList().size()
            ));
            element.add(Integer.toString(
                klubb.getKamper().stream().filter(kamp->(kamp.getResultat()=='U')).toList().size()
            ));
            element.add(Integer.toString(
                klubb.getKamper().stream().filter(kamp->(kamp.getHjemmelag().equals(klubb)&&kamp.getResultat()=='H')
                ||(kamp.getBortelag().equals(klubb)&&kamp.getResultat()=='B')).toList().size()
            ));
            element.add(Integer.toString(klubb.getPoeng()));
            i++;
            utskrift.add(element);
        }
        return utskrift;
    }

    public void simulate(){
        ligaSimulator.simulate(this);
    }

    @Override
    public String toString() {
        oppdaterTabell2();
        int i = 1;
        String tabell ="";
        for (Klubb klubb : klubber) {
            tabell+=i+". "+klubb+":"+klubb.getPoeng()+"\n";
            i++;
        }
        return tabell;
    }


    public static void main(String[] args) {
        
        
        Liga premierLeague = new Liga("Premier league");
        Klubb k1=new Klubb("Southampton", premierLeague);
        Klubb k2=new Klubb("Crystal Palace", premierLeague);
        Klubb k3=new Klubb("Arsenal", premierLeague);
        Klubb k4=new Klubb("Manchester United", premierLeague);
        Klubb k5=new Klubb("Leeds United", premierLeague);
        Klubb k6=new Klubb("Manchester City", premierLeague);
        Klubb k7=new Klubb("Liverpool", premierLeague);
        Klubb k8=new Klubb("Brighton", premierLeague);
        Klubb k9=new Klubb("Everton", premierLeague);
        Klubb k10= new Klubb("Burnley", premierLeague);
        Klubb k11= new Klubb("Watford", premierLeague);
        Klubb k12= new Klubb("Norwich", premierLeague);
        Klubb k13= new Klubb("Brentford", premierLeague);
        Klubb k14= new Klubb("Newcastle", premierLeague);
        Klubb k15= new Klubb("West Ham", premierLeague);
        Klubb k16= new Klubb("Leicester", premierLeague);
        Klubb k17= new Klubb("Wolves", premierLeague);
        Klubb k18= new Klubb("Tottenham", premierLeague);
        Klubb k19= new Klubb("Chelsea", premierLeague);
        Klubb k20= new Klubb("Aston Villa", premierLeague);
        premierLeague.oppdaterTabell2();
        System.out.println(premierLeague.getKlubber().stream().map(Klubb::getKlubbnavn).collect(Collectors.toList()).toString());
        System.out.println(premierLeague.toList().toString());
        System.out.println(premierLeague.toList().get(0).toString());
        System.out.println(premierLeague.toList().toString());
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
            kolonne3.add(l.get(2));
            kolonne4.add(l.get(3));
            kolonne5.add(l.get(4));
            kolonne6.add(l.get(5));
            kolonne7.add(l.get(6));
        }
        
        new Kamp(k13, k3, 'H');
        new Kamp(k4,k5,'H');
        new Kamp(k19,k2,'H');
        new Kamp(k9,k1,'H');
        new Kamp(k16,k17,'H');
        new Kamp(k11,k20,'H');

        Liga pls = new Liga("Pls");
        Klubb k = new Klubb("k", pls);
        Klubb kl = new Klubb("kl", pls);
        pls.simulate();
        System.out.println(pls.toString());

        Liga allsvenskan = new Liga("Allsvenskan");
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

        
        
        Kamp kamp = new Kamp(sundsvall, norrkoping, 'H');
        System.out.println(allsvenskan.toString());
        allsvenskan.slettKamp(kamp);
        System.out.println(allsvenskan.toString());


    }
}
