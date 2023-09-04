package tech.realcpf.nodes;

import tech.realcpf.core.Run;
import tech.realcpf.core.Task;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class NodeTask extends Task {

  private final Run run;
  public NodeTask(String name,Run run) {
    super(name);
    this.run = run;
  }

  @Override
  public Object run(Object... params) {
    GLOGGER.log("start run with params " + Arrays.toString(params));
    try {
      run.run(params);
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    GLOGGER.log("done run with params " + Arrays.toString(params));
    return 0;
  }
}
