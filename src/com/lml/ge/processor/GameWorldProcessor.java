package com.lml.ge.processor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.lml.ge.helper.GameInfo;

public class GameWorldProcessor {
	private GameWorldProcessor() {}
	
	public static GameWorldProcessor getWorldProcessor() {
		return new GameWorldProcessor();
	}
	
	public static ExecutorService dbWorkers;
	public static ExecutorService [] gameWorkers;
	public static ScheduledExecutorService refresherWorkers; 
	
	public void initWorkers() {
		dbWorkers = new ThreadPoolExecutor(GameInfo.GAME_THREAD_COUNT, GameInfo.GAME_THREAD_COUNT, 0, TimeUnit.SECONDS,
				new LinkedTransferQueue<Runnable>(), new ThreadFactory() {
					private AtomicInteger counter = new AtomicInteger();
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "dbWorker#" + counter.incrementAndGet());
					}
				}, new ThreadPoolExecutor.CallerRunsPolicy());
		gameWorkers = new ExecutorService [GameInfo.GAME_THREAD_COUNT];
		for(int index = 0; index < GameInfo.GAME_THREAD_COUNT; index ++) {
			gameWorkers [index] = Executors.newSingleThreadExecutor(new ThreadFactory() {
				private AtomicInteger counter = new AtomicInteger();
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "gameWorker#" + counter.incrementAndGet());
				}
			});
		}
		refresherWorkers = Executors.newScheduledThreadPool(4, new ThreadFactory() {
			private AtomicInteger counter = new AtomicInteger();
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "refresher#" + counter.incrementAndGet());
			};
		});
	}
	
	public void execDBTask(Runnable dbTask) {
		dbWorkers.execute(dbTask);
	}
	
	public <V> void execDBTask(Callable<V> dbTask) {
		dbWorkers.execute(new FutureTask<V>(dbTask));
	}
	
	public Executor getGameWorker(int id) {
		int index = id % GameInfo.GAME_THREAD_COUNT;
		return gameWorkers[index];
	}
	
	public void shutdown() {
		for (ExecutorService exec : gameWorkers) {
			exec.shutdown();
		}
		dbWorkers.shutdown();
	}
}