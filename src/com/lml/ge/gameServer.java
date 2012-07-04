package com.lml.ge;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import com.lml.ge.helper.configHelper.ConfigHelper;
import com.lml.ge.helper.dbHelper.DataBaseConnector;
import com.lml.ge.helper.redisHelper.RedisConnector;
import com.lml.ge.helper.scheduleHelper.ScheduleHelper;
import com.lml.ge.processor.GameWorldProcessor;

public class gameServer implements Daemon {
	private static String [] inputArgs;
	public static void main(String[] args) {
		inputArgs = args;
		gameStart();
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		inputArgs = arg0.getArguments();
	}

	@Override
	public void start() throws Exception {
		if(inputArgs == null || inputArgs.length == 0) {
			gameStart();
		} else {
			
		}
	}

	@Override
	public void stop() throws Exception {
		gameEnd();
	}
	
	@Override
	public void destroy() {}

	private static void gameEnd() {
		
	}
	
	private static void gameStart() {
		try {
			initThreadPool();
			initConnectionPools();
			initConfigs();
			initGameData();
			initScheduledTask();
			initServer();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * 启动线程池
	 */
	private static void initThreadPool() {
		GameWorldProcessor.getWorldProcessor().initThreadPool();
	}

	/**
	 * 游戏配置
	 * @throws Throwable
	 */
	private static void initConfigs() throws Throwable {
		ConfigHelper.getConfigs().init();
	}

	/**
	 * 初始化DB
	 * @throws Throwable
	 */
	private static void initConnectionPools() throws Throwable {
		DataBaseConnector.getDBHelper().init();
		RedisConnector.getRedisHelper().init();
	}

	/**
	 * 初始化游戏数据
	 */
	private static void initGameData() {
		
	}

	/**
	 * 启动所有刷新任务
	 */
	private static void initScheduledTask() {
		ScheduleHelper.getScheduleHelper().init();
	}

	/**
	 * 启动服务器各种监听
	 */
	private static void initServer() {
		
	}
}
