package de.jonnybvb.teamspeakquerry;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.exception.TS3Exception;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.jonnybvb.teamspeakquerry.teamspeakbot.AutoReconnect;
import de.jonnybvb.teamspeakquerry.teamspeakbot.Events;

public class TeamspeakQuery {

    /**
     * Some variables to easily change query login and server.
     * THIS IS THE MOST IMPORTANT STEP!
     */

    public static String teamspeakHost = "localhost"; // Teamspeak host address
    public static String teamspeakQueryName = "serveradmin"; // Teamspeak query name
    public static String teamspeakQueryPassword = "123"; // Teamspeak query password
    public static String teamspeakQueryNickname = "TestBot"; // Teamspeak query nickname

    /**
     * Some variables for other Stuff
     * we will need this too!
     */

    public static int AutoReconnectCheck = 15; // Sets how often the AutoReconnect will check in SECONDS
    public static int AutoReconnectTry = 3; // Sets how often the AutoReconnect will try to reconnect, if it isn't connected in SECONDS

    /**
     * We are using the teamspeak3-api by theholywaffle / version: 1.2.0
     * just add it as maven or gradle dependency.
     */

    public static TS3Config config = new TS3Config();
    public static TS3Query query = new TS3Query(config);
    public static TS3Api api = query.getApi();

    public static void main(String[] args) {
        connect();
    }

    public static void connect () {

        /**
         * Connecting the bot to the server and login the query
         */

        config.setHost(teamspeakHost);
        config.setEnableCommunicationsLogging(false);
        config.setReconnectStrategy(ReconnectStrategy.disconnect());

        try {
            query.connect();
            System.out.println("[Teamspeak] Query connecting..");

        } catch (Exception e) {
            query = new TS3Query(config);
            try {
                query.connect();
                System.out.println("[Teamspeak] Query connecting..");

            } catch (Exception ex) {
                if (AutoReconnect.status == false) {
                    try {
                        AutoReconnect.start(); // If Error comes AutoReconnect will start automatically
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else {
                    return;
                }
            }
        }

        api = query.getApi();

        try {
            Thread.sleep(500); // Wait 500 milliseconds because the query must connect first
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        api.selectVirtualServerById(1); // Select the first Virtual Server
        api.login(teamspeakQueryName, teamspeakQueryPassword); // Login in Query

        try {
            api.setNickname(teamspeakQueryNickname); // Sets Nickname
        } catch (TS3Exception ex) { // If Nickname is already in use, a number will add behind
            try {
                api.setNickname(teamspeakQueryNickname + " #1");
            } catch (TS3Exception e) {
                api.setNickname(teamspeakQueryNickname + " #2");
            }
        }

        Events.load(); // All events in the Events class will load

        api.registerEvent(TS3EventType.TEXT_PRIVATE);

        System.out.println("[Teamspeak] Query connected!");

        try {
            AutoReconnect.start(); // Starting the AutoReconnect class
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect () {
        if(query.isConnected()) {
            AutoReconnect.stop(); // Stopping the AutoReconnect
            query.exit(); // Let exit the query
            System.out.println("StreetNight TS3 disconnected!");
        } else {
            System.out.println("Error: The Bot is currently not connected");
        }

    }

    /**
     * Now we will add some Stuff, that can help us later for some events and such.
     */

    public static int getIdByNickname (String name) {
        int id = 0;
        for (Client c : api.getClients()) {
            if(c.getNickname().equals(name)) {
                id = c.getId();
            }
        }

        return id;
    }

    public static void messageClientsByServerGroup (int serverGroupId, String message) {
        for (Client c : api.getClients()) {
            if(c.isInServerGroup(serverGroupId)) {
                api.sendPrivateMessage(c.getId(), message);
            }
        }

        return;
    }

    public static void pokeClientsByServerGroup (int serverGroupId, String message) {
        for (Client c : api.getClients()) {
            if(c.isInServerGroup(serverGroupId)) {
                api.pokeClient(c.getId(), message);
            }
        }

        return;
    }

}
