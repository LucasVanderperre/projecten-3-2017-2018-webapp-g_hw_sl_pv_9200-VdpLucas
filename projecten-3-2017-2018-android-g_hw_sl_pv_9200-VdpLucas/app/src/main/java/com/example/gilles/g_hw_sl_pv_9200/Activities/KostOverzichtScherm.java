package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.model.Databank;
import com.example.gilles.g_hw_sl_pv_9200.model.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.model.Gesprek;
import com.example.gilles.g_hw_sl_pv_9200.model.Gezin;
import com.example.gilles.g_hw_sl_pv_9200.model.Kost;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostOverzichtScherm extends AppCompatActivity {
    //    @BindView(R.id.textViewKostenHeader) TextView naam;
//    @BindView(R.id.voegKostToeBtn) Button voegKostToeBtn;
    // @BindView(R.id.listViewKosten) ListView kostenLijst;
    private TextView naam;
    private Button voegKostToeBtn;
    private ListView kostenListView;
    private ArrayAdapter<String> listAdapter;
    //private List<Kost> kosten;
    private Databank databank;
    private KostenSingleton kostenSingleton = KostenSingleton.getInstance();

    /**
     * aanmaken van het kostoverzichtscherm en hierin
     * het ophalen van alle kosten van het huidig gezin
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        naam = (TextView) findViewById(R.id.textViewKostenHeader);
        voegKostToeBtn = (Button) findViewById(R.id.voegKostToeBtn);
        // kostenSingleton.setGezin(new Databank().getGezin());
//        this.databank = new Databank();
        //this.kosten = this.gezin.getKosten();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_overzicht_scherm);
        kostenListView = (ListView) findViewById(R.id.listViewKosten);

        ArrayList<String> kostenStringLijst = new ArrayList<>();
        Context context = this;
        List<Kost> kosten = new ArrayList<>();
//
//        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
//        Call<Gezin> gezinCall = dataInterface.getHuidigGezin(Utils.GEBRUIKER.getId());
//        gezinCall.enqueue(new Callback<Gezin>() {
//            @Override
//            public void onResponse(Call<Gezin> call, Response<Gezin> response) {
//                String gezin = response.body().getGezinsNaam();
//            }
//
//            @Override
//            public void onFailure(Call<Gezin> call, Throwable t) {
//                Log.d("Error:",t.getMessage());
//            }
//        });
        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        Call<List<Kost>> call1 = dataInterface.getKosten(Utils.GEBRUIKER.getId());
        call1.enqueue(new Callback<List<Kost>>() {
            @Override
            public void onResponse(Call<List<Kost>> call, Response<List<Kost>> response) {
                HashMap<String, String> kostenlijst = new HashMap<>();
                if (response.body() != null) {
                    for (Kost kost : response.body()) {
                        kostenlijst.put(kost.getNaam(), "€ " + kost.getKost());
                    }

                } else {
                    kostenlijst.put("Er zijn geen kosten om weer te geven", "");
                }
                List<HashMap<String, String>> listItems = new ArrayList<>();
                SimpleAdapter adapter = new SimpleAdapter(kostenListView.getContext(), listItems, R.layout.simplerow, new String[]{"lijn1", "lijn2"}, new int[]{R.id.naamKost, R.id.bedragKost});

                Iterator it = kostenlijst.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> resultMap = new HashMap<>();
                    Map.Entry paar = (Map.Entry) it.next();
                    resultMap.put("lijn1", paar.getKey().toString());
                    resultMap.put("lijn2", paar.getValue().toString());
                    listItems.add(resultMap);
                    kostenListView.setAdapter(adapter);
                }
//                        kosten.add(kost);
//                        kostenStringLijst.add(kost.toString());
//                        Log.d("kostNaam",kost.naam);
//                        Log.d("iets", ""+ kostenStringLijst.size());
//                        //ButterKnife.bind(context);
//                        listAdapter = new ArrayAdapter<String>(context, R.layout.simplerow, kostenStringLijst );
//                        naam.setText(gezin);
//                        voegKostToeBtn.setText("+");
//                        kostenListView.setAdapter( listAdapter );

            }

            @Override
            public void onFailure(Call<List<Kost>> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });
    }



    /**
     * Aanmaken van lijst met de toString van alle kosten
     */


    //ArrayList<String> kostenStringLijst = new ArrayList<>();
//        final int size = kosten.size();
//        for (int i = 0; i < size; i++)
//        {
//            Log.d("d","test"+kosten.get(i).toString());
//            kostenStringLijst.add(kosten.get(i).toString());
//            //do something with i
//        }


       /* for (Kost kost: kostenSingleton.getKosten()) {
         //   naam.setText(kost.getNaam());
           // mededeling.setText(kost.getMededeling());
            this.kostenLijst.
        }*/

    //  this.naam.setText(kostenSingleton.getGezin().getGezinsNaam());
    public void voegKostToe(View view) {
        Intent intent = new Intent(KostOverzichtScherm.this, KostToevoegen.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KostOverzichtScherm.this, MainActivity.class);
        startActivity(intent);
    }
}
