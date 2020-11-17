package fact.it.supermarktproject.model;
//Victor Welters 1 ITF 1 r0784055

import java.time.LocalDate;

public class Personeelslid extends Persoon{
    private LocalDate inDienstSinds;
    private double wedde;

    public Personeelslid(String voornaam,String familienaam){
        super(voornaam, familienaam);
        inDienstSinds=java.time.LocalDate.now();
    }

    public LocalDate getInDienstSinds() {
        return inDienstSinds;
    }

    public void setInDienstSinds(LocalDate inDienstSinds) {
        this.inDienstSinds = inDienstSinds;
    }

    public double getWedde() {
        return wedde;
    }

    public void setWedde(double wedde) {
        this.wedde = wedde;
    }

    public String toString(){
        int day =getInDienstSinds().getDayOfMonth();
        int year=getInDienstSinds().getYear();
        //Month => string lowercase
        String month=getInDienstSinds().getMonth().toString().substring(0,3).toLowerCase();
        //Month lowercase => Capitalise
        month=month.substring(0, 1).toUpperCase() + month.substring(1);
        return "Personeelslid "+super.toString()+" is in dienst sinds "+day+" "+month+" "+year;
    }
}
