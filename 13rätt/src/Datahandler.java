import java.util.Arrays;

/**
 * Created by mq on 2014-12-20.
 */
public class Datahandler {
    Utills utills;
    public Datahandler(){
        utills=new Utills();
    }

    public void calcRows(GameData gd){
        for(int i=0;i<gd.games.length;i++){
            for(int j=0;j<3;j++){
                gd.wvalue[i][j]=gd.crossed[i][j]*(gd.wodds[i][j]);
            }
        }
        if(gd.numMatch==13){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) for(int i=0;i<3;i++) for(int j=0;j<3;j++) for(int k=0;k<3;k++) for(int l=0;l<3;l++) for(int m=0;m<3;m++){
                gd.rowVal[counter]=new MyPair();
                gd.rowVal[counter].append(counter, gd.wvalue[0][a]+gd.wvalue[1][b]+gd.wvalue[2][c]+gd.wvalue[3][d]+gd.wvalue[4][e]+gd.wvalue[5][f]+gd.wvalue[6][g]+gd.wvalue[7][h]+gd.wvalue[8][i]+gd.wvalue[9][j]+gd.wvalue[10][k]+gd.wvalue[11][l]+gd.wvalue[12][m]);
                counter++;
            }
            Arrays.sort(gd.rowVal);
        }
        if(gd.numMatch==8){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) {
                gd.rowVal[counter]=new MyPair();
                gd.rowVal[counter].append(counter, gd.wvalue[0][a] + gd.wvalue[1][b] + gd.wvalue[2][c] + gd.wvalue[3][d] + gd.wvalue[4][e] + gd.wvalue[5][f] + gd.wvalue[6][g] + gd.wvalue[7][h]);
                counter++;
            }
            Arrays.sort(gd.rowVal);
        }

    }

    private void setTecken(GameData gd, int antalrader){
        gd.tecken=new int[gd.wvalue.length][3];
        double chance=0;
        double rowVal=0;
        for(int i=0;i<antalrader;i++){
            double rowchance=100;

            StringBuilder SB=new StringBuilder();
                int num = gd.rowVal[i].key();
                int r = -1;
                while (true){
                    r=num %3;
                    SB.append(r);

                    num=num/3;
                    if(num==0)break;
                }
                for(int j=0;j<gd.wvalue.length;j++){
                    int p = (int)Math.pow(3, j);
                    System.out.println(p);
                    if(gd.rowVal[i].key()<p)SB.append(0);
                }
            SB.reverse();
            System.out.println(SB.toString()+"\n");
            for(int j=0;j<gd.wvalue.length;j++){
                gd.tecken[j][Character.getNumericValue(SB.charAt(j))]++;
                rowchance=rowchance*1/gd.wodds[j][Character.getNumericValue(SB.charAt(j))];
                rowVal+=gd.wvalue[j][Character.getNumericValue(SB.charAt(j))]/gd.wvalue.length;
            }
            chance+=rowchance;
        }
        gd.sannolikhet12=rowVal/(double)antalrader;
        gd.sannolikhet13=chance;
        for(int i=0;i<gd.wvalue.length;i++){
            for(int j=0;j<3;j++){
                gd.dtecken[i][j]=(int)utills.round((double)(gd.tecken[i][j])*100/(double)antalrader,0);
            }
        }
    }


    public void beast(String antalRaderS, GameData gd){
        int antalRader=Integer.parseInt(antalRaderS);
        calcRows(gd);
        setTecken(gd,antalRader);
    }

    public void go(GameData gd, String halv, String hel, int ws){
        System.out.println(halv+" "+hel+" "+ws);
        int ha=Integer.parseInt(halv);
        int he=Integer.parseInt(hel);
        for(int i=0;i<gd.games.length;i++){
            for(int j=0;j<3;j++){
                gd.wvalue[i][j]=gd.crossed[i][j]*(gd.wodds[i][j]-(double)ws/25.00);
            }
        }


        gd.rad=new boolean[gd.games.length][3];


        boolean[] halvor=new boolean[gd.games.length];
        boolean[] helor=new boolean[gd.games.length];

        double best=839457;
        for(int k=0;k<he;k++){
            best=839457;
            int curri=-1;
            for(int i=0;i<gd.games.length;i++){
                if(!helor[i]){
                    if(gd.wvalue[i][0]+gd.wvalue[i][1]+gd.wvalue[i][2]<best){
                        curri=i;
                        best=gd.wvalue[i][0]+gd.wvalue[i][1]+gd.wvalue[i][2];
                    }
                }
            }
            gd.rad[curri][0]=true;
            gd.rad[curri][1]=true;
            gd.rad[curri][2]=true;
            helor[curri]=true;
        }


        for(int k=0;k<ha;k++){
            best=839457;
            int curri=-1;
            int a=-1;
            int b=-1;
            for(int i=0;i<gd.games.length;i++){
                if(!halvor[i] && !helor[i]){
                    if(gd.wvalue[i][utills.getBestSign(gd.wvalue[i])]+gd.wvalue[i][utills.getmiddleBestSign(gd.wvalue[i])]<best){
                        curri=i;
                        a=utills.getBestSign(gd.wvalue[i]);
                        b=utills.getmiddleBestSign(gd.wvalue[i]);
                        best=gd.wvalue[i][utills.getBestSign(gd.wvalue[i])]+gd.wvalue[i][utills.getmiddleBestSign(gd.wvalue[i])];
                    }
                }
            }
            gd.rad[curri][a]=true;
            gd.rad[curri][b]=true;
            halvor[curri]=true;
        }

        for(int i=0;i<gd.games.length;i++){
            if(!helor[i] && !halvor[i])
                gd.rad[i][utills.getBestSign(gd.wvalue[i])]=true;
        }

        /*
        double best=839457;

        for(int k=0;k<ha+he;k++){
            best=839457;
            int curri=-1;
            int currj=-1;
            for(int i=0;i<gd.games.length;i++){
                if(!halvor[i]){
                    for (int j=0;j<3;j++){
                        if(gd.wvalue[i][j]<best && !gd.rad[i][j]){
                            best=gd.wvalue[i][j];
                            curri=i;
                            currj=j;
                        }
                    }
                }
            }
            gd.rad[curri][currj]=true;
            halvor[curri]=true;
        }
        for(int k=0;k<he;k++){
            best=839457;
            int curri=-1;
            int currj=-1;
            for(int i=0;i<gd.games.length;i++){
                if(halvor[i]){
                    for (int j=0;j<3;j++){
                        if(gd.wvalue[i][j]<best && !gd.rad[i][j]){
                            best=gd.wvalue[i][j];
                            curri=i;
                            currj=j;
                        }
                    }
                }
            }
            gd.rad[curri][currj]=true;
        }
        */


        double chance=100;
        double sum=0;
        for(int i=0;i<gd.games.length;i++){
            sum=0;
            for(int j=0;j<3;j++){
                if(gd.rad[i][j])sum+=1.00/gd.wodds[i][j];
            }
            chance=chance*sum;
        }
        gd.sannolikhet13 =utills.round(chance,6);


        chance=100;
        double csum=0;
        for(int i=0;i<gd.games.length;i++){

            for(int j=0;j<gd.games.length;j++){
                sum=0;
                if(i==j){
                    sum+=utills.getNotProb(gd.wodds[j], gd.rad[j]);
                }
                else{
                    sum+=utills.getProb(gd.wodds[j],gd.rad[j]);
                }
                System.out.println(sum);
                chance=chance*sum;
            }
            csum+=chance;
            chance=100;
        }
        gd.sannolikhet12 =utills.round(csum/(double)gd.games.length,6);
    }

}
