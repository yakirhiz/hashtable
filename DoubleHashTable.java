

public class DoubleHashTable extends OAHashTable {

	int m; // Size of the table
	ModHash h1;
	ModHash h2;

	public DoubleHashTable(int m, long p) {
		super(m);
		this.m = m;

		/* Generating two hash functions */
		this.h1 = ModHash.GetFunc(m, p);
		this.h2 = ModHash.GetFunc(m - 1, p); // We can't have h2(x)=0 so we make hash function that gives values between
												// 1 to m. the adjustment to m is made by the adding of one to the
												// result of the hash function in the funtion "Hash".
	}

	@Override
	public int Hash(long x, int i) {
		return Math.floorMod(h1.Hash(x) + i * (h2.Hash(x) + 1), m);
	}

}
