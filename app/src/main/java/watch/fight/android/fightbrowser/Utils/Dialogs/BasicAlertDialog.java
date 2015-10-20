package watch.fight.android.fightbrowser.Utils.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;

public class BasicAlertDialog extends DialogFragment {
    private final static String DIALOG_HEADER = "watch.fight.android.Utils.Dialogs.BasicAlertDialog.dialog_header";
    private final static String DIALOG_MESSAGE = "watch.fight.android.Utils.Dialogs.BasicAlertDialog.dialog_message";

    public interface BasicAlertButtonListener {
        public void onOk();
        public void onCancel();
    }

    public static BasicAlertDialog newInstance(int headerResource, int messageResource) {
        BasicAlertDialog dialog = new BasicAlertDialog();
        Bundle args = new Bundle();
        args.putInt(DIALOG_HEADER, headerResource);
        args.putInt(DIALOG_MESSAGE, messageResource);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle) {
        int headerResource = getArguments().getInt(DIALOG_HEADER);
        int contentResource = getArguments().getInt(DIALOG_MESSAGE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.base_dialog, null);
        TextView header = (TextView) view.findViewById(R.id.dialog_header);
        TextView content = (TextView) view.findViewById(R.id.dialog_content);
        Button okButton = (Button) view.findViewById(R.id.dialog_ok);
        Button cancelButton = (Button) view.findViewById(R.id.dialog_cancel);

        content.setText(contentResource);
        header.setText(headerResource);
        builder.setView(view);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicAlertButtonListener callback = (BasicAlertButtonListener) getActivity();
                callback.onOk();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicAlertButtonListener callback = (BasicAlertButtonListener) getActivity();
                callback.onCancel();
                dismiss();
            }
        });


        Dialog dialog = builder.create();
        return dialog;

    }
}
