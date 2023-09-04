package tech.realcpf.core;

public interface ITask extends Statistical{
  TaskStatus status();
  void init();
  TaskStatus start();
}
