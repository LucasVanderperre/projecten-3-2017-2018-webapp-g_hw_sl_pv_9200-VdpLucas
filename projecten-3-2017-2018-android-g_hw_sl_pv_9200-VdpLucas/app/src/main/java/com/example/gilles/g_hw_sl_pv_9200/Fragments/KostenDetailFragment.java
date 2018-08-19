package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KostOverzichtScherm;
import com.example.gilles.g_hw_sl_pv_9200.Activities.KostenMaandoverzicht;
import com.example.gilles.g_hw_sl_pv_9200.Activities.MainActivity;
import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.model.Kost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostenDetailFragment extends Fragment {


    private static final String ARG_KOST = "kost";
    private static final String ARG_AFREKENING = "afrekening";

    private Kost mKost;
    View view;

    /**
     * methode voor het aanmaken van een nieuwe instance van een kostendetailFragment
     *
     * @param kost
     * @return
     */
    public static KostenDetailFragment newInstance(Kost kost, boolean afrekening) {
        KostenDetailFragment fragment = new KostenDetailFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_AFREKENING,afrekening );
        args.putParcelable(ARG_KOST, kost);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKost = getArguments().getParcelable(ARG_KOST);
        }
    }

    private void initControls(View view) {
        TextView txtBedrag = (TextView) view.findViewById(R.id.txtBedrag);
        TextView txtKostNaam = (TextView) view.findViewById(R.id.txtKostNaam);
        TextView txtBetaler = (TextView) view.findViewById(R.id.txtBetaler);
        TextView txtBetaalDatum = (TextView) view.findViewById(R.id.txtBetaalDatum);
        TextView txtSoortKost = (TextView) view.findViewById(R.id.txtSoortKost);
        TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        TextView txtCategorie = (TextView) view.findViewById(R.id.txtCategorie);
        TextView txtBeschrijving = (TextView) view.findViewById(R.id.txtBeschrijving);
        Button btnKeurGoed = (Button) view.findViewById(R.id.btnKeurGoed);
        txtBedrag.setText(""+ mKost.getKost());
        txtKostNaam.setText(mKost.getNaam());
        txtBetaler.setText(mKost.getAanmakerNaam());
        txtBetaalDatum.setText(mKost.getDatum());
        txtSoortKost.setText((mKost.getUitzonderlijk())?"Uitzonderlijke kost":"Gewone kost");
        txtStatus.setText(mKost.getGoedgekeurd()?"Goedgekeurd":"Nog niet goedgekeurd");
        txtStatus.setTextColor(mKost.getGoedgekeurd()? Color.GREEN:Color.RED);
        txtCategorie.setText(mKost.getCategorie());
        txtBeschrijving.setText(mKost.getBeschrijving());
        String user = Utils.USERID;
        Log.d("id user",  user +" " + mKost.getAanmakerId());
        KostenDetailFragment frag = this;
        if(!getArguments().getBoolean(ARG_AFREKENING)){
            if(mKost.getGoedgekeurd() || mKost.getAanmakerId().equals(user) ){
                btnKeurGoed.setVisibility(View.GONE);
            }
            btnKeurGoed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
                    confirmDialogFragment.setTargetFragment(frag,111);
                    confirmDialogFragment.show(getFragmentManager(),"KostenDetailFragment");
                }
            });
        }


    }

    public void keurGoed() {
        Log.d("keur goed","goedkeuren van een kost wordt afgehandeld");
        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        Call<Kost> call = dataInterface.keurKostGoed(Utils.USERID,mKost.getId());
        call.enqueue(new Callback<Kost>() {
            @Override
            public void onResponse(Call<Kost> call, Response<Kost> response) {

                if(response.isSuccessful()) {
                    updateGoedgekeurd();
                }
            }

            @Override
            public void onFailure(Call<Kost> call, Throwable t) {
                Log.e("Goedkeuren", "Unable to submit post to API.");
            }
        });
    }

    private void updateGoedgekeurd(){
        TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        txtStatus.setText("Goedgekeurd");
        txtStatus.setTextColor(Color.GREEN);
        Button btnKeurGoed = (Button) view.findViewById(R.id.btnKeurGoed);
        btnKeurGoed.setVisibility(View.GONE);


    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.aa_fragment_kostdetail, container, false);
        initControls(view);
        return view;
    }


}

