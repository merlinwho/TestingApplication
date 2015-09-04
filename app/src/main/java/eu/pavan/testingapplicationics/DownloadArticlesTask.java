package eu.pavan.testingapplicationics;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import eu.pavan.testingapplicationics.data.ArticleData;

/**
 * Created by merlinwho on 9/4/15.
 */
class DownloadArticlesTask extends AsyncTask<String, String, String> {

    Activity activity;
    ArticlesArrayAdapter arrayAdapter;

    public DownloadArticlesTask(Activity activity, ArticlesArrayAdapter arrayAdapter) {
        this.activity = activity;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        for (String value : values) {
            Toast.makeText(activity, value, Toast.LENGTH_LONG).show();
        }
    }

    private void createArticles(String page) {
        ArticleData.addArticle(new ArticleData.Article("1", "test1", page.substring(1000, 2000)));
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream is = null;
        String testedUrl = "https://dennikn.sk/autor/michal-patarak/page/2/";

        try {
            URL url = new URL(testedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DownloadArticlesTask", "The response is: " + response);
            is = conn.getInputStream();

            java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
            String contentAsString = s.hasNext() ? s.next() : "";

            publishProgress("Processing data: " + contentAsString.substring(1000, 2000));
            createArticles(contentAsString);
//                Toast.makeText(ArticleListActivity.this, "Processing data: " + contentAsString.substring(0, 100), Toast.LENGTH_LONG).show();

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            Log.e("DownloadArticlesTask", "Wrong URL", e);
        } catch (ProtocolException e) {
            Log.e("DownloadArticlesTask", "Wrong Protocol", e);
        } catch (IOException e) {
            Log.e("DownloadArticlesTask", "Wrong IO", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("DownloadArticlesTask", "Wrong IO", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        arrayAdapter.notifyDataSetChanged();
    }
}