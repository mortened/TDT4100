package Ligatabell;

public class Kamp {
    private Klubb hjemmelag;
    private Klubb bortelag;
    private char resultat;

    public Kamp(Klubb hjemmelag, Klubb bortelag, char resultat){
        sjekkKamp(hjemmelag,bortelag,resultat);
        this.hjemmelag = hjemmelag;
        this.bortelag = bortelag;
        this.resultat = resultat;
        /* beregnPoeng(); */
        /* this.hjemmelag.addKamp(this);
        this.bortelag.addKamp(this); */
        hjemmelag.getLiga().addKamp(this);
    }

    private void sjekkKamp(Klubb hjemmelag, Klubb bortelag, char resultat) {
        if(hjemmelag==null||bortelag==null){
            throw new NullPointerException("Input cannot be null");
        }
        if(!hjemmelag.getLiga().equals(bortelag.getLiga())){
            throw new IllegalStateException("Klubber fra forskjellige ligaer kan ikke spille mot hverandre");
        }
        if(hjemmelag==bortelag){
            throw new IllegalArgumentException("Et lag kan ikke spille mot seg selv.");
        }
        for (Kamp kamp : hjemmelag.getKamper()) {
            if(kamp.getHjemmelag()==hjemmelag&&kamp.getBortelag()==bortelag){
                throw new IllegalArgumentException("Denne kampen er allerede spilt.");
            }
        }
        if(resultat!='H'&&resultat!='U'&&resultat!='B'){
            throw new IllegalArgumentException("Resultatet må være H, U eller B");
        }
    }

    public Klubb getHjemmelag() {
        return hjemmelag;
    }
    public Klubb getBortelag() {
        return bortelag;
    }
    public char getResultat() {
        return resultat;
    }
    /* private void beregnPoeng(){
        hjemmelag.updateKlubb(this);
        bortelag.updateKlubb(this); 
    } */

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.hjemmelag+"-"+this.bortelag+"-"+this.resultat;
    }

    public static void main(String[] args) {
        Liga l = new Liga("l");
        Klubb k = new Klubb("k", l);
        Klubb k2 = new Klubb("k2", l);
        Klubb k3 = new Klubb("k3", l);
        Kamp kamp = new Kamp(k3, k2, 'H');
        Kamp kamp2 = new Kamp(k, k2, 'H');
        
        System.out.println(k3.getKamper());
        System.out.println(k.getKamper());
        
    }
    

}
