package com.example.ginz.funnyphoto.data.source.source.remote;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.ginz.funnyphoto.configuration.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegistrationAsyncTask extends AsyncTask<String, Void, String> {
    private static final int DEFAULT_TIMEOUT = 10000;
    private HttpURLConnection mConn;
    private String mApiUrl;
    private Map<String, String> mParameters;
    private int mConnectTimeout;
    private int mReadTimeout;

    private RegistrationAsyncTask(Builder builder){
        mApiUrl = builder.mApiUrl;
        mParameters = builder.mParameters;
        mConnectTimeout = builder.mConnectTimeout;
        mReadTimeout = builder.mReadTimeout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(mApiUrl);
            mConn = (HttpURLConnection) url.openConnection();
            setupParameter(mParameters);
            return resqonse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private String resqonse() throws IOException {

        StringBuilder result = new StringBuilder();
        InputStream is = mConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while ((line = reader.readLine()) != null){
            result.append(line);
        }
        return result.toString();
    }

    private void setupParameter(Map<String, String> parameters) throws IOException {
        mConn.setConnectTimeout(mConnectTimeout);
        mConn.setReadTimeout(mReadTimeout);
        if(parameters.size() > 0){
            mConn.setRequestMethod(Constants.Method.METHOD_POST);
            mConn.setDoInput(true);
            mConn.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();
            for(String key : parameters.keySet()){
                builder.appendQueryParameter(key, parameters.get(key));
            }
            String query = builder.build().getEncodedQuery();
            OutputStream os = mConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            mConn.connect();
        } else {
            return;
        }
    }

    public static class Builder {
        private String mApiUrl;
        private Map<String, String> mParameters;
        private int mConnectTimeout;
        private int mReadTimeout;

        public Builder() {
            mParameters = new HashMap<>();
            mConnectTimeout = DEFAULT_TIMEOUT;
            mReadTimeout = DEFAULT_TIMEOUT;
        }

        public Builder setApiUrl(String url){
            this.mApiUrl = url;
            return this;
        }

        public Builder addParameter(String key, String value){
            mParameters.put(key, value);
            return this;
        }

        public Builder setConnectTimeOut(int connectTimeout){
            this.mReadTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeOut(int readTimeout){
            this.mConnectTimeout = readTimeout;
            return this;
        }

        public RegistrationAsyncTask build(){
            return new RegistrationAsyncTask(this);
        }
    }
}