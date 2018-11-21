package pra.stringsearch;

import java.util.Random;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test string search strategies
 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Compare the strategies results using random strings
     */
    public void testComparison() {
        String content = generateRandomString();
        String pattern;

        int a = new Random().nextInt(10000);
        int b = a + new Random().nextInt(99) + 1; // The substring must contain at most 100 chars
        if (b > 10000) {
            b = 10000;
        }
        pattern = content.substring(a, b);

        StringSearchStrategy[] st = new StringSearchStrategy[6];

        st[0] = new BoyerMooreStringSearch(pattern);
        st[1] = new KMPStringSearch(pattern);
        st[2] = new NaiveStringSearch(pattern);
        st[3] = new RabinKarpStringSearch(pattern);
        st[4] = new AhoCorasickStringSearch(pattern);
        st[5] = new RadixTreeStringSearch(pattern);

        int[] result = new int[6];
        for (int i = 0; i < 6; i++) {
            result[i] = st[i].find(content, 0);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(result[i], result[i + 1]);
        }
    }

    public static String generateRandomString() {
        String generatedString = "";
        Random rd = new Random();
        
        for (int i = 0; i < 10000; i++) {
            generatedString += (char) rd.nextInt(256);
        }

        return generatedString;
    }

}
