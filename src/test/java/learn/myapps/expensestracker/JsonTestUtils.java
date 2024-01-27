package learn.myapps.expensestracker;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonTestUtils<T> {


    private final Class<T> typeParameterClass;

    public JsonTestUtils(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public String getJson(CharSequence source) {
        if (source == null) {
            return null;
        } else {
            return source.toString().endsWith(".json") ? this.getJson((Resource) (new ClassPathResource(source.toString(), this.typeParameterClass))) : source.toString();
        }
    }

    private String getJson(Resource source) {
        try {
            return this.getJson(source.getInputStream());
        } catch (IOException var3) {
            throw new IllegalStateException("Unable to load JSON from " + source, var3);
        }
    }

    private String getJson(InputStream source) {
        try {
            return FileCopyUtils.copyToString(new InputStreamReader(source, StandardCharsets.UTF_8));
        } catch (IOException var3) {
            throw new IllegalStateException("Unable to load JSON from InputStream", var3);
        }
    }
}
