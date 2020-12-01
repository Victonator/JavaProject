package fact.it.supermarktproject.model;
//Victor Welters 1 ITF 1 r0784055

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return "Personeelslid "+super.toString()+" is in dienst sinds "+getInDienstSinds().format(dtf);
    }
}
