import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PuzzleBoard{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private HashMap<Integer, HashSet<Integer>> locationToVehicle; //Row major representation of locations. Key of map are rows, value is set of columns where there is a vehicle occupying that row/col spot
	// You may add additional private fields here
	
	public PuzzleBoard(Vehicle[] idToVehicleP){
		idToVehicle = new Vehicle[idToVehicleP.length];
		locationToVehicle = new HashMap<Integer, HashSet<Integer>>();
		for(int i = 0; i < idToVehicleP.length; i++){
			Vehicle v = idToVehicleP[i];
			idToVehicle[i] = idToVehicleP[i];
			if(v != null){
				if(!locationToVehicle.containsKey(v.getLeftTopRow())){
					locationToVehicle.put(v.getLeftTopRow(), new HashSet<Integer>());
				}
				for(int row = v.getLeftTopRow()+1; row < v.getLeftTopRow() + v.getLength() && !v.getIsHorizontal(); row++){
					locationToVehicle.put(row, new HashSet<Integer>());
				}
				for(int col = v.getLeftTopColumn(); col < v.getLeftTopColumn() + v.getLength() && v.getIsHorizontal(); col++){
					locationToVehicle.get(v.getLeftTopRow()).add(col);
				}
			}
		}
	}
	
	public Vehicle getVehicle(int id){
		return idToVehicle[id];
	}

	public Vehicle getVehicle(int row, int column){
		throw new UnsupportedOperationException();
	}
	
	public int heuristicCostToGoal(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isGoal(){
		throw new UnsupportedOperationException();
	}
	
	public Iterable<PuzzleBoard> getNeighbors(){
		throw new UnsupportedOperationException();
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
