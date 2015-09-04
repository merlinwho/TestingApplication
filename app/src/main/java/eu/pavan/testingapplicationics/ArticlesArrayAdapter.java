package eu.pavan.testingapplicationics;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import eu.pavan.testingapplicationics.data.ArticleData;

/**
 * Created by merlinwho on 9/4/15.
 */
public class ArticlesArrayAdapter extends ArrayAdapter<ArticleData.Article> {

    Activity context;
    List<ArticleData.Article> items;
    int resource;
    int textViewResourceId;

    public ArticlesArrayAdapter(Activity context, int resource, int textViewResourceId, List<ArticleData.Article> objects) {
        super(context, resource, objects);
        this.context = context;
        this.items = objects;
        this.textViewResourceId = textViewResourceId;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = this.context.getLayoutInflater().inflate(resource, null);
        final ArticleData.Article item = this.items.get(position);
        ((TextView) view.findViewById(textViewResourceId)).setText(item.heading);
        return view;
    }
}
