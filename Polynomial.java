public class Polynomial {
	// Field: Represents the polynomial based on 
	//        ascending order of exponents
	double coefficients[];

	// Constructors
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0.0;
	}

	public Polynomial(double c[]) {
		coefficients = new double[c.length];
		for (int i = 0; i < coefficients.length; i++)
			coefficients[i] = c[i];
	}

	// Methods

	// This method adds two polynomials, (the calling object &
	// the argument), together and returns the resulting polynomial 
	// sum of both polynomials.
	public Polynomial add(Polynomial p) {
		double c[];
		Polynomial result;

		if (coefficients.length <= p.coefficients.length) {
			c = new double[p.coefficients.length];
			result = new Polynomial(c);

			for (int i = 0; i < p.coefficients.length; i++) {
				if (i > coefficients.length-1)
					result.coefficients[i] = p.coefficients[i];
				else
					result.coefficients[i] += (p.coefficients[i] + coefficients[i]);
			}
		}else{
			c = new double[coefficients.length];
			result = new Polynomial(c);

			for (int i = 0; i < coefficients.length; i++) {
				if (i > (p.coefficients.length-1))
					result.coefficients[i] = coefficients[i];
				else
					result.coefficients[i] += (coefficients[i] + p.coefficients[i]);
			}

		}
		return result;
	}

	// This method evaluates the polynomial at a specific x-value.
	public double evaluate(double x) {
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++)
			result += (coefficients[i]*Math.pow(x, i));
		return result;
	}

	// This method returns true if x is a root of the 
	// polynomial and false otherwise.
	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);
	}
}