package fact.it.supermarktproject.model;
//Victor Welters 1 ITF 1 r0784055

import java.util.ArrayList;

public class Supermarkt {
    private String naam;
    private int aantalKlanten;
    private ArrayList<Afdeling> afdelingen = new ArrayList<>();

    public Supermarkt(String naam){
        this.naam=naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ArrayList<Afdeling> getAfdelingen() {
        return afdelingen;
    }

    public int getAantalAfdelingen(){
        return getAfdelingen().size();
    }

    public void voegAfdelingToe(Afdeling afdelingen) {
        getAfdelingen().add(afdelingen);
    }

    public Afdeling zoekAfdelingOpNaam(String naam) {
        Afdeling val=null;
        for(Afdeling afdeling: afdelingen) {
            if (afdeling.getNaam().equals(naam)) {
                val= afdeling;
            }
        }
        return val;
    }

    public void registreerKlant(Klant klant) {
        aantalKlanten++;
        klant.setKlantenkaartnr(aantalKlanten);
    }
}