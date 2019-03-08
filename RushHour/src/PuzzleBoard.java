import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PuzzleBoard{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private Vehicle[][] locationToVehicle; //Row major representation of locations.
	
	//To generalize the class
	private final int height = PuzzleManager.NUM_ROWS;
	private final int width = PuzzleManager.NUM_COLUMNS;
	
	public static void main(String[] args){
		Vehicle[] vs = new Vehicle[16];
		vs[0] = new Vehicle(0, true, 2, 1, 2);
		vs[1] = new Vehicle(1, true, 0, 0 ,2);
		vs[2] = new Vehicle(2, false, 4, 0, 2);
		vs[3] = new Vehicle(3, true, 4, 4, 2);
		vs[12] = new Vehicle(12, false, 0, 5, 3);
		vs[13] = new Vehicle(13, false, 1, 0, 3);
		vs[14] = new Vehicle(14, false, 1, 3, 3);
		vs[15] = new Vehicle(15, true, 5, 2, 3);
		
		PuzzleBoard p = new PuzzleBoard(vs);
		
		/*System.out.println(p);
		System.out.println(p.heuristicCostToGoal());
		for(PuzzleBoard pb : p.getNeighbors()){
			System.out.println(pb);
			System.out.println(pb.heuristicCostToGoal());
		}*/
		System.out.println("Starting solver");
		Solver s = new Solver(p);
		for(PuzzleBoard b : s.getPath()){
			System.out.println(b);
		}
	}
	public PuzzleBoard(Vehicle[] idToVehicleP){
		idToVehicle = new Vehicle[idToVehicleP.length];
		locationToVehicle = new Vehicle[height][width];
		for(int i = 0; i < idToVehicle.length; i++){
			idToVehicle[i] = idToVehicleP[i];
			Vehicle v = idToVehicle[i];
			if(v != null){
				for(int row = v.getLeftTopRow(); !v.getIsHorizontal() && row < v.getLeftTopRow() + v.getLength(); row++){
					locationToVehicle[row][v.getLeftTopColumn()] = v;
				}
				for(int col = v.getLeftTopColumn(); v.getIsHorizontal() && col < v.getLeftTopColumn() + v.getLength(); col++){
					locationToVehicle[v.getLeftTopRow()][col] = v;
				}
			}
		}
	}
	
	public Vehicle getVehicle(int id){
		return idToVehicle[id];
	}

	public Vehicle getVehicle(int row, int col){
		return locationToVehicle[row][col];
	}
	
	public int heuristicCostToGoal(){
		int cost = 0;
		for(int col = getVehicle(0).getLeftTopColumn() + 2; col < width; col++,cost++){
			if(getVehicle(2,col) != null){
				cost++;
			}
		}
		return cost;
	}
	
	public boolean isGoal(){
		return getVehicle(2, 5) != null && getVehicle(2, 5).getId() == 0;
	}
	
	public Iterable<PuzzleBoard> getNeighbors(){
		Queue<PuzzleBoard> neighbors = new Queue<PuzzleBoard>();
		for(int i = 0; i < idToVehicle.length; i++){
			Vehicle v = idToVehicle[i];
			if(v != null){
				if(v.getIsHorizontal()){
					if(v.getLeftTopColumn() > 0 && getVehicle(v.getLeftTopRow(), v.getLeftTopColumn() - 1) == null){
						neighbors.enqueue(createNeighbor(i,0,-1));
					}
					if(v.getLeftTopColumn() + v.getLength() < width && getVehicle(v.getLeftTopRow(), v.getLeftTopColumn() +v.getLength()) == null){
						neighbors.enqueue(createNeighbor(i,0,1));
					}
				}
				else{
					if(v.getLeftTopRow() - 1 >= 0 && getVehicle(v.getLeftTopRow() - 1, v.getLeftTopColumn()) == null){
						neighbors.enqueue(createNeighbor(i,-1,0));
					}
					if(v.getLeftTopRow() + v.getLength() < height && getVehicle(v.getLeftTopRow() + v.getLength(), v.getLeftTopColumn()) == null){
						neighbors.enqueue(createNeighbor(i,1,0));
					}
				}
			}
		}
		return neighbors;
	}
	private PuzzleBoard createNeighbor(int id, int rowChange, int colChange){
		Vehicle[] shifted = Arrays.copyOf(idToVehicle, idToVehicle.length);
		shifted[id] = new Vehicle(id, shifted[id].getIsHorizontal(), shifted[id].getLeftTopRow() + rowChange, shifted[id].getLeftTopColumn() + colChange, shifted[id].getLength());
		return new PuzzleBoard(shifted);
	}
	public String toString(){
		// You do not need to modify this code, but you can if you really
		// want to.  The automated tests will not use this method, but
		// you may find it useful when testing within Eclipse
		
		String ret = "";
		for (int row=0; row < PuzzleManager.NUM_ROWS; row++){
			for (int col=0; col < PuzzleManager.NUM_COLUMNS; col++){
				Vehicle vehicle = getVehicle(row, col);
				if (vehicle == null){
					ret += " . ";
				}
				else{
					int id = vehicle.getId(); 
					ret += " " + id;
					if (id < 10){
						ret += " ";
					}
				}
			}
			ret += "\n";
		}
		
		for (int id = 0; id < PuzzleManager.MAX_NUM_VEHICLES; id++){
			Vehicle v = getVehicle(id);
			if (v != null){
				ret += "id " + v.getId() + ": " + 
						(v.getIsHorizontal() ? "h (" : "v (") + 
						v.getLeftTopRow() + "," + v.getLeftTopColumn() + "), " + v.getLength() + "  \n";
			}
		}
		
		return ret;
	}
	
	public int hashCode(){
		// DO NOT MODIFY THIS METHOD
		
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(idToVehicle);
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
		
		PuzzleBoard other = (PuzzleBoard) obj;
		if (!Arrays.equals(idToVehicle, other.idToVehicle)){
			return false;
		}
		return true;
	}
}