package com.psyvern.status_changer;

import net.minecraft.server.MinecraftServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public record Status(String motd, String icon) {

    public String encodeIcon(MinecraftServer server) throws IOException {

        File file = server.getFile(icon);
        if (file.isFile()) {

            BufferedImage image = ImageIO.read(file);
            if (image.getWidth() != 64 || image.getHeight() != 64) throw new IllegalArgumentException("Icon must be 64x64");
            try (var output = new ByteArrayOutputStream()) {

                ImageIO.write(image, "png", output);
                return "data:image/png;base64," + new String(Base64.getEncoder().encode(output.toByteArray()), StandardCharsets.UTF_8);
            }
        }

        throw new FileNotFoundException("Icon file \"%s\" not found".formatted(icon));
    }
}