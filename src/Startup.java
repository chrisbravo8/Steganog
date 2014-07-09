import java.io.File;
import java.io.IOException;

public class Startup {

	public static void main(String[] args) {
		
		File file = new File("C:\\Users\\cbravo\\Desktop\\decrypt.png");

		
		System.out.println(file.getAbsolutePath());
		try {
			System.out.println(PrimeSuspects.embedIntoImage(file, "IDONTREALLYLIKEDRWHOIDONOTTHINKTHESHOWISTHATGREAT"));
			String message = PrimeSuspects.retreiveFromImage(file);
			System.out.println();
			System.out.println("Mesasge: "+message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
