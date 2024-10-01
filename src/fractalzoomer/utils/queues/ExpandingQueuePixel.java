
package fractalzoomer.utils.queues;

import fractalzoomer.utils.Pixel;

import java.util.Arrays;

/**
 *
 * @author hrkalona
 */
public class ExpandingQueuePixel {

    private static final int LINKED_QUEUES_SIZE = 10;
    private Object[] LinkedQueues;
    private int[] headQueue;
    private int[] tailQueue;
    private int size;
    //private int max_size = 0;
    private int head;
    private int tail;
    private int initialAllocation;
    //private static final int dataSize = 2;

    public ExpandingQueuePixel(int initSize) {

        LinkedQueues = new Object[LINKED_QUEUES_SIZE];
        head = tail = 0;
        headQueue = tailQueue = new int[initSize << 1]; //datasize is 2
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

    public void enqueue(Pixel item) {
        /*if (item == null) {
            throw new NullPointerException("Item must not be null");
        }*/

        int locationInQueue = tail % initialAllocation;

        int dataIndex = locationInQueue << 1;

        tailQueue[dataIndex] = item.x;
        dataIndex++;
        tailQueue[dataIndex] = item.y;

        if(locationInQueue == initialAllocation - 1) {
            tailQueue = new int[initialAllocation << 1];

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

    public Pixel dequeue() {
        /*if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the queue");
        }*/

        int locationInQueue = head % initialAllocation;

        int dataIndex = locationInQueue << 1;

        Pixel item = new Pixel(headQueue[dataIndex], headQueue[dataIndex + 1]);

        if(locationInQueue == initialAllocation - 1) {
            int currentQueue = head / initialAllocation;
            LinkedQueues[currentQueue] = null;
            headQueue = (int[])LinkedQueues[currentQueue + 1]; //assuming that tail is always greater or equal to head, this access shoud be fine
        }

        head++;
        size--;

        return item;
    }

    public Pixel last() {
        /*if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the queue");
        }*/

        tail--;
        size--;

        int locationInQueue = tail % initialAllocation;

        if(locationInQueue == initialAllocation - 1) {
            int currentQueue = tail / initialAllocation;
            LinkedQueues[currentQueue + 1] = null;
            tailQueue = (int[]) LinkedQueues[currentQueue]; //assuming that tail is always greater or equal to head, this access shoud be fine
        }

        int dataIndex = locationInQueue << 1;
        return new Pixel(tailQueue[dataIndex], tailQueue[dataIndex + 1]);

    }

    /*public int getMaxSize() {
        return max_size;
    }*/
}
