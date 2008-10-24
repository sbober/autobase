import liquibase.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.zip.*;
import org.apache.log4j.*;

public class WarFileOpener extends FileSystemFileOpener implements FileOpener {

  private static final Logger logger = Logger.getLogger(WarFileOpener.class);

  public WarFileOpener() throws IOException {
    super(expandStream(createZipStream()).getAbsolutePath());
  }

  /**
  * Expands the given stream and returns the base directory where files were expanded into.
  */
  private static File expandStream(final ZipInputStream source) throws IOException {
    final String timestamp = Long.toString(System.currentTimeMillis(), 16);
    final File base = new File(System.getProperty("java.io.tmpdir"), "autobase-" + timestamp).getAbsoluteFile();
    base.mkdirs();
    logger.debug("Base directory: " + base.getAbsolutePath());
    deleteDirOnExit(base);
    for(ZipEntry entry = source.getNextEntry(); entry != null; entry = source.getNextEntry()) {
      final File entryFile = new File(base, entry.getName()).getAbsoluteFile();
      logger.debug("Writing out file: " + entryFile.getAbsolutePath());
      entryFile.getParentFile().mkdirs();
      if(entry.isDirectory()) {
        source.closeEntry();
        entryFile.mkdir();
      } else {
        entryFile.createNewFile();
        final OutputStream entryStream = new BufferedOutputStream(new FileOutputStream(entryFile));
        for(int c = source.read(); c != -1; c = source.read()) {
          entryStream.write(c);
        }
        source.closeEntry();
        entryStream.close();
      }
    }
    source.close();
    return base;
  }

  private static void deleteDirOnExit(final File base) {
    final Runnable deleteBase = new Runnable() {
      public void run() { 
        delete(base);
      }

      private void delete(final File dir) {
        final File[] files = dir.listFiles();
        if(files != null) {
          for(final File file : files) {
            delete(file);
          }
        }
        dir.delete();
      }
    };
    Runtime.getRuntime().addShutdownHook(new Thread(deleteBase));
  }

  private static ZipInputStream createZipStream() throws IOException {
    return new ZipInputStream(new BufferedInputStream(new ClassLoaderFileOpener().getResourceAsStream("/autobase.zip")));
  }

}
