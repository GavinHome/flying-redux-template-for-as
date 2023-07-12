package com.gavinhome.flutter.flyingreduxtemplate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class Utils {
    private static String SOURCE_SEPARATOR = "/";
    public static void GenerateComponent(ClassLoader classLoader, @NotNull String dest, @NotNull String name, @NotNull JSONArray config) {
        var srcPath = "%s%s%s".formatted("templates", SOURCE_SEPARATOR, "component");
        var destPath = "%s%s%s".formatted(dest, File.separator, name + "_component");
        CopyFolder(classLoader, srcPath, destPath, name, getValueKey(config, "component"));
    }

    public static void GeneratePage(ClassLoader classLoader, @NotNull String src, @NotNull String dest, @NotNull String name, @NotNull JSONArray config) {
        var srcPath = "%s%s%s".formatted("template", SOURCE_SEPARATOR, "page");
        var destPath = "%s%s%s".formatted(dest, File.separator, name);
        CopyFolder(classLoader, srcPath, destPath, name, getValueKey(config, "page"));
    }

    public static boolean IfExists(@NotNull String name, @NotNull String path) {
        var folderPath = ("%s%s%s").formatted(path, File.separator, name);
        return new File(folderPath).exists();
    }

    public static JSONArray getJSONArray(String input) {
        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(input);
            return jsonArray;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new JSONArray();
    }

    private static JSONArray getValueKey(JSONArray array, String key) {
        JSONArray value = null;
        for (int i = 0; i < array.stream().count(); i++) {
            //JSONObject item = getJSONObject(array.get(i).toString());
            JSONObject item = (JSONObject) array.get(i);
            if (item.keySet().contains(key)) {
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

        for (int i = 0; i < config.stream().count(); i++) {
            if (config.get(i) instanceof String) {
                var source = "%s%s%s".formatted(src, SOURCE_SEPARATOR, config.get(i));
                var target = "%s%s%s".formatted(dest, File.separator, config.get(i));
                CopyFile(classLoader, source, target, name);
            } else if(config.get(i) instanceof JSONObject) {
                JSONObject item = (JSONObject) config.get(i);
                var source = "%s%s%s".formatted(src, SOURCE_SEPARATOR, item.keySet().toString());
                var target = "%s%s%s".formatted(dest, File.separator, item.keySet().toString());
                CopyFolder(classLoader, source, target, name, getJSONArray(((JSONObject) config.get(i)).toJSONString()));
            }
        }
//        File srcPath = new File(src);
//        for (final File fileEntry : srcPath.listFiles()) {
//            var source = "%s%s%s".formatted(src, File.separator, fileEntry.getName());
//            var target = "%s%s%s".formatted(dest, File.separator, fileEntry.getName());
//            if (fileEntry.isDirectory()) {
//                CopyFolder(source, target, name);
//            } else if (fileEntry.isFile()) {
//                CopyFile(source, target, name);
//            }
//        }
    }

    private static void CopyFile(ClassLoader classLoader, @NotNull String src, @NotNull String dest, @NotNull String name) {
        String content = readFile(classLoader, src);
        if (name.length() > 0) {
            content = replace(content, name);
        }
        writeFile(content, dest);
//        try {
////            String content = new String(readStream(new FileInputStream(src)));
//            InputStream inStream = classLoader.getResourceAsStream(src);
//            String content = new String(readStream(inStream));
//            if (name.length() > 0) {
//                content = replace(content, name);
//            }
//            writeFile(content, dest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            inStream.close();
//        }
    }

    private static String readFile(ClassLoader classLoader, String filename) {
        InputStream inStream = classLoader.getResourceAsStream(filename);
        String content = "";
        try {
            content = new String(readStream(inStream));
        } catch (Exception e) {
        }
        return content;
    }

    private static String replace(String content, String target) {
        content = content.replaceAll("\\$name", target);
        return content;
    }

    private static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
                System.out.println(new String(buffer));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            outSteam.close();
//            inStream.close();
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
