
package pkg8_puzzle_ai_project;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Node> {

    @Override
    public int compare(Node x, Node y) {
        if (x.getTotalCost() < y.getTotalCost()) {
            return -1;
        }
        if (x.getTotalCost() > y.getTotalCost()) {
            return 1;
        }
        return 0;
    }
}

