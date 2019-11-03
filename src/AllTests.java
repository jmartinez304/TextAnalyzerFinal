import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * Testing Suite class that includes all the unit test classes of the
 * TextAnalyzerWithUITest project.
 *
 */

@RunWith(Suite.class)
@SuiteClasses({ WordFrequencyFinderTest.class, WordTest.class })
public class AllTests {

}
