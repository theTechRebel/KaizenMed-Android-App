package com.afrikaizen.kaizenmed.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.kaizenmed.R;

/**
 * Created by Steve on 08/08/2015.
 */
public class PatientResultsListAdapter extends RecyclerView.Adapter<PatientResultsListAdapter.ViewHolder> {
    private String[] patients;
    private String[] description;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView txtPatientName,txtPatientDescription;
        public ViewHolder(View v) {
            super(v);
            mView = v;
            txtPatientName = (TextView)v.findViewById(R.id.txtPatientName);
            txtPatientDescription = (TextView)v.findViewById(R.id.txtPatientDescription);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PatientResultsListAdapter(String[] patients, String[] description) {
        this.patients = patients;
        this.description = description;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PatientResultsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtPatientName.setText(patients[position]);
        holder.txtPatientDescription.setText(description[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return patients.length;
    }
}
