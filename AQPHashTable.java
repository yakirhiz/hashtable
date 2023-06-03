

public class AQPHashTable extends OAHashTable {

	int m; // Size of the table
	ModHash h;

	public AQPHashTable(int m, long p) {
		super(m);
		this.m = m;
		this.h = ModHash.GetFunc(m, p); // Generating random hash function
	}

	@Override
	public int Hash(long x, int i) {
		if (i % 2 == 0)
			return Math.floorMod(h.Hash(x) + i * i, m);
		else
			return Math.floorMod(h.Hash(x) + -1 * i * i, m);
	}
}
