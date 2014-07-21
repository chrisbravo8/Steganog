import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Steganog {
	/**
	 * Takes a clean image and changes the prime-indexed pixels to secretly
	 * carry the message
	 **/
	public static File embedIntoImage(File original, String message)
			throws IOException {

		BufferedImage image = ImageIO.read(original);
		PrimeIterator ite = new PrimeIterator(image.getWidth()
				* image.getHeight());
		int temp;
		int prime = ite.next();
		int place = 0;

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				if ((i * image.getHeight() + j) == prime) {

					if (message.length() > place) {
						char holder = message.charAt(place);
						Integer result = 0;
						if (holder != ' ') {
							System.out.println(holder);
							holder -= 31;

							String toSave = Integer.toBinaryString(holder);
							System.out.println(toSave);
							temp = image.getRGB(j, i);

							String s = Integer.toBinaryString(temp);

							System.out.println(s);

							String r = s.substring(0, 14) + toSave.charAt(0)
									+ toSave.charAt(1);
							String g = s.substring(16, 22) + toSave.charAt(2)
									+ toSave.charAt(3);
							String b = s.substring(24, 30) + toSave.charAt(4)
									+ toSave.charAt(5);

							String encoded = r + g + b;
							System.out.println(encoded);

							char[] newPix = encoded.toCharArray();
							result = 0;
							int count = 0;
							for (int i2 = newPix.length - 1; i2 >= 0; i2--) {
								if (newPix[i2] == '1')
									result += (int) Math.pow(2, count);
								count++;
							}

						}

						image.setRGB(j, i, result);

						if (ite.hasNext()) {
							prime = ite.next();
						}

						place++;

					} else {
						break;
					}

				}
			}
		}
		ImageIO.write(image, "PNG", original);
		return original;

	}

	/**
	 * Retreives the embedded secret from the secret-carrying image
	 */
	public static String retreiveFromImage(File toDecode) throws IOException {

		String message = "";

		int temp;

		BufferedImage image = ImageIO.read(toDecode);
		PrimeIterator it = new PrimeIterator(image.getWidth()
				* image.getHeight());
		

		int prime = it.next();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				if ((i * image.getHeight() + j) == prime) {
					temp = image.getRGB(j,i);
					String s = Integer.toBinaryString(temp);

					String letter = s.charAt(14) + "" + s.charAt(15)
							+ s.charAt(22) + s.charAt(23) + s.charAt(30)
							+ s.charAt(31) + "";
					int decimal = Integer.parseInt(letter, 2);
					decimal += 32;

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
