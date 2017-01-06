package com.example.sungshin.huddle.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.huddle.huddle.R;

/**
 * Created by LG on 2017-01-06.
 */

public class HelpDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        mBuilder.setView(mLayoutInflater.inflate(R.layout.help_dialog, null));
        mBuilder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                //끝내는거 구현
            }
        });

        return mBuilder.create();
    }

    public void onstop() {
        super.onStop();
    }
}