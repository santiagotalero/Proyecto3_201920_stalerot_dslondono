public class GrafoNoDirigido
{
  
  //Crea un grafo No dirigido de tamaño V vértices y sin arcos
  public GrafoNoDirigido( int V)
  {
    
  }
  
  //Número de vértices
  private int V; 
  
  //Número de arcos. Cada arco No dirigido debe contarse una única vez.
  private int E; 
  
  
  //Adiciona el arco No dirigido entre el vértice IdVertexIni y el vértice
  //IdVertexFin. El arco tiene el costo cost
  public void addEdge(K idVertexIni, K idVertexFin, double cost)
  {
    
  }
  
  //Obtener la información de un vértice. Si el vértice no existe retorna null.
  public V getInfoVertex(K idVertex) 
  {
    
  }
  
  
  //Modificar la información del vértice idVertex
  public void setInfoVertex(K idVertex, V infoVertex)
  {
  
  }
  
  
  //Obtener el costo de un arco, si el arco no existe, retorna -1
  public double getCostArc(K idVertexIni, K idVertexFin)
  {
  
  }
  
  
  
  //Modificar el costo del arco entre los vértices idVertexIni e idVertexFin
  public void setCostArc(K idVertexIni, K idVertexFin, double cost)
  {
  
  }
  
  //Adiciona un vértice con un Id único. El vértice tiene la información InfoVertex
  public void addVertex(K idVertex, V infoVertex)
  {
  
  }

  
  //Retorna los identificadores de los vértices adyacentes a idVertex
  public Iterable <K> adj (K idVertex) 
  {
  
  }
  
  
  //Desmarca todos los vértices del grafo
  public void uncheck()
  {
  
  }
  
  
  //Ejecuta la búsqueda de profundidad sobre el grafo con el vértice s como
  //origen. Los vértices resultado de la búsqueda quedan marcados y deben
  //tener información que pertenecen a una misma componente conectada.  
  public void dfs(K s)
  {
    
  }
  
  
  //Obtiene la cantidad de componentes conectados del grafo. Cada vértice
  //debe quedar marcado y debe reconocer a cuál componente conectada
  //pertenece. En caso de que el grafo esté vacío, retorna 0.
  public int cc()
  {
  
  }
  
  
  //Obtiene los vértices alcanzados a partir del vértice idVertex después de la
  //ejecución de los metodos dfs(K) y cc().
  public Iterable<K> getCC(K idVertex)
  {
  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

}
