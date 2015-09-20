package org.biins.objectbuilder.classes.real;

/**
 * @author Martin Janys
 */
public class Article {

    public enum Type {
        A,
        B,
        C
    }

    private Long id;
    private String articleType;
    private Integer order;
    private String title;
    private String content;
    private String action;
    private Type displayType;
    private String author;
    private Long pageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Type getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Type displayType) {
        this.displayType = displayType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }
}
