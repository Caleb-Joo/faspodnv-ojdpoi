import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public final class Queue1LSort3<T> extends Queue1L<T> {

    /**
     * No-argument constructor.
     */
    public Queue1LSort3() {
        super();
    }

    /**
     * Inserts the given {@code T} in the {@code Queue<T>} sorted according to
     * the given {@code Comparator<T>} and maintains the {@code Queue<T>}
     * sorted.
     *
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to insert into
     * @param x
     *            the {@code T} to insert
     * @param order
     *            the {@code Comparator} defining the order for {@code T}
     * @updates q
     * @requires <pre>
     * IS_TOTAL_PREORDER([relation computed by order.compare method])  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>
     * @ensures <pre>
     * perms(q, #q * <x>)  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>
     */
    private static <T> void insertInOrder(Queue<T> q, T x,
            Comparator<T> order) {
        assert q != null : "Violation of: q is not null";
        assert x != null : "Violation of: x is not null";
        assert order != null : "Violation of: order is not null";
        //declare variables and a temp Q
        boolean endPositionFound = false;
        Queue<T> tempQ = q.newInstance();
        T tempVar = null;
        //while the end position has not been found
        while (!endPositionFound) {
            //dequeue the first var if q is not zero
            if (q.length() != 0) {
                tempVar = q.dequeue();
            }
            //compare tempvar to the variable trying to be inserted
            int compareResult = order.compare(tempVar, x);
            //if either x comes now (as the current spot is larger) or
            //there are no more variables to remove
            if (compareResult > 0 || q.length() <= 1) {
                //declare end position found and then insert them back into q
                endPositionFound = true;
                q.flip();
                q.enqueue(tempVar);
                //if position not found then put the current tempVar into the
                //temp queue to repeat the process
            } else {
                tempQ.enqueue(tempVar);
            }
        }
        while (tempQ.length() >= 0) {
            q.enqueue(tempQ.dequeue());
        }
        q.flip();

    }

    @Override
    public void sort(Comparator<T> order) {
        assert order != null : "Violation of: order is not null";

        Queue<T> tempQ = this.newInstance();
        while (this.length() > 0) {
            insertInOrder(tempQ, this.dequeue(), order);
        }
        this.transferFrom(tempQ);

    }

}
