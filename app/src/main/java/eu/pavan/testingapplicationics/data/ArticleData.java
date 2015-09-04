package eu.pavan.testingapplicationics.data;

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

    private static final Article emptyArticle = new Article("0", "No Article", "No Content");

    /**
     * An array of sample (dummy) items.
     */
    public static List<Article> ARTICLES = new ArrayList<Article>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Article> ARTICLE_MAP = new HashMap<String, Article>();

    static {
        addArticle(emptyArticle);
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
        public String content;
        public String heading;

        public Article(String id, String heading, String content) {
            this.id = id;
            this.content = content;
            this.heading = heading;
        }

        @Override
        public String toString() {
            return content;
        }
    }

}
