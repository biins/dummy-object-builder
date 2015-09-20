package org.biins.objectbuilder.classes.real;

import java.util.List;

/**
 * @author Martin Janys
 */
public class HirearchialPage {

    private Long id;
    private boolean root;
    private Page parent;
    private List<Page> childs;

    public HirearchialPage(boolean root) {
        this.root = root;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public boolean isRoot() {
        return root;
    }

    public Page getParent() {
        return parent;
    }

    public void setParent(Page parent) {
        this.parent = parent;
    }

    public List<Page> getChilds() {
        return childs;
    }

    public void setChilds(List<Page> childs) {
        this.childs = childs;
    }

}
