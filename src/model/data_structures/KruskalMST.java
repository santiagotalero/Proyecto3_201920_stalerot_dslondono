package model.data_structures;

import model.logic.VertexPair;

public class KruskalMST {
	
	private double peso;
	private Queue<Arc> mst= new Queue<Arc>();
	
	public KruskalMST(Graph g)
	{
		MaxHeapPQ<Edge> pq= new MaxHeapPQ<Arc>();
		
		HashTableLinearProbing arcos=g.getArcs();
		
		int i=0;
		
		while(i<arcos.size())
		{
			int j=i+1;
			
			while(j<arcos.size())
			{
				VertexPair vertexPair = new VertexPair(i, j);
				Arc actual= (Arc) arcos.get(vertexPair);
				
				if(actual !=null)
				{
					Vertex s=actual.getStart();
					Vertex f=actual.getFinish();
					int si= (int) s.getId();
					int fi=(int) f.getId();
					actual.
					
					Edge e= new Edge()
					
					pq.insert(actual);
				}
			}
			
		}
	}
	
	
	
}
    
    
