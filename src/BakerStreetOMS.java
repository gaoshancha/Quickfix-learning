import java.io.*;
import java.util.*;

// Quickfix Exceptions
import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.RuntimeError;


// Quickfix Data Types
import quickfix.Initiator;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider.TemplateMapping;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.ScreenLogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;


public class BakerStreetOMS
{
    private Initiator initiator = null;
    private boolean initiatorStarted = false;
    private ClientApplication app;

    public BakerStreetOMS(SessionSettings settings)
        throws ConfigError, FieldConvertError
    {
        app = new ClientApplication(settings);
        MessageStoreFactory msgStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory msgFactory = new DefaultMessageFactory();

        initiator = new SocketInitiator(app, msgStoreFactory, settings,
                                      logFactory, msgFactory);
    }


    public void start() throws RuntimeError, ConfigError
    {
		if( !initiatorStarted )
		{
			try{
				System.out.println("Starting Initiator...");
				initiator.start();
				initiatorStarted = true;
			}
			catch (Exception e){
				System.out.println("Initiator failed: "+e.getMessage());
			}
		}
		else{
		Iterator<SessionID> sessionIds = initiator.getSessions().iterator();
		while (sessionIds.hasNext()) {
			SessionID sessionId = (SessionID) sessionIds.next();
			System.out.println("Attempting to logon...");
			Session.lookupSession(sessionId).logon();
		}
		}
    }

    public void stop()
    {
		Iterator<SessionID> sessionIds = initiator.getSessions().iterator();
        while (sessionIds.hasNext()) {
            SessionID sessionId = (SessionID) sessionIds.next();
            Session.lookupSession(sessionId).logout("user requested");
        }
        initiator.stop();
    }

    public void testQuote()
    {
    	app.sendQuoteRequest("0001");
    }

    // Program entry-point
    public static void main( String args[] )
    {
        SessionSettings sessionSettings = null;
        try{
            if( args.length != 1 ){
                System.err.println(" Usage: LanFongOMS <config file>");
            }
            sessionSettings = loadConfiguration(args[0]);
            Iterator<SessionID> sectionIterator = sessionSettings.sectionIterator();
            while( sectionIterator.hasNext() ){
                SessionID id = sectionIterator.next();
                System.out.println( id.toString() );
            }

            BakerStreetOMS oms = new BakerStreetOMS(sessionSettings);
            oms.start();
			System.out.println("press <enter> to logon ...");
			System.in.read();
			oms.start();
			System.out.println("press <enter> to quit");
            System.in.read();
            oms.stop();
        }
        catch( Exception ex ){
            System.err.println( "Exception: " + ex.getMessage() );
        }
        System.exit(0);
    }


    // Load customer configuration from first argument
    private static SessionSettings loadConfiguration( String configFilePath )
        throws FileNotFoundException
    {
        SessionSettings settings = null;
        try{
            InputStream inputStream = new FileInputStream(configFilePath);
            settings = new SessionSettings(inputStream);
            inputStream.close();
        }
        catch( Exception ex ){
            System.err.println( ex.getMessage() );

        }
        return settings;
    }

}
