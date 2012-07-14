package com.lml.ge;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import com.lml.ge.processor.GameWorldProcessor;
import com.lml.ge.processor.ServerProcessor;
import com.lml.ge.util.config.ConfigUtil;
import com.lml.ge.util.db.DataBaseConnector;
import com.lml.ge.util.redis.RedisConnector;
import com.lml.ge.util.scheduler.ScheduleUtil;

public class gameServer implements Daemon {
	private static String [] inputArgs;
	public static void main(String[] args) {
		inputArgs = args;
		startGame();
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		inputArgs = arg0.getArguments();
	}

	@Override
	public void start() throws Exception {
		if(inputArgs == null || inputArgs.length == 0) {
			startGame();
		} else {
			
		}
	}

	@Override
	public void stop() throws Exception {
		endGame();
	}
	
	@Override
	public void destroy() {}

	private static void endGame() {
		
	}
	
	private static void startGame() {
		try {
			initWorkers();
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

	private static void initWorkers() {
		GameWorldProcessor.getWorldProcessor().initWorkers();
	}

	private static void initConfigs() throws Throwable {
		ConfigUtil.getConfigUtil().init();
	}

	private static void initConnectionPools() throws Throwable {
		DataBaseConnector.getDBUtil().init();
		RedisConnector.getRedisUtil().init();
	}

	private static void initGameData() {
		
	}

	private static void initScheduledTask() {
		ScheduleUtil.getScheduleUtil().init();
	}

	private static void initServer() {
		ServerProcessor.initMainServer();
		ServerProcessor.initManageServer();
	}
}
