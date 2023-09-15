
public class LPHashTable extends OAHashTable {

	ModHash h;
	
	public LPHashTable(int m, long p) {
		super(m);
		this.h = ModHash.GetFunc(m, p); // Generating a random hash function
	}
	
	@Override
	public int Hash(long x, int i) {
		return Math.floorMod(h.Hash(x) + i, m);
	}
}
