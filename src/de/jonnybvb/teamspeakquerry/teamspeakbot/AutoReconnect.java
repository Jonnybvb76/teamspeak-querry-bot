package de.jonnybvb.teamspeakquerry.teamspeakbot;

import de.jonnybvb.teamspeakquerry.TeamspeakQuery;

import java.util.concurrent.TimeUnit;

public class AutoReconnect {

    public static boolean checkBool = false;
    public static boolean status = false;

    static class reconnectTeamspeak implements Runnable {

        @Override
        public void run() {
            status = true;
            System.out.println("[Teamspeak] Trying to reconnect Query..");
            while (status) {
                if(!TeamspeakQuery.query.isConnected()) {
                    TeamspeakQuery.connect();
                } else {
                    status = false;
                    checkBool = true;
                    System.out.println("[Teamspeak] Query is already connected!");
                }

                if(TeamspeakQuery.query.isConnected()) {
                    System.out.println("[Teamspeak] Successfully reconnected!");
                    checkBool = true;
                    status = false;
                }

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(TeamspeakQuery.AutoReconnectTry));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static void checkForTeamspeak() {

        /**
         * This here will check if the query is connected with the server every * seconds.
         * if the query isn't connected, then a new Thread will start, that will
         * try to connect the query every * seconds.
         */

        while (checkBool) { // If Checking is enabled it will check everytime
            if(!TeamspeakQuery.query.isConnected()) {
                System.out.println("[Teamspeak] Query is not connected!");
                checkBool = false; // Sets the checkbool to false, because it already trying to reconnect
                try {
                    Thread thread = new Thread(new reconnectTeamspeak());
                    thread.start();
                    thread.join();
                    checkBool = true; // Sets the checkbool to true, because the Thread has ended (query connected)

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    checkBool = true; // Sets the checkbool to true, because a exception came up
                    System.out.println("[Teamspeak] Reconnection failed");

                }
            }
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(TeamspeakQuery.AutoReconnectCheck));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void start() throws InterruptedException {
        checkBool = true;
        checkForTeamspeak();
    }

    public static void stop() {
        checkBool = false;
    }

}
