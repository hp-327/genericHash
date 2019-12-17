package p10_package;

public class GenericHashClass<GenericData extends 
java.lang.Comparable<GenericData>>
extends java.lang.Object
{
	private int	DEFAULT_TABLE_SIZE = 10;
	int	ITEM_NOT_FOUND = -1;
	static int	LINEAR_PROBING = 101;
	private int	probeFlag;
	static int	QUADRATIC_PROBING = 102;
	private Object[] tableArray;
	private int	tableSize;
	
	/**
	 * Default constructor
	 * Initializes to default table size with probe flag set to linear probing
	 */
	public GenericHashClass()
	{
		tableSize = DEFAULT_TABLE_SIZE;
		probeFlag = LINEAR_PROBING;
		tableArray = new Object[tableSize];
	}
	
	/**
	 * Initialization constructor
	 * Initializes to default table size with probe flag set to 
	 * probe flag parameter
	 * @param inProbeFlag - sets linear or quadratic probing
	 */
	public GenericHashClass(int inProbeFlag)
	{
		probeFlag = inProbeFlag;
		tableSize = DEFAULT_TABLE_SIZE;
		tableArray = new Object[tableSize];
	}
	/**
	 * Initialization constructor
	 * @param inTableSize- sets table size (capacity) but does
	 *  not allow table size to be less than default capacity
	 * @param inProbeFlag - sets linear or quadratic probing
	 */
	public GenericHashClass(int inTableSize, int inProbeFlag)
	{
		probeFlag = inProbeFlag;
		tableSize = inTableSize;
		if (tableSize < DEFAULT_TABLE_SIZE)
		{
			tableSize = DEFAULT_TABLE_SIZE;
		}
		tableArray = new Object[tableSize];
		
	}
	
	/**
	 * Adds GenericData item to hash table
	 * Note: Uses hash index value from generateHash
	 * Note: Shows probed index with data at the point of insertion
	 * Note: Probe attempts are limited to the current size 
	 * (capacity) of the table
	 * @param newItem- GenericData item
	 * @return: boolean success of operation
	 */
	public boolean addItem(GenericData newItem)
	{
		int hashIndex = generateHash(newItem);
		int probeCount = 1;
		int raised = 2;
		while (tableArray[hashIndex] != null && probeCount <= tableSize)
		{
			if(probeFlag == LINEAR_PROBING)
			{
				hashIndex = hashIndex + probeCount;	
			}
			else
			{
				hashIndex = hashIndex + toPower(probeCount,raised);	
			}
			hashIndex %= tableSize;
			probeCount++;
		}
		if(probeCount < tableSize)
		{
			tableArray[hashIndex] = newItem;
			System.out.println(newItem.toString() + ", probed Index: " 
			+ hashIndex);
			return true;
		}
		return false;
	}
	/**
	 * Clears hash table by setting all bins to null
	 */
	public void clearHashTable()
	{
		int index;
		for (index = 0; index < tableSize; index++)
		{
			tableArray[index] = null;
		}
	}
	/**
	 * Returns item found
	 * @param searchItem - GenericData value to be found; uses findItemIndex
	 * @returnGenericData item found
	 */
	@SuppressWarnings("unchecked")
	public GenericData findItem(GenericData searchItem)
	{
		int itemIndex = findItemIndex(searchItem);
		if (itemIndex ==  ITEM_NOT_FOUND)
		{
			return null;
		}
		return (GenericData) tableArray[itemIndex];
	}
	/**
	 * Searches for item index in hash table
	 * Uses linear or quadratic probing as configured
	 * @param searchItem - GenericData value to be found
	 * @return: integer index location of search item
	 */
	@SuppressWarnings("unchecked")
	private int findItemIndex(GenericData searchItem)
	{
		int hashIndex = generateHash(searchItem);
		int probeCount = 1;
		int raise = 2;
		GenericData check = (GenericData) tableArray[hashIndex];
		while (check != null && probeCount <= tableSize && 
				check.compareTo(searchItem) != 0)
		{
			if(probeFlag == LINEAR_PROBING)
			{
				hashIndex = hashIndex + probeCount;	
			}
			else
			{
				hashIndex = hashIndex + toPower(probeCount,raise);	
			}
			hashIndex %= tableSize;
			probeCount++;
			check = (GenericData) tableArray[hashIndex];
		}
		if(check != null && probeCount < tableSize)
		{
			return hashIndex;
		}
		return ITEM_NOT_FOUND;
	}
	/**
	 * Method converts GenericData hash value to index for use in hash table
	 * @param item - GenericData value to be converted to hash value
	 * Note: gets data from object via hashCode, then calculates index
	 * Note: Uses hashCode from object
	 * @return: integer hash value
	 */
	public int generateHash(GenericData item)
	{
		return item.hashCode() % tableSize;
	}
	/**
	 * Removes item from hash table
	 * @param toBeRemoved - GenericData value used for 
	 * requesting data uses findItemIndex
	 * @return: GenericData item removed, or null if not found
	 */
	public GenericData removeItem(GenericData toBeRemoved)
	{
		int index = findItemIndex(toBeRemoved);
		if (index ==  ITEM_NOT_FOUND)
		{
			return null;
		}
		@SuppressWarnings("unchecked")
		GenericData removed = (GenericData) tableArray[index];
		tableArray[index] = null;
		return removed;
		
	}
	/**
	 * traverses through all array bins, finds min and max number of 
	 * contiguous elements, and number of empty nodes; also shows table loading
	 * NOTE: Generates string of used and unused bins in addition to 
	 * displaying results on screen
	 * @return: String result of hash table analysis
	 */
	public String showHashTableStatus()
	{
		String tableStat = ("Hash Table Status: ");
		int index;
		int min = tableSize;
		int max = 0;
		int emptyNodeCounter = 0;
		int nodeCounter = 0;
		for (index = 0; index<tableSize; index++)
		{
			if (tableArray[index] != null)
			{
				tableStat += "D";
			}
			else
			{
				tableStat += "N";
			}
		}
		for (index = 0; index < tableSize; index++)
		{
			if (tableArray[index] != null)
			{
				nodeCounter++;
				
				if (nodeCounter > 0)
				{
					if(nodeCounter > max)
					{
						max = nodeCounter;
					}
					if(nodeCounter < min)
					{
						min = nodeCounter;
					}
				}	
			}
			else
			{
				emptyNodeCounter++;
				nodeCounter = 0 ;
			}
		}
		if (nodeCounter > 0)
		{
			if(nodeCounter > max)
			{
				max = nodeCounter;
			}
			if(nodeCounter < min)
			{
				min = nodeCounter;
			}
		}	
		//System.out.println("Hash Table Status: ");
		System.out.println(tableStat);
		System.out.println("	Minimum contiguous bins: " + min);
		System.out.println("	Maximum contiguous bins: " + max);
		System.out.println("		Number Of Empty Bins: " + 
		emptyNodeCounter);
		// array Dump
		System.out.println("Array Dump:");
		for(index = 0; index<tableSize; index++)
			if(tableArray[index] == null)
			{
				System.out.println("null");
			}
			else
			{
				System.out.println(tableArray[index]);
			}
				
		
		return tableStat;
	}
	
	/**
	 * Local recursive method to calculate exponentiation with integers
	 * @param base - base of exponentiation
	 * @param exponent - exponent of exponentiation
	 * @return: result of exponentiation calculation
	 */
	private int toPower(int base, int exponent)
	{
		if (exponent == 0)
		{
			return 1;
		}
		return base * toPower(base, exponent-1);
	}
}
