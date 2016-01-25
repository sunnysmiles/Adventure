package engine.client;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import engine.network.ClientPacket;
import engine.utils.Input;
import engine.utils.Screen;
import engine.utils.Vector;

public abstract class AbstractGame implements Runnable {
	private int size = 2;
	private int height = 300;
	private int width = 400;
	private double ups = 60;
	private double nsPerTick = 1000000000 / ups;

	private Screen screen;
	private Input input;

	private int frames;
	private long lastTime;
	private double delta;
	private long lastSplay;
	private int ticks;
	private Canvas canvas;

	private boolean displayFPS;
	private int curUPS;
	private int curFPS;
	private JFrame frame;
	private Connection connection;
	private double sps = 60D;
	private double nsPerSend = 1000000000 / sps;
	private double deltaS;
	private boolean running;

	public AbstractGame() {
		canvas = new Canvas();
		displayFPS = false;
	}

	public abstract void init();

	public abstract void update();

	public void run() {
		setup();
		lastTime = System.nanoTime();
		running = true;
		while (running) {
			long curTime = System.nanoTime();
			delta += (curTime - lastTime) / nsPerTick;
			deltaS += (curTime - lastTime) / nsPerSend;
			lastTime = curTime;
			while (delta >= 1) {
				delta--;
				update();
				ticks++;
			}
			if (connection != null)
				while (deltaS >= 1) {
					deltaS--;
					//TODO Go engine through for inconsistensies
					//					connection.stack(new HeartBeatPacket());
					try {
						connection.sendData();
					} catch (IOException e) {
						System.out.println("Problem sending data");
						if (e.getMessage().startsWith(
								"Connection reset by peer:")) {
							System.out
									.println("Lost connection to server, game terminated");
							stopGame();
							return;
						} else
							System.out.println("Problem sending data");
					}

				}
			render();
			if (System.currentTimeMillis() - lastSplay >= 1000) {
				if (displayFPS)
					System.out.println("UPS: " + ticks + " FPS: " + frames);
				curUPS = ticks;
				curFPS = frames;
				lastSplay = System.currentTimeMillis();
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void stopGame() {
		System.out.println("Game terminated");
		running = false;
		if (connection != null)
			connection.stop();
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	private void setup() {
		screen = new Screen(width, height);
		input = new Input(this);
		frames = 0;
		canvas.setVisible(true);
		canvas.setIgnoreRepaint(true);
		canvas.requestFocus();
		init();
	}

	private void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			if(frame != null)
			canvas.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(screen.image, 0, 0, canvas.getWidth(), canvas.getHeight(),
				null);
		g.dispose();
		bs.show();
		frames++;
	}

	public Connection connectToServer() {
		connection = new Connection();
		new Thread(connection).start();
		return connection;
	}
	public void setUPS(int fps) {
		this.ups = (double) fps;
		nsPerTick = 1000000000 / fps;
	}

	public Connection getConnection(){
		return connection;
	}
	
	public Screen getScreen() {
		return screen;
	}

	public int getMulti() {
		return size;
	}

	public int getPixelsWidth() {
		return width;
	}

	public void setPixelsHeight(int height) {
		if (frame == null)
			return;
		this.height = height;
		frame.setSize(width * size, height * size);
	}

	public void setPixelsWidth(int width) {
		if (frame == null)
			return;
		this.width = width;
		frame.setSize(width * size, height * size);
	}
	
	public void setSize(int size){
		if(frame == null) return;
		this.size = size;
		frame.setSize(width * size, height * size);
	}

	public int getPixelsHeight() {
		return height;
	}

	public Input getInput() {
		return input;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setDisplayFPS(boolean b) {
		displayFPS = b;
	}

	public int getCurUPS() {
		return curUPS;
	}

	public int getCurFPS() {
		return curFPS;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public double getSps() {
		return sps;
	}

	public void setSps(double sps) {
		this.sps = sps;
		nsPerSend = 1000000000 / sps;
	}
}