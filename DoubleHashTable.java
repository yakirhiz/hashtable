
public class DoubleHashTable extends OAHashTable {

	ModHash h1, h2;

	public DoubleHashTable(int m, long p) {
		super(m);

		/* Generating two hash functions
		 *
		 * We aim for h2 to produce a permutation on {0, 1, ..., m - 1} for all keys.
		 * Two conditions ensure this:
		 * 1. For all keys k, h2(k) must not be equal to 0.
		 * 2. For all keys k, h2(k) should not share any common divisors with m (the table size).
		 * Thus, we opt for:
		 * 1. Choose m to be a prime number.
		 * 2. let h1(k) = ((a * k + b) % p) % (m).
		 * 3. set h2(k) = (((a * k + b) % p) % (m - 1)) + 1.
		 */
		this.h1 = ModHash.GetFunc(m, p);
		this.h2 = ModHash.GetFunc(m - 1, p); // the adjustment (+1) is made in the "Hash" method.
	}

	@Override
	public int Hash(long x, int i) {
		return Math.floorMod(h1.Hash(x) + i * (h2.Hash(x) + 1), m);
	}

}
