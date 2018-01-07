package com.example.seilab.bp007;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by seilab on 12/20/17.
 */

public class Objekat {
    public String getKolone() {
        return kolone;
    }

    public Objekat() {
    }

    public void setKolone(String kolone) {
        this.kolone = kolone;
    }

    public String getVrijednosti() {
        return vrijednosti;
    }

    public void setVrijednosti(String vrijednosti) {
        this.vrijednosti = vrijednosti;
    }

    public Objekat(String kolone, String vrijednosti) {
        this.kolone = kolone;
        this.vrijednosti = vrijednosti;
    }

    String kolone;
    String vrijednosti;

}
