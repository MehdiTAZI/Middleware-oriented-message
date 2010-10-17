package com.esiag.calc;

public class Calculatrice implements ICalculate{

	
	public double operation(double op1, double op2, char op) {
		
		switch (op) {
		case '+': return op1 + op2;
		case '-': return op1 - op2;
		case '*': return op1 * op2;
		case '/': return op1 / op2;
	}
	return 0;
	}

}
