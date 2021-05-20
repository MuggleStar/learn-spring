package learn.base.myutil;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Json工具
 *
 * @author MuggleStar
 * @date 2020/12/7 23:07
 */
public class JsonUtil {

    private static Gson gson = null;

    private JsonUtil() {
    }

    static {
        synchronized (JsonUtil.class) {
            if (gson == null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
                gsonBuilder.disableHtmlEscaping();
                gsonBuilder.serializeSpecialFloatingPointValues();
                gson = gsonBuilder.create();
            }
        }
    }

    public static Gson getInstance() {
        return gson;
    }

    /**
     * 对象转json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return gson.toJson(object);
    }

    /**
     * json转对象
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        T t = null;
        json = StringUtils.trimWhitespace(json);
        if (gson != null && !StringUtils.isEmpty(json)) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }

    /**
     * json转List<T>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        json = StringUtils.trimWhitespace(json);
        List<T> list = null;
        if (gson != null && !StringUtils.isEmpty(json)) {
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                if (list == null) {
                    list = new ArrayList<T>();
                }
                list.add(gson.fromJson(jsonElement, clazz));
            }
        }
        return list;
    }

    /**
     * json转 List<Map<String, T>>
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, T>> json2MapList(String json, Class<T> clazz) {
        List<Map<String, T>> list = null;
        json = StringUtils.trimWhitespace(json);
        if (gson != null && !StringUtils.isEmpty(json)) {
            list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json转 Map<String, T>
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> json2Map(String json, Class<T> clazz) {
        Map<String, T> map = null;
        json = StringUtils.trimWhitespace(json);
        if (gson != null && !StringUtils.isEmpty(json)) {
            map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
