//Victor Welters 1 ITF 1 r0784055
package fact.it.supermarktproject.controller;
import fact.it.supermarktproject.model.Afdeling;
import fact.it.supermarktproject.model.Klant;
import fact.it.supermarktproject.model.Personeelslid;
import fact.it.supermarktproject.model.Supermarkt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class MainController {
    private ArrayList<Personeelslid> personeelsleden;
    private ArrayList<Klant> klanten;
    private ArrayList<Supermarkt> supermarkten;

    @RequestMapping("/12_overzichtWedde")
    public String overzichtWedde(Model model) {
        model.addAttribute("personeelsleden", personeelsleden);
        return "12_overzichtWedde";
    }



    @RequestMapping("/index")
    public String hoofdpagina() {
        return "index";
    }

    @RequestMapping("/1_registratieKlant")
    public String registreerKlant(Model model) {
        model.addAttribute("supermarkten", supermarkten);
        return "1_registratieKlant";
    }

    @RequestMapping("/2_welkomKlant")
    public String welkomKlant(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        String geboortejaarstr = request.getParameter("geboortejaar");
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarkt"));
        int geboortejaar = Integer.parseInt(geboortejaarstr);
        Supermarkt supermarkt = supermarkten.get(supermarktIndex);
        Klant klant = new Klant(voornaam, familienaam);
        klant.setGeboortejaar(geboortejaar);
        supermarkt.registreerKlant(klant);
        klanten.add(klant);
        model.addAttribute("klant", klant);
        return "2_welkomKlant";
    }

    @RequestMapping("/3_registratiePersoneel")
    public String registreerPersoneel() {
        return "3_registratiePersoneel";
    }

    @RequestMapping("/4_welkomPersoneel")
    public String welkomPersoneel(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        String dienstjaarstr = request.getParameter("dienst");
        String weddeString = request.getParameter("wedde");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dienstjaar = LocalDate.parse(dienstjaarstr,formatter);
        double wedde=Double.parseDouble(weddeString);
        Personeelslid personeel = new Personeelslid(voornaam,familienaam);
        personeel.setInDienstSinds(dienstjaar);
        personeel.setWedde(wedde);
        personeelsleden.add(personeel);
        model.addAttribute("personeel", personeel);
        return "4_welkomPersoneel";
    }

    @RequestMapping("/5_overzichtPersoneel")
    public String overzichtPersoneel(Model model) {
        model.addAttribute("personeelsleden", personeelsleden);
        return "5_overzichtPersoneel";
    }

    @RequestMapping("/6_overzichtKlant")
    public String overzichtKlant(Model model) {
        model.addAttribute("klanten", klanten);
        return "6_overzichtKlant";
    }

    @RequestMapping("/7_registratieSupermarkt")
    public String registreerSupermarkt() {
        return "7_registratieSupermarkt";
    }

    @RequestMapping("/8_overzichtSupermarkt")
    public String overzichtSupermarkt(Model model, HttpServletRequest request) {
            String supermarktNaam = request.getParameter("naam");
            if(supermarktNaam != null){
                Supermarkt supermarkt = new Supermarkt(supermarktNaam);
                supermarkten.add(supermarkt);
             }
        model.addAttribute("supermarkten", supermarkten);
        return "8_overzichtSupermarkt";
    }

    @RequestMapping("/9_registratieAfdeling")
    public String registreerAfdeling(Model model) {
        model.addAttribute("supermarkten", supermarkten);
        model.addAttribute("personeelsleden", personeelsleden);
        return "9_registratieAfdeling";
    }

    @RequestMapping("/10_overzichtAfdelingen")
    public String overzichtAfdelingen(Model model, HttpServletRequest request) {
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarkt"));
        String afdelingNaam = request.getParameter("naam");
        if(afdelingNaam==null){
            Supermarkt supermarkt = supermarkten.get(supermarktIndex);
            model.addAttribute("supermarkt", supermarkt);
            model.addAttribute("afdelingen", supermarkt.getAfdelingen());
            return "10_overzichtAfdelingen";
        }
        else{
        String afdelingFoto = request.getParameter("foto");
        String afdelingGekoeldString = request.getParameter("gekoeld");
        int verantwoordelijkeIndex = Integer.parseInt(request.getParameter("verantwoordelijke"));
            if (verantwoordelijkeIndex < 0 | supermarktIndex < 0) {
                //Geef error pagina
                if (supermarktIndex < 0) {
                    model.addAttribute("error", "Geen supermarkt gekozen");
                } else {
                    model.addAttribute("error", "Geen verantwoordelijke gekozen");
                }
                return "99_errorPagina";
            }
            else {
                //Maak nieuwe afdeling
                boolean afdelingGekoeld = false;
                if(afdelingGekoeldString!=null){
                   afdelingGekoeld = true;
                }
                Afdeling afdeling = new Afdeling(afdelingNaam);
                afdeling.setFoto(afdelingFoto);
                afdeling.setGekoeld(afdelingGekoeld);
                Personeelslid personeelslid = personeelsleden.get(verantwoordelijkeIndex);
                afdeling.setVerantwoordelijke(personeelslid);
                Supermarkt supermarkt = supermarkten.get(supermarktIndex);
                supermarkt.voegAfdelingToe(afdeling);
                model.addAttribute("supermarkt", supermarkt);
                model.addAttribute("afdelingen", supermarkt.getAfdelingen());
                return "10_overzichtAfdelingen";
            }
        }
    }

    @RequestMapping("/11_zoekOpNaam")
    public String zoekOpNaam(Model model, HttpServletRequest request) {
        String naam = request.getParameter("naam");
        String val = "99_errorPagina";
        model.addAttribute("error", "Geen afdeling met naam "+naam+" gevonden!");
        for(int i=0;i<supermarkten.size();i++) {
            Supermarkt supermarkt = supermarkten.get(i);
            if (supermarkt.zoekAfdelingOpNaam(naam) != null) {
                Afdeling afdeling = supermarkt.zoekAfdelingOpNaam(naam);
                model.addAttribute("afdeling", afdeling);
                val= "11_zoekOpNaam";
            }
        }
        return val;
    }

    //Maak aan in begin van programma
    @PostConstruct
    public void opvullen() {
        personeelsleden = vulPersoneelsledenLijst();
        klanten = vulKlantenLijst();
        supermarkten = vulSupermarktenLijst();
    }

    private ArrayList<Personeelslid> vulPersoneelsledenLijst() {
        ArrayList<Personeelslid> personeelsleden = new ArrayList<>();
        Personeelslid jitse = new Personeelslid("Jitse", "Verhaegen");
        Personeelslid bert = new Personeelslid("Bert", "De Meulenaere");
        Personeelslid sanne = new Personeelslid("Sanne", "Beckers");
        sanne.setWedde(1500.0);
        bert.setWedde(2500.0);
        jitse.setWedde(5000.0);
        personeelsleden.add(jitse);
        personeelsleden.add(bert);
        personeelsleden.add(sanne);
        return personeelsleden;
    }

    private ArrayList<Klant> vulKlantenLijst() {
        ArrayList<Klant> klanten = new ArrayList<>();
        Klant daan = new Klant("Daan", "Mertens");
        daan.setGeboortejaar(2001);
        Klant wim = new Klant("Wim", "Wijns");
        wim.setGeboortejaar(1956);
        Klant gert = new Klant("Gert", "Pauwels");
        gert.setGeboortejaar(1978);
        Klant vic = new Klant("Victor", "Welters");
        vic.setGeboortejaar(2001);
        klanten.add(daan);
        klanten.add(wim);
        klanten.add(gert);
        klanten.add(vic);
        klanten.get(0).voegToeAanBoodschappenlijst("melk");
        klanten.get(0).voegToeAanBoodschappenlijst("kaas");
        klanten.get(1).voegToeAanBoodschappenlijst("eieren");
        klanten.get(1).voegToeAanBoodschappenlijst("water");
        klanten.get(1).voegToeAanBoodschappenlijst("bloemkool");
        klanten.get(1).voegToeAanBoodschappenlijst("sla");
        klanten.get(2).voegToeAanBoodschappenlijst("tomaten");
        klanten.get(3).voegToeAanBoodschappenlijst("toiletpapier");
        klanten.get(3).voegToeAanBoodschappenlijst("mondmasker");
        return klanten;
    }

    private ArrayList<Supermarkt> vulSupermarktenLijst() {
        ArrayList<Supermarkt> supermarkten = new ArrayList<>();
        Supermarkt supermarkt1 = new Supermarkt("Colruyt Geel");
        Supermarkt supermarkt2 = new Supermarkt("Okay Meerhout");
        Supermarkt supermarkt3 = new Supermarkt("Colruyt Herentals");
        Afdeling afdeling1 = new Afdeling("Brood");
        Afdeling afdeling2 = new Afdeling("Groenten");
        afdeling2.setGekoeld(true);
        Afdeling afdeling3 = new Afdeling("Fruit");
        afdeling3.setGekoeld(true);
        Afdeling afdeling4 = new Afdeling("Vlees");
        afdeling4.setGekoeld(true);
        Afdeling afdeling5 = new Afdeling("Dranken");
        Afdeling afdeling6 = new Afdeling("Diepvries");
        afdeling1.setFoto("/img/brood.jpg");
        afdeling2.setFoto("/img/groenten.jpg");
        afdeling3.setFoto("/img/fruit.jpg");
        afdeling1.setVerantwoordelijke(personeelsleden.get(0));
        afdeling2.setVerantwoordelijke(personeelsleden.get(1));
        afdeling3.setVerantwoordelijke(personeelsleden.get(2));
        afdeling4.setVerantwoordelijke(personeelsleden.get(0));
        afdeling5.setVerantwoordelijke(personeelsleden.get(1));
        afdeling6.setVerantwoordelijke(personeelsleden.get(2));

        supermarkt1.voegAfdelingToe(afdeling1);
        supermarkt1.voegAfdelingToe(afdeling2);
        supermarkt2.voegAfdelingToe(afdeling3);
        supermarkt2.voegAfdelingToe(afdeling4);
        supermarkt3.voegAfdelingToe(afdeling5);
        supermarkt3.voegAfdelingToe(afdeling6);
        supermarkten.add(supermarkt1);
        supermarkten.add(supermarkt2);
        supermarkten.add(supermarkt3);
        return supermarkten;
    }
}