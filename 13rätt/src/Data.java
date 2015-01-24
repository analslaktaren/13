import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Created by mq on 2014-12-20.
 */
public class Data {
    GameData Stryk;
    GameData EU;
    GameData PP;
    Utills utills;
    public Data(){
        utills=new Utills();
    }

    public void collectStrykData(){
        genHtml(gameTypes.STRYKTIPSET);
    }

    public void collectEUData(){
        genHtml(gameTypes.EUROPATIPSET);

    }

    public void collectPPData(){
        genHtml(gameTypes.POWERPLAY);
    }

    private void genHtml(gameTypes type){
        String gameType;
        if(type==gameTypes.STRYKTIPSET)gameType="stryktipset";
        else if(type==gameTypes.EUROPATIPSET)gameType="europatipset";
        else if(type==gameTypes.POWERPLAY)gameType="powerplay";
        else gameType="";
        try{
            Process pp = Runtime.getRuntime().exec("rm "+gameType);
            pp.waitFor();
            Process p = Runtime.getRuntime().exec("wget https://www.svenskaspel.se/"+gameType+"/spela -O "+gameType);
            p.waitFor();
        }
        catch (Exception e){e.printStackTrace();}
        try{
            int n=0;
            if(type==gameTypes.EUROPATIPSET || type==gameTypes.STRYKTIPSET)n=13;
            if (type==gameTypes.POWERPLAY)n=8;
            BufferedReader br = new BufferedReader(new FileReader(gameType));
            String line = null;
            GameData gd=new GameData(n);
            int i=0;
            while ((line = br.readLine()) != null)
            {
                i++;
                if(i==206)break;
            }
            int c=0;
            for (i = -1; (i = line.indexOf("eventDescription\":\"", i + 1)) != -1; ) {
                c++;
                if(c>n)break;
                String g=(line.substring(i+19,i+60));
                for (int j=0;j<100;j++){
                    if(g.charAt(j)=='\"'){
                        gd.games[c-1]=g.substring(0,j);
                        break;
                    }
                }
            }
             c=0;
            for (i = -1; (i = line.indexOf("\"odds\":{\"one\":\"", i + 1)) != -1; ) {
                c++;
                int inc=0;
                if(c>n)break;
                String o1=(line.substring(i+15,i+19)).replaceAll(",",".");
                gd.odds[c-1][0]=Double.parseDouble(o1);
                if(gd.odds[c-1][0]>10.00)inc++;
                String o2=(line.substring(i+26+inc,i+30+inc)).replaceAll(",",".");
                gd.odds[c-1][1]=Double.parseDouble(o2);
                if(gd.odds[c-1][1]>10.00)inc++;
                String o3=(line.substring(i+39+inc,i+43+inc)).replaceAll(",",".");
                gd.odds[c-1][2]=Double.parseDouble(o3);
                gd.wodds[c-1]=utills.getwodds(gd.odds[c-1].clone());

            }

             c=0;
            for (i = -1; (i = line.indexOf("svenskaFolket\":{\"one\":\"", i + 1)) != -1; ) {
                c++;
                if(c>n)break;
                int inc=0;
                String o1=(line.substring(i+23,i+25));
                gd.crossed[c-1][0]=Double.parseDouble(o1);
                if(gd.crossed[c-1][0]<10.00)inc--;
                String o2=(line.substring(i+34+inc,i+36+inc));
                gd.crossed[c-1][1]=Double.parseDouble(o2);
                if(gd.crossed[c-1][1]<10.00)inc--;
                String o3=(line.substring(i+47+inc,i+49+inc));
                gd.crossed[c-1][2]=Double.parseDouble(o3);
            }

            for(i=0;i<n;i++){
                gd.value[i][0]=utills.round(gd.crossed[i][0]*gd.wodds[i][0],2);
                gd.value[i][1]=utills.round(gd.crossed[i][1]*gd.wodds[i][1],2);
                gd.value[i][2]=utills.round(gd.crossed[i][2]*gd.wodds[i][2],2);
            }
            i = line.indexOf(", stänger ", i + 1);
            gd.spelstopp=line.substring(i+10,i+26);

            i = line.indexOf(",\"currentNetSale\":\"", i + 1);
            gd.omsättning=line.substring(i+17,i+25);

            if(type==gameTypes.STRYKTIPSET)Stryk=gd;
            if(type==gameTypes.EUROPATIPSET)EU=gd;
            if(type==gameTypes.POWERPLAY)PP=gd;
        }catch (Exception e){}

    }
}
