package model;


public class TextMessage {

    private String text;
    private Long id;
    private String owner;

    public TextMessage() {}

    public TextMessage(String text, Long id, String owner) {
        this.text = text;
        this.id = id;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
