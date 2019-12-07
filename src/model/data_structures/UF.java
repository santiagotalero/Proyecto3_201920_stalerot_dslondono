package model.data_structures;

public class UF {

    private int[] parent;
    private int[] size;
    private int n;
    
    public UF(int p)
    {
    	if (n < 0) throw new IllegalArgumentException();
        n = p;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 0;
        }
    }
    
    public int find(int p) {
  
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }
    
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (size[rootP] < size[rootQ]) parent[rootP] = rootQ;
        else if (size[rootP] > size[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            size[rootP]++;
        }
        n--;
    }
    
	
    }
