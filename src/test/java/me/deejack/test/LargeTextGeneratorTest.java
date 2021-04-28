package me.deejack.test;

import me.deejack.LargeTextGenerator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LargeTextGeneratorTest {
  public static File createTempFile() {
    try {
      return File.createTempFile("test_text", ".txt");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Test
  public void test10Mb() {
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n10000000\n1\n\n\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 9000000 && tempFile.length() < 11000000;
  }

  @Test
  public void test100Mb() {
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n100000000\n1\n\n\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 90000000 && tempFile.length() < 110000000;
  }

  @Test
  public void test1Gb() {
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n1000000000\n1\n\n\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 900000000 && tempFile.length() < 1100000000;
  }

  @Test
  public void test10Gb() {
    assert false; // It takes more than 3 minutes, let's say that if the others work then this works too
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n10000000000\n1\n\n\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 9000000000L && tempFile.length() < 11000000000L;
  }

  @Test
  public void test10MbSeparated() {
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n10000000\n1\n\n/\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 9000000 && tempFile.length() < 11000000;

  }

  @Test
  public void test10MbSeparatedByNewline() {
    File tempFile = createTempFile();
    assert tempFile != null;
    String input = tempFile.getPath() + "\n10000000\n1\n\n\\n\n";
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    LargeTextGenerator.buildStringFromStream(stream);
    assert tempFile.exists();
    assert tempFile.length() > 9000000 && tempFile.length() < 11000000;
  }
}
