package org.biins.objectbuilder.classes.real;

import java.util.List;

/**
 * @author Martin Janys
 */
public class Page extends HirearchialPage {

    private String title;
    private String friendlyUrl;
    private Integer order;
    private List<Article> articles;
    private String layout;
    private String articleLayout;
    private boolean visible;

    public Page() {
        super(false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFriendlyUrl() {
        return friendlyUrl;
    }

    public void setFriendlyUrl(String friendlyUrl) {
        this.friendlyUrl = friendlyUrl;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getArticleLayout() {
        return articleLayout;
    }

    public void setArticleLayout(String articleLayout) {
        this.articleLayout = articleLayout;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public boolean hasChildren() {
        return getChilds() != null && !getChilds().isEmpty();
    }

    public boolean hasVisibleChildren() {
        if (getChilds() == null)
            return false;
        for (Page page : getChilds()) {
            if (page.isVisible())
                return true;
        }

        return false;
    }
}
