package jhaturanga;


import jhaturanga.commons.validator.ValidatorBuilderImpl;

public class Launcher {

	public static void main(String[] args) {

	    System.out.println(new ValidatorBuilderImpl()
	            .notEmpty()
	            .notShortedThan(5)
	            .notLongerThan(4)
	            .notEmpty()
	            .build()
	            .apply("ciaooo"));

	}

}
