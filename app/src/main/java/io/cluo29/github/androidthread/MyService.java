package io.cluo29.github.androidthread;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {


        ComputationRun computation=new ComputationRun();
        Thread computationThread=new Thread(computation);
        computationThread.start();


        //to close compuation thread

        //computationThread.interrupt();

    }


    class ComputationRun implements  Runnable
    {
        // volatile makes sure reading it from memory, not cache,
        volatile boolean isRunning = false;
        @Override
        public void run() {

            isRunning = true;
            //

            int usedTime = 0;

            while (isRunning)
            {

                usedTime = usedTime + 1;

                Log.d("haha", "run once");


                if (usedTime>12)
                    Thread.currentThread().interrupt();



                if(Thread.currentThread().isInterrupted())  //Thread refers to current thread
                {
                    // when not blocked, interrupt() will go to this part

                    // release resources

                    Log.d("haha", "quit when not blocked");


                    isRunning = false;

                    // quit the service if u want

                    //MyService.this.stopSelf();

                    return;
                }



                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    // when blocked, interrupt() will go to this part
                    // e.g., when the service calls interrupt()

                    Log.d("haha", "quit when blocked");

                    e.printStackTrace();

                    // release resources


                    isRunning = false;

                    // quit the service if u want

                    //MyService.this.stopSelf();
                }
            }
        }
    }

}
