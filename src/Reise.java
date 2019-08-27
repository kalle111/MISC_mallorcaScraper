public class Reise {
    public String ReiseZiel;
    public String KurzBeschreibung;
    public String Preis;
    public String EinstellDatum;

    public Reise(String rz, String kb, String pr, String dt) {
        this.ReiseZiel = rz;
        this.KurzBeschreibung = kb;
        this.Preis = pr;
        this.EinstellDatum = dt;
    }

    public String ReisetoString(){
        return "Reiseziel: " + this.ReiseZiel + ", Preis: " + this.Preis + ", eingestellt am: " + this.EinstellDatum;
    }
}
