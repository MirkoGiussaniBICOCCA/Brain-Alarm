package it.unimib.brain_alarm.Sveglia;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


@Entity
public class Sveglie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String orario;
    private String etichetta;

    public Sveglie() {}

    public Sveglie(String orario, String etichetta) {
        this.orario = orario;
        this.etichetta = etichetta;
    }

    public Sveglie(String orario) {
        this(orario, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String author) {
        this.orario = orario;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public void setEtichetta(String author) {
        this.etichetta = etichetta;
    }


    //parcelable implementa tutta la logica per leggere e scrivere
    @Override
    public String toString() {
        return "Sveglia{" +
                "orario='" + orario +
                ", etichetta='" + etichetta + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        it.unimib.brain_alarm.Sveglia.Sveglie sveglia = (it.unimib.brain_alarm.Sveglia.Sveglie) o;
        return Objects.equals(orario, sveglia.orario) && Objects.equals(etichetta, sveglia.etichetta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orario, etichetta);
    }
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

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.orario = source.readString();
        this.etichetta = source.readString();

    }

    protected Sveglie(Parcel in) {
        this.id = in.readLong();
        this.orario = in.readString();
        this.etichetta = in.readString();

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