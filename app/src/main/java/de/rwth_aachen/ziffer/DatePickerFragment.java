package de.rwth_aachen.ziffer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener  {
    static String[] MONTHS = new String[]{ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                                           "Sept", "Oct", "Nov", "Dec"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.MONTH, 6);

        // Create a new instance of DatePickerDialog and return itc.get(Calendar.YEAR)
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                                                 this,
                                                                 c.get(Calendar.YEAR),
                                                                 c.get(Calendar.MONTH),
                                                                 c.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMaxDate(maxDate.getTimeInMillis());
        datePicker.setMinDate(c.getTimeInMillis());

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        ((TextView)getActivity().findViewById(getArguments().getInt("id")))
                .setText(MONTHS[month - 1] + " " + day);
    }
}