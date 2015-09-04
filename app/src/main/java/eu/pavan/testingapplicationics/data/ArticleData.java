package eu.pavan.testingapplicationics.data;

import android.text.Spanned;
import android.text.SpannedString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class ArticleData {

    private static final Article emptyArticle = new Article("0", new SpannedString("No Article"), new SpannedString("No Content"));

    /**
     * An array of sample (dummy) items.
     */
    public static List<Article> ARTICLES = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Article> ARTICLE_MAP = new HashMap<>();

    static {
        addArticle(emptyArticle);
    }

    public static void addArticles(List<Article> items) {
        if (ARTICLES.contains(emptyArticle)) {
            ARTICLES.clear();
        }
        for (Article item : items) {
            ARTICLES.add(item);
            ARTICLE_MAP.put(item.id, item);
        }
    }

    public static void addArticle(Article item) {
        if (ARTICLES.contains(emptyArticle)) {
            ARTICLES.clear();
        }
        ARTICLES.add(item);
        ARTICLE_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Article {
        public String id;
        public Spanned content;
        public Spanned heading;

        public Article(String id, Spanned heading, Spanned content) {
            this.id = id;
            this.content = content;
            this.heading = heading;
        }
    }

}
