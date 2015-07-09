package lucas.waitassist;

/**
 * A simple object representing an individual Patron in the restaurant,
 * including methods for converting to and from CSV format.
 * 
 * @author Lucas Kushner
 *
 */
public class Patron 
{
	private String tableId;
	private String seatId;
	private String menuSelection;
	
	/**
	 * Constructs a new Patron from the given data.	 * 
	 * @param tableId
	 * @param seatId
	 * @param menuSelection
	 */
	public Patron(String tableId, String seatId, String menuSelection)
	{
		this.tableId = tableId;
		this.seatId = seatId;
		this.menuSelection = menuSelection;
	}
	
	// Getters
	public String getTableId() { return this.tableId; }
	public String getSeatId() { return this.seatId; }
	public String getMenuSelection() { return this.menuSelection; }
	
	// Setters
	public void setTableId(String tableId) { this.tableId = tableId; }
	public void setSeatId(String seatId) { this.seatId = seatId; }
	public void setMenuSelection(String menuSelection) { this.menuSelection = menuSelection; }
	
	/**
	 * Creates a new Patron object by parsing the given CSV formatted string.
	 * The formatting of the input String must be similar to the format used by
	 * the toString() method of this object.
	 * @param csvLine
	 * @return
	 */
    public static Patron constructFromCSV(String csvLine)
    {
        String[] parts = csvLine.split(",");
        if (parts.length != 3)
        {
            throw new IllegalArgumentException("The input of: \"" + csvLine + "\" is not appropriately line. It must contain exactly 3 comma-separated values.");
        }
        return new Patron(parts[0], parts[1], parts[2]);
    }
	
	@Override
	public String toString()
	{
		return this.tableId + "," + this.seatId + "," + this.menuSelection;
	}
}