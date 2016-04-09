package com.example.bryan.teamproject;

/**
 * Created by Bryan on 3/25/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerRequests {
    private final String serverAddress = "http://128.197.103.77/";
    //private final String serverAddress = "http://155.41.96.36:8000/";
    private final int connection_timeout = 1000 * 15;
    private final String api_getRegister = "apisignup/";
    private final String api_token = "get-token/";
    private ProgressDialog progressDialog;
    private JSONObject token = null;


    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallBack) {
        //progressDialog.show();
        new storeUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        //progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }

    public void fetchProjectDataInBackground(User user, GetProjectCallback callback) {
        //progressDialog.show();
        new fetchProjectDataAsyncTask(user, callback).execute();
    }

    public class storeUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public storeUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.userCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Data to send to server
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("firstname", user.Firstname);
            dataToSend.put("lastname", user.Lastname);
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.passWord);
            dataToSend.put("email", user.email);
            //json object
            String encodedStr = getEncodedData(dataToSend);
            BufferedReader reader = null;
            try {
                //connect ot URL
                URL url = new URL(serverAddress + api_getRegister);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //Post Method
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                //Writing dataToSend to outputstreamwriter
                writer.write(encodedStr);
                writer.flush();
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                line = sb.toString();
                Log.i("custom_check", "The values received in the store part are as follows:");
                Log.i("custom_check", line);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }

        private String getEncodedData(Map<String, String> data) {
            StringBuilder sb = new StringBuilder();
            for (String key : data.keySet()) {
                String value = null;
                try {
                    value = URLEncoder.encode(data.get(key), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;
        User returnedUser = null;

        public fetchUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.userCallback = callback;
        }

        @Override
        protected User doInBackground(Void... params) {
            //Data to send to server

            try {
                String url = serverAddress + api_token;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                //add request header
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                String urlParameters = "";

                // Send post request
                con.setDoOutput(true);
                con.setDoInput(true);

                JSONObject credentials = new JSONObject();
                credentials.put("username", "" + user.username + "");
                credentials.put("password", "" + user.passWord + "");

                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(credentials.toString());
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                System.out.println(con.getResponseMessage().toString());
                System.out.println(con.getHeaderFields().toString());
                System.out.println(con.getContentLength());

                BufferedReader in;
                switch (responseCode) {
                    case 400:
                        returnedUser = new User(user.username, user.passWord, getTokenValue());
                        break;
                    case 200:
                        in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                            if (!in.ready()) {
                                break;
                            }
                        }
                        in.close();
                        //print result
                        token = new JSONObject(response.toString());
                        returnedUser = new User(user.username, user.passWord, getTokenValue());
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                System.out.print(ex.fillInStackTrace());
            }

            return returnedUser;
        }

        public String getTokenValue() {
            String response;
            try {
                response = token.get("token").toString();

            } catch (JSONException e) {
                response = null;
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }


    // class to fetch Projects Async Task
    public class fetchProjectDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private User user;
        private GetProjectCallback projectCallback;
        private Project project = new Project();

        public fetchProjectDataAsyncTask(User user, GetProjectCallback callbackproject) {
            projectCallback = callbackproject;
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Data to send to server
            String api_getProjects = "api/"+user.username+"/projects/";
            String url = serverAddress + api_getProjects;

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "JWT " + user.token);
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in;
                if (responseCode >= 400) {
                    in = new BufferedReader(
                            new InputStreamReader(con.getErrorStream()));
                } else {
                    in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print result
                JSONArray arrays = new JSONArray(response.toString());
                for (int i = 0; i < arrays.length(); i++) {
                    JSONObject c = arrays.getJSONObject(i);
                    project.setTitle(c.getString("title"));
                    project.setDescription(c.getString("description"));
                    project.setOwner(c.getString("owner"));
                    project.setId(c.getInt("id"));
                }
                project.setLoadedInfo(true);
            } catch (Exception ex) {
                System.out.print(ex.fillInStackTrace());
            }
            return project.getLoaded();
        }

        @Override
        protected void onPostExecute(Boolean loaded) {
            //progressDialog.dismiss();
            projectCallback.done(loaded);
            super.onPostExecute(loaded);
        }
    }

}