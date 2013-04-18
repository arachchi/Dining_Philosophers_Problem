
package chickenfarm;

import java.util.ArrayList;

/**
 *
 * @author Nuran Arachchi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Farm ourFarm = new Farm();
        ourFarm.chickenLife();
    }


}
class Grain{
private static int total=0;

public Grain(){
    total++;
}
public int getGrainCount(){
    return total;
}

}

class GrainFeeder{
public static final int FOOD=10000;
boolean busy;
public ArrayList<Grain> grains;

public GrainFeeder(){
grains=new ArrayList<Grain>(FOOD);
busy=false;
}
synchronized public void acquire(){
    grains.add(new Grain());
    busy=true;

}
synchronized public void realease(){
    busy=false;
}
}
class Chicken extends Thread{
    private static final double MUNCH=200;
    private static final double DIGEST=300;
    private int grainsEat;
    private GrainFeeder feeder;

    public Chicken (GrainFeeder feeder){
        this.feeder=feeder;
        grainsEat=0;

    }
    public void eat(){
        grainsEat++;
    feeder.acquire();
    System.out.println(Thread.currentThread().getName()+"    chicken acqure ");

    try{
    sleep((long) MUNCH);
        }catch(InterruptedException e){}

    }
    public void digest(){
        try{
        sleep((long) DIGEST);
        }catch(InterruptedException e){};
    }
    public void tryToEat(){
        for(int i=0;100>=feeder.grains.size()-1;i++){
            eat();
        }

    }
    @Override
    public void run(){
    tryToEat();
    }
}
class Farm{
    private Chicken[] chickens;
    private GrainFeeder feeder;
    public  Farm(){
        chickens=new Chicken[20];
        feeder=new GrainFeeder();
    for(int i=0;i<20;i++)
        chickens[i]=new Chicken(feeder);

}
public void chickenLife(){
    for (int i=0;i<20;i++){
        chickens[i].setName("chick "+i);
        chickens[i].start();

    }
}

}
