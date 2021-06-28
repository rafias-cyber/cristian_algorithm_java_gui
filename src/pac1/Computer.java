package pac1;

public class Computer {
    public Integer getNumer_komp() {
        return numer_komp;
    }

    public void setNumer_komp(Integer numer_komp) {
        Computer.numer_komp = numer_komp;
    }

    public String getAdres_ip() {
        return adres_ip;
    }

    public void setAdres_ip(String adres_ip) {
        this.adres_ip = adres_ip;
    }

    public Integer getCzas() {
        return czas;
    }

    public void setCzas(Integer czas) {
        this.czas = czas;
    }

    public Integer getDrift() {
        return drift;
    }

    public void setDrift(Integer drift) {
        this.drift = drift;
    }

    private static Integer numer_komp;
    private String adres_ip;
    private Integer czas;
    private Integer drift;

    public Computer( String adres_ip, Integer czas, Integer drift) {
        numer_komp++;
        this.adres_ip = adres_ip;
        this.czas = czas;
        this.drift = drift;
    }


}
