package de.rwth_aachen.ziffer;

/**
 * Created by jasimwhd on 6/29/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
    }
    @Override
    protected String doInBackground(String... params) {
        // LocalSettings class is ignored from the repository.  Must be created locally.
        String user_reg = LocalSettings.Base_URL + "user_registration.php";
        String profile_data = LocalSettings.Base_URL + "profile_data.php";
        String reg_url = LocalSettings.Base_URL+ "register.php";
        String login_url = LocalSettings.Base_URL + "login.php";

        String method = params[0];
        Log.d("filecheck",user_reg);
        if (method.equals("event")) {
            String user_name_host = params[1];
            String german_level_event = params[2];
            String title = params[3];
            String location = params[4];
            String date = params [5];
            String start_time = params [6];
            String end_time = params [7];
            String min_attendees = params [8];
            String max_attendees = params[9];
            String description = params [10];

            Log.d("param",german_level_event);
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name_host", "UTF-8") + "=" + URLEncoder.encode(user_name_host, "UTF-8") + "&" +
                        URLEncoder.encode("german_level_event", "UTF-8") + "=" + URLEncoder.encode(german_level_event, "UTF-8") + "&" +
                        URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&" +
                        URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                        URLEncoder.encode("start_time", "UTF-8") + "=" + URLEncoder.encode(start_time, "UTF-8") + "&" +
                        URLEncoder.encode("end_time", "UTF-8") + "=" + URLEncoder.encode(end_time, "UTF-8") + "&" +
                        URLEncoder.encode("min_attendees", "UTF-8") + "=" + URLEncoder.encode(min_attendees, "UTF-8") + "&" +
                        URLEncoder.encode("max_attendees", "UTF-8") + "=" + URLEncoder.encode(max_attendees, "UTF-8") + "&" +
                        URLEncoder.encode("1", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
                Log.d("Ziffer",data);
                bufferedWriter.write(data);


                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Event Registered Successfully";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("register"))
        {
            String user_name = params[1];
            String user_pass = params[2];

            Log.d("param",user_pass);
            try
            {
                URL url = new URL(user_reg);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                Log.d("Ziffer",data);
                bufferedWriter.write(data);


                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "User registered successfully";
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("Catchexception1",e.toString());


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Catchexception2",e.toString());

            }
        }

        else if (method.equals("profile_data"))
        {
            String user_name = params[1];
            String firstName = params[2];
            String lastName = params[3];
            String dob = params[4];
            String german_level = params[5];
            String description = params[6];

            Log.d("param",user_name);
            try
            {
                URL url = new URL(profile_data);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                        URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                        URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8") + "&" +
                        URLEncoder.encode("german_level", "UTF-8") + "=" + URLEncoder.encode(german_level, "UTF-8") + "&" +
                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");

                Log.d("Ziffer",data);
                bufferedWriter.write(data);


                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Profile is created!";
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("Catchexception1",e.toString());


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Catchexception2",e.toString());

            }
        }


        else if(method.equals("login"))
        {
            int tmp;
            String user_name = params[1];
            String user_pass = params[2];
            Log.d("logindata",user_name);
            Log.d("logindata",user_pass);
            String data="";
            try {
                URL url = new URL(login_url);
                String urlParams = "user_name="+user_name+"&user_pass="+user_pass;
                Log.d("urlParams",urlParams);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();
                Log.d("SelectData",data);
                if(data.indexOf("user_info_id") >=0)
                return data;
                else
                return "Please Enter correct credentials!!!";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("CatchingLogging",e.getMessage());
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {

        String firstName ="",lastName="",user_name="";
        if(result.equals("Event Registered Successfully"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("User registered successfully"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Profile is created!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.indexOf("user_data") >=0)
        {
            try
            {
                JSONObject root = new JSONObject(result);
                JSONObject user_data = root.getJSONObject("user_data");
              //   firstName = user_data.getString("profile_data.firstName");
               // lastName = user_data.getString("profile_data.lastName");
                 user_name = user_data.getString("user_name");
                Log.d("jsoncheck",user_name);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            Intent i = new Intent(ctx,MainActivity.class);
            i.putExtra("user_name",user_name);
            ctx.startActivity(i);
            Toast.makeText(ctx, "Welcome " + user_name , Toast.LENGTH_LONG).show();

        }
        else if(result.equals("Please Enter correct credentials!!!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
