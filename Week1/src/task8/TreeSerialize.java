package task8;

import java.util.*;


class TreeSerialize {
   
    public String serialize(TNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

   
    public TNode deserialize(String data) {
        Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(nodes);
    }

    private TNode deserializeHelper(Queue<String> nodes) {
        String val = nodes.poll();
        if (val.equals("null")) {
            return null;
        }

        TNode node = new TNode(Integer.parseInt(val));
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);
        return node;
    }

    
    public void inorder(TNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {
        TreeSerialize serializer = new TreeSerialize();

        
        TNode root = new TNode(1);
        root.left = new TNode(2);
        root.right = new TNode(3);
        root.right.left = new TNode(4);
        root.right.right = new TNode(5);

        System.out.println("Original Tree (Inorder):");
        serializer.inorder(root);
        System.out.println();

        
        String serialized = serializer.serialize(root);
        System.out.println("Serialized Tree: " + serialized);

        
        TNode deserializedRoot = serializer.deserialize(serialized);
        System.out.println("Deserialized Tree (Inorder):");
        serializer.inorder(deserializedRoot);
        System.out.println();
    }
}