package com.lml.ge.helper;

public class GameInfo {
	/** 
	 * 数据库URL(db_ip:db_port/db_name) 
	 */
	public static String DB_URL = "localhost:3306/ge";
	/** 
	 * 数据库用户名 
	 * */
	public static String DB_USR = "root";
	/** 
	 * 数据库密码 
	 */
	public static String DB_PWD = "123";
	/**
	 * CPU个数
	 */
	public static final int GAME_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
	
//	public static String DB_CROSS_URL;
//	public static String DB_CROSS_USR;
//	public static String DB_CROSS_PWD;
	
}
