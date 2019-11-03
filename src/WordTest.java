import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * This is a JUnit testing class for the Word class. Each test will verify each
 * of the methods of that class.
 *
 */

public class WordTest {

	@Test
	/**
	 * This test involves all the methods from the Word object class. It is a simple
	 * test in which we verify that the inputs from when we first call the object
	 * and the getter & setters all works as expected.
	 */
	public void test() {
		Word word1 = new Word(5, "Hello", 6);
		assertEquals(5, word1.getOrder());
		word1.setOrder(7);
		assertEquals(7, word1.getOrder());
		assertEquals("Hello", word1.getWord());
		word1.setWord("Bye");
		assertEquals("Bye", word1.getWord());
		assertEquals(6, word1.getFrequency());
		word1.setFrequency(9);
		assertEquals(9, word1.getFrequency());

	}

}
