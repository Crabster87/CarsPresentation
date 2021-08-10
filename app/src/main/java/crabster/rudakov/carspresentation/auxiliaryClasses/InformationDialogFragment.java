package crabster.rudakov.carspresentation.auxiliaryClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class InformationDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String deleteButton = "DELETE DATA";
        String editButton = "EDIT DATA";
        String viewButton = "VIEW CAR";
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle("Edit window")
                .setMessage("Choose action")
//                .setPositiveButton(deleteButton, (dialog, id) -> ((MainActivity) requireActivity()).deleteClicked())
//                .setNegativeButton(editButton, (dialog, id) -> ((MainActivity) requireActivity()).editClicked())
//                .setNeutralButton(viewButton, (dialog, id) -> ((MainActivity) requireActivity()).viewClicked())
                .setCancelable(true).create();
    }

}
