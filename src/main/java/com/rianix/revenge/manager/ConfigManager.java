package com.rianix.revenge.manager;

// ez by s1ash

import com.google.gson.*;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

public class ConfigManager {

    private static final String CONFIG_FOLDER = Revenge.NAME + "/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser PARSER = new JsonParser();

    private ConfigManager() {
    }

    public static void load() {
        if (Files.notExists(Paths.get(CONFIG_FOLDER))) return;
        if (Files.exists(Paths.get(CONFIG_FOLDER + "other/friends.json"))) {
            try (InputStream stream = Files.newInputStream(Paths.get(CONFIG_FOLDER + "other/friends.json"))) {
                FriendsManager.deserialize(PARSER.parse(new InputStreamReader(stream)).getAsJsonArray());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        for (Module module : Revenge.moduleManager.getModules()) {
            if (Files.notExists(Paths.get(CONFIG_FOLDER + module.getCategory().name() + "/" + module.getName() + ".json")))
                continue;
            try (InputStream stream = Files.newInputStream(Paths.get(CONFIG_FOLDER + module.getCategory().name() + "/" + module.getName() + ".json"))) {
                JsonElement el = PARSER.parse(new InputStreamReader(stream));
                if (!el.isJsonObject()) continue;
                JsonObject obj = el.getAsJsonObject();
                if (obj.has("bind")) module.setKey(obj.get("bind").getAsInt());
                if (obj.has("enabled")) module.setToggled(obj.get("enabled").getAsBoolean());
                if (obj.has("settings")) {
                    JsonObject setObj = obj.getAsJsonObject("settings");
                    Revenge.settingsManager.getSettingsInMod(module).forEach(s -> {
                        if (setObj.has(s.getName())) {
                            switch (s.getType()) {
                                case INTEGER:
                                    ((SettingInteger) s).setValue(setObj.get(s.getName()).getAsInt());
                                    break;
                                case DOUBLE:
                                    ((SettingDouble) s).setValue(setObj.get(s.getName()).getAsDouble());
                                    break;
                                case BOOLEAN:
                                    ((SettingBoolean) s).setValue(setObj.get(s.getName()).getAsBoolean());
                                    break;
                                case MODE:
                                    ((SettingMode) s).setValue(setObj.get(s.getName()).getAsString());
                                    break;
                                case COLOR:
                                    JsonArray array = setObj.get(s.getName()).getAsJsonArray();
                                    if (array.size() >= 4) {
                                        SettingColor cs = (SettingColor) s;
                                        cs.setAWTValue(new Color(array.get(0).getAsInt(), array.get(1).getAsInt(), array.get(2).getAsInt(), array.get(3).getAsInt()));
                                    }
                                    break;
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        if (Files.notExists(Paths.get(CONFIG_FOLDER + "other"))) {
            try {
                Files.createDirectories(Paths.get(CONFIG_FOLDER + "other"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!FriendsManager.FRIENDS.isEmpty()) {
            String json = GSON.toJson(FriendsManager.serialize());
            try {
                Files.write(Paths.get(CONFIG_FOLDER + "other/friends.json"), json.getBytes(StandardCharsets.UTF_8));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        for (Module.Category category : Module.Category.values()) {
            if (Files.notExists(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT)))) {
                try {
                    Files.createDirectories(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Module module : Revenge.moduleManager.getModsInCategory(category)) {
                JsonObject obj = new JsonObject();
                obj.addProperty("bind", module.getKey());
                obj.addProperty("enabled", module.isToggled());
                JsonObject setObj = new JsonObject();
                Revenge.settingsManager.getSettingsInMod(module).forEach(s -> {
                    switch (s.getType()) {
                        case INTEGER:
                            setObj.addProperty(s.getName(), ((SettingInteger) s).getValue());
                            break;
                        case DOUBLE:
                            setObj.addProperty(s.getName(), ((SettingDouble) s).getValue());
                            break;
                        case BOOLEAN:
                            setObj.addProperty(s.getName(), ((SettingBoolean) s).getValue());
                            break;
                        case MODE:
                            setObj.addProperty(s.getName(), ((SettingMode) s).getValue());
                            break;
                        case COLOR:
                            JsonArray array = new JsonArray();
                            Color value = ((SettingColor) s).getAWTValue(false);
                            array.add(value.getRed());
                            array.add(value.getGreen());
                            array.add(value.getBlue());
                            array.add(value.getAlpha());
                            setObj.add(s.getName(), array);
                            break;
                    }
                });
                obj.add("settings", setObj);
                String json = GSON.toJson(obj);
                try {
                    Files.write(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT) + "/" + module.getName() + ".json"), json.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
