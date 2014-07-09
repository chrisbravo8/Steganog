import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PrimeSuspects {
	/**
	 * Takes a clean image and changes the prime-indexed pixels to secretly
	 * carry the message
	 **/
	public static File embedIntoImage(File original, String message)
			throws IOException {

		BufferedImage bufferedOriginal = ImageIO.read(original);

		PrimeIterator it = new PrimeIterator(bufferedOriginal.getHeight()
				* bufferedOriginal.getWidth());
		int temp;
		int prime = it.next();
		int place = 0;

		for (int x = 0; x < bufferedOriginal.getWidth(); x++) {
			for (int y = 0; y < bufferedOriginal.getHeight(); y++) {
				if ((x * bufferedOriginal.getHeight() + y) == prime) {

					if (message.length() > place) {
						char holder = message.charAt(place);
						String toSave = Integer.toBinaryString(holder)
								.substring(1);
						
						temp = bufferedOriginal.getRGB(x, y);
						
						String s = Integer.toBinaryString(temp);
						String pix = s.substring(0, 13) + toSave.charAt(0) + toSave.charAt(1) + s.substring(16, 21) + toSave.charAt(2) + toSave.charAt(3) + s.substring(24, 29) + toSave.charAt(4) + toSave.charAt(5) + s.substring(32);
					
						temp = Integer.parseInt(pix, 2);
						
						bufferedOriginal.setRGB(x, y, temp);

					} else {
						break;
					}
					place++;

					if (it.hasNext()) {
						prime = it.next();
					}
				}
			}
		}
		ImageIO.write(bufferedOriginal, "PNG", original);
		return original;

	}

	/**
	 * Retreives the embedded secret from the secret-carrying image
	 */
	public static String retreiveFromImage(File toDecode) throws IOException {

		String message = "";

		int temp;

		BufferedImage image = ImageIO.read(toDecode);
		int pixels[][];
		PrimeIterator it = new PrimeIterator(image.getWidth()
				* image.getHeight());
		pixels = new int[image.getWidth()][image.getHeight()];

		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				pixels[i][j] = image.getRGB(i, j);
			}
		}

		int prime = it.next();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				if ((i * image.getHeight() + j) == prime) {
					temp = pixels[j][i];
					String s = Integer.toBinaryString(temp);

					String letter = s.charAt(14) + "" + s.charAt(15)
							+ s.charAt(22) + s.charAt(23) + s.charAt(30)
							+ s.charAt(31) + "";
					int decimal = Integer.parseInt(letter, 2);
					decimal += 32;

					// System.out.print((char) decimal); // test printing

					message += (char) decimal;// convert and add to message
					if (it.hasNext()) {
						prime = it.next();
					}
				}
			}
		}

		return message;

	}
}
