package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase que representa una cola de elementos T.
 * @param <T> Tipo del cual ser� la cola.
 */
public class Queue <T> implements Iterable<T>
{
	// Atributos

	/**
	 * Atributo que representa el primer nodo de la cola. 
	 */
	private Node<T> primero;

	/**
	 * Atributo que representa el �ltimo nodo de la cola.
	 */
	private Node<T> ultimo;

	/**
	 * Atributo que representa el tama�o de la cola. 
	 */
	private int tamano;

	// Constructor

	/**
	 * Construye la cola de elementos.
	 */
	public Queue()
	{
		primero = null;
		ultimo = null;
		tamano = 0;
	}

	// M�todos

	/**
	 * @return True si la cola est� vac�a, false de lo contrario.
	 */
	public boolean isEmpty() 
	{
		return tamano == 0;
	}

	/**
	 * Retorna el tamaño de la lista
	 */
	public int size() 
	{
		return tamano;
	}

	/**
	 * Agrega el elemento al final de la cola.
	 */
	public void enqueue(T t) 
	{
		Node<T> temp = new Node<T>(t);
		if(!isEmpty())
		{
			ultimo.setNext(temp);
			ultimo = temp;
			tamano++;
		}
		else
		{
			primero = temp;
			ultimo = temp;
			tamano++;
		}
	}

	/**
	 * Retorna y elimina el primer elemento de la cola si existe.
	 * @return El primero elemento de la cola, null si est� vac�a.
	 */
	public T dequeue() 
	{
		T ans = null;
		if(primero != null)
		{
			ans = primero.get();
			primero = primero.next();
			tamano--;
		}
		return ans;
	}
	
	/**
	 * Retorna el iterador de los elementos de la cola. 
	 */
	public Iterator<T> iterator() 
	{
		return new IteratorLista<>(primero);
	}

	/**
	 * Clase que representa el iterador sobre la cola.
	 * @author Daniel del Castillo A.
	 * @param <T> Tipo del cual ser� el iterador.
	 */
	private class IteratorLista<T> implements Iterator<T>
	{
		// Atributos

		/**
		 * Atributo que representa el pr�ximo nodo del iterador.
		 */
		private Node<T> proximo;

		// Constructor

		/**
		 * Construye el iterador de la lista encadenada. 
		 * @param primero
		 */
		public IteratorLista(Node<T> primero)
		{
			proximo = primero;
		}

		// M�todos

		@Override
		/**
		 * Revisa si hay un pr�ximo nodo por visitar. 
		 */
		public boolean hasNext() 
		{
			return proximo != null;
		}

		@Override
		/**
		 * Retorna el pr�ximo elemento gen�rico y actualiza el pr�ximo nodo.
		 */
		public T next() 
		{
			if(proximo == null)
				throw new NoSuchElementException("No hay pr�ximo.");
			T elem = proximo.get();
			proximo = proximo.next();
			return elem;
		}
	}

	/**
	 * Clase auxiliar que representa un nodo con un elemento y un nodo siguiente.
	 * @param <T> Tipo del cual ser� el nodo.
	 */
	private class Node<T> implements Comparable<T>
	{
		// Atributos

		/**
		 * Elemento gen�rico del nodo.
		 */
		private T elemento;

		/**
		 * Siguiente elemento gen�rico del nodo. 
		 */
		private Node<T> siguiente;

		// Constructor

		/**
		 * Construye el nodo para el elemento gen�rico. Siguiente = null. 
		 * @param pElemento elemento gen�rico a asignar dentro del nodo. 
		 */
		public Node(T pElemento)
		{
			elemento = pElemento;
			siguiente = null;
			invariant();
		}

		// M�todos

		/**
		 * @return el elemento gen�rico asignado al nodo.
		 */
		public T get()
		{
			return elemento;
		}

		/**
		 * @return el siguiente nodo del actual nodo.
		 */
		public Node<T> next()
		{
			return siguiente;
		}

		/**
		 * Cambia el siguiente nodo del nodo actual por el dado por par�metro.
		 * @param pSiguiente Nodo a asignar como siguiente. 
		 */
		public void setNext(Node<T> pSiguiente)
		{
			siguiente = pSiguiente;
		}

		// Invariante

		/**
		 * Contrato que se asegura de que no se agregue un elemento null.
		 */
		private void invariant()
		{
			assert elemento != null : "El elemento no puede ser null.";
		}

		@Override
		/**
		 * Comparaci�n natural para nodos. Funciona por igualdad.
		 * @return 0 si los nodos son iguales, 1 si son distintos.
		 */
		public int compareTo(T pNode) 
		{
			return this.equals(pNode) ? 0 : 1;
		}
	}
	public Object peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return primero.elemento;
    }
	public Object bottom() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return ultimo.elemento;
    }
}
