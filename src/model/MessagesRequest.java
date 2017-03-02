package model;

import java.util.ArrayList;

public class MessagesRequest {

    ArrayList<Long> messagesIds = new ArrayList<>();

    public MessagesRequest() {}

    public MessagesRequest(ArrayList<Long> messagesIds) {
        this.messagesIds = messagesIds;
    }

    public ArrayList<Long> getMessagesIds() {
        return messagesIds;
    }

    public void setMessagesIds(ArrayList<Long> messagesIds) {
        this.messagesIds = messagesIds;
    }
}
