package eu.pavan.testingapplicationics;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.pavan.testingapplicationics.data.ArticleData;

/**
 * Created by merlinwho.
 */
class DownloadArticlesTask extends AsyncTask<String, String, String> {
    private List<ArticleData.Article> articles;
    Activity activity;
    ArticlesArrayAdapter arrayAdapter;

    public DownloadArticlesTask(Activity activity, ArticlesArrayAdapter arrayAdapter) {
        this.activity = activity;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected void onProgressUpdate(String... values) {
//        for (String value : values) {
//            Toast.makeText(activity, value, Toast.LENGTH_LONG).show();
//        }
    }

    private void createArticles(String page) {
        articles = new ArrayList<>();
        int position = page.indexOf("<h3 class=\"title\"><a href=\"https://dennikn.sk/blog/");
        int id = 1;
        String heading;
        String text;
        while (position != -1) {
            int urlStart = page.indexOf("https://", position);
            int urlEnd = page.indexOf("\">", urlStart);
            int headingStart = urlEnd + 2;
            int headingEnd = page.indexOf("</a></h3>", headingStart);
            heading = page.substring(headingStart, headingEnd);
            text = extractTextFromUrl(page.substring(urlStart, urlEnd));
            articles.add(new ArticleData.Article("" + id++, Html.fromHtml(heading), Html.fromHtml(text)));

            position = page.indexOf("<h3 class=\"title\"><a href=\"https://dennikn.sk/blog/", headingEnd);
        }
    }

    private String extractTextFromUrl(String url) {
        Log.e(this.getClass().getName(), "URL requested: " + url);
        String urlData = getStringFromUrl(url);
        if (urlData == null || urlData.length() < 1) {
            return "";
        }
        int position = urlData.indexOf("<article class=\"b");
        position = urlData.indexOf(">", position) + 1;
        int endPosition = urlData.indexOf("</article>", position);
        return urlData.substring(position, endPosition);
    }

    @Override
    protected String doInBackground(String... params) {
        String testedUrl = "https://dennikn.sk/autor/michal-patarak/page/2/";
        String contentAsString = getStringFromUrl(testedUrl);

        publishProgress("Processing data: " + contentAsString.substring(1000, 2000));
        createArticles(contentAsString);

        return null;
    }

    private String getStringFromUrl(String requestedUrl) {
        String resultString = "";
        InputStream is = null;
        try {
            URL url = new URL(requestedUrl);
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
            resultString = s.hasNext() ? s.next() : "";
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
        return resultString;
    }

    @Override
    protected void onPostExecute(String s) {
        ArticleData.addArticles(articles);
        arrayAdapter.notifyDataSetChanged();
    }
}