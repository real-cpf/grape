package tech.realcpf.core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GLogger {
  private final String name;
  private final Level level;
  private final Logger logger;

  public GLogger(String name){
    this.name = name;
    this.level = Level.INFO;
    logger = Logger.getLogger(name);
  }

  public GLogger(String name,Level level) {
    this.name = name;
    this.level = level;
    logger = Logger.getLogger(name);
  }

  public void log(String msg) {
    logger.log(level,msg);
  }

  public static GLogger thisLog(String name) {
    return new GLogger(name);
  }
  public static GLogger thisLog(String name,Level level) {
    return new GLogger(name,level);
  }

}
