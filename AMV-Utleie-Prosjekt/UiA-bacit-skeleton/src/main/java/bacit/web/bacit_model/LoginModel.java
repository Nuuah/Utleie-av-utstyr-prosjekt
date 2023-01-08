package bacit.web.bacit_model;

public class LoginModel {
    private String telefonnummer;
    private String passord;
public LoginModel(String telefonnummer, String passord){
    this.telefonnummer = telefonnummer;
    this.passord = passord;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}


