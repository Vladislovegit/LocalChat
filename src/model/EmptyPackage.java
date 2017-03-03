package model;


public final class EmptyPackage extends BasePackage {

    EmptyPackage() {
        super();
    }

    @Override
    public Integer getPackageType() {
        return type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
