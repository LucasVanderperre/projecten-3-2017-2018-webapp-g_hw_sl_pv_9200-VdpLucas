package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;


import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;

public class ConfirmDialogFragment  extends DialogFragment  {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Goedkeuren?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // ((KostenDetailFragment) getParentFragment().getParentFragment()).keurGoed();
                        KostenDetailFragment fragment = (KostenDetailFragment) getTargetFragment();

                        fragment.keurGoed();
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(),"Geannuleerd", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

