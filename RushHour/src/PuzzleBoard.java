import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PuzzleBoard{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private HashMap<Integer, HashMap<Integer, Vehicle>> locationToVehicle; //Row major representation of locations. Key of map are rows, value is set of columns where there is a vehicle occupying that row/col spot
	// You may add additional private fields here
	/*public static void main(String[] args){
		Vehicle[] vs = new Vehicle[3];
		vs[0] = new Vehicle(0, true, 2, 4, 2);
		vs[1] = new Vehicle(1, true, 5, 2, 3);
		vs[2] = new Vehicle(2, false, 0, 2, 3);
		PuzzleBoard p = new PuzzleBoard(vs);
		
		System.out.println(p.getVehicle(2, 0));
		System.out.println(p.getVehicle(2, 2));
		System.out.println(p.getVehicle(2, 4));
		System.out.println(p.getVehicle(5, 4));
		System.out.println();
		System.out.println(p.getVehicle(0));
		System.out.println(p.getVehicle(1));
		System.out.println(p.getVehicle(2));
		System.out.println(p.isGoal());
	}*/
	public PuzzleBoard(Vehicle[] idToVehicleP){
		idToVehicle = new Vehicle[idToVehicleP.length];
		locationToVehicle = new HashMap<Integer, HashMap<Integer, Vehicle>>();
		for(int i = 0; i < idToVehicleP.length; i++){
			Vehicle v = idToVehicleP[i];
			idToVehicle[i] = idToVehicleP[i];
			if(v != null){
				if(!locationToVehicle.containsKey(v.getLeftTopRow())){
					locationToVehicle.put(v.getLeftTopRow(), new HashMap<Integer, Vehicle>());
					locationToVehicle.get(v.getLeftTopRow()).put(v.getLeftTopColumn(), v);
				}
				for(int row = v.getLeftTopRow()+1; row < v.getLeftTopRow() + v.getLength() && !v.getIsHorizontal(); row++){
					if(locationToVehicle.get(row) == null){
						locationToVehicle.put(row, new HashMap<Integer, Vehicle>());
					}
					locationToVehicle.get(row).put(v.getLeftTopColumn(), v);
				}
				for(int col = v.getLeftTopColumn(); col < v.getLeftTopColumn() + v.getLength() && v.getIsHorizontal(); col++){
					locationToVehicle.get(v.getLeftTopRow()).put(col, v);
				}
			}
		}
	}
	
	public Vehicle getVehicle(int id){
		return idToVehicle[id];
	}

	public Vehicle getVehicle(int row, int column){
		if(locationToVehicle.get(row) == null){
			return null;
		}
		return locationToVehicle.get(row).get(column);
	}
	
	public int heuristicCostToGoal(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isGoal(){
		return getVehicle(2, 5).getId() == 0;
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
