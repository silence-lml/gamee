package com.lml.ge.util.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lml.ge.helper.GameInfo;

public class ScheduleUtil {
	private final ScheduledExecutorService scheduleExec;
	private ScheduleUtil() {
		scheduleExec = Executors.newScheduledThreadPool(GameInfo.GAME_THREAD_COUNT + 1, new ThreadFactory() {
			private AtomicInteger counter = new AtomicInteger();
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "scheduleWorker#" + counter.incrementAndGet());
			}
		});
	}
	
	public static ScheduleUtil getScheduleHelper() {
		return new ScheduleUtil();
	}
	
	public static void init() {
		
	}
}
