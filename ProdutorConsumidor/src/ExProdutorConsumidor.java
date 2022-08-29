import java.util.ArrayList;
import java.util.Random;

public class ExProdutorConsumidor {
    private final int MAX = 10;
    private ArrayList<Integer> buffer = new ArrayList<>(MAX);
    private Object cheio = new Object();
    private Object vazio = new Object();

    public static void main(String[] args) {
        new ExProdutorConsumidor().executaParalelo();

    }

    public void executaParalelo(){
        Produtor p = new Produtor();
        Consumidor c = new Consumidor();
        new Thread(p).start();
        new Thread(c).start();

    }

    public class Produtor implements Runnable{

        Random rnd = new Random();
        @Override
        public void run() {

            while (true){
                synchronized (cheio){
                    if(buffer.size() == MAX ){
                        try {
                            cheio.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }

                int num = rnd.nextInt(100);
                synchronized (buffer){
                    buffer.add(num);
                }

                synchronized (vazio){
                    if(buffer.size()==1){
                        vazio.notify();
                    }
                }
            }
        }
    }


    public class Consumidor implements Runnable{
        @Override
        public void run() {

            while (true){
                synchronized (vazio){
                    if(buffer.size() == 0){
                        try {
                            vazio.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }


                synchronized (buffer){
                    System.out.println(buffer.remove(0));
                }


                synchronized (cheio){
                    if(buffer.size()==0){
                        cheio.notify();
                    }
                }
            }
        }
    }
}
