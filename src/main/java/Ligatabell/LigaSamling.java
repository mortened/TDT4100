package Ligatabell;

import java.util.Collection;


public class LigaSamling {
    private String name;
    private Collection<Liga> samling;

    public LigaSamling(Collection<Liga> samling, String name){
        if(samling.isEmpty()){
            throw new IllegalArgumentException("En ligasamling kan ikke være tom");
        }
        if(name.isBlank()){
            throw new IllegalArgumentException("Oversikten må ha et navn.");
        }
        this.name = name;
        this.samling = samling;
    }
    

    public void addLiga(Liga liga){
        if(liga==null){
            throw new NullPointerException("Ligaen må være definert");
        }
        if(samling.contains(liga)){
            throw new IllegalArgumentException("Ligaen er allerede med i samlingen");
        }
        samling.add(liga);
    }

    public Collection<Liga> getSamling(){
        return this.samling;
    }

    public String getName(){
        return this.name;
    }
}
