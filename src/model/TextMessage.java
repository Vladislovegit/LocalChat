package model;


public class TextMessage {

    private String text;
    private Long id;

    public TextMessage() {}

    public TextMessage(String text, Long id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
