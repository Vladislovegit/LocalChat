package model;

public class IpRequestPackage extends EmptyPackage{

    public IpRequestPackage() {
        super();
    }

    @Override
    public Long getPackageType() {
        return Constant.IP_REQUEST_PACKAGE;
    }
}
