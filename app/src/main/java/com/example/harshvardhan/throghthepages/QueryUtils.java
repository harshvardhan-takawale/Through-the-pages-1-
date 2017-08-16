package com.example.harshvardhan.throghthepages;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 6/6/2017.
 */

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    public static List<book_attributes> fetchBooksdata(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<book_attributes> earthquakes = extractFeaturefromJson(jsonResponse);

        return earthquakes;

    }

    private static URL createUrl(String stringurl){
        URL url = null;
        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch(IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static List<book_attributes> extractFeaturefromJson(String jsonforBooks){
        if (TextUtils.isEmpty(jsonforBooks)){
            return null;
        }

        List<book_attributes> allbooklistresp = new ArrayList<>();

        try {
            JSONObject baseJsonobject = new JSONObject(jsonforBooks);
            JSONArray bookshelf = baseJsonobject.getJSONArray("items");

            for (int i=0; i< bookshelf.length(); i++){
                JSONObject currentbook = bookshelf.getJSONObject(i);
                JSONObject volumeinfo = currentbook.getJSONObject("volumeInfo");

                String BookTitle = volumeinfo.getString("title");
                String noofpages = volumeinfo.getString("pageCount");
                int pages = (int) Integer.parseInt(noofpages);
                JSONArray authorlist = volumeinfo.getJSONArray("authors");
                String MainAuthor;
                if (authorlist!=null){
                MainAuthor = authorlist.getString(0);}
                else { MainAuthor = "Anonymous"; }

                book_attributes newBook = new book_attributes(BookTitle, MainAuthor, pages);
                allbooklistresp.add(newBook);
            }


        } catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return allbooklistresp;
    }

    private static String readFromStream(InputStream inputstream) throws IOException{
        StringBuilder output  =  new StringBuilder();
        if(inputstream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
