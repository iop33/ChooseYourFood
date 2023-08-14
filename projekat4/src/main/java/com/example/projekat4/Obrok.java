package com.example.projekat4;

public class Obrok {

    String dan;
    String hrana;
    int brojPorudzbina=0;

    public Obrok(String dan, String hrana) {
        this.dan = dan;
        this.hrana = hrana;
    }

    public int getBrojPorudzbina() {
        return brojPorudzbina;
    }

    public void setBrojPorudzbina(int brojPorudzbina) {
        this.brojPorudzbina = brojPorudzbina;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getHrana() {
        return hrana;
    }

    public void setHrana(String hrana) {
        this.hrana = hrana;
    }
}
