import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleBoard
{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	
	// You may add additional private fields here
	
	public PuzzleBoard(Vehicle[] idToVehicleP)
	{
		throw new UnsupportedOperationException();
	}
	
	public Vehicle getVehicle(int id)
	{
		throw new UnsupportedOperationException();
	}

	public Vehicle getVehicle(int row, int column)
	{
		throw new UnsupportedOperationException();
	}
	
	public int heuristicCostToGoal()
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean isGoal()
	{
		throw new UnsupportedOperationException();
	}
	
	public Iterable<PuzzleBoard> getNeighbors()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString()
	{
		// You do not need to modify this code, but you can if you really
		// want to.  The automated tests will not use this method, but
		// you may find it useful when testing within Eclipse
		
		String ret = "";
		for (int row=0; row < PuzzleManager.NUM_ROWS; row++)
		{
			for (int col=0; col < PuzzleManager.NUM_COLUMNS; col++)
			{
				Vehicle vehicle = getVehicle(row, col);
				if (vehicle == null)
				{
					ret += " . ";
				}
				else
				{
					int id = vehicle.getId(); 
					ret += " " + id;
					if (id < 10)
					{
						ret += " ";
					}
				}
			}
			ret += "\n";
		}
		
		for (int id = 0; id < PuzzleManager.MAX_NUM_VEHICLES; id++)
		{
			Vehicle v = getVehicle(id);
			if (v != null)
			{
				ret += "id " + v.getId() + ": " + 
						(v.getIsHorizontal() ? "h (" : "v (") + 
						v.getLeftTopRow() + "," + v.getLeftTopColumn() + "), " + v.getLength() + "  \n";
			}
		}
		
		return ret;
	}
	
	@Override
	public int hashCode()
	{
		// DO NOT MODIFY THIS METHOD
		
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(idToVehicle);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		// DO NOT MODIFY THIS METHOD
		
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		PuzzleBoard other = (PuzzleBoard) obj;
		if (!Arrays.equals(idToVehicle, other.idToVehicle))
		{
			return false;
		}
		return true;
	}
}
