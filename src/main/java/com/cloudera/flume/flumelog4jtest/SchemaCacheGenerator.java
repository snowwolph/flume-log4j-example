package com.cloudera.flume.flumelog4jtest;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;

public class SchemaCacheGenerator {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: " + SchemaCacheGenerator.class.getSimpleName() +
          " schema_files");
      System.exit(1);
    }
    Properties properties = new Properties();
    Schema.Parser parser = new Schema.Parser();
    for (String file : args) {
      Schema schema = parser.parse(new File(file));
      long id = SchemaNormalization.parsingFingerprint64(schema);
      properties.setProperty(Long.toString(id), schema.toString());
    }
    properties.store(System.out, null);
  }
}
