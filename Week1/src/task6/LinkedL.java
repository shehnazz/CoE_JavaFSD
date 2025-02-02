package task6;

public class LinkedL {
	static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public boolean hasCycle(Node head) {
        if (head == null || head.next == null) {
            return false;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;        
            fast = fast.next.next;   

            if (slow == fast) {  
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        LinkedL list = new LinkedL();
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = head.next;  

        System.out.println("Has Cycle: " + list.hasCycle(head));  
    }

}
