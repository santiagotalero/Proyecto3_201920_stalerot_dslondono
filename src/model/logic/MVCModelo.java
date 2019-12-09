package model.logic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringJoiner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;

import model.data_structures.Arc;
import model.data_structures.ArrayList;
import model.data_structures.ArrayList.IteratorArrayList;
import model.data_structures.Dijkstra;
import model.data_structures.DirectedEdge;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedDigraph;
import model.data_structures.Graph;
import model.data_structures.Queue;
import model.data_structures.Vertex;
import model.logic.JSon.ArcoJSon;
import model.logic.JSon.VerticeJSon;





/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */

	private EdgeWeightedDigraph grafo0;
	private Graph grafoDistancia;
	private Graph grafoTiempo; 
	private Graph grafoVelocidad; 
	private Graph grafoZonas;
	
	private Queue<Integer> movements;
	private Queue<TravelTime> tiemposDeViaje;
	private Queue<Interseccion> zonasUber;
	private int tamano;
	private Dijkstra dijkstra; 

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		grafoDistancia= new Graph();
		grafoTiempo= new Graph();
		grafoVelocidad= new Graph();
		grafoZonas= new Graph();
		tiemposDeViaje= new Queue<TravelTime>();
		zonasUber= new Queue<Interseccion>();
		movements= new Queue<Integer>();
		tamano=0;

		//dijkstra = new Dijkstra(grafo0, 0);
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return tamano;
	}


	public void cargarGrafoInicial()
	{
		//ARCHIVOS JSON
		String path1= "./data/Archivos Json Grafo/vertices.json";


		Gson gson = new Gson();

		JsonReader readerJSon;

		VerticeJSon[] v= null;

		try{
			readerJSon= new JsonReader(new FileReader(path1));
			v= gson.fromJson(readerJSon, VerticeJSon[].class);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		int i=0;

		while(i<v.length)
		{
			VerticeJSon actual= v[i];

			Coordinate interseccion= new Coordinate(actual.getLongitud(),actual.getLatitud(),actual.getMOVEMENT_ID());
			Vertex<Integer,Coordinate,Double> vertice= new Vertex<Integer,Coordinate,Double>(actual.getId(),interseccion);


			grafoDistancia.addVertex(vertice.getId(), vertice.getValue());

			i++;
		}

		String path2= "./data/Archivos Json Grafo/arcos.json"; 

		Gson gson2 = new Gson();

		JsonReader readerJSon2;

		ArcoJSon[] a= null;

		try{
			readerJSon= new JsonReader(new FileReader(path2));
			a= gson.fromJson(readerJSon, ArcoJSon[].class);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		int j=0;

		while(j<a.length)
		{
			ArcoJSon actual= a[j];

			grafoDistancia.addEdge(actual.getOrigen(), actual.getDestino(), actual.getHaversine());

			j++;
		}

		System.out.println("El número de vertices cargado fue:"+ grafoDistancia.V()+" , y el número de arcos cargado fue:" + grafoDistancia.E());


	}

	public void cambiarCostos() throws Exception
	{	
		cargarArchivosViajesZonas();
		generarGrafoTiempo();

	}

	private void generarGrafoTiempo()
	{
		grafoTiempo=grafoDistancia;
		int z=0;
		
		int i=0;
		
		double[] tiempos= new double[grafoTiempo.E()];
		
		volverTiempoArreglo(tiempos);
		
		while(i<grafoTiempo.E() && z<grafoTiempo.E())
		{
			int j=i+1;
			
			while(j<grafoTiempo.E() && z<grafoTiempo.E())
			{
				grafoTiempo.setInfoArc(i, j, tiempos[z] );
				z++;
				j++;
			}
			i++;
		}
	}
	
	private void volverTiempoArreglo(double[] tiempos)
	{
		int i=0;
		
		Iterator iter= tiemposDeViaje.iterator();
		
		while(iter.hasNext() && i<18558)
		{
			TravelTime actual=(TravelTime) iter.next();
			
			double t= actual.getMeanTravelTime();
			
			tiempos[i]= t;
			
			i++;
		}
		
	}
	
	
	
	
	private void cargarArchivosViajesZonas() throws Exception
	{
		File archivo = new File ("./data/bogota_vertices.txt");
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);


		String linea = br.readLine();

		while((linea=br.readLine())!=null)
		{
			String[] l= linea.split(";");

			Interseccion interseccion= new Interseccion(Double.parseDouble(l[1]),Double.parseDouble(l[2]),Integer.parseInt(l[3]), Integer.parseInt(l[0]));

			zonasUber.enqueue(interseccion);
		}


		CSVReader readerCSV = null;
		try 
		{
			readerCSV = new CSVReader(new FileReader("./data/bogota-cadastral-2018-1-WeeklyAggregate.csv"));


			readerCSV.readNext();

			for(String[] nextLine : readerCSV)
			{
				TravelTime actual= new TravelTime(1,Integer.parseInt(nextLine[0]),Integer.parseInt(nextLine[1]),Integer.parseInt(nextLine[2]),Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]));
				tiemposDeViaje.enqueue(actual);
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		finally{
			if (readerCSV != null) {
				try {
					readerCSV.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public Vertex encontrarVerticeMasCercano(double lat2, double lon2)
	{
		int i=0;

		Vertex v=null;

		double distancia= Double.POSITIVE_INFINITY;
		

		while(i<grafoDistancia.V())
		{
			Coordinate actual=(Coordinate) grafoDistancia.getInfoVertex(i);

			if(actual!=null)
			{
				Double lat1= actual.getLatitude();
				Double lon1= actual.getLongitude();
				

			    final int R = 6371; // Radius of the earth

			    double latDistance = Math.toRadians(lat2 - lat1);
			    double lonDistance = Math.toRadians(lon2 - lon1);
			    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			    double distance = R * c * 1000; // convert to meters


			    distance = Math.pow(distance, 2);

			    Double Haversine=Math.sqrt(distance);
			    
				
				if(Haversine<distancia)
				{
					distancia=Haversine;
					v= new Vertex(i, actual);
				}
			}

			i++;
		}



		return v;
	}
	
	public void generarMapa() throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/mapa.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		int i=0;
		
		while(i<grafoDistancia.E())
		{
			Coordinate c= (Coordinate) grafoDistancia.getInfoVertex(i);

			if(c!=null)
			{
				IteratorArrayList adyacentes=grafoDistancia.adj(i);


				while(adyacentes.hasNext())
				{
					
					int id= (int) adyacentes.next();
					
					pr.println("line = [");
					
					StringJoiner joiner= new StringJoiner(",");
					
					Coordinate c2= (Coordinate) grafoDistancia.getInfoVertex(id);
					
					double lat1= c.latitude;
					double lon1=c.longitude;
					double lat2=c2.latitude;
					double lon2=c2.longitude;
					
					String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";
					String s2= "{ lat: "+lat2+" , lng: "+lon2+"}";
					
					joiner.add(s1).add(s2);
					
					pr.println(joiner.toString());
					
					pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");
				}
			}
			i++;
		}
		
		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	////////////////////////////////////////////////////////////////////////////
	///// PARTE A  /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	public Queue<DirectedEdge> reqA1( long lat1, long lon1, long lat2, long lon2 )
	{
		Queue<DirectedEdge> retorno = new Queue<>();

		// La interseccion esta dado por dos coordenadas
		Interseccion cor1 = new Interseccion(lat1, lon1, 0, tamano);
		Interseccion cor2 = new Interseccion(lat2, lon2, 0, tamano);


		// Cada zona uber es el Movement_ID de cada interseccion
		int zona1 = cor1.getMOVEMENT_ID();
		int zona2 = cor2.getMOVEMENT_ID();

		// Declarar el Dijkstra con el origen desde zona1
		Dijkstra x = new Dijkstra( grafo0, zona1 ); 

		//Preguntar si hay path entre los dos Y si ambas coordenadas dadas son diferentes
		if( x.hasPathTo(zona2) && cor1 != cor2 ) 
		{
			//Declarar el "arreglo" de arcos del path hasta la zona uber 2
			Iterable<DirectedEdge> arregloArcos = x.pathTo(zona2);


			//Iterar sobre dicho arreglo y agregar al Queue retorno
			Iterator iter = arregloArcos.iterator(); 

			while( iter.hasNext()) 
			{
				DirectedEdge actual = (DirectedEdge) iter.next();
				retorno.enqueue(actual);
			}

		}
		
		else 
		{
			return null; 
		}

		return retorno; 
	}

	public Queue<Vertex> reqA2(int n)
	{
		Queue<Vertex> retorno = new Queue<>();
		
		
		
		
		
		//Hacer BFS
		
		
		return null; 
	}

	public Graph req3A()
	{
		return null; 
	}



	////////////////////////////////////////////////////////////////////////////
	///// PARTE B  /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	public void reqB1( double lat1, double lon1, double lat2, double lon2 )
	{
		Vertex v1= encontrarVerticeMasCercano(lat1, lon1);
		Vertex v2= encontrarVerticeMasCercano(lat2, lon2);
		
		int id1= (int) v1.getId();
		int id2=(int) v2.getId();

		
		Queue<Vertex> q= new Queue<Vertex>();
		
		boolean[] marked = new boolean[grafoDistancia.V()];
		
		DFSB1(id1,id2, marked, q );
		
		System.out.println("Camino a seguir:");
		System.out.println("");
		System.out.println("Total vertices:"+ q.size());
		
		Iterator iter= q.iterator();
		
		
		while(iter.hasNext())
		{
			Vertex actual= (Vertex) iter.next();
			Coordinate c= (Coordinate) actual.getValue();
			
			System.out.println("Id: "+ actual.getId()+ " latitud: " + c.latitude + " longitud: " + c.longitude);
		} 
		
		double distanciaTotal=0;
		double tiempoTotal=0;
		
		iter=q.iterator();
		while(iter.hasNext())
		{
			Vertex actual= (Vertex) iter.next();
			int i1= (int) actual.getId();
			
			if(iter.hasNext())
			{
				Vertex siguiente= (Vertex) iter.next();
				int i2=(int) siguiente.getId();
				
				if(grafoDistancia.getInfoArc(i1, i2)!=null)
				{
					double d=(double) grafoDistancia.getInfoArc(i1, i2);
					distanciaTotal += d;
				}
				
//				double t= (double) grafoTiempo.getInfoArc(i1, i2);
				
				
//				tiempoTotal += t;
			}
			
			
		} 
		
		System.out.println("Distancia total: " + distanciaTotal);
		System.out.println("Tiempo total: "+ tiempoTotal);

		try {
			generarMapaB1(q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void DFSB1(int v, int f, boolean[] marked, Queue<Vertex> q)
	{
		Coordinate actual=(Coordinate) grafoDistancia.getInfoVertex(v);
		Vertex ver= new Vertex(v, actual);
		q.enqueue(ver);
		
		if(v==f)
		{
			return;
		}
		
		marked[v] = true;
		int actKey =(int) grafoDistancia.indexToKey(v);

		Iterable<Integer> adjs = grafoDistancia.adjacents(actKey);
		for(Integer id : adjs)
		{
			int adjId = grafoDistancia.keyToIndex(id);
			if(!marked[adjId])
			{
				DFSB1(adjId, f, marked, q);
			}
				
		}
	}
	
	private void generarMapaB1(Queue<Vertex> q) throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/ReqB1.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		Iterator iter= q.iterator();
		
		pr.println("line = [");
		StringJoiner joiner= new StringJoiner(",");

		while(iter.hasNext())
		{
			Vertex v1= (Vertex) iter.next();
			Coordinate c= (Coordinate) v1.getValue();

			if(c!=null)
			{

				double lat1= c.latitude;
				double lon1=c.longitude;


				String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";

				joiner.add(s1);

			}

		}
		pr.println(joiner.toString());

		pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");


		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	public void reqB2(double lat, double lon, int T )
	{
		Vertex v= encontrarVerticeMasCercano(lat, lon);
		int id=(int) v.getId();
		
		Queue<Vertex> q= new Queue<Vertex>();
		
		boolean[] marked = new boolean[grafoDistancia.V()];
		
		DFSB2(id,T, marked, q );
		
		System.out.println("Vertices alcanzables en ese tiempo: ");
		System.out.println("");
		
		Iterator iter= q.iterator();
		
		
		while(iter.hasNext())
		{
			Vertex actual= (Vertex) iter.next();
			Coordinate c= (Coordinate) actual.getValue();
			
			System.out.println("Id: "+ actual.getId()+ " latitud: " + c.latitude + " longitud: " + c.longitude);
		}
		
		try {
			generarMapaB2(q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void DFSB2(int v, double tRestante, boolean[] marked, Queue<Vertex> q)
	{

		Coordinate actual=(Coordinate) grafoDistancia.getInfoVertex(v);
		Vertex ver= new Vertex(v, actual);
		q.enqueue(ver);

		marked[v] = true;
		int actKey =(int) grafoDistancia.indexToKey(v);

		Iterable<Integer> adjs = grafoDistancia.adjacents(actKey);
		for(Integer id : adjs)
		{
			int adjId = grafoDistancia.keyToIndex(id);
			
			double tiempoActual= (double) grafoTiempo.getInfoArc(actKey, adjId);
			
			if(!marked[adjId] && tiempoActual <= tRestante)
			{
				DFSB2(adjId, tRestante-tiempoActual, marked, q);
			}
				
		}
		
	}

	private void generarMapaB2(Queue<Vertex> q) throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/ReqB2.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		Iterator iter= q.iterator();
		
		pr.println("line = [");
		StringJoiner joiner= new StringJoiner(",");

		while(iter.hasNext())
		{
			Vertex v1= (Vertex) iter.next();
			Coordinate c= (Coordinate) v1.getValue();

			if(c!=null)
			{

				double lat1= c.latitude;
				double lon1=c.longitude;


				String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";

				joiner.add(s1);

			}

		}
		pr.println(joiner.toString());

		pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");


		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void reqB3() 
	{
		long tiempoInicial= System.currentTimeMillis();
		
		Queue<Vertex> maxima= new Queue<Vertex>();
		int max=0;
		
		
		
		int i=0;
		
		while(i<grafoDistancia.V())
		{
			Queue<Vertex> q= new Queue<Vertex>();
			boolean[] marked = new boolean[grafoDistancia.V()];
			KruskalB3(i, marked, q);
			
			if(q.size()>max)
			{
				maxima=q;
				max=q.size();
			}
			i++;
		}
		
		try {
			generarMapaB3(maxima);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long tiempoFinal= System.currentTimeMillis();
		
		long tiempoTranscurrido= tiempoFinal-tiempoInicial;
		
		System.out.println("Al algoritmo le tomó: "+ tiempoTranscurrido +" milisegundos en encontrar la solución.");
		System.out.println("El total de vertices de la componente fue: "+ maxima.size());
		
		Iterator iter= maxima.iterator();
		
		double distanciaTotal= 0;
		
		int idActual=-100;
		
		
		while(iter.hasNext())
		{
			Vertex actual= (Vertex) iter.next();
			
			int id= (int) actual.getId();
			
			System.out.println("Identificador vértice: "+ id);
			
			if(idActual !=-100)
			{
				double d=0;
				
				if(grafoDistancia.existsArc(idActual, id))
				{
					d= (double) grafoDistancia.getInfoArc(idActual, id);
				}

				distanciaTotal+= d;
				
				
			}
			idActual=id;
		}
		
		System.out.println("El costo total del arbol (en km) es: "+ distanciaTotal);
		
	}  
	
	private void KruskalB3(int v, boolean[] marked, Queue<Vertex> q)
	{
		Coordinate actual=(Coordinate) grafoDistancia.getInfoVertex(v);
		Vertex ver= new Vertex(v, actual);
		q.enqueue(ver);
		
		marked[v] = true;
		int actKey =(int) grafoDistancia.indexToKey(v);

		Iterable<Integer> adjs = grafoDistancia.adjacents(actKey);
		for(Integer id : adjs)
		{
			int adjId = grafoDistancia.keyToIndex(id);
			if(!marked[adjId])
			{
			KruskalB3(adjId, marked, q);
			}
				
		}
	}
	private void generarMapaB3(Queue<Vertex> q) throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/ReqB3.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		Iterator iter= q.iterator();
		
		pr.println("line = [");
		StringJoiner joiner= new StringJoiner(",");

		while(iter.hasNext())
		{
			Vertex v1= (Vertex) iter.next();
			Coordinate c= (Coordinate) v1.getValue();

			if(c!=null)
			{

				double lat1= c.latitude;
				double lon1=c.longitude;


				String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";

				joiner.add(s1);

			}

		}
		pr.println(joiner.toString());

		pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");


		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	

	////////////////////////////////////////////////////////////////////////////
	///// PARTE C //////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	public void reqC1() 
	{
	
		
		Queue<Vertex> q= new Queue<Vertex>();
		
		int i=0;
		
		while(i<grafoDistancia.V())
		{
			boolean[] marked = new boolean[grafoDistancia.V()];
			DFSC1(i, marked, q, movements );
			i++;
		}
		
		
		Iterator<Vertex> iter= q.iterator();
		
		while(iter.hasNext())
		{
			Vertex actual= iter.next();
			Coordinate c= (Coordinate) actual.getValue();
			int mov= c.getMOVEMENT_ID();
			
			grafoZonas.addVertex(mov, actual.getValue());
			
		}
		iter= q.iterator();
		
		while(iter.hasNext())
		{
			Vertex actual= iter.next();
			Coordinate c= (Coordinate) actual.getValue();
			int mov= c.getMOVEMENT_ID();
			
			Iterator<Integer> iterM= movements.iterator();
			
			while(iterM.hasNext())
			{
				Integer idActual= iterM.next();
				
				if(grafoTiempo.existsArc(mov, idActual) && !grafoZonas.existsArc(idActual, mov))
				{
					double tiempo= (double) grafoTiempo.getInfoArc(idActual, mov);
					grafoZonas.addEdge(idActual, mov, tiempo);
				}
			}
			
		}


		try {
			generarMapaC1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("El número de vertices cargado fue:"+ grafoZonas.V()+" , y el número de arcos cargado fue:" + grafoZonas.E());

		
		
		
	}
	private void DFSC1(int v, boolean[] marked, Queue<Vertex> q, Queue<Integer> movs)
	{

		Coordinate actual=(Coordinate) grafoDistancia.getInfoVertex(v);
		
		if(actual!=null)
		{
			int mov= actual.getMOVEMENT_ID();
			
			if(!estaEnQueue(mov, movs))
			{
				movs.enqueue(mov);
				Vertex ver= new Vertex(v, actual);
				q.enqueue(ver);

				marked[v] = true;
				int actKey =(int) grafoDistancia.indexToKey(v);

				Iterable<Integer> adjs = grafoDistancia.adjacents(actKey);
				for(Integer id : adjs)
				{
					int adjId = grafoDistancia.keyToIndex(id);
					
					
					if(!marked[adjId])
					{
						DFSC1(adjId, marked, q,movs);
					}
						
				}
			}
		}
		
		
		
		
	}

	
	private boolean estaEnQueue(int n, Queue<Integer>q)
	{
		Iterator<Integer> iter= q.iterator();
		
		while(iter.hasNext())
		{
			Integer actual= iter.next();
			
			if(actual==n)
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
	
	
	private void generarMapaC1() throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/ReqC1.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		int i=0;
		
		while(i<grafoZonas.E())
		{
			Coordinate c= (Coordinate) grafoZonas.getInfoVertex(i);

			if(c!=null)
			{
				IteratorArrayList adyacentes=grafoZonas.adj(i);


				while(adyacentes.hasNext())
				{
					
					int id= (int) adyacentes.next();
					
					pr.println("line = [");
					
					StringJoiner joiner= new StringJoiner(",");
					
					Coordinate c2= (Coordinate) grafoZonas.getInfoVertex(id);
					
					double lat1= c.latitude;
					double lon1=c.longitude;
					double lat2=c2.latitude;
					double lon2=c2.longitude;
					
					String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";
					String s2= "{ lat: "+lat2+" , lng: "+lon2+"}";
					
					joiner.add(s1).add(s2);
					
					pr.println(joiner.toString());
					
					pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");
				}
			}
			i++;
		}
		
		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void reqC2( int IDZonaOrigen, int IDZonaDestino) 
	{
		
		
		
		
	}
	
	

	public void reqC3() 
	{
		long tiempoInicial= System.currentTimeMillis();
		
		
		int max=0;
		Queue<Vertex> q= new Queue<Vertex>();
		
		
//		 Iterator<Integer> iter= movements.iterator();
//		 
//		 int movAct=-1;
//		 
//		 while(iter.hasNext())
//		 {
//			 Integer actual= iter.next();
//			 Queue<Vertex> act= new Queue<Vertex>();
//			 
//			 if(movAct!=-1)
//			 {
//				 boolean[] marked = new boolean[grafoZonas.V()];
//				 DFSC3(movAct,actual, marked, act);
//				 
//				 if(act.size()>max)
//				 {
//					 max=act.size();
//					 q=act;
//				 }
//			 }
//			 movAct=actual;
//		 }
		
		int i=0;

		while(i<grafoZonas.V())
		{
			Coordinate c= (Coordinate) grafoZonas.getInfoVertex(i);

			if(c!=null)
			{
				int mov1= c.getMOVEMENT_ID();

				int j=0;

				while(j<grafoZonas.V())
				{
					Coordinate c2= (Coordinate) grafoZonas.getInfoVertex(j);

					if(c2!=null)
					{
						int mov2= c2.getMOVEMENT_ID();

						Queue<Vertex> act= new Queue<Vertex>();

						boolean[] marked = new boolean[grafoZonas.V()];
						DFSC3(mov1,mov2, marked, act);

						if(act.size()>max)
						{
							max=act.size();
							q=act;
						}
					}
					j++;
				}

			}
			i++;
		}


		 
		 
		 long tiempoFinal= System.currentTimeMillis();
		 
		 try {
			generarMapaC3(q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 long tiempoTranscurrido= tiempoFinal-tiempoInicial;
		 
		 System.out.println("El tiempo (en milisegundos) que le tomó al algoritmo encontrar la respuesta fue: "+ tiempoTranscurrido);
		 
		 Iterator iterQ=q.iterator();
		 
		 
		 while(iterQ.hasNext())
		 {
			 Vertex actual= (Vertex) iterQ.next();
			 
			 System.out.println("Movement ID: "+ actual.getId());
		 }
		 
		 System.out.println("El total de arcos fue: "+ (max-1));
		 
		 
	}
	
	
	private void DFSC3(int v, int f, boolean[] marked, Queue<Vertex> q)
	{
		Coordinate actual=(Coordinate) grafoZonas.getInfoVertex(v);
		Vertex ver= new Vertex(v, actual);
		q.enqueue(ver);
		
		if(v==f)
		{
			return;
		}
		
		marked[v] = true;
		int actKey =(int) grafoZonas.indexToKey(v);

		Iterable<Integer> adjs = grafoZonas.adjacents(actKey);
		for(Integer id : adjs)
		{
			int adjId = grafoZonas.keyToIndex(id);
			if(!marked[adjId])
			{
				DFSC3(adjId, f, marked, q);
			}
				
		}
	}
	private void generarMapaC3(Queue<Vertex> q) throws Exception
	{
		File pre= new File("./data/HTML/pre.html");
		FileReader frPre= new FileReader(pre);
		BufferedReader brPre= new BufferedReader(frPre);

		File post= new File("./data/HTML/post.html");
		FileReader frPost= new FileReader(post);
		BufferedReader brPost= new BufferedReader(frPost);


		File escritura = new File ("./data/HTML/ReqC3.html");
		PrintWriter pr = new PrintWriter(escritura);

		String lineaPre= brPre.readLine();

		while(lineaPre!=null)
		{
			pr.println(lineaPre);

			lineaPre=brPre.readLine();
		}
		
		brPre.close();

		Iterator iter= q.iterator();
		
		pr.println("line = [");
		StringJoiner joiner= new StringJoiner(",");

		while(iter.hasNext())
		{
			Vertex v1= (Vertex) iter.next();
			Coordinate c= (Coordinate) v1.getValue();

			if(c!=null)
			{

				double lat1= c.latitude;
				double lon1=c.longitude;


				String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";

				joiner.add(s1);

			}

		}
		pr.println(joiner.toString());

		pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");


		String lineaPost= brPost.readLine();

		while(lineaPost!=null)
		{
			pr.println(lineaPost);

			lineaPost=brPost.readLine();
		}
		
		brPost.close();
		pr.close();

		File htmlFile = escritura;
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

//	public void cambiarCostos() throws Exception
//	{	
//		cargarArchivosViajesZonas();
//		grafoTiempo=grafoDistancia;
//		grafoVelocidad=grafoDistancia;
//
//		int i=0;
//
//		while(i<grafoDistancia.E())
//		{
//			int j=0;
//
//			while(j<grafoDistancia.E())
//			{
//
//				//Costo 1
//				Double haversine=0.0;
//
//				if(grafoDistancia.getInfoArc(i, j)!=null)
//				{
//					haversine=(Double) grafoDistancia.getInfoArc(i, j);
//					System.out.println(haversine);
//				}
//
//
//				//Costo 2
//				double totalTiempos=0;
//				int numeroTiempos=0;
//
//
//				Iterator<TravelTime> iter= tiemposDeViaje.iterator();
//
//				while(iter.hasNext())
//				{
//					TravelTime actual= iter.next();
//
//					int origen=actual.getSourceID();
//					int destino= actual.getDstID();
//
//					if((origen==i && destino==j)||(origen==j&&destino==i))
//					{
//						System.out.println("Encontro uno");
//						totalTiempos += actual.getMeanTravelTime();
//						numeroTiempos++;
//					}
//
//				}
//
//				double promedio=0;
//
//				if(numeroTiempos !=0)
//				{
//					promedio=(totalTiempos/numeroTiempos);
//				}
//				else
//				{
//					Coordinate v1=(Coordinate) grafoDistancia.getInfoVertex(i);
//					Coordinate v2=(Coordinate) grafoDistancia.getInfoVertex(j);
//
//					if(v1!=null&&v2!=null&&v1.getMOVEMENT_ID()==v2.getMOVEMENT_ID())
//					{
//						promedio=10;
//					}
//					else
//					{
//						promedio=100;
//					}
//				}
//				
//
//				//Costo 3
//
//				double velocidad=haversine/promedio;
//
////				//Cambiar costos
////				
////				grafoTiempo.setInfoArc(i, j, promedio);
////				grafoVelocidad.setInfoArc(i, j, velocidad);
//
//				j++;
//			}
//			i++;
//		}

//	public void reqC1() 
//	{
//		Queue<Vertex> q= new Queue<Vertex>();
//		Queue<Integer> movements= new Queue<Integer>();
//		
//		Iterator<Interseccion> iterZonas= zonasUber.iterator();
//		
//		while(iterZonas.hasNext())
//		{
//			Interseccion actual= iterZonas.next();
//			
//			int zona=actual.getMOVEMENT_ID();
//			
//			if(!estaEnQueue(zona, movements))
//			{
//				movements.enqueue(zona);
//				Coordinate c= new Coordinate(actual.getLatitud(), actual.getLongitud(), actual.getMOVEMENT_ID());
//				Vertex v= new Vertex(actual.getId(), c);
//				q.enqueue(v);
//			}
//		}
//		try {
//			generarMapaC1(q);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(zonasUber.size());
//		System.out.println(q.size());
//		
//		
//		
//	}
//	
//	private void generarMapaC1(Queue<Vertex> q) throws Exception
//	{
//		File pre= new File("./data/HTML/pre.html");
//		FileReader frPre= new FileReader(pre);
//		BufferedReader brPre= new BufferedReader(frPre);
//
//		File post= new File("./data/HTML/post.html");
//		FileReader frPost= new FileReader(post);
//		BufferedReader brPost= new BufferedReader(frPost);
//
//
//		File escritura = new File ("./data/HTML/ReqC1.html");
//		PrintWriter pr = new PrintWriter(escritura);
//
//		String lineaPre= brPre.readLine();
//
//		while(lineaPre!=null)
//		{
//			pr.println(lineaPre);
//
//			lineaPre=brPre.readLine();
//		}
//		
//		brPre.close();
//
//		Iterator iter= q.iterator();
//		
//		pr.println("line = [");
//		StringJoiner joiner= new StringJoiner(",");
//
//		while(iter.hasNext())
//		{
//			Vertex v1= (Vertex) iter.next();
//			Coordinate c= (Coordinate) v1.getValue();
//
//			if(c!=null)
//			{
//
//				double lat1= c.latitude;
//				double lon1=c.longitude;
//
//
//				String s1= "{ lat: "+lat1+" , lng: "+lon1+"}";
//
//				joiner.add(s1);
//
//			}
//
//		}
//		pr.println(joiner.toString());
//
//		pr.println("]; path = new google.maps.Polyline({path: line, strokeColor: '#FF0000', strokeWeight: 2 }); path.setMap(map);");
//
//
//		String lineaPost= brPost.readLine();
//
//		while(lineaPost!=null)
//		{
//			pr.println(lineaPost);
//
//			lineaPost=brPost.readLine();
//		}
//		
//		brPost.close();
//		pr.close();
//
//		File htmlFile = escritura;
//		try {
//			Desktop.getDesktop().browse(htmlFile.toURI());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}

}



