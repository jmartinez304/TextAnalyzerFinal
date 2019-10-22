import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * Testing Suite that includes all the tests classes of the
 * TextAnalyzerWithUITest project.
 *
 */

@RunWith(Suite.class)
@SuiteClasses({ WordFrequencyFinderTest.class, WordTest.class })
public class AllTests {

}
