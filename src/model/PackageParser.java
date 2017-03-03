package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

public class PackageParser {

    public static Integer getType(byte[] bytes) {
        JsonReader reader = new JsonReader(new StringReader(new String(bytes)));
        reader.setLenient(true);
        return ((EmptyPackage)new Gson().fromJson(reader, EmptyPackage.class)).getType();
    }

    public static <Data extends DataPackage> Data fromBytes(byte[] bytes, Class<Data> classOfT) {
        JsonReader reader = new JsonReader(new StringReader(new String(bytes)));
        reader.setLenient(true);
        return new Gson().fromJson(reader, classOfT);
    }
}
