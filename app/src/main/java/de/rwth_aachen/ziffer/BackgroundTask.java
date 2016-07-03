package de.rwth_aachen.ziffer;

/**
 * Created by jasimwhd on 6/29/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
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
        String user_reg = "http://192.168.0.19:8000/webapp/user_registration.php";
        String reg_url = LocalSettings.Base_URL + "register.php";
        String login_url = LocalSettings.Base_URL + "login.php";
        String method = params[0];
        if (method.equals("event"))
        {
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
            try
            {
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
                return "Event Registered Successfully";
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
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
                return "User name registered successfully";
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("Catchexception",e.toString());


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Catchexception",e.toString());

            }
        }


        else if(method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            Log.d("logindata",login_name);
            Log.d("logindata",login_pass);
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("ResponseDB",response);
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.print("Exception 1");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Exception 1");
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
        if(result.equals("Event Registered Successfully"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("User name registered successfully"))
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
