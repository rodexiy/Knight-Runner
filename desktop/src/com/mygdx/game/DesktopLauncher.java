package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Classe responsável por iniciar a aplicação desktop.
 */
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setMaximized(true);
		config.setForegroundFPS(120);
		config.setTitle("Knight Jumper");

		new Lwjgl3Application(new Game(), config);
	}
}
