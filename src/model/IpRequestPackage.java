package model;

public class IpRequestPackage extends BasePackage {

    public IpRequestPackage() {
        super();
    }

    @Override
    public Integer getPackageType() {
        return Constant.IP_REQUEST_PACKAGE;
    }
}
