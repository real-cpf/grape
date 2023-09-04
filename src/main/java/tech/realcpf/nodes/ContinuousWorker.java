package tech.realcpf.nodes;

import tech.realcpf.core.Task;
import tech.realcpf.core.Worker;
import tech.realcpf.core.WorkerStatus;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class ContinuousWorker extends Worker {

  private final AtomicBoolean BUSY = new AtomicBoolean(false);
  private final AtomicBoolean RUNNING = new AtomicBoolean(false);
  private final Queue<NodeTask> TASK_QUEUE = new ConcurrentLinkedQueue<>();
  @Override
  public WorkerStatus start() {
    RUNNING.compareAndSet(false,true);
    System.out.println("worker start");
    return super.start();
  }


  @Override
  public void run() {
    start();
    while (RUNNING.get()) {
      Task task = TASK_QUEUE.poll();
      if (task == null) {
        BUSY.compareAndSet(true,false);
        if (BUSY.get()) {
          ContinuousEventBus.reclaim(this);
        }
        System.out.println("not busy");
//        LockSupport.parkNanos(100000);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }else {
        System.out.println("task run");
        task.run();
      }
    }
    done();
  }
  public void noBusy(){
    BUSY.compareAndSet(false,true);
  }
  public void pushTask(NodeTask task) {
    TASK_QUEUE.add(task);
  }

  @Override
  public void done() {
    System.out.println("worker done");
  }

  @Override
  public WorkerStatus stop() {



    return super.stop();
  }
}
