package com.gavinhome.flutter.flyingreduxtemplate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "ResultOfMethodCallIgnored", "deprecation"})
public class Utils {
    private static final String SOURCE_SEPARATOR = "/";

    public static void GenerateComponent(ClassLoader classLoader, @NotNull String dest, @NotNull String name) {
        var key = "component";
        var config = Utils.getJSONArray(getConfigJson(classLoader).getAsString("templates"));
        var srcPath = "%s%s%s".formatted("templates", SOURCE_SEPARATOR, key);
        var destPath = "%s%s%s".formatted(dest, File.separator, name + "_component");
        CopyFolder(classLoader, srcPath, destPath, name, getValueKey(config, key));
    }

    public static void GeneratePage(ClassLoader classLoader, @NotNull String dest, @NotNull String name) {
        var key = "page";
        var config = Utils.getJSONArray(getConfigJson(classLoader).getAsString("templates"));
        var srcPath = "%s%s%s".formatted("templates", SOURCE_SEPARATOR, key);
        var destPath = "%s%s%s".formatted(dest, File.separator, name + "_page");
        CopyFolder(classLoader, srcPath, destPath, name, getValueKey(config, key));
    }

    public static void GenerateTodos(ClassLoader classLoader, String dest, String name) {
        var key = "todos";
        var config = Utils.getJSONArray(getConfigJson(classLoader).getAsString("templates"));
        var srcPath = "%s%s%s".formatted("templates", SOURCE_SEPARATOR, key);
        var destPath = "%s%s%s".formatted(dest, File.separator, name);
        CopyFolder(classLoader, srcPath, destPath, name, getValueKey(config, key));
    }

    public static boolean IfNotExists(@NotNull String name, @NotNull String path) {
        var folderPath = ("%s%s%s").formatted(path, File.separator, name);
        return !new File(folderPath).exists();
    }

    public static JSONArray getJSONArray(String input) {
        try {
            JSONArray jsonArray;
            jsonArray = (JSONArray) new JSONParser().parse(input);
            return jsonArray;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new JSONArray();
    }

    private static JSONObject getConfigJson(ClassLoader classLoader) {
        try {
            var inputStream = classLoader.getResourceAsStream("config.json");
            JSONObject jsonObject;
            assert inputStream != null;
            jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream));
            return jsonObject;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new JSONObject();
    }

    private static JSONArray getValueKey(JSONArray array, String key) {
        JSONArray value = null;
        for (Object o : array) {
            JSONObject item = (JSONObject) o;
            if (item.containsKey(key)) {
                value = (JSONArray) item.get(key);
                break;
            }
        }

        return value;
    }

    private static void CopyFolder(ClassLoader classLoader, @NotNull String src, @NotNull String dest, @NotNull String name, JSONArray config) {
        File destPath = new File(dest);
        if (!destPath.exists()) {
            destPath.mkdirs();
        }

        for (int i = 0; i < (long) config.size(); i++) {
            if (config.get(i) instanceof String) {
                var source = "%s%s%s".formatted(src, SOURCE_SEPARATOR, config.get(i));
                var target = "%s%s%s".formatted(dest, File.separator, config.get(i));
                CopyFile(classLoader, source, target, name);
            } else if (config.get(i) instanceof JSONObject item) {
                var key = item.keySet().stream().findFirst().get();
                var source = "%s%s%s".formatted(src, SOURCE_SEPARATOR, key);
                var target = "%s%s%s".formatted(dest, File.separator, key);
                var jsonArray = (JSONArray) item.get(key);
                CopyFolder(classLoader, source, target, name, jsonArray);
            }
        }
    }

    private static void CopyFile(ClassLoader classLoader, @NotNull String src, @NotNull String dest, @NotNull String name) {
        String content = readFile(classLoader, src);
        if (name.length() > 0) {
            content = replace(content, name);
        }
        writeFile(content, dest);
    }

    private static String readFile(ClassLoader classLoader, String filename) {
        InputStream inStream = classLoader.getResourceAsStream(filename);
        String content = "";
        try {
            assert inStream != null;
            content = new String(readStream(inStream));
        } catch (Exception ignored) {
        }
        return content;
    }

    private static String replace(String content, String target) {
        content = content.replaceAll("\\$name", target);
        return content;
    }

    private static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
                System.out.println(new String(buffer));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outSteam.toByteArray();
    }

    private static void writeFile(String content, String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
