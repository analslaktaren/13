/**
 * Created by mq on 2014-12-20.
 */
public class Datahandler {
    Utills utills;
    public Datahandler(){
        utills=new Utills();
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
            System.out.println(utills.round(chance,6)+"    "+sum);
            chance=chance*sum;
        }
        gd.sannolikhet13 =utills.round(chance,6);
        chance=100;
        for(int i=0;i<gd.games.length;i++){
            sum=0;
            for(int j=0;j<gd.games.length;j++){
                if(i==j)continue;
                sum+=utills.getProb(gd.wvalue[i],gd.rad[j])
            }
        }

    }

}
