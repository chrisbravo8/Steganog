import java.io.File;
import java.io.IOException;

public class Startup {

	public static void main(String[] args) {
		
		File file = new File("images/decrypt.png");

		
		System.out.println(file.getAbsolutePath());
		try {
			System.out.println(Steganog.embedIntoImage(file, "DR WHO IS NOT A SHOW I WATCH             "));
			String message = Steganog.retreiveFromImage(file);
			System.out.println();
			System.out.println("Mesasge: "+message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
