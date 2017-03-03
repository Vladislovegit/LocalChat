package model;


import com.google.gson.Gson;

public abstract class BasePackage {

    protected Integer type = 0;

    public BasePackage() {}

    public abstract Integer getPackageType();

    public byte[] toBytes() {
        type = getPackageType();
        return new Gson().toJson(this).getBytes();
    }
}
