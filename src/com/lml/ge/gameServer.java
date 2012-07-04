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
	 * �����̳߳�
	 */
	private static void initThreadPool() {
		GameWorldProcessor.getWorldProcessor().initThreadPool();
	}

	/**
	 * ��Ϸ����
	 * @throws Throwable
	 */
	private static void initConfigs() throws Throwable {
		ConfigHelper.getConfigs().init();
	}

	/**
	 * ��ʼ��DB
	 * @throws Throwable
	 */
	private static void initConnectionPools() throws Throwable {
		DataBaseConnector.getDBHelper().init();
		RedisConnector.getRedisHelper().init();
	}

	/**
	 * ��ʼ����Ϸ����
	 */
	private static void initGameData() {
		
	}

	/**
	 * ��������ˢ������
	 */
	private static void initScheduledTask() {
		ScheduleHelper.getScheduleHelper().init();
	}

	/**
	 * �������������ּ���
	 */
	private static void initServer() {
		
	}
}
