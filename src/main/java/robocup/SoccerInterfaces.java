//
//	File:			SoccerInterfaces.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

//      Modified by:     Edgar Acosta
//      Date:            March 5, 2008
//      Added the bye command


//***************************************************************************
//
//	This interface declares functions which are used to send
//	command to player
//
//***************************************************************************
package robocup;

interface SendCommand {
    // This function sends a bye command to the server
    void bye();

    // This function sends chage_view command to the server
    void changeView(String angle, String quality);

    // This function sends dash command to the server
    void dash(double power);

    // This function sends kick command to the server
    void kick(double power, double direction);

    // This function sends move command to the server
    void move(double x, double y);

    // This function sends say command to the server
    void say(String message);

    // This function sends turn command to the server
    void turn(double moment);

    void turn_neck(double moment);
}


interface SensorInput {
    //---------------------------------------------------------------------------
    // This function receives hear information from player
    void hear(int time, int direction, String message);

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    void hear(int time, String message);

    //---------------------------------------------------------------------------
    // This function sends see information
    void see(VisualInfo info);
}
