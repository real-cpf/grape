package tech.realcpf.core;

import java.util.concurrent.atomic.AtomicLong;

public class Worker implements IWorker ,Runnable{
  private final GLogger GLOGGER = new GLogger(Worker.class.getName());
  private final AtomicLong statId = new AtomicLong(0);
  private WorkerStatus workerStatus = WorkerStatus.CREATED;
  @Override
  public void run() {
    init();
    start();
    done();
  }

  @Override
  public void init() {
    GLOGGER.log("init");
  }

  @Override
  public WorkerStatus start() {
    workerStatus = WorkerStatus.RUNNING;
    return workerStatus;
  }

  @Override
  public WorkerStatus status() {
    return workerStatus;
  }

  @Override
  public void done() {
    GLOGGER.log("done");
  }

  @Override
  public WorkerStatus stop() {
    return WorkerStatus.DONE;
  }

  @Override
  public Object statistics() {
    return workerStatus;
  }

  @Override
  public Long id() {
    return statId.get();
  }
}
