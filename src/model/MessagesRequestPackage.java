package model;


public class MessagesRequestPackage extends DataPackage<MessagesRequest> {

    public MessagesRequestPackage() {
        super();
    }

    public MessagesRequestPackage(MessagesRequest data) {
        super(data);
    }

    @Override
    public Integer getPackageType() {
        return Constant.MESSAGES_REQUEST_PACKAGE;
    }
}
