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
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GetDateTime {


    //crea una stringa che contiene le date delle ripetizoni, date separate dalla lettera d
    //input stringa settimana ha la letetra r davanti mentre oraraio non ha la 'o'
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static final String getDateRipetizioni(String settimana, String orario) {

        String dateRipetizioni = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime orarioSveglia = LocalTime.parse(orario, formatter);

        //data e orario attuali
        LocalTime oraAttuale = LocalTime.parse(LocalDateTime.now().format(formatter));
        LocalDate oggi = LocalDate.now(); //giorno ordierno


        //se non ho ripetizioni scelgo tra oggi e domani in base all'orario
        if (settimana.equals("r0000000")) {
            //Se l'orario impostato non è già passato metto oggi altrimenti domani
            if (orarioSveglia.isAfter(oraAttuale)) {
                dateRipetizioni = "d" + String.valueOf(oggi);
            } else {
                dateRipetizioni = "d" + String.valueOf(oggi.plusDays(1));
            }

        }


        //se ho ripetizoni vado a vedere il giorno della settimana
        else {
            DayOfWeek dayOfWeek = oggi.getDayOfWeek();
            //Log.d(TAG, "DAY " + dayOfWeek);
            dateRipetizioni = "";
            for (int i = 0; i < (settimana.length()); i++) {
                switch (settimana.charAt(i)) {
                    case '1':
                        if (dayOfWeek.equals(DayOfWeek.MONDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.MONDAY));
                        }
                        break;
                    case '2':
                        if (dayOfWeek.equals(DayOfWeek.TUESDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.TUESDAY));
                        }
                    case '3':
                        if (dayOfWeek.equals(DayOfWeek.WEDNESDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.WEDNESDAY));
                        }
                        break;
                    case '4':
                        if (dayOfWeek.equals(DayOfWeek.THURSDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.THURSDAY));
                        }
                        break;
                    case '5':
                        if (dayOfWeek.equals(DayOfWeek.FRIDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.FRIDAY));
                        }
                        break;
                    case '6':
                        if (dayOfWeek.equals(DayOfWeek.SATURDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.SATURDAY));
                        }
                        break;
                    case '7':
                        if (dayOfWeek.equals(DayOfWeek.SUNDAY) && orarioSveglia.isAfter(oraAttuale)) {
                            dateRipetizioni += "d" + oggi;
                        } else {
                            dateRipetizioni += "d" + String.valueOf(getNextDay(oggi, DayOfWeek.SUNDAY));
                        }
                        break;
                }
            }

        }


        return getDataPiuVicina(dateRipetizioni);
    }




    //prende in input un elenco di date e restituisce la più vicina
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static final String getDataPiuVicina(String elencoDate) {
        String dataPiuVicina = "d";
        LocalDate bestDate = null;
        //elencoDate è un una stringa del tipo AAAA:MM:GGAAAA:MM:GG...

        if (elencoDate == null || elencoDate.isEmpty()) {
            Log.e(TAG, "Elenco date è nullo o vuoto");
            return "Data non disponibile1"; // o un valore di default appropriato
        }

        // Verifica che la lunghezza di elencoDate sia sufficiente
        if (elencoDate.length() < 11) {
            Log.e(TAG, "Elenco date non contiene date valide");
            return "Data non disponibile2"; // o un valore di default appropriato
        }

        for (int i = 0; i < elencoDate.length(); i += 11) {

            dataPiuVicina = elencoDate.substring(i, i + 11);
            //Log.d(TAG, "datapiùvicina" + dataPiuVicina);

            LocalDate data = LocalDate.parse(dataPiuVicina.substring(1));

            if (bestDate == null || bestDate.isAfter(data)) {
                bestDate = data;
            }

        }


        if (bestDate == null) {
            Log.e(TAG, "Nessuna data valida trovata in elencoDate");
            return "Data non disponibile3"; // o un valore di default appropriato
        }

        dataPiuVicina = "d" + bestDate.toString();

        //Log.d(TAG, "datapiùvicina vincitrice " + dataPiuVicina);

        return dataPiuVicina;
    }


    //dati in input il giorno di oggi il giorno della settimana per cui voglio conoscere la data mi restituisce la data
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getNextDay(LocalDate oggi, DayOfWeek dayOfWeek) {
        // Ottieni il giorno della settimana della data fornita
        DayOfWeek currentDayOfWeek = oggi.getDayOfWeek();

        // Calcola i giorni rimanenti per arrivare al prossimo giorno desiderato
        int daysUntilNextTarget = dayOfWeek.getValue() - currentDayOfWeek.getValue();

        // Se il giorno corrente è uguale o dopo il giorno desiderato, aggiungi 7 giorni
        if (daysUntilNextTarget <= 0) {
            Log.d(TAG, "sommo 7");
            daysUntilNextTarget += 7;
        }

        // Aggiungi i giorni calcolati alla data iniziale per ottenere il prossimo giorno desiderato
        return oggi.plusDays(daysUntilNextTarget);
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String giornoPiuVicino(String giorniSettimana) {


        Map<String, DayOfWeek> nomeGiornoToDayOfWeek = new HashMap<>();


            nomeGiornoToDayOfWeek.put("lunedì", DayOfWeek.MONDAY);
            nomeGiornoToDayOfWeek.put("martedì", DayOfWeek.TUESDAY);
            nomeGiornoToDayOfWeek.put("mercoledì", DayOfWeek.WEDNESDAY);
            nomeGiornoToDayOfWeek.put("giovedì", DayOfWeek.THURSDAY);
            nomeGiornoToDayOfWeek.put("venerdì", DayOfWeek.FRIDAY);
            nomeGiornoToDayOfWeek.put("sabato", DayOfWeek.SATURDAY);
            nomeGiornoToDayOfWeek.put("domenica", DayOfWeek.SUNDAY);



        // Divide la stringa dei giorni settimana in un array di nomi di giorni
        String[] nomiGiorni = giorniSettimana.split("\\s+");

        // Ottieni la data odierna
        LocalDate oggi = LocalDate.now();

        // Inizializza le variabili per il giorno più vicino e la sua differenza minima
        DayOfWeek giornoPiuVicino = null;
        int differenzaMinima = Integer.MAX_VALUE;

        // Scansiona tutti i nomi dei giorni
        for (String nomeGiorno : nomiGiorni) {

            // Cerca il nome del giorno nel mapping (ignorando il case)
            String nomeGiornoLowerCase = nomeGiorno.toLowerCase();
            if (nomeGiornoToDayOfWeek.containsKey(nomeGiornoLowerCase)) {
                DayOfWeek giorno = nomeGiornoToDayOfWeek.get(nomeGiornoLowerCase);

                // Calcola la differenza in giorni tra oggi e il prossimo giorno desiderato
                int differenzaGiorni = giorno.compareTo(oggi.getDayOfWeek());

                // Se la differenza in giorni è negativa, aggiungi 7 per ottenere la differenza positiva
                if (differenzaGiorni < 0) {
                    differenzaGiorni += 7;
                }

                // Verifica se è il giorno più vicino fino ad ora
                if (differenzaGiorni < differenzaMinima) {
                    differenzaMinima = differenzaGiorni;
                    giornoPiuVicino = giorno;
                    Log.d(TAG, "giornoPiuVicini che dice null è " + giornoPiuVicino);
                }
            }
        }

        Log.d(TAG, "returna " + giornoPiuVicino.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
        // Restituisci il nome completo del giorno più vicino
        return prossimoGiornoSettimana(giornoPiuVicino.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
    }


        @RequiresApi(api = Build.VERSION_CODES.O)
        public static String prossimoGiornoSettimana(String giornoSettimana) {

            Map<String, DayOfWeek> nomeGiornoToDayOfWeek = new HashMap<>();


            nomeGiornoToDayOfWeek.put("lunedì", DayOfWeek.MONDAY);
            nomeGiornoToDayOfWeek.put("martedì", DayOfWeek.TUESDAY);
            nomeGiornoToDayOfWeek.put("mercoledì", DayOfWeek.WEDNESDAY);
            nomeGiornoToDayOfWeek.put("giovedì", DayOfWeek.THURSDAY);
            nomeGiornoToDayOfWeek.put("venerdì", DayOfWeek.FRIDAY);
            nomeGiornoToDayOfWeek.put("sabato", DayOfWeek.SATURDAY);
            nomeGiornoToDayOfWeek.put("domenica", DayOfWeek.SUNDAY);


            // Ottieni la data odierna
            LocalDate oggi = LocalDate.now();

            // Converte il nome del giorno in DayOfWeek enum (ignorando il case)
            DayOfWeek giornoDesiderato = nomeGiornoToDayOfWeek.get(giornoSettimana.toLowerCase());

            // Usa TemporalAdjusters per trovare il prossimo giorno della settimana
            LocalDate prossimoGiorno = oggi.with(TemporalAdjusters.next(giornoDesiderato));

            // Formatta la data nel formato desiderato (AAAA-MM-GG)
            return "d" + prossimoGiorno.toString();
        }


    }