package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.model.Kost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostToevoegen extends AppCompatActivity {
    @BindView(R.id.bedragTextField)
    EditText bedrag;
    @BindView(R.id.naamKostTextField)
    EditText naamKost;
    @BindView(R.id.mededelingTextField)
    EditText mededeling;
    @BindView(R.id.btnKosttoevoegen)
    Button btnKosttoevoegen;
    @BindView(R.id.txtSelectDatum)
    TextView txtSelectDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private KostenSingleton kostenSingleton = KostenSingleton.getInstance();

    /**
     * aanmaken van het kosttoevoegenfragment
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_toevoegen);
        ButterKnife.bind(this);

        Context context = this;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        txtSelectDate.setText(day+"/"+month+"/"+year);

        //spinner categorie
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Sport");
        spinnerArray.add("School");
        spinnerArray.add("Medisch");
        spinnerArray.add("Cadeau");
        spinnerArray.add("Geen categorie");

        Spinner mSpinner = (Spinner) findViewById(R.id.Categoriespinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        //spinner buitengewoon
        List<String> spinnerUitzArray = new ArrayList<>();
        spinnerUitzArray.add("Gewone kost");
        spinnerUitzArray.add("Buitengewone kost");
        Spinner buitengewoonSpinner = (Spinner) findViewById(R.id.uitzonderlijkSpinner);
        ArrayAdapter<String> adapterUitz = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerUitzArray);
        adapterUitz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buitengewoonSpinner.setAdapter(adapterUitz);

        /**
         * doorgeven van een nieuwe kost aan de databank
         */

        btnKosttoevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bedrag.getText().toString().equals("") || bedrag.getText().toString().length() == 0
                        || naamKost.getText().toString().equals("") || naamKost.getText().toString().length() == 0
                        ) {
                    new CustomToast().Show_Toast(context, getWindow().getDecorView().getRootView(),
                            "De velden naam en bedrag zijn verplicht in te vullen");

                } else {
                    DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
                    String[] personen = {Utils.GEBRUIKER.getVoorNaam()};
                    String kost = bedrag.getText().toString();
                    String nKost = kost.split(",")[0];
                    int mbedrag = Integer.parseInt(nKost);
//                    Log.d("kost", mSpinner.getSelectedItem().toString());
                    Calendar cal = Calendar.getInstance();
                    String[] parts = txtSelectDate.getText().toString().split("/");
                    String year = parts[2];
                    String month = parts[1];
                    String day = parts[0];
                    String maand ;
                    //if(month<10){maand = "0"+month;}else{maand = ""+month;}
                    if (month.length()<2){
                        month = "0"+month;
                    }
                    if (day.length()<2){
                        day = "0"+day;
                    }
                    String datum = (year+"-"+month+"-"+day+"T12:12:12.836Z");
                    Kost newKost = new Kost(naamKost.getText().toString(), Utils.GEBRUIKER.getId()
                            , personen, datum, mbedrag, mededeling.getText().toString(),
                            mSpinner.getSelectedItem().toString(),
                            (buitengewoonSpinner.getSelectedItem().equals(spinnerUitzArray.get(0)))?false:true);
                    Log.d("kost", newKost.stringKost());
                    Call<Kost> call = dataInterface.voegKostToe(Utils.GEBRUIKER.getId(), newKost);
                    call.enqueue(new Callback<Kost>() {
                        @Override
                        public void onResponse(Call<Kost> call, Response<Kost> response) {
                            Intent intent = new Intent(KostToevoegen.this, KostenMaandoverzicht.class);
                            startActivity(intent);
                            Toast.makeText(context, "Kost succesvol toegevoegd", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onFailure(Call<Kost> call, Throwable t) {

                        }
                    });
                }

            }
        });

        txtSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                         mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txtSelectDate.setText(date);

            }
        };
    }

    public void voegKostToe(View view) {
        // Kost kost = new Kost(naamKost.getText().toString(), mededeling.getText().toString(), Integer.parseInt(bedrag.getText().toString()));
        // kostenSingleton.VoegKostToe(kost);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KostToevoegen.this, KostenMaandoverzicht.class);
        startActivity(intent);
    }

}
