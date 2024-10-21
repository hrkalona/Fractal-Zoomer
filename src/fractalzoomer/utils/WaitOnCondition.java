package fractalzoomer.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class WaitOnCondition {
    public static int WaitOnCyclicBarrier(CyclicBarrier barrier) throws StopExecutionException {
        try {
            return barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            throw new StopExecutionException();
        }
    }

    public static void Sleep(long millis) throws StopExecutionException {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            throw new StopExecutionException();
        }
    }

    public static void LockWrite(ReadWriteLock lock) throws StopExecutionException {
        try {
            lock.lockWrite();
        }
        catch (InterruptedException ex) {
            throw new StopExecutionException();
        }
    }

    public static void UnlockWrite(ReadWriteLock lock) {
        lock.unlockWrite();
    }

    public static void LockRead(ReadWriteLock lock) throws StopExecutionException {
        try {
            lock.lockRead();
        }
        catch (InterruptedException ex) {
            throw new StopExecutionException();
        }
    }

    public static void UnlockRead(ReadWriteLock lock) {
        lock.unlockRead();
    }
}
