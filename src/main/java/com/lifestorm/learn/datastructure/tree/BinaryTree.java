package com.lifestorm.learn.datastructure.tree;

/**
 * Created by life_storm on 2018/7/23.
 */
public class BinaryTree {

    private TreeNode rootNode ; //根节点

    public BinaryTree() {
    }

    public BinaryTree(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getParent(TreeNode element){
        return (rootNode == null || rootNode == element) ? null : parent(rootNode,element);
    }

    public TreeNode parent(TreeNode subTree, TreeNode element) {

        if(subTree == null) {
            return null;
        }

        if(subTree.getLeftNode() == element || subTree.getRightNode() == element) {
            //返回付节点地址
            return subTree;
        }
        TreeNode p;
        //先查找左树，如果左树查找不到，查找右树
        if( (p = parent(subTree.getLeftNode(),element)) !=null ){
            return p;
        }else {
            return parent(subTree.getRightNode(),element);
        }
    }

    /**
     * 节点个数获取
     * @return
     */
    public int getSize(){
        return getNum(rootNode);
    }

    private int getNum( TreeNode node) {
        if( node == null ){
            return 0;
        }else {
            int i = getNum(node.getLeftNode());
            int j = getNum(node.getRightNode());
            return j + i + 1;
        }
    }

    public int getHeight(){
        return getHeight(rootNode);
    }

    private int getHeight(TreeNode node) {

        if( node == null ){
            return 0;//递归结束：空树高度为0
        }else {
            int i = getHeight(node.getLeftNode());
            int j = getHeight(node.getRightNode());
            return ( i < j ) ? ( j + 1 ) : ( i + 1);
        }
    }


    /**
     * 前序遍历
     * @param node
     */
    public void preOrder(TreeNode node ){
        if( node != null ){
            System.out.println(node.getDate());
            preOrder(node.getLeftNode());
            preOrder(node.getRightNode());
        }
    }

    /**
     * 中序遍历
     * @param node
     */
    public void inOrder(TreeNode node) {
        if( node != null ){
            inOrder(node.getLeftNode());
            System.out.println(node.getDate());
            inOrder(node.getRightNode());
        }
    }

    public void  postOrder(TreeNode node) {
        if( node != null ){
            postOrder(node.getLeftNode());
            postOrder(node.getRightNode());
            System.out.println(node.getDate());
        }
    }


    private static class TreeNode {
        //数据
        private String date = null;
        //左树
        private TreeNode leftNode ;
        //右树
        private TreeNode rightNode ;

        public TreeNode(String date, TreeNode leftNode, TreeNode rightNode) {
            this.date = date;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public TreeNode() {
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public TreeNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(TreeNode leftNode) {
            this.leftNode = leftNode;
        }

        public TreeNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(TreeNode rightNode) {
            this.rightNode = rightNode;
        }
    }


    public static void main(String[] args) {
        TreeNode l1 = new TreeNode("left1", null , null );
        TreeNode r1 = new TreeNode("right1", null , null );
        TreeNode l2 = new TreeNode("left2", null , null );
        TreeNode r2 = new TreeNode("right2", null , null );

        TreeNode rl1 = new TreeNode("left", l1 , r1 );
        TreeNode rr1 = new TreeNode("right", l2 , r2 );
        TreeNode root = new TreeNode("root", rl1 , rr1 );
        BinaryTree bt = new BinaryTree(root);
        System.out.println("=======先序遍历=======");
        bt.preOrder(bt.getRootNode());
        System.out.println("=======中序遍历=======");
        bt.inOrder(bt.getRootNode());
        System.out.println("=======后序遍历=======");
        bt.postOrder(bt.getRootNode());
        System.out.println(bt.getHeight());
        System.out.println(bt.getSize());
        System.out.println(bt.getParent(r2).getDate());
    }
}
