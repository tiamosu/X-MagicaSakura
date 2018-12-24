package com.bilibili.magicasakurademo.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.bilibili.magicasakurademo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


/**
 * @author xyczero
 * @time 16/5/23
 */
public class ProgressStyleDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = ProgressStyleDialog.class.getSimpleName();

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioButton1 = view.findViewById(R.id.progress_style_1);
        mRadioButton2 = view.findViewById(R.id.progress_style_2);
        mRadioButton1.setOnClickListener(this);
        mRadioButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mRadioButton1.setChecked(v.getId() == R.id.progress_style_1);
        mRadioButton2.setChecked(v.getId() == R.id.progress_style_2);
    }
}
