package eu.factorx.awac.compilers;

import play.Logger;

import java.io.PrintWriter;
import java.nio.file.*;

public class RecompilerThread extends Thread {

	public RecompilerThread() {
	}

	public void run() {
		try {

			try {
				AngularCompiler compiler = new AngularCompiler();
				String result = compiler.compile("app/eu/factorx/awac/angular");

				PrintWriter writer = new PrintWriter("public/javascripts/app.js", "UTF-8");
				writer.print(result);
				writer.close();
			} catch (Exception ex) {
				Logger.error("Error while watching for angular changes but resuming watching.", ex);
			}


			final Path path = FileSystems.getDefault().getPath("app/eu/factorx/awac/angular");
			System.out.println(path);
			final WatchService watchService = FileSystems.getDefault().newWatchService();
			final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
			while (true) {
				final WatchKey wk = watchService.take();

				for (WatchEvent<?> event : wk.pollEvents()) {
					System.out.println(event.kind().name() + " => " + event.context().toString());
				}

				AngularCompiler compiler = new AngularCompiler();
				String result = compiler.compile("app/eu/factorx/awac/angular");

				try {
					PrintWriter writer = new PrintWriter("public/javascripts/app.js", "UTF-8");
					writer.print(result);
					writer.close();
				} catch (Exception ex) {
					Logger.error("Error while watching for angular changes but resuming watching.", ex);
				}


				// reset the key
				boolean valid = wk.reset();
				if (!valid) {
					System.out.println("Key has been unregistered");
				}
			}
		} catch (Exception e) {
			Logger.error("Fatal error while watching for angular changes", e);
		}
	}


}
