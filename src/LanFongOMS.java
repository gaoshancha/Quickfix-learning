import java.io.*;
import java.util.*;

// Quickfix Exceptions
import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.RuntimeError;


// Quickfix Data Types
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider.TemplateMapping;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.ScreenLogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;


public class LanFongOMS
{
    private final SocketAcceptor acceptor;

    public LanFongOMS(SessionSettings settings)
        throws ConfigError, FieldConvertError
    {
        BrokerApplication app = new BrokerApplication(settings);
        MessageStoreFactory msgStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory msgFactory = new DefaultMessageFactory();

        acceptor = new SocketAcceptor(app, msgStoreFactory, settings,
                                      logFactory, msgFactory);
    }


    // Accept incoming connections
    public void start() throws RuntimeError, ConfigError
    {
        acceptor.start();
    }

    // Stop accepting incoming connections
    public void stop()
    {
        acceptor.stop();
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

            LanFongOMS oms = new LanFongOMS(sessionSettings);
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
