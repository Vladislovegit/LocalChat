package model;


import com.google.gson.Gson;

public abstract class EmptyPackage {

    private Long type = 0L;

    public EmptyPackage() {}

    public abstract Long getPackageType();

    public static Long getPackageType(byte[] bytes) {
        return new Gson().fromJson(new String(bytes), EmptyPackage.class).type;
    }

    public byte[] toBytes() {
        type = getPackageType();
        return new Gson().toJson(this).getBytes();
    }
}
