package labs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) {

		double [] c1 = {1,3};
		int [] e1 = {1,0};
		Polynomial p1 = new Polynomial(c1,e1);
		double [] c2 = {1,-3};
		int [] e2 = {1,0};
		Polynomial p2 = new Polynomial(c2,e2);
		Polynomial s = p1.multiply(p2);
		
		//Write to a file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("myFile.txt"));
			writer.write("-5");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File f = new File("myFile.txt");
		
		Polynomial file = new Polynomial(f);
		
		s.saveToFile(f);
		
	}
}
