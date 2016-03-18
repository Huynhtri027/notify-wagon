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
import android.connecthings.util.ViewUtils;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 */
public class DialogMessageType extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {

    private static final String TAG = "DialogMessage";

    private TextView tvStatus;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle(R.string.dialog_msg_title);
        View dialogView = View.inflate(getActivity(), R.layout.content_menu, null);
        alertBuilder.setView(dialogView);
        alertBuilder.setNeutralButton(R.string.btn_cancel, this);
        ViewUtils.setListenerToView(dialogView, this, R.id.tv_msg_agression,
                R.id.tv_msg_social,
                R.id.tv_msg_lost,
                R.id.tv_msg_pickpocket);

        tvStatus = (TextView) dialogView.findViewById(R.id.tv_status);
        return alertBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
    }

    public void sendMessage(String msg){
        Message message = new Message(msg,
                NwSharedPreference.getInstance().getPseudo(),
                Message.TYPE.pickpocket,
                BeaconExitEnterCentralizer.getInstance().getCurrentBeaconContent().getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.ID));
        tvStatus.setText(R.string.msg_sent_in_progress);
        tvStatus.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_msg_agression:
                break;
            case R.id.tv_msg_pickpocket:
                break;
            case R.id.tv_msg_lost:
                break;
            case R.id.tv_msg_social:
                break;

        }
    }
}
