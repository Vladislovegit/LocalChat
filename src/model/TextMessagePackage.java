package model;

public class TextMessagePackage extends DataPackage<TextMessage> {

    public TextMessagePackage() {}

    public TextMessagePackage(TextMessage textMessage) {
        super(textMessage);
    }

    @Override
    public Integer getPackageType() {
        return Constant.TEXT_MESSAGE;
    }

}
