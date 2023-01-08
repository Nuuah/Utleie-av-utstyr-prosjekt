package bacit.web.bacit_utility;

import javax.servlet.http.HttpSession;

/*
    Dette er noen notater om hva som skal være med. Det er bare de første tingene
    jeg klarte å komme på. Så godt mulig at det er flere funksjoner som skal være
    med, men det kan man eventuelt legge til senere.
 */
public class sessionUtility {

    public sessionUtility(HttpSession session) {
        // Når man lager et nytt sessionUtilityobjekt kan man starte med å gjøre
        // hvisIkkeLoggetInn, for det skal skje hver gang.
    }

    public void hvisIkkeLoggetInn() {
        // Redirect tilbake til login
    }

    public int getAnsatt_ID() {
        // Returner ansatt_id
        return 1;
    }

    public void setAnsatt_ID(int ansatt_id) {
        // Kode for å lagre den i session kan legges her.
    }

    // Get og set for telefon, admin, union også
}
