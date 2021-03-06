import java.io.*;
import java.util.*;

// Quickfix Exceptions
import quickfix.DoNotSend;
import quickfix.FieldConvertError;
import quickfix.ConfigError;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.FieldNotFound;
import quickfix.UnsupportedMessageType;
import quickfix.SessionNotFound;
import quickfix.RejectLogon;

// Quickfix Data Types
import quickfix.SessionID;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionSettings;
import quickfix.FixVersions;
import quickfix.MessageUtils;
import quickfix.field.*;
import quickfix.fix40.QuoteRequest;



public class ClientApplication extends quickfix.MessageCracker
                         implements quickfix.Application
{
	
    public ClientApplication(SessionSettings settings)
        throws ConfigError, FieldConvertError
    {
    }

    // ===================================================
    // Overriden quickfix.MessageCracker onMessage()
    // ===================================================
    public void onMessage(quickfix.fix42.NewOrderSingle order,
                          SessionID sessionID)
        throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue
    {
    }

    // ===================================================
    // quickfix.Application interface implementation
    // ===================================================

    // Called when quickfix creates a new session
    public void onCreate(SessionID sessionID)
    {
		System.out.println("==> client onCreate()");
		System.out.println("<== client onCreate()");		
    }

    // notification that a valid logon has been established
    // with the countery party
    public void onLogon(SessionID sessionID)
    {
    	System.out.println("==> client onLogon()");
		System.out.println("<== client onLogon()");
    }

    // notification that a FIX session is no longer online
    public void onLogout(SessionID sessionID)
    {
    	System.out.println("==> onLogout()");
		System.out.println("<== onLogout()");
    }

    // allows for peaking at msgs from this apps FIX engine to
    // the counter party
    public void toAdmin(Message msg, SessionID sessionID)
    {
    }

    // callback notify for when admin msgs are received by FIX from 
    // the counter party
    public void fromAdmin(Message msg, SessionID sessionID)
        throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
               RejectLogon
    {
    }

    // callback for app messages this app send to the counter party
    public void toApp(Message msg, SessionID sessionID)
        throws DoNotSend
    {
    }

    // all app level requests comes through here
    public void fromApp(Message msg, SessionID sessionID)
        throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
               UnsupportedMessageType
    {
        // call base class message parser
        crack(msg, sessionID);
    }
    
    public void sendQuoteRequest(String symbol)
    {
		/*
    	String targetSessionIDString = "LANFONG";
		String senderSessionIDString = "BAKERST";
    	Symbol sym = new Symbol(symbol);
        
		try{
			quickfix.fix42.QuoteRequest msg = new quickfix.fix42.QuoteRequest();
        	Session.sendToTarget(msg, senderSessionIDString, targetSessionIDString);
        }
        catch( quickfix.SessionNotFound ex )
        {
			System.out.println("Exception: " + ex.getMessage());
        }
		*/
    }
}
