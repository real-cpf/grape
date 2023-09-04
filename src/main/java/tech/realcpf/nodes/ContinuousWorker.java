package tech.realcpf.nodes;

import tech.realcpf.core.Task;
import tech.realcpf.core.Worker;
import tech.realcpf.core.WorkerStatus;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContinuousWorker extends Worker {

  private final AtomicBoolean RUNNING = new AtomicBoolean(false);
  private final Queue<Task> TASK_QUEUE = new ConcurrentLinkedQueue<>();
  @Override
  public WorkerStatus start() {
    RUNNING.compareAndSet(false,true);
    return super.start();
  }

  @Override
  public void run() {
    start();
    while (RUNNING.get()) {

    }
    done();
  }

  @Override
  public void done() {
    super.done();
  }

  @Override
  public WorkerStatus stop() {



    return super.stop();
  }
}
