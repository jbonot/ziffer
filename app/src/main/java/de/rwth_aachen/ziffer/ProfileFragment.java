package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.Years;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private  String user_name,data_event="";


    private Bitmap bmp;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String data="",firstName="",lastName="",dob="",german_level="",description="",Qrimage="";
        MainActivity activity = (MainActivity) getActivity();
         user_name = activity.getMyData();


        BackgroundTask2 backgroundTask = new BackgroundTask2(getActivity());
        backgroundTask.execute("myprofile",user_name);


        //getting JSON for profile
        try {
            data = backgroundTask.get().toString();


        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try
        {   Log.d("checkdata",dob);
            JSONObject root = new JSONObject(data);

            firstName=root.getJSONObject("profile_data").getString("firstName");
            lastName= root.getJSONObject("profile_data").getString("lastName");
            dob= root.getJSONObject("profile_data").getString("dob");
            german_level=root.getJSONObject("profile_data").getString("german_level");
            description=root.getJSONObject("profile_data").getString("description");
            Qrimage=root.getJSONObject("profile_data").getString("photo");
            byte[] qrimage = Base64.decode(Qrimage.getBytes(),0);

            bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);

            Log.d("checkdob",dob);


        }
        catch (JSONException e)
        {
            Log.d("JSONexception",e.toString());
            e.printStackTrace();
        }
        String age[] = new String[3];
       int i=0;
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(dob);
        while (matcher.find()) {
          age[i] = matcher.group();
            i++;
        }

        LocalDate birthdate = new LocalDate (Integer.parseInt(age[0]), Integer.parseInt(age[1]),Integer.parseInt(age[2]));
        LocalDate now = new LocalDate();
        Years current = Years.yearsBetween(birthdate, now);
        String current_age=current.toString();
        current_age = current_age.replaceAll("\\D+","");
        View view =  inflater.inflate(R.layout.profile_fragment, container, false);
        TextView headline_text = (TextView)view.findViewById(R.id.headline);
        TextView germanLevel_text = (TextView)view.findViewById(R.id.germanLevel);
        TextView description_text = (TextView)view.findViewById(R.id.description);
        TextView eventListHeadline = (TextView)view.findViewById(R.id.eventListHeadline);

        headline_text.setText(firstName + " " + lastName + ", " + current_age);
        germanLevel_text.setText(german_level);
        description_text.setText(description);
        eventListHeadline.setText(firstName+"'s Event");

        ImageView imageview = (ImageView)view.findViewById(R.id.image);

        imageview.setImageBitmap(bmp);

    return view;
      //  return inflater.inflate(R.layout.profile_fragment, container, false);

    }

    @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.


        BackgroundTask3 backgroundTask3 = new BackgroundTask3(getActivity());
        backgroundTask3.execute("myevent",user_name);
        //getting JSON for events
        try {
            data_event = backgroundTask3.get().toString();
            Log.d("data_event",data_event);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
          //older code here
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new TestData(data_event).getEventListAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                startActivity(intent);
            }
        });
    }

}
