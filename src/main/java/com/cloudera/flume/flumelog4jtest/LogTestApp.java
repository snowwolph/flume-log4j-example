package com.cloudera.flume.flumelog4jtest;

import com.google.common.io.Resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class LogTestApp {

  public static void main(String[] args) throws IOException {
    Logger logger = Logger.getLogger(LogTestApp.class);

    System.out.println("Initializing Flume log4j appender test.");
    System.out.println("A few Avro records will be sent to Flume.");

    // Read an Avro schema from the schema file on the classpath
    Schema schema = new Schema.Parser().parse(
        Resources.getResource("appevent.avsc").openStream());

    // Optional optimization. Assign an ID to the schema so that Flume events don't
    // have to carry the schema. The ID is arbitrary, but must match one defined in the
    // flume properties file, where the schema definition is given.
    MDC.put("flume.client.log4j.avro.schema.id", 1);

    GenericRecordBuilder builder = new GenericRecordBuilder(schema);
    for (long i = 0; i < 10; i++) {
      GenericRecord appEvent = builder.set("id", i)
          .set("message", "Hello " + i).build();
      System.out.println("Sending to log4j: " + appEvent);
      logger.info(appEvent);
    }

  }
}
