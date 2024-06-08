
public class LPHashTable extends OAHashTable {

	ModHash h;
	
	public LPHashTable(int m, long p) {
		super(m);
		this.h = ModHash.GetFunc(m, p); // Generating a random hash function
	}

	@Override
	public HashTableElement Find(long key) {
		for (int i = 0; i < this.table.length; i++) {
			int currHash = Hash(key, i);
			HashTableElement e = table[currHash];
			if (e == null) {
				return null;
			}
			if (e.GetKey() == key) { // No use of deletedArray
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
			if (table[currHash] == null) {
				table[currHash] = hte;
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
			if (e.GetKey() == key) {
				table[currHash] = null;
				numOfElements--;
				Shift(currHash);
				return;
			}
		}
	}

	public void Shift(int deletedIndex) {
		int s = 1; // Shift amount
		while (table[(deletedIndex + s) % m] != null) { /* Maybe shorter traversal is possible */
			long candidateIndex = Hash(table[(deletedIndex + s) % m].GetKey(), 0);

			if (InInterval(deletedIndex, candidateIndex, s) == false) {
				table[deletedIndex] = table[(deletedIndex + s) % m]; // Fill in the "hole"
				table[(deletedIndex + s) % m] = null;                // Move the "hole"
				deletedIndex = (deletedIndex + s) % m;               // New "hole" index
				s = 1;
			} else {
				s++;
			}
		}
	}

	// Check if candidateIndex is in (deletedIndex, deletedIndex + shift]
	public boolean InInterval(int deletedIndex, long candidateIndex, int shift) {
		if (candidateIndex == deletedIndex)
			return false;
		return (candidateIndex + m - deletedIndex) % m <= shift; // Always non-negative
	}

	@Override
	public int Hash(long x, int i) {
		return Math.floorMod(h.Hash(x) + i, m);
	}
}
