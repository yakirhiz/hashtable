import java.util.concurrent.ThreadLocalRandom;

public class ModHash {

	int m;
	long a, b, p;

	private ModHash(long a, long b, int m, long p) {
		this.a = a;
		this.b = b;
		this.m = m;
		this.p = p;
	}

	public static ModHash GetFunc(int m, long p) {
		long a = ThreadLocalRandom.current().nextLong(1, p); // Choose uniformly long between 1 to p-1
		long b = ThreadLocalRandom.current().nextLong(0, p); // Choose uniformly long between 0 to p-1

		return new ModHash(a, b, m, p);
	}

	public int Hash(long key) {
		return Math.floorMod(Math.floorMod(a * key + b, p), m);
	}
}
