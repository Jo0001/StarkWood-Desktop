package de.Jo0001.StarkWood.Core;

import javafx.application.Application;

import java.io.IOException;

public class AppLauncher {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            String currentDir = System.getProperty("user.dir");
            String jar = new java.io.File(AppLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName().replaceAll("%20", " ");
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("cmd.exe /c cd " + currentDir + " && java -jar \"" + jar + "\" -withcmd");
            System.out.println("Restarting");
            System.exit(-99);
        }

        Application.launch(Main.class);
    }
}
