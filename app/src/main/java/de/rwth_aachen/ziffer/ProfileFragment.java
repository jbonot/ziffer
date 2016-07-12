package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private  String user_name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String data="",firstName="",lastName="",dob="",german_level="",description="";
        MainActivity activity = (MainActivity) getActivity();
         user_name = activity.getMyData();
        BackgroundTask2 backgroundTask = new BackgroundTask2(getActivity());
        backgroundTask.execute("myprofile",user_name);

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

            Log.d("checkdob",dob);


        }
        catch (JSONException e)
        {
            Log.d("JSONexception",e.toString());
            e.printStackTrace();
        }
        final View view =  inflater.inflate(R.layout.profile_fragment, container, false);
        TextView headline_text = (TextView)view.findViewById(R.id.headline);
        TextView germanLevel_text = (TextView)view.findViewById(R.id.germanLevel);
        TextView description_text = (TextView)view.findViewById(R.id.description);

        headline_text.setText(firstName + " " + lastName + " , " + dob);
        germanLevel_text.setText(german_level);
        description_text.setText(description);

    return view;
      //  return inflater.inflate(R.layout.profile_fragment, container, false);

    }

    @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.
        String result="";
       // String user_name = this.getArguments().getString("userkey");
        Log.d("userid",user_name);


        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(TestData.getEventListAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                startActivity(intent);
            }
        });
    }

}
