
package fractalzoomer.utils.queues;

import fractalzoomer.utils.Square;

import java.util.Arrays;

/**
 *
 * @author hrkalona
 */
public class ExpandingQueueSquare {

    private static final int LINKED_QUEUES_SIZE = 10;
    private Object[] LinkedQueues;
    private int[] headQueue;
    private int[] tailQueue;
    private int size;
    //private int max_size = 0;
    private int head;
    private int tail;
    private int initialAllocation;
    private static final int dataSize = 5;

    public ExpandingQueueSquare(int initSize) {

        LinkedQueues = new Object[LINKED_QUEUES_SIZE];
        head = tail = 0;
        headQueue = tailQueue = new int[initSize * dataSize];
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

    public void enqueue(Square item) {
        /*if (item == null) {
            throw new NullPointerException("Item must not be null");
        }*/

        int locationInQueue = tail % initialAllocation;

        int dataIndex = locationInQueue * dataSize;

        tailQueue[dataIndex] = item.x1;
        dataIndex++;
        tailQueue[dataIndex] = item.y1;
        dataIndex++;
        tailQueue[dataIndex] = item.x2;
        dataIndex++;
        tailQueue[dataIndex] = item.y2;
        dataIndex++;
        tailQueue[dataIndex] = item.iteration;

        if(locationInQueue == initialAllocation - 1) {
            tailQueue = new int[initialAllocation * dataSize];

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

    public Square dequeue() {
        /*if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the queue");
        }*/

        int locationInQueue = head % initialAllocation;

        int dataIndex = locationInQueue * dataSize;

        Square item = new Square(headQueue[dataIndex], headQueue[dataIndex + 1], headQueue[dataIndex + 2], headQueue[dataIndex + 3], headQueue[dataIndex + 4]);

        if(locationInQueue == initialAllocation - 1) {
            int currentQueue = head / initialAllocation;
            LinkedQueues[currentQueue] = null;
            headQueue = (int[])LinkedQueues[currentQueue + 1]; //assuming that tail is always greater or equal to head, this access shoud be fine
        }

        head++;
        size--;

        return item;
    }

    public Square last() {
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

        int dataIndex = locationInQueue * dataSize;
        return new Square(tailQueue[dataIndex], tailQueue[dataIndex + 1], tailQueue[dataIndex + 2], tailQueue[dataIndex + 3], tailQueue[dataIndex + 4]);

    }

    /*public int getMaxSize() {
        return max_size;
    }*/
}
