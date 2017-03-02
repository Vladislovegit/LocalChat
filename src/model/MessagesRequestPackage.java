package model;


public class MessagesRequestPackage extends DataPackage<MessagesRequest> {

    public MessagesRequestPackage(MessagesRequest data) {
        super(data);
    }

    @Override
    public Long getPackageType() {
        return Constant.MESSAGES_REQUEST_PACKAGE;
    }
}
