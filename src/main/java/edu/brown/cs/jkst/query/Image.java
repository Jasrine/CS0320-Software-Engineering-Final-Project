package edu.brown.cs.jkst.query;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * class that handles some of the image work.
 */
public final class Image {

  private Image() {
  }

  /**
   * save an image.
   *
   * @param imageUrl
   *          to get image from.
   * @param destinationFile
   *          to write the file to.
   * @throws IOException
   *           if there's an error reading in file.
   */
  public static void saveImage(String imageUrl, String destinationFile)
      throws IOException {
    URL url = new URL(imageUrl);
    InputStream is = url.openStream();
    OutputStream os = new FileOutputStream(destinationFile);

    byte[] b = new byte[2048];
    int length;

    while ((length = is.read(b)) != -1) {
      os.write(b, 0, length);
    }

    is.close();
    os.close();
  }
}
