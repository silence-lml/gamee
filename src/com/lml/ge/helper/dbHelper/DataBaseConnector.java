package com.lml.ge.helper.dbHelper;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.lml.ge.helper.GameInfo;

public class DataBaseConnector {
	private static final Logger logger = LoggerFactory.getLogger(DataBaseConnector.class);
	
	private DataBaseConnector() {}
	
	public static DataBaseConnector getDBHelper() {
		return new DataBaseConnector();
	}

	public void init() throws Throwable {
		initSingleDB();
	}

	private void initSingleDB() throws Throwable {
		init(GameInfo.DB_URL, GameInfo.DB_USR, GameInfo.DB_PWD);
	}

//	private void initCrossDB() {
//		Preconditions.checkNotNull(GameInfo.DB_CROSS_URL, "DB_CROSS_URL is null");
//		Preconditions.checkNotNull(GameInfo.DB_CROSS_USR, "DB_CROSS_USR is null");
//		Preconditions.checkNotNull(GameInfo.DB_CROSS_PWD, "DB_CROSS_PWD is null");
//		
//		String [] db_cross_urls = GameInfo.DB_CROSS_URL.split(",");
//		String [] db_cross_usrs = GameInfo.DB_CROSS_USR.split(",");
//		String [] db_cross_pwds = GameInfo.DB_CROSS_PWD.split(",");
//		
//		int crossDBUrlCount = db_cross_urls.length;
//		int crossDBUsrCount = db_cross_usrs.length;
//		int crossDBPwdCount = db_cross_pwds.length;
//		if(crossDBUrlCount == crossDBUsrCount && crossDBUrlCount == crossDBPwdCount) {
//		}
//	}

	private GenericObjectPool<?> connectionPool;
	private static final int CONNECTION_POOL_SIZE = 30;
	private static final int CONNECTION_POOL_ABANDON_TIMEOUT = 10;
	private static final String VALIDATION_QUERY_STRING = "select 1";
	private static final String SINGLE_CONNECTION_STRING = "jdbc:apache:commons:dbcp:example";
	
	public void init(String urlStr, String userVal, String passwordVal) throws Throwable {
		System.out.println("database init single pool start ...");
		init("example", urlStr, userVal, passwordVal);
		System.out.println("database init single pool end ...");
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(String poolName, String urlStr, String userVal, String passwordVal) throws Throwable {
		logger.debug("dbInit urlStr is {}, userVal is {}, pwdVal is {}", new Object [] {urlStr, userVal, passwordVal});
		
		Preconditions.checkNotNull(poolName, "poolName is null");
		Preconditions.checkNotNull(urlStr, "urlStr is null");
		Preconditions.checkNotNull(userVal, "userVal is null");
		Preconditions.checkNotNull(passwordVal, "passwordVal is null");
		
		Class.forName("com.mysql.jdbc.Driver");
		connectionPool = new GenericObjectPool(null, CONNECTION_POOL_SIZE);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(urlStr, userVal, passwordVal);
		AbandonedConfig ac = new AbandonedConfig();
		ac.setRemoveAbandoned(true);
		ac.setRemoveAbandonedTimeout(CONNECTION_POOL_ABANDON_TIMEOUT);
		ac.setLogAbandoned(true);
		ac.setLogWriter(new PrintWriter(System.err, true));
		KeyedObjectPoolFactory kopf = new GenericKeyedObjectPoolFactory(null, CONNECTION_POOL_SIZE);
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
				connectionPool, kopf, VALIDATION_QUERY_STRING, false, true, ac);
		PoolingDriver driver = new PoolingDriver();
		connectionPool.setMinIdle(1);
		connectionPool.setTestWhileIdle(true);
		connectionPool.setTimeBetweenEvictionRunsMillis(10000);
		driver.registerPool(poolName, connectionPool);
	}
	
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(SINGLE_CONNECTION_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void closeConnectionPool() {
    	if(connectionPool != null) {
    		try {
				connectionPool.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void closePool() {
    	getDBHelper().closeConnectionPool();
    }
}
