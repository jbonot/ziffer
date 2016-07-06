package de.rwth_aachen.ziffer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(),
                                    this,
                                    c.get(Calendar.HOUR_OF_DAY),
                                    c.get(Calendar.MINUTE),
                                    DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Set the time, using the time format that corresponds with the phone settings
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        ((TextView) getActivity().findViewById(getArguments().getInt("id"))).setText(
                new SimpleDateFormat(DateFormat.is24HourFormat(getActivity()) ? "H:mm" : "h:mm a")
                        .format(c.getTime()));
    }
}
