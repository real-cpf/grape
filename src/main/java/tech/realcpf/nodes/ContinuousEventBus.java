package tech.realcpf.nodes;

import tech.realcpf.core.Task;
import tech.realcpf.core.Worker;
import tech.realcpf.eventcenter.EventBus;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContinuousEventBus extends EventBus {

  private ContinuousEventBus(){

  }
  private final AtomicBoolean RUNNING = new AtomicBoolean(true);
  private static class ContinuousHolder {
    static ContinuousEventBus eventBus = new ContinuousEventBus();
  }
  private static BlockingQueue<ContinuousWorker> workerQueue = new ArrayBlockingQueue<>(4);
  private final Queue<NodeTask> tmpTaskQueue = new ConcurrentLinkedQueue<>();
  @Override
  public void init() {
    int i=4;
    ExecutorService service = Executors.newFixedThreadPool(i);
    while (i > 0) {
      ContinuousWorker worker = new ContinuousWorker();
      try {
        workerQueue.put(worker);
        service.execute(worker);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      i--;
    }
    service.shutdown();
  }

  public static ContinuousEventBus get() {
    return ContinuousHolder.eventBus;
  }


  @Override
  public void event() {
    while (RUNNING.get()) {
      try {
        ContinuousWorker worker = workerQueue.take();
        worker.noBusy();
        int max = 4;
        while (max > 0){
          NodeTask task = tmpTaskQueue.poll();
          if (task == null){
            System.out.println("no task now");
            break;
          }else {
            System.out.println("get task");
            worker.pushTask(task);
            max--;
          }
        }

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void pushTask(NodeTask task) {
    tmpTaskQueue.add(task);
  }

  public void shutdown(){
    RUNNING.compareAndSet(true,false);
  }

  public static void reclaim(ContinuousWorker worker) {
    workerQueue.add(worker);
  }
}
