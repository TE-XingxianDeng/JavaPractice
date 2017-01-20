package association.fptree;

import java.util.ArrayList;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class TreeNode implements Comparable<TreeNode>, Cloneable {
    // 节点类别名称
    private String name;
    // 计数数量
    private Integer count;
    // 父亲节点
    private TreeNode parentNode;
    // 孩子节点，可以为多个
    private ArrayList<TreeNode> childNodes;

    public TreeNode(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public ArrayList<TreeNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(ArrayList<TreeNode> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public int compareTo(TreeNode o) {
        return o.getCount().compareTo(this.getCount());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //因为对象内部有引用，需要采用深拷贝
        TreeNode node = (TreeNode) super.clone();
        if (this.getParentNode() != null) {
            node.setParentNode((TreeNode) this.getParentNode().clone());
        }

        if (this.getChildNodes() != null) {
            node.setChildNodes((ArrayList<TreeNode>) this.getChildNodes().clone());
        }

        return node;
    }
}
