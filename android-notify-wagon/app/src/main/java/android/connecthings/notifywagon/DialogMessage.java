package android.connecthings.notifywagon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 */
public class DialogMessage extends DialogFragment implements DialogInterface.OnClickListener {



    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle(R.string.dialog_msg_title);
        alertBuilder.setView(R.layout.dialog_msg);
        alertBuilder.setNegativeButton(R.string.btn_cancel, this);
        alertBuilder.setPositiveButton(R.string.btn_send, this);

        return alertBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
