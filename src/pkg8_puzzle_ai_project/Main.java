package pkg8_puzzle_ai_project;

/**
 *
 * @author acer
 */
public class Main {

    final static private String CURRENT_STATE = "432650781";
    final static private String GOAL_STATE = "012345678";

    public static void main(String[] args) {
        String rootState = CURRENT_STATE;
        long StartTime = System.currentTimeMillis();
        
        SearchTechnics search = new SearchTechnics(new Node(rootState), GOAL_STATE);
        try {
            search.aStar(Heuristic.H_TWO);
        }catch (Exception e){
            System.out.println("Unsolvable case of the 8-puzzle");
        }
        long FinishTime = System.currentTimeMillis();
        long TotalTime = FinishTime - StartTime;
        System.out.println("Time  :" + TotalTime);

    }

}
