package tech.realcpf.nodes;

import tech.realcpf.eventcenter.EventBus;
import tech.realcpf.eventcenter.GrapeExecutors;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContinuousEventBus extends EventBus {

  private ContinuousEventBus(){

  }
  private final ThreadPoolExecutor executor = GrapeExecutors.newTheExecutor();
  private final AtomicBoolean RUNNING = new AtomicBoolean(true);
  private static class ContinuousHolder {
    static ContinuousEventBus eventBus = new ContinuousEventBus();
  }
  /**
   * worker queue for push no busy and poll no busy
   */
  private static BlockingQueue<ContinuousWorker> workerQueue = new ArrayBlockingQueue<>(4);
  private final Queue<NodeTask> tmpTaskQueue = new ConcurrentLinkedQueue<>();
  @Override
  public void init() {
    int i=4;
    /**
     * 初始化存活worker,need imporove
     */
    while (i > 0) {
      ContinuousWorker worker = new ContinuousWorker();
      try {
        workerQueue.put(worker);
        executor.execute(worker);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      i--;
    }

  }

  public static ContinuousEventBus get() {
    return ContinuousHolder.eventBus;
  }


  @Override
  public void event() {
    while (RUNNING.get()) {
      try {
        /**
         * poll no busy worker
         */
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
            /**
             * make the worker busy
             */
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
    executor.shutdown();
  }

  public static void reclaim(ContinuousWorker worker) {
    workerQueue.add(worker);
  }
}
