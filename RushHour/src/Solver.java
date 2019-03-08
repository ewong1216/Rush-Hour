import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Solver{
	private SearchNode goal;
	
	public Solver(PuzzleBoard initial){
		UpdateableMinPQ<SearchNode> pq = new UpdateableMinPQ<SearchNode>();
		pq.insert(new SearchNode(initial,0,null));
		HashSet<PuzzleBoard> closed = new HashSet<PuzzleBoard>();
		HashMap<PuzzleBoard, SearchNode> open = new HashMap<PuzzleBoard, SearchNode>();
		while(!pq.isEmpty()){
			SearchNode cur = pq.delMin();
			if(cur.board.isGoal()){
				goal = cur;
				break;
			}
			closed.add(cur.board);
			open.remove(cur.board);
			
			for(PuzzleBoard board : cur.board.getNeighbors()){
				if(!closed.contains(board)){
					SearchNode neighbor = new SearchNode(board,cur.costFromBeginningToHere+1,cur);
					if(open.containsKey(board) && neighbor.costFromBeginningToHere < open.get(board).costFromBeginningToHere){
						pq.updateKey(open.get(board), neighbor);
						open.replace(neighbor.board, neighbor);
					}
					if(!open.containsKey(board)){
						pq.insert(neighbor);
						open.put(board, neighbor);
					}
				}
			}
		}
	}
	private static class SearchNode implements Comparable<SearchNode>{
		// Important!! Do not change the names or types of these fields!
		private PuzzleBoard board;
		private int costFromBeginningToHere;
		private SearchNode previous;
		public SearchNode(PuzzleBoard b, int c, SearchNode p){
			board = b;
			costFromBeginningToHere = c;
			previous = p;
		}
		public int compareTo(SearchNode that){
			int cost = costFromBeginningToHere + board.heuristicCostToGoal();
			int otherCost = that.costFromBeginningToHere + that.board.heuristicCostToGoal();
			if(cost < otherCost){
				return -1;
			}
			if(cost == otherCost){
				return 0;
			}
			return 1;
		}

		public int hashCode(){
			// DO NOT MODIFY THIS METHOD

			final int prime = 31;
			int result = 1;
			result = prime * result + ((board == null) ? 0 : board.hashCode());
			result = prime * result + costFromBeginningToHere;
			result = prime * result + ((previous == null) ? 0 : previous.hashCode());
			return result;
		}

		public boolean equals(Object obj){
			// DO NOT MODIFY THIS METHOD

			if (this == obj){
				return true;
			}
			if (obj == null){
				return false;
			}
			if (getClass() != obj.getClass()){
				return false;
			}
			SearchNode other = (SearchNode) obj;
			if (board == null){
				if (other.board != null){
					return false;
				}
			} 
			else if (!board.equals(other.board)){
				return false;
			}
			if (costFromBeginningToHere != other.costFromBeginningToHere){
				return false;
			}
			if (previous == null){
				if (other.previous != null){
					return false;
				}
			}
			else if (!previous.equals(other.previous)){
				return false;
			}
			return true;
		}
	}

	public Solver(PuzzleBoard initial, boolean extraCredit){
		// DO NOT TOUCH unless you are passing all of the tests and wish to
		// attempt the extra credit.
		throw new UnsupportedOperationException();
	}

	public Iterable<PuzzleBoard> getPath(){
		Stack<PuzzleBoard> s = new Stack<PuzzleBoard>();
		SearchNode cur = goal;
		while(cur != null){
			s.push(cur.board);
			cur = cur.previous;
		}
		return s;
	}
}
