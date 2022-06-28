package com.psyvern.status_changer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class ModConfig {

    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(StatusChanger.ID + ".json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public int delay = 60 * 60;
    public Map<String, String> statuses = Map.of("", "server-icon.png");

    public static ModConfig loadConfig() {

        var config = readFile().orElseGet(ModConfig::new);

        config.writeFile();

        return config;
    }

    private static Optional<ModConfig> readFile() {

        if (!Files.isRegularFile(CONFIG_FILE)) return Optional.empty();

        try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE)) {

            return Optional.of(GSON.fromJson(reader, ModConfig.class));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    private void writeFile() {

        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE)) {

            var jsonWriter = GSON.newJsonWriter(writer);
            jsonWriter.setIndent("    ");
            GSON.toJson(GSON.toJsonTree(this), jsonWriter);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}