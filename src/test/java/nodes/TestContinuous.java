package nodes;

import tech.realcpf.core.Run;
import tech.realcpf.nodes.ContinuousEventBus;
import tech.realcpf.nodes.NodeTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TestContinuous {
  public static void main(String[] args) {
    ContinuousEventBus eventBus = ContinuousEventBus.get();
    eventBus.init();

    int n = 20;
    while (n > 0){
      int finalN = n;
      NodeTask nodeTask = new NodeTask("task " + n, params -> {
        final int nn = finalN;
        System.out.println("this is task " + finalN);
        return 0;
      });
      System.out.println("push task " + n);
      eventBus.pushTask(nodeTask);
      n--;
    }
    CompletableFuture.runAsync(()->{eventBus.event();});
    try {
      TimeUnit.SECONDS.sleep(60);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    eventBus.shutdown();
  }

}
