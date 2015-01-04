

public class MyPair implements Comparable<MyPair>
{
    private  int key;
    private  Double value;

    public MyPair()
    {
        key   = -1;
        value = -1.0;
    }

    public void append(int aKey, Double aValue){
        key   = aKey;
        value = aValue;
    }

    public int key()   { return key; }
    public Double value() { return value; }

    @Override
    public int compareTo(MyPair myPair) {
        return (int)(this.value()-myPair.value());
    }
}