public abstract class OAHashTable implements IHashTable {

	private HashTableElement[] table;
	private int numOfElements;
	private int[] deletedArray;

	public OAHashTable(int m) {
		this.table = new HashTableElement[m];
		this.numOfElements = 0;
		deletedArray = new int[table.length]; // array indicating whether the element has been delete
		// Notice: We are not actually deleting the item from the table, we will know
		// whether the item has been deleted only through the deletedArray.
	}

	@Override
	public HashTableElement Find(long key) {
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(key, i);
			HashTableElement currEle = table[currHash];
			if (currEle == null) {
				return null;
			}
			long currKey = currEle.GetKey();
			if ((currKey == key) && (deletedArray[currHash] != 1)) {
				return currEle;
			}
		}
		return null;
	}

	@Override
	public void Insert(HashTableElement hte) throws TableIsFullException, KeyAlreadyExistsException {
		boolean flag = false;

		if (Find(hte.GetKey()) != null) {
			throw new KeyAlreadyExistsException(hte);
		}
		if (numOfElements == this.table.length) {
			throw new TableIsFullException(hte);
		}
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(hte.GetKey(), i);
			if (table[currHash] == null || deletedArray[currHash] == 1) {
				table[Hash(hte.GetKey(), i)] = hte;
				deletedArray[currHash] = 0;
				numOfElements++;
				flag = true;
				break;
			}
		}
		if (flag == false)
			throw new TableIsFullException(hte);
	}

	@Override
	public void Delete(long key) throws KeyDoesntExistException {
		if (Find(key) == null) {
			throw new KeyDoesntExistException(key);
		} else {
			for (int i = 0; i < this.table.length; i++) {
				int currHash = Hash(key, i);
				HashTableElement currEle = table[currHash];
				long currKey = currEle.GetKey();
				if ((currKey == key) && (deletedArray[currHash] != 1)) {
					deletedArray[currHash] = 1;
					numOfElements--;
					break;
				}
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