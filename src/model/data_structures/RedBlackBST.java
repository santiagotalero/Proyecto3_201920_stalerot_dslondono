package model.data_structures;

import java.util.NoSuchElementException;


/**
 * Tomado de Algorithms: 4th Ed
 * Represents a red-black binary search tree.
 * @param <Key> The key's type.
 * @param <Value> The value's type.
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> implements IOrderedSymbolTable<Key, Value>
{
	// Constants 

	/**
	 * Constant that represents a red node.
	 */
	private static final boolean RED = true;

	/**
	 * Constant that represents a black node.
	 */
	private static final boolean BLACK = false;

	// Attributes

	/**
	 * Represents the tree's root.
	 */
	private Node root;

	// Constructor

	/**
	 * Creates an red-black binary search tree object.
	 */
	public RedBlackBST() 
	{ 
		root= null;

	}

	// Methods

	/**
	 * @param node The node to check if it's red.
	 * @return True if the node is red, false if contrary.
	 */
	private boolean isRed(Node node)
	{ return node == null ? false : node.color == RED; }

	/**
	 * @param node The node to check the size.
	 * @return The node's size. size >= 0 (0 if null).
	 */
	private int size(Node node)
	{ return node == null ? 0 : node.size; }

	/**
	 * @return The tree's size. size >= 0.
	 */
	public int size()
	{ return size(root); }

	/**
	 * @return True if the tree is empty, false if contrary.
	 */
	public boolean isEmpty()
	{ return root == null; }

	/**
	 * @param key The key of the value to retrieve.
	 * @return The value of the key-pair couple, null if it didn't find it.
	 */
	public Value get(Key key) 
	{
		if(key == null) 
			throw new IllegalArgumentException("argument to get() is null");
		return get(root, key);
	}

	/**
	 * @param node The node to check for the key-value pair.
	 * @param key The key of the key-value couple.
	 * @return The value if it's found, null if not.
	 */
	private Value get(Node node, Key key) 
	{
		while(node != null) 
		{
			int cmp = key.compareTo(node.key);
			if(cmp < 0) 
				node = node.left;
			else if(cmp > 0) 
				node = node.right;
			else              
				return node.value;
		}
		return null;
	}

	/**
	 * Rettona la altura del camino desde la raiz hasta la llave dado por parámetro.
	 * @param key
	 * @return altura del camino, -1 si la llave no esta en el árbol.
	 */
	public int getHeight(Key key)
	{
		if (root.getKey().compareTo(key)==0)
		{
			return 0;
		}
		else 
		{
			Node node = buscar(key);
			if(node!=null)
			{
				return height(root)-height(node);
			}
			else
			{
				return -1;
			}
		}

	}

	private Node buscar(Key key)
	{
		Node node= root;
		while(node != null) 
		{
			int cmp = key.compareTo(node.key);
			if(cmp < 0) 
				node = node.left;
			else if(cmp > 0) 
				node = node.right;
			else              
				return node;
		}
		return null;


	}

	/**
	 * @param key The key of the key-value pair.
	 * @return True if the key is in the tree, false if contrary.
	 */
	public boolean contains(Key key)
	{ return get(key) != null; }

	/**
	 * @param key The key of the key-value pair.
	 * @param val The value of the key-value pair.
	 */
	public void put(Key key, Value val) 
	{
		if(key == null || val == null) 
			throw new IllegalArgumentException("first argument to put() is null");
		if(val == null) 
		{
			delete(key);
			return;
		}
		root = put(root, key, val);
		root.color = BLACK;
	}

	/**
	 * @param node The node to check for insert of the key-value pair.
	 * @param key The key of the key-value pair.
	 * @param value The value of the key-value pair.
	 * @return The node that contains (or checks) the key-value pair. 
	 */
	private Node put(Node node, Key key, Value value) 
	{ 
		if(node == null) 
			return new Node(key, value, RED, 1);
		int cmp = key.compareTo(node.key);
		if(cmp < 0) 
			node.left  = put(node.left,  key, value); 
		else if(cmp > 0) 
			node.right = put(node.right, key, value); 
		else              
			node.value = value;
		if(isRed(node.right) && !isRed(node.left))      
			node = rotateLeft(node);
		if(isRed(node.left)  &&  isRed(node.left.left)) 
			node = rotateRight(node);
		if(isRed(node.left)  &&  isRed(node.right))     
			flipColors(node);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * Deletes the minimum key.
	 */
	public void deleteMin() 
	{
		if(isEmpty()) 
			throw new NoSuchElementException("BST underflow");
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMin(root);
		if(!isEmpty()) 
			root.color = BLACK;
	}

	/**
	 * Deletes the mininum key.
	 * @param node Node to check for the minimum key.
	 * @return The node that contains (or checks) the minumum key.
	 */
	private Node deleteMin(Node node) 
	{ 
		if(node.left == null)
			return null;
		if(!isRed(node.left) && !isRed(node.left.left))
			node = moveRedLeft(node);
		node.left = deleteMin(node.left);
		return balance(node);
	}

	/**
	 * Deletes the maximum key.
	 */
	public void deleteMax() 
	{
		if(isEmpty()) 
			throw new NoSuchElementException("BST underflow");
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMax(root);
		if(!isEmpty()) 
			root.color = BLACK;
	}

	/**
	 * Deletes the maximum key.
	 * @param node Node to check for the maximum key.
	 * @return The node that contains (or checks) the maximum key.
	 */
	private Node deleteMax(Node node) 
	{ 
		if(isRed(node.left))
			node = rotateRight(node);
		if(node.right == null)
			return null;
		if(!isRed(node.right) && !isRed(node.right.left))
			node = moveRedRight(node);
		node.right = deleteMax(node.right);
		return balance(node);
	}

	/**
	 * Deletes the key-value pair of the tree.
	 * @param key The key of the key-value pair.
	 */
	public void delete(Key key) 
	{ 
		if(key == null) 
			throw new IllegalArgumentException("argument to delete() is null");
		if(!contains(key)) 
			return;
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = delete(root, key);
		if(!isEmpty()) 
			root.color = BLACK;
	}

	/**
	 * Deletes the key-value pair of the tree.
	 * @param node The node that contains (or checks) for the key-value pair.
	 * @param key The key of the key-value pair.
	 * @return The node that contains (or checks) for the key-value pair.
	 */
	private Node delete(Node node, Key key) 
	{ 
		if(key.compareTo(node.key) < 0)  
		{
			if(!isRed(node.left) && !isRed(node.left.left))
				node = moveRedLeft(node);
			node.left = delete(node.left, key);
		}
		else 
		{
			if(isRed(node.left))
				node = rotateRight(node);
			if(key.compareTo(node.key) == 0 && (node.right == null))
				return null;
			if(!isRed(node.right) && !isRed(node.right.left))
				node = moveRedRight(node);
			if(key.compareTo(node.key) == 0) 
			{
				Node temp = min(node.right);
				node.key = temp.key;
				node.value = temp.value;
				node.right = deleteMin(node.right);
			}
			else node.right = delete(node.right, key);
		}
		return balance(node);
	}

	// Auxiliar methods

	/**
	 * Rotates the right children of the node.
	 * @param node Node to apply the rotation.
	 * @return The node after the rotation.
	 */
	private Node rotateRight(Node node) 
	{
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		temp.color = temp.right.color;
		temp.right.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return temp;
	}

	/**
	 * Rotates the left children of the node.
	 * @param node Node to apply the rotation.
	 * @return The node after the rotation.
	 */
	private Node rotateLeft(Node node) 
	{
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		temp.color = temp.left.color;
		temp.left.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return temp;
	}

	/**
	 * Flips the colors of the node's grandchildren.
	 * @param node Node to flip the colors.
	 */
	private void flipColors(Node node)
	{
		node.color = !node.color;
		node.left.color = !node.left.color;
		node.right.color = !node.right.color;
	}

	/**
	 * Moves the red node to the left.
	 * @param node Node to apply the movement to.
	 * @return The node after the movement.
	 */
	private Node moveRedLeft(Node node) 
	{
		flipColors(node);
		if(isRed(node.right.left)) 
		{ 
			node.right = rotateRight(node.right);
			node = rotateLeft(node);
			flipColors(node);
		}
		return node;
	}

	/**
	 * Moves the red node to the right.
	 * @param node Node to apply the movement to.
	 * @return The node after the movement.
	 */
	private Node moveRedRight(Node node) 
	{
		flipColors(node);
		if(isRed(node.left.left)) 
		{ 
			node = rotateRight(node);
			flipColors(node);
		}
		return node;
	}

	/**
	 * Balances the binary tree.
	 * @param node Node to apply the balance to.
	 * @return The node after the balancing.
	 */
	private Node balance(Node node) 
	{
		if(isRed(node.right))                      
			node = rotateLeft(node);
		if(isRed(node.left) && isRed(node.left.left)) 
			node = rotateRight(node);
		if(isRed(node.left) && isRed(node.right))     
			flipColors(node);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * @return The tree's height. height >= 0.
	 */
	public int height()
	{ return height(root); }

	/**
	 * @param node The node whose height is checked.
	 * @return The node's height. height >= 0.
	 */
	private int height(Node node)
	{ 
		if (node == null )
			return -1;
		else
		{
			if(node.getKey().compareTo(root.getKey())==0)
				return  (node.left ==null&& node.right==null)? 0:Math.max(height(node.left), height(node.right));
			else
				return 1 +  Math.max(height(node.left), height(node.right));

		}
	}

	/**
	 * @return The minimum key.
	 */
	public Key min() 
	{
		if(isEmpty()) 
			return null;
		return min(root).key;
	} 

	/**
	 * @param node Node to check for the minimum key.
	 * @return The minimum key if it exists. 
	 */
	private Node min(Node node)
	{ return node.left == null ? node : min(node.left); }

	/**
	 * @return The maximum key.
	 */
	public Key max() 
	{
		if(isEmpty()) 
			return null;
		return max(root).key;
	} 

	/**
	 * @param node Node to check for the maximum key.
	 * @return The maximum key if it exists.
	 */
	private Node max(Node node)
	{ return node.right == null ? node : max(node.right); }

	/**
	 * @param key Key to check for the floor of.
	 * @return The floor of the key.
	 */
	public Key floor(Key key) 
	{
		if(key == null) 
			throw new IllegalArgumentException("argument to floor() is null");
		if(isEmpty()) 
			throw new NoSuchElementException("calls floor() with empty symbol table");
		Node temp = floor(root, key);
		if(temp == null) 
			return null;
		else           
			return temp.key;
	}

	/**
	 * @param node Node to check the floor key.
	 * @param key Key to check for the floor of.
	 * @return The node than contains the key's floor (if it exists).
	 */
	private Node floor(Node node, Key key) 
	{
		if(node == null) 
			return null;
		int cmp = key.compareTo(node.key);
		if(cmp == 0) 
			return node;
		if(cmp < 0)  
			return floor(node.left, key);
		Node temp = floor(node.right, key);
		if(temp != null) 
			return temp; 
		else           
			return node;
	}

	/**
	 * @param key Key to check the ceiling of.
	 * @return The ceiling of the key.
	 */
	public Key ceiling(Key key) 
	{
		if(key == null) 
			throw new IllegalArgumentException("argument to ceiling() is null");
		if(isEmpty()) 
			throw new NoSuchElementException("calls ceiling() with empty symbol table");
		Node node = ceiling(root, key);
		if(node == null) 
			return null;
		else           
			return node.key;  
	}

	/**
	 * @param node Node to check the ceiling key.
	 * @param key Key to check for the ceiling of.
	 * @return The node than contains the key's ceiling (if it exists). 
	 */
	private Node ceiling(Node node, Key key) 
	{  
		if(node == null) 
			return null;
		int cmp = key.compareTo(node.key);
		if(cmp == 0) 
			return node;
		if(cmp > 0)  
			return ceiling(node.right, key);
		Node temp = ceiling(node.left, key);
		if(temp != null) 
			return temp; 
		else           
			return node;
	}

	/**
	 * @param k The number of the subkeys to check.
	 * @return The key assigned to the given index.
	 */
	public Key select(int k) 
	{
		if(k < 0 || k >= size()) 
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		Node node = select(root, k);
		return node.key;
	}

	/**
	 * @param node Node to check for the key of.
	 * @param k The number of the subkeys to check.
	 * @return The Node that contains (or checks) for the indexed key.
	 */
	private Node select(Node node, int k) 
	{
		int temp = size(node.left); 
		if(temp > k) 
			return select(node.left,  k); 
		else if(temp < k)
			return select(node.right, k-temp-1); 
		else            
			return node; 
	} 

	/**
	 * @param key The key to check for.
	 * @return the numbers of keys in the left subtree if equal, or the numbers of 
	 * keys of the children that is equal to the given key.
	 */
	public int rank(Key key) 
	{
		if(key == null) 
			throw new IllegalArgumentException("argument to rank() is null");
		return rank(root, key);
	}

	/**
	 * @param node Node to check for the rank of the key.
	 * @param key Key to check for the rank of.
	 * @return The numbers of keys in the subtree.
	 */
	private int rank(Node node, Key key) 
	{
		if(node == null) 
			return 0; 
		int cmp = key.compareTo(node.key); 
		if(cmp < 0) 
			return rank(node.left, key); 
		else if(cmp > 0) 
			return 1 + size(node.left) + rank(node.right, key); 
		else              
			return size(node.left); 
	} 

	/**
	 * @return A Iterable Queue with the keys. 
	 */
	public Iterable<Key> keys() 
	{
		if(isEmpty()) 
			return new Queue<Key>();
		return keysInRange(min(), max());
	}

	/**
	 * @param lo Lowest key.
	 * @param hi Highest key.
	 * @return A Iterable Queue with the keys that fit the ranged parameter.
	 */
	public Iterable<Key> keysInRange(Key lo, Key hi) 
	{
		if(lo == null) 
			throw new IllegalArgumentException("first argument to keys() is null");
		if(hi == null) 
			throw new IllegalArgumentException("second argument to keys() is null");
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue, lo, hi);
		return queue;
	}

	/**
	 * @param node Node to check if contains the lowest key.
	 * @param queue The Queue to add the keys to.
	 * @param lo The lowest key.
	 * @param hi The highest key.
	 */
	private void keys(Node node, Queue<Key> queue, Key lo, Key hi) 
	{ 
		if(node == null) 
			return; 
		int cmplo = lo.compareTo(node.key); 
		int cmphi = hi.compareTo(node.key); 
		if(cmplo < 0) 
			keys(node.left, queue, lo, hi); 
		if(cmplo <= 0 && cmphi >= 0) 
			queue.enqueue(node.key); 
		if(cmphi > 0)
			keys(node.right, queue, lo, hi); 
	}
	/**
	 * @param lo Lowest key.
	 * @param hi Highest key.
	 * @return A Iterable Queue with the values that fit the ranged parameter.
	 */
	public Iterable<Value> valuesInRange(Key lo, Key hi) 
	{
		if(lo == null) 
			throw new IllegalArgumentException("first argument to keys() is null");
		if(hi == null) 
			throw new IllegalArgumentException("second argument to keys() is null");
		Queue<Value> queue = new Queue<Value>();
		values(root, queue, lo, hi);
		return queue;
	}

	/**
	 * @param node Node to check if contains the lowest key.
	 * @param queue The Queue to add the value to.
	 * @param lo The lowest key.
	 * @param hi The highest key.
	 */
	private void values(Node node, Queue<Value> queue, Key lo, Key hi) 
	{ 
		if(node == null) 
			return; 
		int cmplo = lo.compareTo(node.key); 
		int cmphi = hi.compareTo(node.key); 
		if(cmplo < 0) 
			values(node.left, queue, lo, hi); 
		if(cmplo <= 0 && cmphi >= 0) 
			queue.enqueue(node.value); 
		if(cmphi > 0)
			values(node.right, queue, lo, hi); 
	}

	/**
	 * @param lo Lowest key.
	 * @param hi Highest key.
	 * @return The size of the subtree between the given keys.
	 */
	public int size(Key lo, Key hi) 
	{
		if(lo == null) 
			throw new IllegalArgumentException("first argument to size() is null");
		if(hi == null) 
			throw new IllegalArgumentException("second argument to size() is null");
		if(lo.compareTo(hi) > 0) 
			return 0;
		if(contains(hi)) 
			return rank(hi) - rank(lo) + 1;
		else              
			return rank(hi) - rank(lo);
	}

	// Invariant

	/**
	 * @return true if the tree is alright, false if contrary.
	 */
	public boolean check() 
	{
		if(!isBST())           
			System.out.println("Not in symmetric order");
		if(!isSizeConsistent()) 
			System.out.println("Subtree counts not consistent");
		if(!isRankConsistent()) 
			System.out.println("Ranks not consistent");
		if(!is23())             
			System.out.println("Not a 2-3 tree");
		if(!isBalanced())       
			System.out.println("Not balanced");
		return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
	}

	/**
	 * @return True if the tree is a binary search tree, false if contrary.
	 */
	private boolean isBST() 
	{ return isBST(root, null, null); }

	/**
	 * @param node The node to check.
	 * @param min The minimum key.
	 * @param max The maximum key.
	 * @return true if the tree is a balanced search tree, false if contrary.
	 */
	private boolean isBST(Node node, Key min, Key max) 
	{
		if(node == null) 
			return true;
		if(min != null && node.key.compareTo(min) <= 0) 
			return false;
		if(max != null && node.key.compareTo(max) >= 0) 
			return false;
		return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
	}

	/**
	 * @return True if the tree's size is consistent, false if contrary.
	 */
	private boolean isSizeConsistent() 
	{ return isSizeConsistent(root); }

	/**
	 * @param node The node to check.
	 * @return True if the node is consistent, false if contrary.
	 */
	private boolean isSizeConsistent(Node node) 
	{
		if(node == null) 
			return true;
		if(node.size != size(node.left) + size(node.right) + 1) 
			return false;
		return isSizeConsistent(node.left) && isSizeConsistent(node.right);
	}

	/**
	 * @return True if the rank is consistent, false if contrary.
	 */
	private boolean isRankConsistent() 
	{
		for (int i = 0; i < size(); i++)
			if(i != rank(select(i))) 
				return false;
		for (Key key : keys())
			if(key.compareTo(select(rank(key))) != 0) 
				return false;
		return true;
	}

	/**
	 * @return True if the tree is 2-3, false if contrary.
	 */
	private boolean is23() 
	{ return is23(root); }

	/**
	 * @param node Node to check.
	 * @return True if the tree is 2-3, false if contrary.
	 */
	private boolean is23(Node node) 
	{
		if(node == null) 
			return true;
		if(isRed(node.right)) 
			return false;
		if(node != root && isRed(node) && isRed(node.left))
			return false;
		return is23(node.left) && is23(node.right);
	}

	/**
	 * @return True if the tree is balanced, false if contrary.
	 */
	private boolean isBalanced() 
	{ 
		int black = 0;     
		Node node = root;
		while (node != null) 
		{
			if(!isRed(node)) 
				black++;
			node = node.left;
		}
		return isBalanced(root, black);
	}

	/**
	 * @param node Node to check.
	 * @param black The value of the tree.
	 * @return True if the tree is balanced, false if contrary.
	 */
	private boolean isBalanced(Node node, int black) 
	{
		if(node == null) 
			return black == 0;
		if(!isRed(node)) 
			black--;
		return isBalanced(node.left, black) && isBalanced(node.right, black);
	}
	
	/**
	 * Daña el árbol para que todos los nodos de la izqueirda ya no sean menores
	 * Pre: El árbol no esta vació y hay al menos tres niveles.
	 */
	public void fallarIzqMenor()
	{
		Node tem = root.right;
		root.right= tem.left;
		tem.left=tem;
	}
	
	public Iterable<Value> hojas()
	{
		Queue<Value> hojas = new Queue<Value>();
	
		hojas(root, hojas);
		
		return hojas;
	}


	/**
	 * @param node The node whose height is checked.
	 * @return The node's height. height >= 0.
	 */
	private void hojas(Node node, Queue<Value> lista)
	{ 
		if(node.left==null && node.right==null)
		{
			lista.enqueue(node.value);
		}
		else
		{
			if(node.left != null)
			{
				hojas(node.left,lista);
			}
			if(node.right != null)
			{
				hojas(node.right,lista);
			}
			
			
		}
	}
	

	// Node class

	/**
	 * Represents the node of the tree.
	 * @author Daniel del Castillo A.
	 */
	private class Node
	{
		// Attributes

		/**
		 * The key's value.
		 */
		private Key key;

		/**
		 * The node's value.
		 */
		private Value value;

		/**
		 * The node's children.
		 */
		private Node left, right;

		/**
		 * The node's color.
		 */
		private boolean color;

		/**
		 * The node's subtree size.
		 */
		private int size;

		// Constructor

		/**
		 * Creates a node for the red-black binary search tree.
		 * @param pKey The key of the key-value pair.
		 * @param pVal The value of the key-value pair.
		 * @param pColor Boolean indicating whether it's a red or black node.
		 * @param pSize The size of the node. 
		 */
		public Node(Key pKey, Value pVal, boolean pColor, int pSize)
		{
			this.key = pKey;
			this.value = pVal;
			this.color = pColor;
			this.size = pSize;
		}

		public Key getKey() 
		{
			return key;
		}
	}
}
