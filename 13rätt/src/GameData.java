/**
 * Created by mq on 2014-12-20.
 */
public class GameData {
    public String[] games;
    public double[][] odds;
    public double[][] wodds;
    public double[][] crossed;
    public double[][] value;
    public double[][] wvalue;
    public boolean[][] rad;
    public double sannolikhet13;
    public double sannolikhet12;
    public double sannolikhet11;
    public double sannolikhet10;
    public double edge;
    public String spelstopp;
    public String omsättning;
    public GameData(int numMatches){
        games=new String[numMatches];
        odds=new double[numMatches][3];
        crossed=new double[numMatches][3];
        wodds=new double[numMatches][3];
        value=new double[numMatches][3];
        wvalue=new double[numMatches][3];
        rad=new boolean[numMatches][3];
        spelstopp=new String("");
        omsättning=new String("");
        sannolikhet13 =-1;
        sannolikhet12 =-1;
        sannolikhet11 =-1;
        sannolikhet10 =-1;
        edge=-1;
    }

}
