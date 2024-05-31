package it.unimib.brain_alarm.util;

import static android.content.ContentValues.TAG;


import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTime {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static final String getDateRipetizioni(String settimana, String orario) {

        String dateRipetizioni = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime orarioSveglia = LocalTime.parse(orario, formatter);

        //data e orario attuali
        LocalTime oraAttuale = LocalTime.parse(LocalDateTime.now().format(formatter));
        LocalDate oggi = LocalDate.now(); //giorno ordierno


        //TODO sistemare data riattivazione sveglia
        if (settimana.equals("r0000000")) {
            //Se l'orario impostato non è già passato metto oggi altrimenti domani
            if (orarioSveglia.isAfter(oraAttuale)) {
                dateRipetizioni = "d" + String.valueOf(oggi); }
            else {
                dateRipetizioni = "d" + String.valueOf(oggi.plusDays(1)); }
            Log.d(TAG, "DAY " + dateRipetizioni);
        }
        else {
            //TODO questo codice se oggi è venerdì (anche se l'oraio non è ancora passato) non mi restituisce la data di oggi ma quella di venerdì prossimo

            DayOfWeek dayOfWeek = oggi.getDayOfWeek();
            //Log.d(TAG, "DAY " + dayOfWeek);
            dateRipetizioni = "";
            for (int i=0; i<(settimana.length()); i++) {
                switch (settimana.charAt(i)) {
                    case '1':
                        if (dayOfWeek.equals(DayOfWeek.MONDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.MONDAY));
                        }
                    break;
                    case '2':
                        if (dayOfWeek.equals(DayOfWeek.TUESDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.TUESDAY));
                        }
                    case '3':
                        if (dayOfWeek.equals(DayOfWeek.WEDNESDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.WEDNESDAY));
                        }
                    break;
                    case '4':
                        if (dayOfWeek.equals(DayOfWeek.THURSDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.THURSDAY));
                        }
                    break;
                    case '5':
                        if (dayOfWeek.equals(DayOfWeek.FRIDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.FRIDAY));
                        }
                    break;
                    case '6':
                        if (dayOfWeek.equals(DayOfWeek.SATURDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.SATURDAY));
                        }
                    break;
                    case '7':
                        if (dayOfWeek.equals(DayOfWeek.SUNDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        }
                        else {
                            dateRipetizioni  += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.SUNDAY));
                        }
                    break;
                }
            }

        }


        return getDataPiuVicina(dateRipetizioni);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static final String getDataPiuVicina(String elencoDate) {
        String dataPiuVicina = "d";
        LocalDate bestDate = null;
        //elencoDate è un una stringa del tipo AAAA:MM:GGAAAA:MM:GG...


        for (int i=0; i<elencoDate.length(); i+=11) {

            dataPiuVicina = elencoDate.substring(i,i+11);
            //Log.d(TAG, "datapiùvicina" + dataPiuVicina);

            LocalDate data = LocalDate.parse(dataPiuVicina.substring(1));

            if (bestDate == null || bestDate.isAfter(data)){
                bestDate = data;
            }
        }

        dataPiuVicina = "d" + bestDate.toString();

        //Log.d(TAG, "datapiùvicina vincitrice " + dataPiuVicina);
        return dataPiuVicina;
    }


    //TODO sistemare descrizioni metodi di questa pagina
    //dati in input il giorno di oggi e quale giorno della settimana
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getNextDay(LocalDate oggi, DayOfWeek dayOfWeek) {
        // Ottieni il giorno della settimana della data fornita
        DayOfWeek currentDayOfWeek = oggi.getDayOfWeek();

        // Calcola i giorni rimanenti per arrivare al prossimo giorno desiderato
        int daysUntilNextTarget = dayOfWeek.getValue() - currentDayOfWeek.getValue();

        // Se il giorno corrente è uguale o dopo il giorno desiderato, aggiungi 7 giorni
        if (daysUntilNextTarget <= 0) {
            daysUntilNextTarget += 7;
        }

        // Aggiungi i giorni calcolati alla data iniziale per ottenere il prossimo giorno desiderato
        return oggi.plusDays(daysUntilNextTarget);
    }

}