import java.util.Random;

public class Queue<T> extends DLinkedList<T> {
    void Push(T data) {
        Append(data);
    }

    T Pop() {
        if (head == null) {
            return null;
        } else {
            T ret = head.data;
            Node temp = head;
            if (temp.next == null) {
                head = tail = null;
            } else {
                head = temp.next;
                head.previous = null;
                temp.next = null;
            }
            return ret;
        }
    }

    T Peek() {
        if (head == null) {
            return null;
        } else {
            return head.data;
        }
    }

    // IsEmpty() defined in parent class can be reused.

    int GetLength() {
        return Count();
    }

    public static void main(String args[])
    {
        Random rand = new Random();
        int count = 10;
        Queue<Integer> queue = new Queue<Integer>();
        for (int x = 0; x < count; x++)
        {
            int rnumber = rand.nextInt(100) + 1;
            queue.Push(rnumber);
            System.out.print(rnumber+"\t");
        }
        System.out.println();

        System.out.println("\t1) Output queue from tail to head is:");
        queue.Output();

        int a = rand.nextInt(100) + 1;
        System.out.println("\t2) Push element: " + a);
        queue.Push(a);
        System.out.println("\t3) After push " + a + ", the queue is (from tail to head): ");
        queue.Output();

        int b = rand.nextInt(100) + 1;
        System.out.println("\t4) Push element " + b);
        queue.Push(b);
        System.out.println("\t5) After push " + b + ", the queue is (from tail to head): ");
        queue.Output();

        System.out.println("\t6) queue size is: " + queue.GetLength());

        System.out.println("\t7) Pop ");
        int c = queue.Pop();
        System.out.println("\t8) After Pop " + c + ", the queue is (from tail to head): ");
        queue.Output();

        System.out.println("\t9) After Pop queue size is: " + queue.GetLength());

        int d = queue.Peek();
        System.out.println("\t10) Peek the queue we got " + d);

        System.out.println("\t11) queue is Empty now?" + queue.IsEmpty());
    }
}
