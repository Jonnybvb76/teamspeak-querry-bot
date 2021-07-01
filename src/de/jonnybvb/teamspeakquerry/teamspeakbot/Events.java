package de.jonnybvb.teamspeakquerry.teamspeakbot;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.exception.TS3QueryShutDownException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.jonnybvb.teamspeakquerry.TeamspeakQuery;

import java.net.Socket;
import java.net.SocketException;

public class Events {

    public static void load(){

        TeamspeakQuery.api.registerAllEvents();

        TeamspeakQuery.api.addTS3Listeners(new TS3Listener() {
            public void onTextMessage(TextMessageEvent e) {
                if (e.getTargetMode() == TextMessageTargetMode.CLIENT) {
                    Client client = TeamspeakQuery.api.getClientInfo(e.getInvokerId());
                    String message = e.getMessage().toLowerCase();

                    /**
                     * We just add a new test command to see if everything is working
                     */

                    if(message.equalsIgnoreCase("!test")) {
                        TeamspeakQuery.api.sendPrivateMessage(client.getId(), "test successfully!");
                        System.out.println("[Teamspeak] " + client.getNickname() + " used the test command!");
                    }
                }
            }

            @Override
            public void onClientJoin(ClientJoinEvent e) {
                Client client = TeamspeakQuery.api.getClientInfo(e.getClientId());

                System.out.println("[Teamspeak] " + client.getNickname() + " joined the Server!");

                /**
                 * We'll add a nice welcome message to every new user
                 */

                try {
                    client.getLastConnectedDate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    TeamspeakQuery.api.sendPrivateMessage(client.getId(), "Hey, welcome on our Server!");
                }

            }


            public void onClientLeave(ClientLeaveEvent e) {
                try {
                    Client client = TeamspeakQuery.api.getClientInfo(e.getClientId());

                    System.out.println("[Teamspeak] " + client.getNickname() + " left the Server!");
                } catch (Exception ex) {
                    System.out.println("[Teamspeak] Couldn't get User who left because of a Error");
                }
            }


            public void onServerEdit(ServerEditedEvent serverEditedEvent) {

            }

            public void onChannelEdit(ChannelEditedEvent e) {
            }

            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {

            }

            public void onClientMoved(ClientMovedEvent e) {
                Client client = TeamspeakQuery.api.getClientInfo(e.getClientId());

                /**
                 * Now we will add a simple Support notification if someone joined a Channel
                 */

                if(e.getTargetChannelId() == 3) { // when the client joins a channel with this id
                    TeamspeakQuery.messageClientsByServerGroup(6, "The User " + client.getNickname() + " needs Support!");
                }
            }
            public void onChannelCreate(ChannelCreateEvent e) {
            }

            public void onChannelDeleted(ChannelDeletedEvent e) {

            }

            public void onChannelMoved(ChannelMovedEvent e) {
            }

            public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
            }

            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
            }
        });

    }
}
