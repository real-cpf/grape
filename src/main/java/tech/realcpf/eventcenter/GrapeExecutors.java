package tech.realcpf.eventcenter;

import java.util.concurrent.*;

/**
 * 自定义executors,未来会有更多合适的参赛组合
 * will more ThreadPoolExecutors
 */
public class GrapeExecutors {
  private static int corePoolSize = 4;
  private static int maximumPoolSize = corePoolSize * 4;

  public static ThreadPoolExecutor newTheExecutor() {

    return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
      1L, TimeUnit.MILLISECONDS,
      new ArrayBlockingQueue<>(maximumPoolSize * 16), new GrapeThreadFactory(), (r, executor) -> {
      executor.execute(r);
      });
  }

}
