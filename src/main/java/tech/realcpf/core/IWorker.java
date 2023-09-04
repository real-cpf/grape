package tech.realcpf.core;

public interface IWorker extends Statistical {

  void init();
  WorkerStatus start();
  WorkerStatus status();
  void done();
  WorkerStatus stop();
}
