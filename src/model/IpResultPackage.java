package model;

public class IpResultPackage extends DataPackage<IpResult> {

    public IpResultPackage(IpResult ipResult) {
        super(ipResult);
    }

    public IpResultPackage() {
        super();
    }

    @Override
    public Integer getPackageType() {
        return Constant.IP_RESULT_PACKAGE;
    }
}
