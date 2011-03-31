package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.PriorityEventModelComparator;

public class JdbcDOAImpl implements JdbcDAO {

    private String userName = "test";
    private String password = "test";
    private String dbFile = "C:/mezzodev/coseventBase";

    private static Logger log = LoggerFactory.getLogger(JdbcDOAImpl.class);

    private Connection connection;

    private Connection getConnection() throws SQLException {
	Connection conn = null;
	Properties connectionProps = new Properties();
	connectionProps.put("user", this.userName);
	connectionProps.put("password", this.password);
	conn = DriverManager.getConnection("jdbc:derby:" + this.dbFile
		+ ";create=true");
	log.info("Connected to database");
	try {
	    conn.prepareCall("SELECT COUNT(*) FROM CHANNEL").execute();
	} catch (SQLException e) {
	    log.info("Shema doesn't exist. Creating...");
	    importSQL(conn, getClass().getResourceAsStream("/cosevent.sql"));
	    log.info("Shema created.");
	}
	return conn;
    }

    public static void importSQL(Connection conn, InputStream in)
	    throws SQLException {
	Scanner s = new Scanner(in);
	s.useDelimiter("(;(\r)?\n)|(--\n)");
	Statement st = null;
	try {
	    st = conn.createStatement();
	    while (s.hasNext()) {
		String line = s.next();
		if (line.startsWith("/*!") && line.endsWith("*/")) {
		    int i = line.indexOf(' ');
		    line = line
			    .substring(i + 1, line.length() - " */".length());
		}

		if (line.trim().length() > 0) {
		    try {
			log.info(line);
			st.execute(line);
		    } catch (Exception e) {
			log.info(e.toString());
		    }
		}
	    }
	} finally {
	    if (st != null)
		st.close();
	}
    }

    public JdbcDOAImpl(String userName, String password, String dbFile) {
	super();
	this.userName = userName;
	this.password = password;
	this.dbFile = dbFile;
	try {
	    this.connection = getConnection();
	} catch (SQLException e) {
	    log.error("Error creating connection to derby database", e);
	    throw new RuntimeException(
		    "Error creating connection to derby database", e);
	}
    }

    @Override
    public List<Channel> findAllChannel() {
	try {
	    List<Channel> list = new ArrayList<Channel>();
	    String sql = "SELECT * FROM CHANNEL";
	    ResultSet rs = connection.prepareStatement(sql).executeQuery();
	    while (rs.next()) {
		Channel c = new Channel();
		c.setId(rs.getInt("ID"));
		c.setCapacity(rs.getInt("CAPACITY_QUEUE"));
		c.setIdentifier(rs.getInt("IDENTIFIER"));
		c.setTopic(rs.getString("TOPIC"));
		list.add(c);
	    }
	    return list;
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public SortedSet<EventModel> findEventByChannel(int channelId) {
	try {
	    SortedSet<EventModel> set = new TreeSet<EventModel>(
		    new PriorityEventModelComparator());
	    String sql = "SELECT * FROM EVENT, CHANNEL_EVENT WHERE EVENT.ID=CHANNEL_EVENT.EVENTS_ID AND CHANNEL_EVENT.CHANNEL_ID=?";
	    
	    ResultSet rs = connection.prepareStatement(sql).executeQuery();
	    while (rs.next()) {
		EventModel c = new EventModel();
		c.setId(rs.getInt("ID"));
		//...
	    }
	    return set;
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public Map<String, ConsumerModel> findConsumerByChannel(int channelId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public SortedSet<EventModel> findEventByConsumer(int idConsumer) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int insertChannel(Channel channel) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int insertConsumer(ConsumerModel consumer) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int insertEvent(EventModel event) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int updateChannel(int channelId) {
	// TODO Auto-generated method stub
	return 0;
    }

    public static void main(String[] args) {
	JdbcDOAImpl dao = new JdbcDOAImpl("test", "test",
		"C:/mezzodev/coseventBase");
    }

    @Override
    public void deleteConsumer(int consumerId) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteChannel(int channelId) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteAllConsumers() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteEventByConsumer(int consumerId, int eventId) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteEvent(int eventId) {
	// TODO Auto-generated method stub
	
    }
}
