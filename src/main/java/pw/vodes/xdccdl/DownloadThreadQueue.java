package pw.vodes.xdccdl;

import java.util.ArrayList;

public class DownloadThreadQueue extends Thread {

    public long timeSinceEnd = 000000000000;
    public ArrayList<DownloadThread> queue = new ArrayList<>();
    public DownloadThread running = null;

    @Override
    public void run() {
        timeSinceEnd = System.currentTimeMillis();
        while(true){
            try{
                if(running != null){
                    if(!running.isAlive()){
                        running = null;
                        sleep(5000);
                    }
                }
                if(!queue.isEmpty() && running == null && System.currentTimeMillis() - timeSinceEnd > 20000){
                    running = queue.get(0);
                    running.start();
                    queue.remove(running);
                    timeSinceEnd = System.currentTimeMillis();
                }
                sleep(1000);
            } catch(Exception ex){

            }
        }
    }
}
