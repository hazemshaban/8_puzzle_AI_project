/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8_puzzle_ai_project;

import static java.lang.Math.sqrt;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SearchTechnics {
    private Node root;
    private String goalSate;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getGoalSate() {
        return goalSate;
    }

    public void setGoalSate(String goalSate) {
        this.goalSate = goalSate;
    }

    public  SearchTechnics(Node root, String goalSate) {
        this.root = root;
        this.goalSate = goalSate;
    }


    //******************************************************************************************************
    //    breadthFirstSearch() find the goal state using Breadth First Search algorithm
   
    public void breadthFirstSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        Queue<Node> queue = new LinkedList<Node>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                queue.add(child);

            }
            currentNode = queue.poll();
            time += 1;
        }

        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }
//**********************************************************************************************

    public void depthFirstSearch() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        //the queue that store nodes that we should expand
        MyQueue<Node> mainQueue = new MyQueue<>();
        //the queue that contains the successors of the expanded node
        MyQueue<Node> successorsQueue = new MyQueue<>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                successorsQueue.enqueue(child);

            }
            //we add the queue that contains the successors of the visted node to the beginning of the main queue
            mainQueue.addQueue(successorsQueue);
            //successors queue should be cleared in order to store the next expaneded's successors
            successorsQueue.clear();
            currentNode = mainQueue.dequeue();
            
            time += 1;
            nodeSuccessors.clear();
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }
       /**
     * Find the goal using A* algorithm**/
    //*************************************************************************************************
    public void aStar(Heuristic heuristic) {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        
        int time = 0;
        Node node = new Node(root.getState());
        node.setTotalCost(0);

        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        PriorityComparator nodePriorityComparator = new PriorityComparator();

        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);

                if (heuristic == Heuristic.H_ONE)
                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), heuristicOne(child.getState(), goalSate));
                else if (heuristic == Heuristic.H_TWO)
                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), heuristicTwo(child.getState(), goalSate));
                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);
    }
   //*************************************************************************************************
    // This heuristic estimates the cost to the goal from current state by calculating the Manhathan distance
 
    private int heuristicOne(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            for (int j = 0; j < goalSate.length(); j += 1)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    difference = difference + ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
        return difference;
    }


    //*************************************************************************************************
    // This heuristic estimates the cost to the goal from current state by calculating the Euclidean Distance

    private int heuristicTwo(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            for (int j = 0; j < goalSate.length(); j += 1)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    difference = (int) (difference + sqrt((Math.pow(Math.abs(i % 3 - j % 3),2) +Math.pow(Math.abs(i / 3 + j / 3),2))));
        return difference;
    }

}