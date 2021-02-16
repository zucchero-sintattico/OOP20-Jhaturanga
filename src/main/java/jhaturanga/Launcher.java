package jhaturanga;


import jhaturanga.commons.validator.ValidatorBuilderImpl;

public class Launcher {

	public static void main(String[] args) {

	    System.out.println(new ValidatorBuilderImpl()
	            .notEmpty()
	            .notShortedThan(3)
	            .notLongerThan(5)
	            .notEmpty()
	            .build()
	            .apply("123456"));

	}

}
