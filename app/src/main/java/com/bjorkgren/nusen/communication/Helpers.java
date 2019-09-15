package com.bjorkgren.nusen.communication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class Helpers {

    public static String getStringDataFromUrl(String searchUrl){
        URLConnection conn = null;
        InputStream in = null;
        String contents = "";

        URL url = null;
        try {
            url = new URL(searchUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();
        } catch (MalformedURLException e) {
            Log.e("url - error", e.getMessage());
        } catch (URISyntaxException e) {
            Log.e("url - error", e.getMessage());
        }



        try {
            conn = url.openConnection();

            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept","*/*"); //viktigt eftersom de är https

            in = conn.getInputStream();
            return Helpers.convertStreamToString(in);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Content - error", e.getMessage());
        }
        return "";
    }

    private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
