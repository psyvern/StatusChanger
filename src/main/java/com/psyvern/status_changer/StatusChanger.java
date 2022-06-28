package com.psyvern.status_changer;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StatusChanger implements DedicatedServerModInitializer {

	public static final String ID = "status_changer";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static StatusChanger INSTANCE;

	public ModConfig config;

	@Override
	public void onInitializeServer() {

		INSTANCE = this;
		config = ModConfig.loadConfig();

		ServerLifecycleEvents.SERVER_STARTED.register(this::startTimer);
		ServerLifecycleEvents.SERVER_STOPPING.register(this::stopTimer);
	}

	private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);

	public void startTimer(MinecraftServer server) {

		var statuses = config.statuses.keySet().stream().map(key -> new Status(key, config.statuses.get(key))).toList();
		var random = new Random();

		executor.scheduleAtFixedRate(() -> {

			var status = statuses.get(random.nextInt(statuses.size()));

			try {

				var metadata = server.getServerMetadata();

				metadata.setDescription(Text.of(status.motd().isEmpty() ? server.getServerMotd() : status.motd()));
				metadata.setFavicon(status.encodeIcon(server));

			} catch (Exception e) {

				LOGGER.error("Failed to set status: " + status, e);
			}

		}, 0, config.delay, TimeUnit.SECONDS);
	}

	public void stopTimer(MinecraftServer server) {

		executor.shutdownNow();
	}
}
