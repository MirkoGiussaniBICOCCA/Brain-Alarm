package it.unimib.brain_alarm.Sveglia;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import it.unimib.brain_alarm.AggiungiActivity;


@Entity
public class Sveglie implements Serializable {

    private static final String TAG = Sveglie.class.getSimpleName();
    private String key;

    private String orario;

    private String data;
    private String etichetta;

    private String ripetizioni;

    private String ripetizioniNum;

    private String suono;
    private String vibrazione;

    private String posticipa;

    private String modalita;

    private String missioni;

    private Set<String> elementi;



    public Sveglie(Set<String> elementi) {
            this.elementi = elementi;
            //Log.d(TAG, "elementi " + elementi);
    }


    public String getKey() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 's')
                    key = e.toString();
        }
        return key;
    }

    public void setKey(String id) {
        this.key = key;
    }

    public String getOrario() {

       for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'o')
                    orario = e.toString().substring(1);
        }
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getData() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'd')
                    data = e.toString().substring(1);
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEtichetta() {
        for (String e : elementi) {
            e.toString();

            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'e')
                    etichetta = e.toString().substring(1);
        }
        return etichetta;
    }

    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    public String getRipetizioniNum() {
        for (String e : elementi) {
            e.toString();
            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'r')
                    ripetizioniNum = e.toString().substring(1);
        }

        return ripetizioniNum;
    }

    public void setRipetizioniNum(String ripetizioniNum) {
        this.ripetizioniNum = ripetizioniNum;
    }


    public String getRipetizioni() {
        String ripetizioni1 = "";
        for (String e : elementi) {
            e.toString();
            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'r')
                    ripetizioni1 = e.toString().substring(1);
        }

        ripetizioni = "";

        for (int i=0; i<(ripetizioni1.length()); i++) {
            if (ripetizioni1.charAt(i) == '1')
                ripetizioni += "Lunedì ";
            if (ripetizioni1.charAt(i) == '2')
                ripetizioni += "Martedì ";
            if (ripetizioni1.charAt(i) == '3')
                ripetizioni += "Mercoledì ";
            if (ripetizioni1.charAt(i) == '4')
                ripetizioni += "Giovedì ";
            if (ripetizioni1.charAt(i) == '5')
                ripetizioni += "Venerdì ";
            if (ripetizioni1.charAt(i) == '6')
                ripetizioni += "Sabato ";
            if (ripetizioni1.charAt(i) == '7')
                ripetizioni += "Domenica";
            //Log.d(TAG, "ripetizioni" + ripetizioni);
        }

        return ripetizioni;
    }

    public void setRipetizioni(String ripetizioni) {
        this.etichetta = etichetta;
    }

    public String getSuono() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if (e.toString().equals("Classica") || e.toString().equals("Radar") || e.toString().equals("Arpeggio"))
                    suono = e.toString();
        }
        return suono;
    }

    public void setSuono(String suono) {
        this.suono = suono;
    }

    public String getVibrazione() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if (e.toString().equals("v0"))
                    vibrazione = "no vibrazione";
                else if (e.toString().equals("v1"))
                    vibrazione = "vibrazione";
        }
        return vibrazione;
    }

    public void setVibrazione(String vibrazione) {
        this.vibrazione = vibrazione;
    }

    public String getPosticipa() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if (e.toString().equals("true"))
                    posticipa = "true";
                else if (e.toString().equals("false"))
                    posticipa = "false";
        }
        return posticipa;
    }

    public void setPosticipae(String posticipa) {
        this.posticipa = posticipa;
    }


    public String getModalita() {

        for (String e : elementi) {
            e.toString();
            //Log.d(TAG, "prima parola " + e.toString());

            if (!e.toString().isEmpty())
                if (e.toString().equals("tc") || e.toString().equals("ts"))
                    modalita = e.toString();
        }
        return modalita;
    }

    public void setModalita(String modalita) {
        this.modalita = modalita;
    }

    public String getMissioni() {

        for (String e : elementi) {
            if (!e.toString().isEmpty())
                if ((e.toString()).charAt(0) == 'm')
                    missioni = e.toString().substring(1);
        }
        return missioni;
    }

    public void setMissioni(String missioni) {
        this.missioni = missioni;
    }


    //parcelable implementa tutta la logica per leggere e scrivere
    @Override
    public String toString() {
        return "Sveglia{" +
                "orario='" + orario +
                ", etichetta='" + etichetta + '\'' +
                ", ripetizioni='" + ripetizioni + '\'' +
                ", ripetizioniNum='" + ripetizioniNum + '\'' +
                ", suono='" + suono + '\'' +
                ", vibrazione='" + vibrazione + '\'' +
                ", posticipa='" + posticipa + '\'' +
                ", modalita='" + modalita + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        it.unimib.brain_alarm.Sveglia.Sveglie sveglia = (it.unimib.brain_alarm.Sveglia.Sveglie) o;
        return Objects.equals(orario, sveglia.orario) && Objects.equals(etichetta, sveglia.etichetta) && Objects.equals(ripetizioni, sveglia.ripetizioni)
                && Objects.equals(ripetizioniNum, sveglia.ripetizioniNum) && Objects.equals(suono, sveglia.suono) && Objects.equals(vibrazione, sveglia.vibrazione)
                && Objects.equals(posticipa, sveglia.posticipa) && Objects.equals(modalita, sveglia.modalita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orario, etichetta, ripetizioni, ripetizioniNum, suono, vibrazione, posticipa, modalita);
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.orario);
        dest.writeString(this.etichetta);

    }


     */
    public void readFromParcel(Parcel source) {
        this.key = source.readString();
        this.orario = source.readString();
        this.etichetta = source.readString();
        this.ripetizioni = source.readString();
        this.ripetizioniNum = source.readString();
        this.suono = source.readString();
        this.vibrazione = source.readString();
        this.posticipa = source.readString();
        this.modalita = source.readString();
    }

    protected Sveglie(Parcel in) {
        this.key = in.readString();
        this.orario = in.readString();
        this.etichetta = in.readString();
        this.ripetizioni = in.readString();
        this.ripetizioniNum = in.readString();
        this.suono = in.readString();
        this.vibrazione = in.readString();
        this.posticipa = in.readString();
        this.modalita = in.readString();
    }

    public static final Parcelable.Creator<it.unimib.brain_alarm.Sveglia.Sveglie> CREATOR = new Parcelable.Creator<it.unimib.brain_alarm.Sveglia.Sveglie>() {
        @Override
        public it.unimib.brain_alarm.Sveglia.Sveglie createFromParcel(Parcel source) {
            return new it.unimib.brain_alarm.Sveglia.Sveglie(source);
        }

        @Override
        public it.unimib.brain_alarm.Sveglia.Sveglie[] newArray(int size) {
            return new it.unimib.brain_alarm.Sveglia.Sveglie[size];
        }
    };
}