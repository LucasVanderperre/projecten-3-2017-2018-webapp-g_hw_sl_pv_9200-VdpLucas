package com.example.gilles.g_hw_sl_pv_9200.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tomde on 11/13/2017.
 */

public class Kost implements Parcelable {
    public String naam, aanmakerId, beschrijving, categorie;
    public String datum;
    public String[] betrokkenPersonen;
    public Boolean uitzonderlijk, goedgekeurd;
    public float kost;
    private String _id;

    /**
     *
     * @param naam
     * @param aanmakerId
     * @param betrokkenPersonen
     * @param datum
     * @param kost
     * @param beschrijving
     * @param categorie
     * @param uitzonderlijk

     */
    public Kost(String naam, String aanmakerId,
                String[] betrokkenPersonen, String datum, float kost,
                String beschrijving, String categorie, Boolean uitzonderlijk){
        this.naam = naam;
        this.aanmakerId = aanmakerId;
        this.betrokkenPersonen = betrokkenPersonen;
        this.datum = datum;
        this.kost = kost;
        this.beschrijving = beschrijving;
        this.categorie = categorie;
        this._id = _id;
        this.uitzonderlijk = uitzonderlijk;
        this.goedgekeurd = false;
    }

    public static final Creator<Kost> CREATOR = new Creator<Kost>() {
        @Override
        public Kost createFromParcel(Parcel in) {
            return new Kost(in);
        }

        @Override
        public Kost[] newArray(int size) {
            return new Kost[size];
        }
    };

    public String getNaam() {
        return naam;
    }
    public String getAanmakerId(){
        return aanmakerId;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getKost() {
        return kost;
    }

    public void setKost(float kost) {
        this.kost = kost;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String mededeling) {
        this.beschrijving = mededeling;
    }

    public String toString(){
        return String.format(this.naam+"\t"+this.kost+"€");
    }
    public String getId() { return this._id;}
    public String getDatum() { return this.datum;}


    public Boolean getUitzonderlijk(){return uitzonderlijk;}

    public Boolean getGoedgekeurd() {return goedgekeurd;}

    public void keurGoed(){
        this.goedgekeurd = true;
    }

    public String stringKost(){

            return String.format(this.naam+"\t"+this.kost+"€"+"\t"+this.uitzonderlijk+"\t"+this.aanmakerId+"\t"+this.datum);

    }

    public String getAanmakerNaam(){
        return this.betrokkenPersonen[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(naam);
        parcel.writeString(_id);
        parcel.writeString(aanmakerId);
        parcel.writeString(beschrijving);
        parcel.writeString(categorie);
        parcel.writeStringArray(betrokkenPersonen);
        parcel.writeInt(uitzonderlijk==null?0:uitzonderlijk?1:0);
        parcel.writeInt(goedgekeurd==null?0:goedgekeurd?1:0);
        parcel.writeFloat(kost);
        }


    protected Kost(Parcel in) {
        naam = in.readString();
        aanmakerId = in.readString();
        beschrijving = in.readString();
        categorie = in.readString();
        datum = in.readString();
        betrokkenPersonen = in.createStringArray();
        byte tmpUitzonderlijk = in.readByte();
        uitzonderlijk = tmpUitzonderlijk == 1;
        byte tmpGoedgekeurd = in.readByte();
        goedgekeurd = tmpGoedgekeurd == 1;
        kost = in.readFloat();
        _id = in.readString();
    }

}
