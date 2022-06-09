package com.badlogic.Base;
import com.badlogic.FileHandling.FileWriter;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher  {
	static FileWriter write = new FileWriter();

	public static void main(String[] args) {
		write.function();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(900,700);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("CrazyGolf");
		config.setResizable(false);
		new Lwjgl3Application(new App(), config);
	}
}
