package it.unimib.brain_alarm.adapter;



import static com.google.android.material.internal.ContextUtils.getActivity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.util.DateTimeUtil;



public class SveglieAdapter extends RecyclerView.Adapter<SveglieAdapter.ViewHolder> {

    private String[] localDataSet;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textview_orario);
        }

        public TextView getTextView() {
            return textView;
        }
    }


    public SveglieAdapter(Context context, String[] dataSet) {

        SharedPreferences sharedPreferences =  context.getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("mySetKey", new HashSet<>());
        List<String> dataList = new ArrayList<>(set);

        localDataSet = dataList.toArray(new String[0]);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sveglie_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
