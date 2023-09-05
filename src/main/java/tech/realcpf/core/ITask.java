package tech.realcpf.core;

/**
 * task interface , the function need run
 * 抽象task本质是一个函数
 */
public interface ITask extends Statistical{
  TaskStatus status();
  void init();
  TaskStatus start();
}
