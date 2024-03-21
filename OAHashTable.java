
public abstract class OAHashTable implements IHashTable {

	private HashTableElement[] table;
	private int numOfElements;
	private boolean[] deletedArray; // Array of indicators for deleted elements
	protected int m; // Size of the table

	public OAHashTable(int m) {
		this.table = new HashTableElement[m];
		this.numOfElements = 0;
		deletedArray = new boolean[table.length];
		// Notice: We are not actually deleting the item from the table, we will know
		// whether the item has been deleted only through the deletedArray.
		this.m = m;
	}

	@Override
	public HashTableElement Find(long key) {
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(key, i);
			HashTableElement e = table[currHash];
			if (e == null) {
				return null;
			}
			if (e.GetKey() == key && deletedArray[currHash] == false) {
				return e;
			}
		}
		return null;
	}

	@Override
	public void Insert(HashTableElement hte) throws TableIsFullException, KeyAlreadyExistsException {
		if (Find(hte.GetKey()) != null) {
			throw new KeyAlreadyExistsException(hte);
		}
		if (numOfElements == this.table.length) {
			throw new TableIsFullException(hte);
		}
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(hte.GetKey(), i);
			if (table[currHash] == null || deletedArray[currHash] == true) {
				table[currHash] = hte;
				deletedArray[currHash] = false;
				numOfElements++;
				return;
			}
		}
	}

	@Override
	public void Delete(long key) throws KeyDoesntExistException {
		if (Find(key) == null) {
			throw new KeyDoesntExistException(key);
		}
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(key, i);
			HashTableElement e = table[currHash];
			if (e.GetKey() == key && deletedArray[currHash] == false) {
				deletedArray[currHash] = true;
				numOfElements--;
				return;
			}
		}
	}

	/**
	 *
	 * @param x - the key to hash
	 * @param i - the index in the probing sequence
	 * @return the index into the hash table to place the key x
	 */
	public abstract int Hash(long x, int i);

}