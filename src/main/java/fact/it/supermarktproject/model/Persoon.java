package fact.it.supermarktproject.model;
//Victor Welters 1 ITF 1 r0784055

public class Persoon {
    private String voornaam, familienaam;
    private int geboortejaar;

    public Persoon() {

    }

    public Persoon(String voornaam,String familienaam){
        this.voornaam=voornaam;
        this.familienaam=familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public void setFamilienaam(String famillienaam) {
        this.familienaam = famillienaam;
    }

    public int getGeboortejaar() {
        return geboortejaar;
    }

    public void setGeboortejaar(int geboortejaar) {
        this.geboortejaar = geboortejaar;
    }

    public String toString(){
        return getFamilienaam().toUpperCase()+" "+getVoornaam();
    }
}
