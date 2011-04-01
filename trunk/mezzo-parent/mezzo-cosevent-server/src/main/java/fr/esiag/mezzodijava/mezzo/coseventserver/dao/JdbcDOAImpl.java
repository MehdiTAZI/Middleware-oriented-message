package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	    log.debug(sql);
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
	    String sql = "SELECT * FROM EVENT E WHERE CHANNEL_ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, channelId);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
		EventModel e = new EventModel();
		e.setId(rs.getInt("ID"));
//		Blob b = rs.getBlob("DATA");
//		e.setData(b.getBytes(0L, (int) b.length()));
		e.setData(rs.getBytes("DATA"));
		e.setCode(rs.getLong("CODE"));
		e.setCreationdate(rs.getLong("CREATIONDATE"));
		e.setPriority(rs.getInt("PRIORITY"));
		e.setTimetolive(rs.getLong("TTL"));
		e.setType(rs.getString("TYPE"));
		set.add(e);
	    }
	    return Collections.synchronizedSortedSet(set);
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public Map<String, ConsumerModel> findConsumerByChannel(int channelId) {
	try {
	    Map<String, ConsumerModel> map = new HashMap<String, ConsumerModel>();
	    String sql = "SELECT * FROM CONSUMER CO WHERE CHANNEL_ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, channelId);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
		ConsumerModel co = new ConsumerModel();
		co.setId(rs.getInt("ID"));
		co.setIdConsumer(rs.getString("IDCONSUMER"));
		map.put(co.getIdConsumer(), co);
	    }
	    return Collections.synchronizedMap(map);
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public SortedSet<EventModel> findEventByConsumer(int idConsumer) {
	try {
	    SortedSet<EventModel> set = new TreeSet<EventModel>(
		    new PriorityEventModelComparator());
	    String sql = "SELECT * FROM EVENT E, CONSUMER_EVENT CE WHERE E.ID=CE.EVENT_ID AND CE.CONSUMER_ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, idConsumer);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
		EventModel e = new EventModel();
		e.setId(rs.getInt("ID"));
		Blob b = rs.getBlob("DATA");
		e.setData(b.getBytes(0L, (int) b.length()));
		e.setCode(rs.getLong("CODE"));
		e.setCreationdate(rs.getLong("CREATIONDATE"));
		e.setPriority(rs.getInt("PRIORITY"));
		e.setTimetolive(rs.getLong("TTL"));
		e.setType(rs.getString("TYPE"));
		set.add(e);
	    }
	    return Collections.synchronizedSortedSet(set);
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public synchronized int insertChannel(Channel channel) {
	int cle = 0;
	try {
	    String[] idColumn = { "ID" };
	    String sql = "INSERT INTO CHANNEL (CAPACITY_QUEUE, CONNECTIONCAPACITY, IDENTIFIER, TOPIC) VALUES(?,?,?,?)";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql, idColumn);
	    stmt.setInt(1, channel.getCapacity());
	    stmt.setInt(2, channel.getConnectionCapacity());
	    stmt.setLong(3, channel.getIdentifier());
	    stmt.setString(4, channel.getTopic());
	    stmt.executeUpdate();
	    // Les clefs auto-générées sont retournées sous forme de ResultSet
	    ResultSet clefs = stmt.getGeneratedKeys();
	    if (clefs.next()) {
		cle = clefs.getInt(1);
		channel.setId(cle);
	    }
	    return cle;

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public synchronized int insertConsumer(ConsumerModel consumer) {
	int cle = 0;
	try {
	    String[] idColumn = { "ID" };
	    String sql = "INSERT INTO CONSUMER (IDCONSUMER, CHANNEL_ID) VALUES(?,?)";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql,idColumn);
	    stmt.setString(1, consumer.getIdConsumer());
	    stmt.setInt(2, consumer.getChannel().getId());
	    stmt.executeUpdate();
	    // Les clefs auto-générées sont retournées sous forme de ResultSet
	    ResultSet clefs = stmt.getGeneratedKeys();
	    if (clefs.next()) {
		cle = clefs.getInt(1);
		consumer.setId(cle);
	    }
	    return cle;

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public synchronized int insertEvent (int channelId, EventModel event) {
	int cle = 0;
	try {
	    String[] idColumn = { "ID" };
	    String sql = "INSERT INTO EVENT (CODE, CREATIONDATE, DATA, PRIORITY, TTL, TYPE, CHANNEL_ID) VALUES(?,?,?,?,?,?,?)";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql,idColumn);
	    stmt.setLong(1, event.getCode());
	    stmt.setLong(2, event.getCreationdate());
	    stmt.setBytes(3,event.getData());
	    stmt.setInt(4, event.getPriority());
	    stmt.setLong(5, event.getTimetolive());
	    stmt.setString(6, event.getType());
	    stmt.setInt(7, channelId);
	    stmt.executeUpdate();
	    // Les clefs auto-générées sont retournées sous forme de ResultSet
	    ResultSet clefs = stmt.getGeneratedKeys();
	    if (clefs.next()) {
		cle = clefs.getInt(1);
		event.setId(cle);
	    }
	    return cle;

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }
    
    @Override
    public void addEventToConsumer(int eventId,int consumerId) {
	int cle = 0;
	try {
	    String sql = "INSERT INTO CONSUMER_EVENT (CONSUMER_ID,EVENT_ID) VALUES(?,?)";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, consumerId);
	    stmt.setInt(2, eventId);
	    stmt.executeUpdate();
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public void updateChannel(Channel channel) {
	try {
	    String sql = "UPDATE CHANNEL SET CAPACITY_QUEUE=? WHERE ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, channel.getCapacity());
	    stmt.setInt(2, channel.getId());
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);
	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public void deleteConsumer(int consumerId) {
	try {
	    String sql2 = "DELETE FROM CONSUMER_EVENT WHERE CONSUMER_ID=?";
	    log.debug(sql2);
	    PreparedStatement stmt2 = connection.prepareStatement(sql2);
	    stmt2.setInt(1, consumerId);
	    int nb2 = stmt2.executeUpdate();
	    log.debug("nb delete:" + nb2);

	    String sql = "DELETE FROM CONSUMER WHERE ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, consumerId);
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}

    }

    @Override
    public void deleteChannel(int channelId) {
	try {
	    String sql = "DELETE FROM CHANNEL WHERE ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, channelId);
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}

    }

    @Override
    public void deleteAllConsumers(int channelId) {
	try {
//	    String sql2 = "DELETE FROM CONSUMER_EVENT";
//	    log.debug(sql2);
//	    PreparedStatement stmt2 = connection.prepareStatement(sql2);
//	    int nb2 = stmt2.executeUpdate();
//	    log.debug("nb delete:" + nb2);

	    String sql = "DELETE FROM CONSUMER WHERE CHANNEL_ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, channelId);
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}

    }

    @Override
    public void deleteEventByConsumer(int consumerId, int eventId) {
	try {
	    String sql = "DELETE FROM CONSUMER_EVENT CE WHERE CE.CONSUMER_ID=? AND CE.EVENT_ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, consumerId);
	    stmt.setInt(2, eventId);
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }

    @Override
    public void deleteEvent(int eventId) {
	try {
//	    String sql2 = "DELETE FROM CHANNEL_EVENT CE WHERE CE.EVENT_ID=?";
//	    log.debug(sql2);
//	    PreparedStatement stmt2 = connection.prepareStatement(sql2);
//	    stmt2.setInt(1, eventId);
//	    int nb2 = stmt2.executeUpdate();
//	    log.debug("nb delete:" + nb2);

	    String sql = "DELETE FROM EVENT E WHERE E.ID=?";
	    log.debug(sql);
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setInt(1, eventId);
	    int nb = stmt.executeUpdate();
	    log.debug("nb delete:" + nb);

	} catch (SQLException e) {
	    log.error("SQL Error", e);
	    throw new RuntimeException("SQL Error", e);
	}
    }
}
