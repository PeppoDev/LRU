import java.util.HashMap;
import java.util.Map;

public class LRUCache<T> {

    private final int capacity;
    private int size;
    private final Map<String, Node> hashmap;
    private final DoublyLinkedList internalQueue;

    LRUCache(final int capacity){
        this.capacity =  capacity;
        this.hashmap = new HashMap<>();
        this.internalQueue =  new DoublyLinkedList();
        this.size = 0;
    }

    public T get(final String key){
        Node node =  hashmap.get(key);

        if(node == null){
            return null;
        }

        internalQueue.moveNodeToFront(node);

        return hashmap.get(key).value;
    }

    public void put(final String key, final T value){
        Node currentNode = hashmap.get(key);

        if(currentNode != null){
            currentNode.value = value;
            internalQueue.moveNodeToFront(currentNode);
        }

        if(size == capacity){
            String backNode =  internalQueue.getBackNodeKey();
            internalQueue.removeNodeFromBack();
            hashmap.remove(key);
            size--;
        }

        Node node = new Node(key, value);
        internalQueue.addNodeToFront(node);

        hashmap.put(key, node);

        size++;
    }

    private class Node{
        String Key;
        T value;
        Node next, prev;

        public Node(final String key, final T value){
            this.Key = key;
            this.value =  value;
            next = prev = null;
        }

    }

    private class DoublyLinkedList{
        private Node front, back;

        public DoublyLinkedList(){
            front = back = null;
        }

        public void addNodeToFront(final Node node){
            if(back == null){
                front = back = node;
                return;
            }

            node.next = front;
            front.prev = node;
            front = node;
        }

        public void moveNodeToFront(final Node node){
            if(front == node){
                return;
            }

            if(node == back){
                back = back.prev;
                back.next =  null;
            }

            node.prev.next = node.next;
            node.next.prev = node.prev;
            front.prev = node;
            front = node;
        }

        public void removeNodeFromBack(){
            if(back == null){
                return;
            }

            if(front == back){
                front =  back = null;
            } else {
                back =  back.prev;
                back.next =  null;
            }
        }

        public String getBackNodeKey(){
            return "";
        }

    }
}
