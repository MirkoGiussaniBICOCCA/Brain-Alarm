package it.unimib.brain_alarm.adapter;



import static android.content.ContentValues.TAG;
import static android.text.TextUtils.substring;
import static com.google.android.material.internal.ContextUtils.getActivity;

import static it.unimib.brain_alarm.ui.HomeFragment.SVEGLIE_KEY;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.Sveglia.Sveglie;
import it.unimib.brain_alarm.util.DateTimeUtil;
import it.unimib.brain_alarm.util.GetDateTime;


public class SveglieAdapter extends
        RecyclerView.Adapter<SveglieAdapter.ViewHolder> {


    public interface OnItemClickListenerS  {
        void onSveglieItemClick(Sveglie sveglie);
        void onDeleteButtonPressed(int position);

    }

    private static final String TAG = SveglieAdapter.class.getSimpleName();
    private Context context;
    private final Application application;

    private final List<Sveglie> sveglieList;
    private final boolean disattiva;

    private final String keyDaDisattivare;

    private final OnItemClickListenerS onItemClickListenerS;

    public SveglieAdapter(Context context, Application application, List<Sveglie> sveglieList, Boolean disattiva, String keyDaDisattivare, SveglieAdapter.OnItemClickListenerS onItemClickListener) {
        this.context = context;
        this.application = application;
        this.sveglieList = sveglieList;
        this.disattiva = disattiva;
        this.keyDaDisattivare = keyDaDisattivare;
        this.onItemClickListenerS = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sveglie_list_item, viewGroup, false);

        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(sveglieList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (sveglieList != null) {
            return sveglieList.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewOrario;
        private final TextView textViewEtichetta;
        private final TextView textViewRipetizioni;


        private final ImageView imageSfida;

        private final Switch switchAttiva;

        public ViewHolder(View view) {
            super(view);
            textViewOrario = (TextView) view.findViewById(R.id.textview_orario);
            textViewEtichetta = (TextView) view.findViewById(R.id.textview_etichetta);
            textViewRipetizioni = (TextView) view.findViewById(R.id.textview_ripetizioni);
            ImageButton buttonDelete = itemView.findViewById(R.id.buttonEliminaSveglia);
            switchAttiva = itemView.findViewById(R.id.switchAttiva);
            imageSfida = itemView.findViewById(R.id.imageSfida);
            itemView.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
            switchAttiva.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(Sveglie sveglie) {

            textViewOrario.setText(sveglie.getOrario().substring(0,5));

            if (!sveglie.getEtichetta().equals(""))
                textViewEtichetta.setText(sveglie.getEtichetta());
            else
                textViewEtichetta.setVisibility(View.GONE);

            if (!sveglie.getRipetizioni().equals(""))
                textViewRipetizioni.setText(sveglie.getRipetizioni());
            else
                textViewRipetizioni.setVisibility(View.GONE);


            if ((sveglieList.get(getAdapterPosition())).getModalita().equals("tc")) {
                imageSfida.setImageDrawable(
                        AppCompatResources.getDrawable(application,
                                R.drawable.sveglia));
                imageSfida.setColorFilter(
                        ContextCompat.getColor(
                                imageSfida.getContext(),
                                R.color.white)
                );
            } else if ((sveglieList.get(getAdapterPosition())).getModalita().equals("ts")) {
                imageSfida.setImageDrawable(
                        AppCompatResources.getDrawable(application,
                                R.drawable.puzzle));
                imageSfida.setColorFilter(
                        ContextCompat.getColor(
                                imageSfida.getContext(),
                                R.color.white)
                );
            }

            SharedPreferences sharedPref = context.getSharedPreferences("information_shared", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if (disattiva){
                Log.d(TAG, "disattivare o cambiare data");
                if(!keyDaDisattivare.equals("")) {
                    Log.d(TAG,  "chiave " + keyDaDisattivare);

                    Set<String> sveglieSet = sharedPref.getStringSet(keyDaDisattivare, new HashSet<>());

                    Set<String> newSet = new HashSet<>(sveglieSet);

                    Log.d(TAG, "inizio sveglieSet " + sveglieSet );
                    Log.d(TAG, "inizio newSet " + newSet);


                    if (newSet.contains("attiva")) {
                        if (newSet.contains("r0000000")) {

                            Log.d(TAG, "disattivazione senza ripetizioni");


                            newSet.remove("attiva");
                            newSet.add("non attiva");

                            editor.putStringSet(keyDaDisattivare, newSet);

                            // Rimuove la chiave dalla lista delle attive
                            Set<String> attive = sharedPref.getStringSet("sveglieAttive", new HashSet<>());

                            attive.remove(keyDaDisattivare);

                            editor.putStringSet("sveglieAttive", attive);

                            editor.apply();

                            switchAttiva.setChecked(false);

                        } else { //la sveglia ha ripetizioni devo aggiornare la data
                            Log.d(TAG, "disattivazione con ripetizioni" + newSet);
                            //faccio passare sveglieSet e trovo la data

                            String dataSveglia = "";
                            String ripetizioni = "";
                            for (String e : sveglieSet) {
                                Log.d(TAG, "elemento " + e);
                                if (e.startsWith("d")) {
                                    dataSveglia = e;
                                }
                                else if (e.startsWith("r")) {
                                    ripetizioni = e.toString().substring(1);
                                }
                            }
                            Log.d(TAG, "ripetizioni " + ripetizioni + "dataSveglia " + dataSveglia);
                            newSet.remove(dataSveglia);
                            Log.d(TAG, "1dentro disattivazione con ripetizioni" + newSet);
                            newSet.add(GetDateTime.giornoPiuVicino(ripetizioni));
                            Log.d(TAG, "2dentro disattivazione con ripetizioni" + newSet);


                            editor.putStringSet(keyDaDisattivare, newSet);

                            editor.apply();

                        }

                    }
                }
            }


            else {
                String key = (sveglieList.get(getAdapterPosition())).getKey();

                Set<String> sveglieSet = sharedPref.getStringSet(key, new HashSet<>());
                if(!keyDaDisattivare.equals(""))
                    sveglieSet = sharedPref.getStringSet(keyDaDisattivare, new HashSet<>());

                Set<String> newSet = new HashSet<>(sveglieSet);



                if (newSet.contains("attiva")) {
                    switchAttiva.setChecked(true);
                    Log.d(TAG, "checked attiva ");

                } else if (newSet.contains("non attiva")) {
                    switchAttiva.setChecked(false);
                    Log.d(TAG, "checked non attiva ");

                }
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            SharedPreferences sharedPref = context.getSharedPreferences("information_shared", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String key = (sveglieList.get(getAdapterPosition())).getKey();

            if (v.getId() == R.id.buttonEliminaSveglia) {
                //Log.d(TAG, "rimuovo " + (sveglieList.get(getAdapterPosition())).getId());
                // Rimuove la sveglia usando la chiave specificata
                editor.remove(key);

                // Rimuove la chiave dalla lista delle chiavi
                Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
                //Log.d(TAG,"keySet prima " + keySet);
                keySet.remove(key);
                //Log.d(TAG,"keySet dopo " + keySet);
                editor.putStringSet("sveglia_keys", keySet);

                // Rimuove la chiave dalla lista delle sveglie attive
                Set<String> attiveSet = sharedPref.getStringSet("sveglieAttive", new HashSet<>());
                attiveSet.remove(key);
                editor.putStringSet("sveglieAttive", keySet);


                editor.apply();

                //getAdapterPosition mi restituisce la posizione della sveglia da cancellare, partono da 0
                sveglieList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClickListenerS.onDeleteButtonPressed(getAdapterPosition());

            }


            else if (v.getId() == R.id.switchAttiva) {

                Set<String> sveglieSet = sharedPref.getStringSet(key, new HashSet<>());

                Set<String> newSet = new HashSet<>(sveglieSet);

                if (newSet.contains("attiva")) {
                    //Log.d (TAG, "switch trova attiva");
                    newSet.remove("attiva");
                    newSet.add("non attiva");

                    // Rimuove la chiave dalla lista delle attive
                    Set<String> attive= sharedPref.getStringSet("sveglieAttive", new HashSet<>());

                    attive.remove(key);

                    editor.putStringSet("sveglieAttive", attive);

                    editor.apply();
                }
                else if (newSet.contains("non attiva")) {
                    //Log.d (TAG, "switch trova non attiva");
                    newSet.remove("non attiva");
                    newSet.add("attiva");

                    String dataSveglia = (sveglieList.get(getAdapterPosition()).getData()).substring(0);
                    LocalDate data = LocalDate.parse(dataSveglia);
                    LocalDate oggi = LocalDate.now(); //giorno ordierno

                    String orarioSveglia = (sveglieList.get(getAdapterPosition()).getOrario()).substring(0);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime orario = LocalTime.parse(orarioSveglia, formatter);
                    LocalTime oraAttuale = LocalTime.parse(LocalDateTime.now().format(formatter));//giorno ordierno


                    //data passata se NON HO RIPETIZIONI controllo orario e scelgo quella di oggi o domani invece
                    //             se HO RIPETIZIONI calcolo la data più vicina
                    //data di oggi se NON HO RIPETIZIONI controllo orario e scelgo tra oggi e domani altrimenti
                    //             se HO RIPETIZIONI calcolo data più vicina
                    //data futura lascio quella

                    //se la data è futura non voflio entrare nell'if perchè non devo fare niente
                    if(!data.isAfter(oggi)) {
                        Log.d(TAG, "adapter data passata");
                        //se NON HO RIPETIZIONI
                        if (sveglieList.get(getAdapterPosition()).getRipetizioni().equals("r0000000")) {
                            Log.d(TAG, "adapet non ho ripetizioni");
                            //data passata o di oggi scelgo tra oggi e domani
                            if (data.isBefore(oggi) || data.isEqual(oggi)) {
                                //controllo se l'ora è passata per scegliere tra oggi e domani
                                if (orario.isAfter(oraAttuale)) {
                                    newSet.remove("d" + dataSveglia);
                                    newSet.add("d" + String.valueOf(oggi));
                                } else {
                                    newSet.remove("d" + dataSveglia);
                                    newSet.add("d" + String.valueOf(oggi.plusDays(1)));
                                }
                            }

                        }
                        //HO RIPETIZIONI quindi devo calcolare la data più vicina
                        //TODO controllare
                        else {
                            //Log.d(TAG ,"adapter ho ripetizoni devo calcolare data");
                            newSet.remove("d" + dataSveglia);
                            //Log.d(TAG, "Adapter settimana " + sveglieList.get(getAdapterPosition()).getRipetizioni() + " e orario " + (sveglieList.get(getAdapterPosition()).getOrario()));
                            newSet.add(GetDateTime.getDateRipetizioni("r0000000", sveglieList.get(getAdapterPosition()).getOrario()));
                        }
                    }
                    else
                        Log.d(TAG, "adapter data futura non faccio niente");

                    //Log.d(TAG, "adapetr NewSet dopo agg data" + newSet);

                    Set<String> attive= sharedPref.getStringSet("sveglieAttive", new HashSet<>());

                    attive.add(key);

                    editor.putStringSet("sveglieAttive", attive);

                    editor.apply();

                }

                Log.d(TAG, "switch sveglia dopo " + sveglieSet);

                editor.putStringSet(key, newSet);
                editor.apply();


            }

            else {
                onItemClickListenerS.onSveglieItemClick(sveglieList.get(getAdapterPosition()));
                //Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_modificaFragment); // , args);

            }


        }

    }
}
