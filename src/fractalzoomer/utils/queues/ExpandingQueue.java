
package fractalzoomer.utils.queues;

import java.util.Arrays;

/**
 *
 * @author hrkalona
 */
public class ExpandingQueue<Item> {

    private static final int LINKED_QUEUES_SIZE = 10;
    private Object[] LinkedQueues;
    private Item[] headQueue;
    private Item[] tailQueue;
    private int size;
    //private int max_size = 0;
    private int head;
    private int tail;
    private int initialAllocation;

    public ExpandingQueue(int initSize) {

        LinkedQueues = new Object[LINKED_QUEUES_SIZE];
        head = tail = 0;
        headQueue = tailQueue = (Item[]) new Object[initSize];
        LinkedQueues[0] = headQueue;
        size = 0;
        initialAllocation = initSize;

    }

    /**
     * Returns the size of the queue
     *
     * @return Integer representing the number of items in the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns whether or not the queue is empty
     *
     * @return True if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        /*if (item == null) {
            throw new NullPointerException("Item must not be null");
        }*/

        int locationInQueue = tail % initialAllocation;

        tailQueue[locationInQueue] = item;
        
        if(locationInQueue == initialAllocation - 1) {
            tailQueue = (Item[])new Object[initialAllocation];

            int LinkedQueueuIndex = tail / initialAllocation + 1;
            if (LinkedQueueuIndex >= LinkedQueues.length) {
                LinkedQueues = Arrays.copyOf(LinkedQueues, LinkedQueues.length << 1);
            }
            LinkedQueues[LinkedQueueuIndex] = tailQueue;
        }
        
        tail++;
        size++;

        /*if (size > max_size) {
            max_size = size;
        }*/
    }

    public Item dequeue() {
        /*if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the queue");
        }*/

        int locationInQueue = head % initialAllocation;

        Item item = headQueue[locationInQueue];
        headQueue[locationInQueue] = null;

        if(locationInQueue == initialAllocation - 1) {
            int currentQueue = head / initialAllocation;
            LinkedQueues[currentQueue] = null;           
            headQueue = (Item[])LinkedQueues[currentQueue + 1]; //assuming that tail is always greater or equal to head, this access shoud be fine
        }

        head++;
        size--;

        return item;
    }

    public Item last() {
        /*if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the queue");
        }*/

        tail--;
        size--;

        int locationInQueue = tail % initialAllocation;

        if(locationInQueue == initialAllocation - 1) {
            int currentQueue = tail / initialAllocation;
            LinkedQueues[currentQueue + 1] = null;
            tailQueue = (Item[]) LinkedQueues[currentQueue]; //assuming that tail is always greater or equal to head, this access shoud be fine
        }

        Item item = tailQueue[locationInQueue];
        tailQueue[locationInQueue] = null;

        return item;
    }

    /*public int getMaxSize() {
        return max_size;
    }*/
}
