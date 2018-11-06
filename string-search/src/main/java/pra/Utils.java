package pra;

/**
 * A class for generating hash values
 */
public class Hash {
    
    private final int R = 256;
    private final int h;
    private final int Q = 101; // A prime number
    private final String str;
    private int value = 0;
    private int begin;
    private final int wLength;
    
    public Hash(String str, int begin, int wLength){
        this.str = str;
        this.begin = begin;
        this.wLength = wLength;
        
        int temp = 1;
        for(int i=begin;i<end-1;i++) temp = (temp * R)%Q;
        h = temp;
        
        for(int i=begin; i<begin + wLength; i++) value = (R*value + str[i])%Q;
    }
    
    public void updateHash(){
        begin++;
        value = (R*(value - str[begin]*h) + str[begin+wLength])%Q;
        if(value < 0) value += Q;
    }
    
    @Override
    public boolean equals(Object o){
        if(!o instanceof Hash){
            return false;
        }
          
        if(this.value == ((Hash) o).value) return true;
        return false;
    }
    
}
