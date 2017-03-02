package model;

public class TextMessagePackage extends DataPackage<TextMessage> {

    public TextMessagePackage(TextMessage textMessage) {
        super(textMessage);
    }

    @Override
    public Long getPackageType() {
        return Constant.TEXT_MESSAGE;
    }

}
