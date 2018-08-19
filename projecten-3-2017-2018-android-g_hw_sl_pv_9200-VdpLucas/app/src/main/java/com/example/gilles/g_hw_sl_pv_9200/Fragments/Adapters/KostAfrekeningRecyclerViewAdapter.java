package com.example.gilles.g_hw_sl_pv_9200.Fragments.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KostenMaandoverzicht;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.AfrekeningFragment;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.model.Kost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KostAfrekeningRecyclerViewAdapter extends RecyclerView.Adapter<KostAfrekeningRecyclerViewAdapter.ViewHolder> {


    private final List<Kost> mValues;
    private boolean afrekening;
    private final AfrekeningFragment.Callback mListener;
    private String [] maanden = { "januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november","december"};
    private String [] dagen = {"maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag", "zondag"};


    /**
     * nieuwe recyclerviewadapter voor het activiteitdetail fragment aanmaken
     * @param context
     * @param listener
     */
    public KostAfrekeningRecyclerViewAdapter(Context context, AfrekeningFragment.Callback listener) {
        mValues = ((KostenMaandoverzicht) context).getKosten();
        afrekening = ((KostenMaandoverzicht) context).getAfrekening();
        Log.d("mlistener kosten",mValues.toArray().toString());
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aa_kostenlijstgoedkeurenitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitelView.setText(holder.mItem.getNaam());
        holder.mNaamView.setText(holder.mItem.getAanmakerNaam());
        holder.mPrijsView.setText("€ "+holder.mItem.getKost());
        String[] parts = holder.mItem.getDatum().split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2].split("T")[0];
        Date date = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day) );
        String datum = (dagen[date.getDay()] + " " + date.getDate() +" " + maanden[Integer.parseInt(month)-1]);
        holder.mDatumView.setText(datum);
        if(!afrekening){
            if(holder.mItem.getGoedgekeurd()) {
                holder.mGoedgekeurdView.setText("Goedgekeurd");
                holder.mGoedgekeurdView.setTextColor(Color.GREEN);
            }else{
                holder.mGoedgekeurdView.setText("Niet goedgekeurd");
                holder.mGoedgekeurdView.setTextColor(Color.RED);
            }
        }else{
            holder.mGoedgekeurdView.setVisibility(View.GONE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitelView;
        public final TextView mNaamView;
        public final TextView mPrijsView;
        public final TextView mGoedgekeurdView;
        public final TextView mDatumView;

        public Kost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitelView = (TextView) view.findViewById(R.id.txtTitel);
            mNaamView = (TextView) view.findViewById(R.id.txtNaam);
            mPrijsView = (TextView) view.findViewById(R.id.txtPrijs);
            mGoedgekeurdView = (TextView) view.findViewById(R.id.txtGoedgekeurd);
            mDatumView = (TextView) view.findViewById(R.id.txtDatum);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitelView.getText() + "'";
        }
    }
}


