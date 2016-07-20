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

import com.squareup.picasso.Picasso;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.Years;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private  String user_name,data_event="";






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String data="",firstName="",lastName="",dob="",german_level="",description="",Qrimage="",image_url="";
         user_name = getActivity().getIntent().getStringExtra("username") == null ?
            SaveSharedPreference.getUserName(getActivity())
                 : getActivity().getIntent().getStringExtra("username");
        Bitmap bmp=null;

        BackgroundTask2 backgroundTask = new BackgroundTask2(getActivity());
        backgroundTask.execute("myprofile",user_name);


        //getting JSON for profile
        try {

            data = backgroundTask.get().toString();
            Log.d("datafromprofile",data);

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
            Qrimage=root.getJSONObject("profile_data").getString("image");
            image_url=LocalSettings.Base_URL + "image/"+ Qrimage;
        //    URL url = new URL(image_url);
            Log.d("image_url",image_url);


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
        Picasso.with(getActivity()).load(image_url).into(imageview);
       // imageview.setImageBitmap(bmp);

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
        JSONArray jsonArray;
        ArrayList<EventListItem> e = new ArrayList<EventListItem>();
        try
        {

            JSONObject root = new JSONObject(data_event);
            jsonArray= root.getJSONArray("event_data");
            int count=0;
            while(count<jsonArray.length())
            {
                JSONObject JO= jsonArray.getJSONObject(count);
                EventListItem event = new EventListItem();
                event.setId(JO.getInt("event_id"));
                event.setLevel(JO.getString("german_level_event"));
                event.setHeadline(JO.getString("title"));
                event.setDescription(JO.getString("name") + " " + JO.getString("address"));

                e.add(event);
                count++;
            }

        }
        catch (JSONException e1)
        {
            Log.d("JSONexception",e1.toString());
            e1.printStackTrace();
        }

          //older code here
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new EventListAdapter(getActivity(), e.toArray(new EventListItem[0])));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                intent.putExtra("event_id",Integer.valueOf(
                        ((TextView) view.findViewById(R.id.event_id)).getText().toString()));
                startActivity(intent);
            }
        });
    }

}
