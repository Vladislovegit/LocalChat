package model;

public abstract class DataPackage<PackageData> extends EmptyPackage {

    private PackageData data;

    public DataPackage() {}

    public DataPackage(PackageData data) {
        this.data = data;
    }

    public PackageData getData() {
        return data;
    }

    public void setData(PackageData data) {
        this.data = data;
    }
}
