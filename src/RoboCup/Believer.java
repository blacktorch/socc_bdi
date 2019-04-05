/**
 * File:   Believer.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 * **/
package RoboCup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//***************************************************************************
//
//	This is main object class
//
//***************************************************************************
public class Believer implements SendCommand {

    /************************************************************
    * Private members
    * class members
    * **********************************************************/
    private DatagramSocket socket;        // Socket to communicate with server
    private InetAddress host;             // Server address
    private int port;                     // Server port
    private String team;                  // team name
    private SensorInput brain;            // input for sensor information
    private boolean playing;              // controls the MainLoop
    private Pattern messagePattern = Pattern.compile("^\\((\\w+?)\\s.*");
    private Pattern hearPattern = Pattern.compile("^\\(hear\\s(\\w+?)\\s(\\w+?)\\s(.*)\\).*");
    //private Pattern coach_pattern = Pattern.compile("coach");
    // constants
    private static final int MSG_SIZE = 4096;    // Size of socket buffer

    /***********************************************************
     The main appllication function.
     Command line format:

     Reactor [-parameter value]

     Parameters:

     *  host (default "localhost")
     The host name can either be a machine name, such as "java.sun.com"
     or a string representing its IP address, such as "206.26.48.100."

     *	port (default 6000)
     Port number for communication with server

     *	team (default Reactors)
     Team name. This name can not contain spaces.
     ***********************************************************/

    /****************************************************************************
    * Initialization member functions

    *****************************************************************************/

    public static void main(String a[]) throws SocketException, IOException {
        String hostName = "";
        int port = 6000;
        String team = "Believer";

        try {
            // First look for parameters
            for (int c = 0; c < a.length; c += 2) {
                if (a[c].compareTo("-host") == 0) {
                    hostName = a[c + 1];
                } else if (a[c].compareTo("-port") == 0) {
                    port = Integer.parseInt(a[c + 1]);
                } else if (a[c].compareTo("-team") == 0) {
                    team = a[c + 1];
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            System.err.println();
            System.err.println("USAGE: Reactor [-parameter value]");
            System.err.println();
            System.err.println("    Parameters  value        default");
            System.err.println("   ------------------------------------");
            System.err.println("    host        host_name    localhost");
            System.err.println("    port        port_number  6000");
            System.err.println("    team        team_name    Believer");
            System.err.println();
            System.err.println("    Example:");
            System.err.println("      Believer -host www.host.com -port 6000 -team Nigeria");
            System.err.println("    or");
            System.err.println("      Believer -host 193.117.005.223");
            return;
        }

        Believer player = new Believer(InetAddress.getByName(hostName), port, team);

        // enter main loop
        player.mainLoop();
    }

    //---------------------------------------------------------------------------
    // This constructor opens socket for  connection with server
    public Believer(InetAddress host, int port, String team) throws SocketException {
        socket = new DatagramSocket();
        this.host = host;
        this.port = port;
        this.team = team;
        playing = true;
    }

    //---------------------------------------------------------------------------
    // This destructor closes socket to server
    public void finalize() {
        socket.close();
    }


    //===========================================================================
    // Protected member functions

    //---------------------------------------------------------------------------
    // This is main loop for player
    protected void mainLoop() throws IOException {
        byte[] buffer = new byte[MSG_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE);

        // first we need to initialize connection with server
        init();

        socket.receive(packet);
        parseInitCommand(new String(buffer));
        port = packet.getPort();

        // Now we should be connected to the server
        // and we know side, player number and play mode
        while (playing) {
            parseSensorInformation(receive());
        }
        finalize();
    }


    //===========================================================================
    // Implementation of SendCommand Interface

    //---------------------------------------------------------------------------
    // This function sends move command to the server
    public void move(double x, double y) {
        send("(move " + x + " " + y + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends turn command to the server
    public void turn(double moment) {
        send("(turn " + moment + ")");
    }

    public void turn_neck(double moment) {
        send("(turn_neck " + moment + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends dash command to the server
    public void dash(double power) {
        send("(dash " + power + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends kick command to the server
    public void kick(double power, double direction) {
        send("(kick " + power + " " + direction + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends say command to the server
    public void say(String message) {
        send("(say " + message + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends change_view command to the server
    public void changeView(String angle, String quality) {
        send("(change_view " + angle + " " + quality + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends bye command to the server
    public void bye() {
        playing = false;
        send("(bye)");
    }

    //---------------------------------------------------------------------------
    // This function parses initial message from the server
    protected void parseInitCommand(String message) throws IOException {
        Matcher m = Pattern.compile("^\\(init\\s(\\w)\\s(\\d{1,2})\\s(\\w+?)\\).*$").matcher(message);
        if (!m.matches()) {
            throw new IOException(message);
        }

        // initialize player's brain
        brain = new Brain(this,
                team,
                m.group(1).charAt(0),
                Integer.parseInt(m.group(2)),
                m.group(3),
                getPerception());
    }


    //===========================================================================
    // Here comes collection of communication function
    //---------------------------------------------------------------------------
    // This function sends initialization command to the server
    private void init() {
        send("(init " + team + " (version 9))");
    }

    //---------------------------------------------------------------------------
    // This function parses sensor information
    private void parseSensorInformation(String message) throws IOException {
        // First check kind of information
        Matcher m = messagePattern.matcher(message);
        if (!m.matches()) {
            throw new IOException(message);
        }
        if (m.group(1).compareTo("see") == 0) {
            VisualInfo info = new VisualInfo(message);
            info.parse();
            brain.see(info);
        } else if (m.group(1).compareTo("hear") == 0) {
            parseHear(message);
        }
    }


    //---------------------------------------------------------------------------
    // This function parses hear information
    private void parseHear(String message)
            throws IOException {
        // get hear information
        Matcher m = hearPattern.matcher(message);
        int time;
        String sender;
        String uttered;
        if (!m.matches()) {
            throw new IOException(message);
        }
        time = Integer.parseInt(m.group(1));
        sender = m.group(2);
        uttered = m.group(3);
        if (sender.compareTo("referee") == 0) {
            brain.hear(time, uttered);
        }
        else if (sender.compareTo("self") != 0) {
            brain.hear(time, Integer.parseInt(sender), uttered);
        }
    }


    //---------------------------------------------------------------------------
    // This function sends via socket message to the server
    private void send(String message) {
        byte[] buffer = Arrays.copyOf(message.getBytes(), MSG_SIZE);
        try {
            DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE, host, port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("socket sending error " + e);
        }

    }

    //---------------------------------------------------------------------------

    // This function waits for new message from server
    private String receive() {
        byte[] buffer = new byte[MSG_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE);
        try {
            socket.receive(packet);
        } catch (SocketException e) {
            System.out.println("shutting down...");
        } catch (IOException e) {
            System.err.println("socket receiving error " + e);
        }
        return new String(buffer);
    }

    private Perception getPerception(){
        List<PlayView.PlayerView> perceptions = new ArrayList<>();
        for (PlayView.PlayerView e : PlayView.PlayerView.values()){
            perceptions.add(e);
        }
        try {
            return new Perception(perceptions);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
