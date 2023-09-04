package tech.realcpf.nodes;

import tech.realcpf.core.Task;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class NodeTask extends Task {


  public NodeTask(String name) {
    super(name);
  }

  @Override
  public Object run(Object... params) {
    GLOGGER.log("start run with params " + Arrays.toString(params));
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    GLOGGER.log("done run with params " + Arrays.toString(params));
    return 0;
  }
}
