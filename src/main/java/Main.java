import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static final int NUM_ACCESO_SIMULTANEOS = 5;
    public static void main (String[]args){
        Semaphore semaforo1 = new Semaphore(NUM_ACCESO_SIMULTANEOS,true);

        for (int i=1;i<=20;i++){
            Camiones cam= new Camiones(i);
            cam.start();
        }

    }
}
class Darsena extends Thread {

    private static final int NUM_ACCESO_SIMULTANEOS = 5;
    static Semaphore semaforo1 = new Semaphore(NUM_ACCESO_SIMULTANEOS, false);
    static Semaphore semaforo2 = new Semaphore(2, true);

    public static void carga(Camiones camiones) {
        try {
            semaforo1.acquire(1);
            ocupar(camiones);
            semaforo1.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void ocupar(Camiones camiones) {
        System.out.println("Camion " + camiones.id + " entra en su dársena");
        try {
            semaforo2.acquire(1);
            descarga(camiones);
            semaforo2.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void descarga(Camiones camiones) throws InterruptedException{
        System.out.println(("El mozo empieza a descargar el camión "+camiones.id));
        Thread.sleep((new Random().nextInt(6)+5)*1000);
        System.out.println(("El mozo ha terminado de descargar el camión "+camiones.id));

    }
}
class Camiones extends Thread{

    int id;

    public Camiones(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Darsena.carga(this);

    }
}