package android.connecthings.notifywagon.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.beacon.BeaconExitEnterCentralizer;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.connecthings.util.Log;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 */
public class DialogMessage extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String TAG = "DialogMessage";

    private TextView tvStatus;
    private EditText etMsg;


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle(R.string.dialog_msg_title);
        View dialogView = View.inflate(getActivity(), R.layout.dialog_msg, null);
        alertBuilder.setView(dialogView);
        alertBuilder.setNegativeButton(R.string.btn_cancel, this);
        alertBuilder.setPositiveButton(R.string.btn_send, this);

        tvStatus = (TextView)dialogView.findViewById(R.id.tv_status);
        etMsg = (EditText)dialogView.findViewById(R.id.et_msg);
        return alertBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == Dialog.BUTTON_NEGATIVE){
            dismiss();
            return;
        }
        Log.d(TAG, "msg ", etMsg.getText());
        Message message = new Message(etMsg.getText().toString(),
                                        NwSharedPreference.getInstance().getPseudo(),
                                        Message.TYPE.pickpocket,
                BeaconExitEnterCentralizer.getInstance().getCurrentBeaconContent().getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.ID));
        tvStatus.setText(R.string.msg_sent_in_progress);
        ConnectionManagerServices.getInstance().sendMessage(message, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "error statusCode ", statusCode, " response ", responseString);
                tvStatus.setText(R.string.msg_sent_error);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "sucess statusCode ", statusCode, " response ", responseString);
                dismiss();
            }
        });
    }
}
