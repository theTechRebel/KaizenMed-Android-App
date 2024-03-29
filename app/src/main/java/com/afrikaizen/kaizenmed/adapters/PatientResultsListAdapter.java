package com.afrikaizen.kaizenmed.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.models.PatientsResults;
import com.afrikaizen.kaizenmed.singleton.AppBus;

import java.util.ArrayList;

/**
 * Created by Steve on 08/08/2015.
 */
public class PatientResultsListAdapter extends RecyclerView.Adapter<PatientResultsListAdapter.ViewHolder>{
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public View mView;
        public TextView txtPatientName,txtPatientDescription;
        Context ctx;
        ArrayList<PatientsResults.JSONObject> results;

        public ViewHolder(Context ctx, ArrayList<PatientsResults.JSONObject> results, View v) {
            super(v);
            this.ctx = ctx;
            this.results = results;
            mView = v;
            txtPatientName = (TextView)v.findViewById(R.id.txtPatientName);
            txtPatientDescription = (TextView)v.findViewById(R.id.txtPatientDescription);
        }
    }

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private ArrayList<PatientsResults.JSONObject> patients;
    private Context ctx;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PatientResultsListAdapter(Context ctx, ArrayList<PatientsResults.JSONObject> patients) {
        this.patients = patients;
        this.ctx = ctx;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PatientResultsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(this.ctx,this.patients,v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        PatientsResults.JSONObject patient = patients.get(position);
        holder.txtPatientName.setText(patient.getName()+" "+patient.getSurname());
        holder.txtPatientDescription.setText(patient.getCondition());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null)
                    listener.onItemClick(v, position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return patients.size();
    }


    // Clean all elements of the recycler
    public void clear() {
        patients.clear();
        notifyDataSetChanged();

    }

    // Add a list of items
    public void addAll(ArrayList<PatientsResults.JSONObject> results) {
        patients.addAll(results);
        notifyDataSetChanged();

    }
}
