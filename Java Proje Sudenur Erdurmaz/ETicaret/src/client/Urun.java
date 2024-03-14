package client;

public class Urun {
    private int id;
    private String tur;
    private String ad;
    private int miktar;
    private double fiyat;
    private String renk;

    public Urun(int id, String tur, String ad, int miktar, double fiyat, String renk) {
        this.id = id;
        this.tur = tur;
        this.ad = ad;
        this.miktar = miktar;
        this.fiyat = fiyat;
        this.renk = renk;
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public String getTur() {
        return tur;
    }

    public String getAd() {
        return ad;
    }

    public int getMiktar() {
        return miktar;
    }

    public double getFiyat() {
        return fiyat;
    }

    public String getRenk() {
        return renk;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               ", Tür: " + tur +
               ", Ad: " + ad +
               ", Miktar: " + miktar +
               ", Fiyat: " + fiyat +
               ", Renk: " + renk;
    }
}
