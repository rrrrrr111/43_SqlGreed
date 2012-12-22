package ru.roman.bim.gui.pane.main;

import ru.roman.bim.gui.common.Model;

/** @author Roman 19.12.12 23:36 */
public class MainViewModel implements Model {


    public MainViewModel() {}

    private String textFaced;
    private String textShadowed;
    private String type;
    private Integer rating;


    public String getTextFaced() {
        return textFaced;
    }

    public void setTextFaced(String textFaced) {
        this.textFaced = textFaced;
    }

    public String getTextShadowed() {
        return textShadowed;
    }

    public void setTextShadowed(String textShadowed) {
        this.textShadowed = textShadowed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
