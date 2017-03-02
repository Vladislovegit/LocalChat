package model;

public class IpResultPackage extends EmptyPackage {

    public IpResultPackage() {
        super();
    }

    @Override
    public Long getPackageType() {
        return Constant.IP_RESULT_PACKAGE;
    }
}
