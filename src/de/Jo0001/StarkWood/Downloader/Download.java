package de.Jo0001.StarkWood.Downloader;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class Download {
    private static HttpURLConnection con;

    public static void main(String[] args) throws IOException {
        System.out.println("Laden . . .");
        final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
        con = (HttpURLConnection) myurl.openConnection();
        con.setDoOutput(true);

        String[] infos = new String[8];
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            for (int i = 0; i < infos.length; i++) {
                infos[i] = in.readLine();
            }
            con.disconnect();
        } catch (UnknownHostException e) {
            System.err.println("Keine Netzwerkverbindung!");
            e.printStackTrace();
        }
        System.out.print("Suche nach Updates: ");

        checkVersion(infos[7]);
        /*Infos[0] vanilla, [1]lite, [2]full, [7]date, [8] version*/
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welche Version soll heruntergeladen werden? Vanilla, Lite oder Full?");

        String type = scanner.next();
        String hash, link, mb;
        if (type.equalsIgnoreCase("vanilla")) {
            hash = infos[0];
            link = "https://www.dropbox.com/sh/x5moafif0w3mnfe/AACHeceFYOf_KcmwoOsfwUeWa?dl=1";
            mb = infos[3];

        } else if (type.equalsIgnoreCase("lite")) {
            hash = infos[1];
            link = "https://www.dropbox.com/sh/8kdowv83vjar9qp/AABTJgGfb6ojW-Xi3H8300m6a?dl=1";
            mb = infos[4];

        } else if (type.equalsIgnoreCase("full")) {
            hash = infos[2];
            link = "https://www.dropbox.com/sh/q7r6vehzc3j1xrw/AADf9zH24QUF8yKFpww4WTbca?dl=1";
            mb = infos[5];
        } else {
            hash = infos[0];
            link = "https://www.dropbox.com/sh/x5moafif0w3mnfe/AACHeceFYOf_KcmwoOsfwUeWa?dl=1";
            mb = infos[3];
            System.out.println("Keine (korrekte) Angabe -> Using vanilla");
        }
        final URL dl_url = new URL(link);

        System.out.println("Download von " + type.toLowerCase() + "(" + mb + "MB) mit hash " + hash + " gestartet . . .");

        ReadableByteChannel rbc = Channels.newChannel(dl_url.openStream());
        FileOutputStream fos = new FileOutputStream(System.getenv("APPDATA") + "\\.minecraft\\server-resource-packs\\" + hash);
        fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
        fos.close();
        rbc.close();
        System.out.println("Fertig");
    }

    public static void checkVersion(String ver) {
        final String cVer = "1.1";
        if (cVer.equalsIgnoreCase(ver)) {
            System.out.println(" Aktuell");
        } else {
            System.err.println("Veraltete Version, bitte downloade die neuste Version von LINK");
            System.exit(400);
        }
    }
}
