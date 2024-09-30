package labs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	// Fields:
	//	An array representing the coefficients of the polynomial.
	// 	An array representing the exponents corresponding to the placement of coefficients.
	double coefficients[];
	int exponents[];

	// Constructors
	public Polynomial() {
		coefficients = new double[0];
		exponents = new int[0];
	}

	public Polynomial(double c[], int e[]) {
		coefficients = new double[c.length];
		exponents = new int[e.length];
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = c[i];
			exponents[i] = e[i];
		}
	}

	public Polynomial(File f) {
		// Read file f
		String polynomial = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("myFile.txt"));
			polynomial = reader.readLine();			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		polynomial = polynomial.replaceAll("-", "+-");

		String arr[] = polynomial.split("\\+");
		coefficients = new double[arr.length-1];
		exponents = new int[arr.length-1];
		int i = 0;
		for (String s : arr) {
			if (s.indexOf('x') == -1 && s != "") {
				coefficients[i] = Double.parseDouble(s);
				exponents[i] = 0;
				i++;
			}else {
				String terms[] = s.split("x");
				if (terms[0] != "" && terms[1] != "") {
					coefficients[i] = Double.parseDouble(terms[0]);
					exponents[i] = Integer.parseInt(terms[1]);
					i++;
				}

			}

		}
	}

	//Methods

	public void saveToFile(File f) {
		//Write to a file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("myFile.txt"));
			String s = "";
			for(int i = 0; i < coefficients.length; i++) {
				if (i == 0) {
					s += coefficients[i];
					if (exponents[i] != 0)
						s += ("x" + exponents[i]);
				}else {
					if (coefficients[i] > 0) {
						s += ("+" + coefficients[i]);
						if (exponents[i] != 0)
							s += ("x" + exponents[i]);
					}else {
						s += coefficients[i];
						if (exponents[i] != 0)
							s += ("x" + exponents[i]);
					}
				}
					
			}
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int exponentThere(int exp, int e[]) {
		for(int i = 0; i < e.length; i++) {
			if (e[i] == exp)
				return i;
		}
		return -1;
	}

	public double addTerms(int exp, int placement, Polynomial p) {
		double sum = 0.0;
		int isExpThere = exponentThere(exp, p.exponents);
		if (isExpThere == -1) return 1.0;
		sum = p.coefficients[isExpThere] + coefficients[placement];
		if(sum != 0) return sum;
		else return 0.0;

	}
 
	public int computeLen(Polynomial p) {
		int totalExps[] = new int[p.exponents.length + exponents.length];

		//initialize elements of totalExps to -1
		for(int i = 0; i< totalExps.length; i++) {
			totalExps[i] = -1;
			System.out.println("totalExps[" + i + "] = " + totalExps[i]);
		}

		// Count the total number of terms present when adding the two polynomials
		int k, j = 0, len = 0;
		for(int i = 0; i < exponents.length + p.exponents.length; i++) {
			if (i < exponents.length) {
				if (exponentThere(exponents[i], totalExps) == -1 && addTerms(exponents[i], i, p) != 0) {
					totalExps[j] = exponents[i];
					j++;
					len++;
				}

			}else{
				k = i - exponents.length;
				if (exponentThere(p.exponents[k], totalExps) == -1 && addTerms(p.exponents[k], k, p) != 0) {
					totalExps[j] = p.exponents[k];
					j++;
					len++;
				}
			}

		}
		for(int i = 0; i< totalExps.length; i++) {
			System.out.println("totalExps[" + i + "] = " + totalExps[i]);
		}
		System.out.println("len: " + len);
		return len;
	}


	public Polynomial add(Polynomial p) {
		if ((p == null) || p.coefficients.length == 0 && coefficients.length == 0) return null;
		else if (p.coefficients.length == 0) {
			double c[] = new double[coefficients.length];
			int e[] = new int[exponents.length];
			for(int i = 0; i < coefficients.length; i++) {
				c[i] = coefficients[i];
				e[i] = exponents[i];
			}
			Polynomial result = new Polynomial(c,e);
			return result;
			
		}
		else if (coefficients.length == 0) return p;
		// Declarations & Initializations
		int p_len = computeLen(p); // Returns the length of the resulting polynomial when we add both the calling and p together.
		System.out.println("RESULTING LEN: " + p_len);


		double c[] = new double[p_len];
		int e[] = new int[p_len]; 
		Polynomial result = new Polynomial(c,e);
		int k, j = 0, expThere;

		for(int i = 0; i < exponents.length + p.exponents.length; i++) {
			if (i < exponents.length) {
				expThere = exponentThere(exponents[i], p.exponents);
				if (expThere == -1) {
					result.coefficients[j] = coefficients[i];
					result.exponents[j] = exponents[i];
					j++;
				}else {
					if (coefficients[i] + p.coefficients[expThere] != 0.0) {
						result.coefficients[j] = (coefficients[i] + p.coefficients[expThere]);
						result.exponents[j] = exponents[i];
						j++;
					}
				}


			}else {
				k = i - p.exponents.length;
				if(exponentThere(p.exponents[k], exponents) == -1) {
					result.coefficients[j] = p.coefficients[k];
					result.exponents[j] = p.exponents[k];
					j++;
				}
			}

		}

		return result;
	}

	public boolean noRedundantExponents(int e[]) {
		for(int i = 0; i < e.length; i++) {
			for(int j = i+1; j < e.length; j++) {
				if (e[i] == e[j]) return false;
			}
		}
		return true;
	}

	public int computeLen(int e[]) {
		int len = 0, k = 0;
		int exps[] = new int[e.length];
		for(int i = 0; i < exps.length; i++) 
			exps[i] = -1;

		for(int i = 0; i < e.length; i++) {
			if(e[i] != -1 && exponentThere(e[i], exps) == -1) {
				exps[k] = e[i];
				len++;
				k++;
			}

		}
		return len;
	}
	
	public int computeLen(double c[]) {
		int len = 0;
		for(int i = 0; i < c.length; i++) {
			if (c[i] != 0) len++;
		}
		return len;
	}

	//FIXED
	public Polynomial multiply(Polynomial p) {
		if ((p == null) || p.coefficients.length == 0 && coefficients.length == 0) return null;
		else if (p.coefficients.length == 0) {
			double c[] = new double[coefficients.length];
			int e[] = new int[exponents.length];
			for(int i = 0; i < coefficients.length; i++) {
				c[i] = coefficients[i];
				e[i] = exponents[i];
			}
			Polynomial result = new Polynomial(c,e);
			return result;
			
		}
		else if (coefficients.length == 0) return p;
		
		double c[] = new double[p.coefficients.length * coefficients.length];
		int e[] = new int[p.exponents.length * exponents.length];
		int k = 0;
		Polynomial result;

		for(int i = 0; i < coefficients.length; i++) {
			for(int j = 0; j < p.coefficients.length; j++) {
				c[k] = coefficients[i] * p.coefficients[j];
				e[k] = exponents[i] + p.exponents[j];
				k++;
			}
		}

		if (noRedundantExponents(e)) {
			result = new Polynomial(c,e);

		}else {
			
			int len = computeLen(e);
			int val, a = 0;
			double coefs[] = new double[len];
			int exps[] = new int[len];
			
			for(int i = 0; i < exps.length; i++)
				exps[i] = -1;

			for(int i = 0; i < coefficients.length; i++) {
				for(int j = 0; j < p.coefficients.length; j++) {
					val = exponentThere(exponents[i] + p.exponents[j], exps);
					if(val == -1 && coefficients[i] * p.coefficients[j] != 0) {
						coefs[a] = coefficients[i] * p.coefficients[j];
						exps[a] = exponents[i] + p.exponents[j];
						a++;
					}else
						coefs[val] += coefficients[i];
				}
			}
			
			// Check for zeros and fix
			int newLen = computeLen(coefs);
			if (newLen != coefs.length) {
				double newCoefs[] = new double[newLen];
				int newExps[] = new int[newLen];
				int z = 0;
				for(int i = 0; i < coefs.length; i++) {
					if (coefs[i] != 0.0) {
						newCoefs[z] = coefs[i];
						newExps[z] = exps[i];
						z++;
					}
				}
				result = new Polynomial(newCoefs, newExps);
				return result;
			}
			result = new Polynomial(coefs,exps);			
		}
		return result;

	}
	// This method evaluates the polynomial at a specific x-value.
	// Method Fixed
	public double evaluate(double x) {
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++)
			result += (coefficients[i]*Math.pow(x, exponents[i]));
		return result;
	}

	// This method returns true if x is a root of the 
	// polynomial and false otherwise.
	// Method Fixed.
	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);
	}
}