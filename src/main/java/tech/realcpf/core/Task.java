package tech.realcpf.core;

import java.util.concurrent.atomic.AtomicLong;

public class Task implements ITask {
  private final String name;
  public Task(String name){
    this.name = name;
  }
  public final GLogger GLOGGER = new GLogger(this.getClass().getName());

  private final AtomicLong taskId = new AtomicLong(0);
  private TaskStatus taskStatus = TaskStatus.CREATED;
  @Override
  public TaskStatus status() {
    return taskStatus;
  }

  public Object run(Object... params) {
    return params;
  }

  @Override
  public void init() {
    GLOGGER.log("init");
  }

  @Override
  public TaskStatus start() {
    taskStatus = TaskStatus.RUNNING;
    return taskStatus;
  }

  @Override
  public Object statistics() {
    return String.format("task[%s] id:%s status:%s %n",this.name,id(),status());
  }

  @Override
  public Long id() {
    return taskId.get();
  }
}
