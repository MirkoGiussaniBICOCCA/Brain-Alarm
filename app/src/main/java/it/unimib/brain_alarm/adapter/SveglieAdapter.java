package it.unimib.brain_alarm.adapter;



import static com.google.android.material.internal.ContextUtils.getActivity;

import static it.unimib.brain_alarm.ui.HomeFragment.SVEGLIE_KEY;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.Sveglia.Sveglie;
import it.unimib.brain_alarm.util.DateTimeUtil;



public class SveglieAdapter extends
        RecyclerView.Adapter<SveglieAdapter.ViewHolder> {


    public interface OnItemClickListenerS  {
        void onSveglieItemClick(Sveglie sveglie);
        void onDeleteButtonPressed(int position);

    }

    private static final String TAG = SveglieAdapter.class.getSimpleName();
    private Context context;
    private final List<Sveglie> sveglieList;
    private final OnItemClickListenerS onItemClickListenerS;

    public SveglieAdapter(Context context, List<Sveglie> sveglieList, SveglieAdapter.OnItemClickListenerS onItemClickListener) {
        this.context = context;
        this.sveglieList = sveglieList;
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



        public ViewHolder(View view) {
            super(view);
            textViewOrario = (TextView) view.findViewById(R.id.textview_orario);
            textViewEtichetta = (TextView) view.findViewById(R.id.textview_etichetta);
            textViewRipetizioni = (TextView) view.findViewById(R.id.textview_ripetizioni);
            Button buttonDelete = itemView.findViewById(R.id.buttonEliminaSingolaSveglia);
            itemView.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        public void bind(Sveglie sveglie) {
            textViewOrario.setText(sveglie.getOrario());
            textViewEtichetta.setText(sveglie.getEtichetta());
            textViewRipetizioni.setText(sveglie.getRipetizioni());
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonEliminaSingolaSveglia) {
                SharedPreferences sharedPref = context.getSharedPreferences("information_shared", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Log.d(TAG, "rimuovo " + (sveglieList.get(getAdapterPosition())).getId());
                // Rimuove la sveglia usando la chiave specificata
                editor.remove((sveglieList.get(getAdapterPosition())).getId());

                // Rimuove la chiave dalla lista delle chiavi
                Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
                Log.d(TAG,"keySet prima " + keySet);
                keySet.remove((sveglieList.get(getAdapterPosition())).getId());
                Log.d(TAG,"keySet dopo " + keySet);
                editor.putStringSet("sveglia_keys", keySet);

                editor.apply();

                //getAdapterPosition mi restituisce la posizione della sveglia da cancellare, partono da 0
                sveglieList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClickListenerS.onDeleteButtonPressed(getAdapterPosition());

            } else {
                onItemClickListenerS.onSveglieItemClick(sveglieList.get(getAdapterPosition()));
            }

        }
    }
}
