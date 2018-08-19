package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.AfrekeningFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.KostenDetailFragment;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;

import com.example.gilles.g_hw_sl_pv_9200.model.Kost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

public class KostenMaandoverzicht extends AppCompatActivity implements AfrekeningFragment.Callback {
    private TextView txtmaandOverzicht;

    private int maand;
    private int jaar;
    private static FragmentManager fragmentManager;
    private List<Kost> kosten = new ArrayList<>();
    private String[] maanden = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"};

    public List<Kost> getKosten() {
        return kosten;
    }
    private boolean afrekening;
    public boolean getAfrekening(){return afrekening;}
    private ImageButton btnVorigeMaand;
    private ImageButton btnVolgendeMaand;
    private FloatingActionButton btnAdd;
    private Button btnAfrekening;
    private TextView txtTotaal;
    private TextView txtHelftTotaal;
    private float totaal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        jaar = cal.get(Calendar.YEAR);
        maand = cal.get(Calendar.MONTH) + 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_activity_afrekening);
        afrekening = false;
        fragmentManager = getSupportFragmentManager();
        kostenOphalen();
        btnAfrekening = (Button) findViewById(R.id.btnAfrekening);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAddKost);
        txtTotaal = (TextView) findViewById(R.id.txtTotaal);
        txtHelftTotaal = (TextView) findViewById(R.id.txtHelftTotaal);
        btnVorigeMaand = (ImageButton) findViewById(R.id.btnVorigeMaand);
        btnVorigeMaand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maand--;
                if(maand == 0){
                    jaar--;
                    maand = 12;
                }
                kostenOphalen();
            }
        });

        btnVolgendeMaand = (ImageButton) findViewById(R.id.btnVolgendeMaand);
        btnVolgendeMaand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maand++;
                if(maand == 13){
                    jaar++;
                    maand = 1;
                }
                kostenOphalen();
            }
        });

    }

    private void kostenOphalen() {
        totaal = 0;
        kosten = new ArrayList<>();
        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        Call<List<Kost>> call = dataInterface.getKostenMaand(Utils.USERID, maand, jaar);
        call.enqueue(new Callback<List<Kost>>() {
            @Override
            public void onResponse(Call<List<Kost>> call, Response<List<Kost>> response) {
                if (response.body() != null) {
                    for (Kost kost : response.body()) {
                        kosten.add(kost);

                        if(kost.getGoedgekeurd()){totaal += kost.getKost();}
                        Log.d("Kosten", "Opgehaald");
                    }
                }
                //setContentView(R.layout.aa_activity_afrekening);
                Log.d("opgehaalde kosten", kosten.toArray().toString());
                txtmaandOverzicht = (TextView) findViewById(R.id.txtmaandAfrekening);
                txtmaandOverzicht.setText(maanden[maand - 1] + " " + jaar);
                txtTotaal.setText("€ "+ totaal);
                txtHelftTotaal.setText("€ "+ totaal/2);
                AfrekeningFragment frag = (AfrekeningFragment) fragmentManager.findFragmentById(R.id.afrekenenFragment);
                frag.updateAdapter();
            }

            @Override
            public void onFailure(Call<List<Kost>> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });
    }

    public void voegKostToe(View view) {
        Intent intent = new Intent(KostenMaandoverzicht.this, KostToevoegen.class);
        startActivity(intent);

    }

    public void afrekening(View view){
        afrekening = !afrekening;
        if(afrekening) {
            totaal = 0;
            List<Kost> kostenB = new ArrayList<>();
            for (Kost kost:kosten) {
                if(kost.getGoedgekeurd()){
                    kostenB.add(kost);
                    totaal+=kost.getKost();
                }
            }
            kosten = kostenB;
            txtTotaal.setText("€ "+ totaal);
            txtHelftTotaal.setText("€ "+ totaal/2);
            String maand = txtmaandOverzicht.getText().toString();
            txtmaandOverzicht.setText("Afrekening " + maand);
            btnVorigeMaand.setVisibility(View.GONE);
            btnVolgendeMaand.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
            btnAfrekening.setText("Terug");
            btnAfrekening.setBackgroundColor(Color.RED);
            AfrekeningFragment frag = (AfrekeningFragment) fragmentManager.findFragmentById(R.id.afrekenenFragment);
            frag.updateAdapter();
        }else{
            kostenOphalen();
            btnAfrekening.setText("Afrekening");
            String maand = txtmaandOverzicht.getText().toString().replaceFirst("Afrekening ","");
            txtmaandOverzicht.setText(maand);
            btnVorigeMaand.setVisibility(View.VISIBLE);
            btnVolgendeMaand.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
            btnAfrekening.setText("Afrekening");
            btnAfrekening.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    @Override
    public void onListFragmentInteraction(Kost item) {
        Log.d("Listelement kosten", "pressed");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        KostenDetailFragment fragment = KostenDetailFragment.newInstance(item, afrekening);
        ft.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.containerAfrekening, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {

        KostenDetailFragment frag = (KostenDetailFragment) fragmentManager.findFragmentByTag(Utils.KostenDetailFragment);
        if(frag != null){
            frag.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                    .remove(frag).commit();
        }else{
            Intent intent = new Intent(KostenMaandoverzicht.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
