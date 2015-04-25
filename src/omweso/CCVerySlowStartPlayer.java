package omweso;

import boardgame.Board;
import boardgame.BoardState;
import boardgame.Move;
import boardgame.Player;
import boardgame.Server;

import java.util.ArrayList;
import java.util.Random;

/** An Omweso player slow enough to lose the game on the first turn. */
public class CCVerySlowStartPlayer extends Player {
    Random rand = new Random();

    public CCVerySlowStartPlayer() { super("veryslowstart"); }
    public CCVerySlowStartPlayer(String s) { super(s); }

    public Board createBoard() { return new CCBoard(); }

    /** Use this method to take actions when the game is over. */
    public void gameOver( String msg, BoardState bs) {
        CCBoardState board_state = (CCBoardState) bs;

        if(board_state.haveWon()){
            System.out.println("I won!");
        }else if(board_state.haveLost()){
            System.out.println("I lost!");
        }else if(board_state.tieGame()){
            System.out.println("Draw!");
        }else{
            System.out.println("Undecided!");
        }
    }

    /** Implement a slow way of picking moves. */
    public Move chooseMove(BoardState bs)
    {
        // Cast the arguments to the objects we want to work with.
        CCBoardState board_state = (CCBoardState) bs;

        if(!board_state.isInitialized()){
            try {
                Thread.sleep(Server.FIRST_MOVE_TIMEOUT + Server.FIRST_MOVE_TIMEOUT_CUSHION);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            return new CCMove(new int[]{32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        }else{

            // Play a normal turn. Choose a random pit to play.
            ArrayList<CCMove> moves = board_state.getLegalMoves();
            return moves.get(rand.nextInt(moves.size()));
        }
    }
}
