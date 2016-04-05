package com.example.bryan.teamproject;

/**
 * Created by Bryan on 3/25/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ServerRequests {
    //private final String localhost = "http://127.0.0.1:8000/";
    private final String serverAddress = "http://128.197.103.77/";
    private final int connection_timeout = 1000 * 15;
    //String api_getProjects = "api/projects/";
    private final String api_getRegister = "signup/";
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
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("firstname", user.Firstname));
            dataToSend.add(new BasicNameValuePair("lastname", user.Lastname));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.passWord));
            dataToSend.add(new BasicNameValuePair("email", user.email));

            //Set to servertimeouts and requests
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, connection_timeout);
            HttpConnectionParams.setSoTimeout(httpRequestParams, connection_timeout);

            //set client
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(serverAddress + api_getRegister);
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
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
                    if (!in.ready()) {
                        break;
                    }
                }
                in.close();
                //print result
                token = new JSONObject(response.toString());

            } catch (Exception ex) {
                System.out.print(ex.fillInStackTrace());
            }
            returnedUser = new User(user.username, user.passWord, getTokenValue());
            //fetchProjects();
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
            String api_getProjects = "api/projects/";
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