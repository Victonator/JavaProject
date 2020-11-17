package fact.it.supermarktproject.model;
//Victor Welters 1 ITF 1 r0784055

import java.util.ArrayList;

public class Klant extends Persoon{
    private ArrayList<String> boodschappenlijst = new ArrayList<>();
    private int klantenkaartnr;

    public Klant(String voornaam, String familienaam){
        super(voornaam,familienaam);
        klantenkaartnr= -1;
    }

    public int getKlantenkaartnr() {
        return klantenkaartnr;
    }

    public void setKlantenkaartnr(int klantenkaartnr) {
        this.klantenkaartnr = klantenkaartnr;
    }

    public ArrayList<String> getBoodschappenlijst() {
        return boodschappenlijst;
    }

    public boolean voegToeAanBoodschappenlijst(String product){
        if(getAantalOpBoodschappenlijst() <= 4){
            getBoodschappenlijst().add(product);
            return true;
        }
        else{
            return false;
        }
    }

    public int getAantalOpBoodschappenlijst(){
        return getBoodschappenlijst().size();
    }

    public String toString(){
        return "Klant "+super.toString()+" met klantenkaartnr "+getKlantenkaartnr();
    }
}

