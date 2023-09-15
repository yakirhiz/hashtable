

public class QPHashTable extends OAHashTable {

	ModHash h;

	public QPHashTable(int m, long p) {
		super(m);
		this.h = ModHash.GetFunc(m, p); // Generating a random hash function
	}

	@Override
	public int Hash(long x, int i) {
		return Math.floorMod(h.Hash(x) + i * i, m);
	}
}
