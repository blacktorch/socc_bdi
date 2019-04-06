/**
 * File:   SoccerUtil.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 * **/
package robocup;

import java.io.*;
import java.util.List;

public class SoccerUtil {
    private SoccerUtil() {
        //do not initialize...
    }

    public static ObjectInfo getOpponentsGoal(Memory memory, char side) {
        if (side == Constants.LEFT) {
            return memory.getObject(Constants.GOAL_RIGHT);
        } else {
            return memory.getObject(Constants.GOAL_LEFT);
        }
    }

    public static ObjectInfo getOpponentsSide(Memory memory, char side) {
        if (side == Constants.LEFT) {
            return memory.getObject(Constants.LINE_RIGHT);
        } else {
            return memory.getObject(Constants.LINE_LEFT);
        }
    }

    public static ObjectInfo getMyGoal(Memory memory, char side) {
        if (side == Constants.LEFT) {
            return memory.getObject(Constants.GOAL_LEFT);
        } else {
            return memory.getObject(Constants.GOAL_RIGHT);
        }
    }

    public static ObjectInfo getGoalTop(Memory memory, char side, boolean isOpponent) {
        char s = side;
        if (isOpponent) {
            s = setOpponentSide(side);
        }
        return memory.getObject(Constants.FLAG + Constants.SPACE + Constants.GOAL + Constants.SPACE + s + Constants.SPACE + Constants.TOP);
    }

    public static ObjectInfo getGoalBottom(Memory memory, char side , boolean isOpponent) {
        char s = side;
        if (isOpponent){
            s = setOpponentSide(side);
        }
        return memory.getObject(Constants.FLAG + Constants.SPACE + Constants.GOAL + Constants.SPACE + s + Constants.SPACE + Constants.BOTTOM);
    }

    public static ObjectInfo getPostTop(Memory memory, char side, boolean isOpponent) {
        char s = side;
        if (isOpponent){
            s = setOpponentSide(side);
        }
        return memory.getObject(Constants.FLAG + Constants.SPACE + Constants.POST + Constants.SPACE + s + Constants.SPACE + Constants.TOP);
    }

    public static ObjectInfo getPostCentre(Memory memory, char side, boolean isOpponent) {
        char s = side;
        if (isOpponent){
            s = setOpponentSide(side);
        }
        return memory.getObject(Constants.FLAG + Constants.SPACE + Constants.POST + Constants.SPACE + s + Constants.SPACE + Constants.CENTRE);
    }

    public static ObjectInfo getPostBottom(Memory memory, char side, boolean isOpponent) {
        char s = side;
        if (isOpponent){
            s = setOpponentSide(side);
        }
        return memory.getObject(Constants.FLAG + Constants.SPACE + Constants.POST + Constants.SPACE + s + Constants.SPACE + Constants.BOTTOM);
    }

    public static PlayerInfo getTeamMember(Memory memory, String team){
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        if (player != null && player.getTeamName().equals(team)) {
            return player;
        } else {
            return null;
        }
    }

    public static boolean areAllTrue(List<Boolean> array) {

        for (Boolean b : array) {
            if (!b || b == null) {
                return false;
            }
        }
        return true;
    }

    public static String toString(InputStream input) throws IOException {
        return toString((InputStream) input, (String) null);
    }

    public static String toString(InputStream input, String encoding) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy((InputStream) input, (Writer) sw, encoding);
        return sw.toString();
    }

    public static void copy(InputStream input, Writer output) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy((Reader) in, (Writer) output);
    }

    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        if (encoding == null) {
            copy(input, output);
        } else {
            InputStreamReader in = new InputStreamReader(input, encoding);
            copy((Reader) in, (Writer) output);
        }

    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        char[] buffer = new char[4096];
        long count = 0L;

        int n;
        for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    private static char setOpponentSide(char side) {

        if (side == Constants.LEFT) {
            side = Constants.RIGHT;
        } else {
            side = Constants.LEFT;
        }
        return side;
    }
}
