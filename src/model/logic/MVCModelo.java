package model.logic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;

import model.data_structures.Arc;
import model.data_structures.ArrayList;
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
	private Graph grafo; 
	private Queue<TravelTime> tiemposDeViaje;
	private Queue<Interseccion> zonasUber;
	private int tamano;
	private Dijkstra dijkstra; 

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		grafo= new Graph();
		tiemposDeViaje= new Queue<TravelTime>();
		zonasUber= new Queue<Interseccion>();
		tamano=0;

		dijkstra = new Dijkstra(grafo0, 0);
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


			grafo.addVertex(vertice.getId(), vertice.getValue());

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

			grafo.addEdge(actual.getOrigen(), actual.getDestino(), actual.getHaversine());

			j++;
		}

		System.out.println("El número de vertices cargado fue:"+ grafo.numberOfVertex()+" , y el número de arcos cargado fue:" + grafo.numberOfArcs());

		File htmlFile = new File("marca.com");
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cambiarCostos() throws Exception
	{	
		cargarArchivosViajesZonas();

		int i=0;

		while(i<grafo.numberOfArcs())
		{
			int j=0;

			while(j<grafo.numberOfArcs())
			{

				//Costo 1
				Double haversine=0.0;

				if(grafo.getInfoArc(i, j)!=null)
				{
					haversine=(Double) grafo.getInfoArc(i, j);
				}


				//Costo 2
				double totalTiempos=0;
				int numeroTiempos=0;

				Iterator<TravelTime> iter= tiemposDeViaje.iterator();

				while(iter.hasNext())
				{
					TravelTime actual= iter.next();

					int origen=actual.getSourceID();
					int destino= actual.getDstID();

					if((origen==i && destino==j)||(origen==j&&destino==i))
					{
						totalTiempos += actual.getMeanTravelTime();
						numeroTiempos++;
					}

				}

				double promedio=0;

				if(numeroTiempos !=0)
				{
					promedio=(totalTiempos/numeroTiempos);
				}
				else
				{
					Coordinate v1=(Coordinate) grafo.getInfoVertex(i);
					Coordinate v2=(Coordinate) grafo.getInfoVertex(j);

					if(v1!=null&&v2!=null&&v1.getMOVEMENT_ID()==v2.getMOVEMENT_ID())
					{
						promedio=10;
					}
					else
					{
						promedio=100;
					}
				}

				//Costo 3

				double velocidad=haversine/promedio;

				//Cambiar costos

				//				double[] costos= new double[3];
				//				
				//				costos[0]= haversine;
				//				costos[1]= promedio;
				//				costos[2]= velocidad;



				EstructuraCostos costos= new EstructuraCostos(haversine,promedio,velocidad);
				System.out.println("h:"+haversine+ " t:"+ promedio+ " v:"+ velocidad);

				grafo.setInfoArc(i, j, (Comparable) costos);


				j++;
			}
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

		while(i<grafo.numberOfVertex())
		{
			Coordinate actual=(Coordinate) grafo.getInfoVertex(i);

			if(actual!=null)
			{
				Double lat1= actual.getLatitude();
				Double lon1= actual.getLongitude();


				double earthRadius = 6371;

				lat1 = Math.toRadians(lat1);
				lon1 = Math.toRadians(lon1);
				lat2 = Math.toRadians(lat2);
				lon2 = Math.toRadians(lon2);

				double dlon = (lon2 - lon1);
				double dlat = (lat2 - lat1);

				double sinlat = Math.sin(dlat / 2);
				double sinlon = Math.sin(dlon / 2);

				double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
				double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

				double Haversine = earthRadius * c * 1000;

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

	public Queue<Vertex> reqB1( long lat1, long lon1, long lat2, long lon2 )
	{
		return null; 
	}

	public Queue<Vertex> reqB2(long lat1, long lon2, int T )
	{
		return null;
	}

	public Graph reqB3() 
	{
		return null; 
	}  

	////////////////////////////////////////////////////////////////////////////
	///// PARTE C //////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	public Graph reqC1() 
	{
		return null; 
	}

	public Queue<Vertex> reqC2( int IDZonaOrigen, int IDZonaDestino) 
	{
		return null;		
	}

	public Queue<Queue<Vertex>> reqC3() 
	{
		return null; 
	}




}



