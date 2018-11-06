package pra.stringsearch;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String b = "teste";
        String a = "este";
        StringSearchStrategy st = new RabinKarpStringSearch(a);
        System.out.println( st.find(b, 0) );
    }
}
