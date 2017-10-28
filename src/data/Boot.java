package data;

import static helpers.RenderingEngine.beginSession;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;


public class Boot {

	public static void main(String[] args) {
		// Poziv staticke metod iz Artist klase za inicijalizaciju OpenGL poziva
		beginSession();
		
		// Glavna petlja
		while(!Display.isCloseRequested()){
			Clock.update();
			StateManager.update();
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		
	}
	
}
