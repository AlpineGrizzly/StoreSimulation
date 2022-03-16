
public class MySortedList<E extends Comparable<E> > {

	   public static class Node<E>
	   {
	      /** the element held in the node */
	      E element;
	      /** the next node */
	      Node<E> next;

	      /**
	       * create a new list node
	       *
	       * @param element the object to hold
	       */
	      public Node(final E element)
	      {
	         this.element = element;
	         this.next = null;
	      }
	   }

	   /** the start of the list */
	   private Node<E> head;
	   /** the size of the list */
	   private int size = 0;
	   /** the end of the list */
	   private Node<E> tail;

	   /** Create an empty list */
	   public MySortedList() 
	   {
	      this.head = null;
	      this.tail = null;
	      size = 0;
	   }
	   
	   public void add(E e) {
		   Node <E> temp = new Node<E>(e);
		  

		   if(head == null) {
			   head = temp;
			   tail = temp;
			   size++;
		   } else if(e.compareTo(head.element) < 0) {
			   temp.next = head;
			   head = temp;
			   size++;
		   } else {
			   Node <E> iterator = head;
			   while(iterator.next != null && iterator.next.element.compareTo(e) < 0) {
				   iterator = iterator.next;
			   }
			   temp.next = iterator.next;
			   iterator.next = temp;
			   size++;
		   	}
		   
	   }
	   /** Clear the list */
	   public void clear()
	   {
	      size = 0;
	      head = tail = null;
	   }

	   /**
	    * Return true if this list contains the element e
	    *
	    * @param  e the element to find
	    * @return   {@code true} if the element exists in the list, {@code false} otherwise
	    */
	   public boolean contains(final E e)
	   {
	      boolean found = false;
	      if (size == 0)
	      {
	         return false;
	      }
	      Node<E> current = head;
	      while (current != null && !found)
	      {
	         if (current.element == e)
	         {
	            found = true;
	         }
	         else
	         {
	            current = current.next;
	         }
	      }
	      return found;
	   }

	   /**
	    * Return the head element in the list
	    *
	    * @param  index the offset of the element to return
	    * @return       the element at the geven index in the list, or {@code null} if not found
	    */
	   public E get(final int index)
	   {
	      if (index < 0 || index >= size)
	      {
	         return null;
	      }
	      else if (index == 0)
	      {
	         return getFirst();
	      }
	      else if (index == size - 1)
	      {
	         return getLast();
	      }
	      else
	      {
	         Node<E> current = head.next;
	         for (int i = 1; i < index; i++)
	         {
	            current = current.next;
	         }

	         return current.element;
	      }
	   }

	   /**
	    * Return the head element in the list
	    *
	    * @return the first element in the list
	    */
	   public E getFirst()
	   {
	      if (size == 0)
	      {
	         return null;
	      }
	      return head.element;
	   }

	   /**
	    * Return the last element in the list
	    *
	    * @return the last element in the list
	    */
	   public E getLast()
	   {
	      if (size == 0)
	      {
	         return null;
	      }
	      return tail.element;
	   }

	   /**
	    * check is the list is empty
	    *
	    * @return {@code true} if the list is empty, or {@code false} otherwise
	    */
	   public boolean isEmpty()
	   {
	      return size == 0;
	   }

	   /**
	    * Remove the element at the specified position in this list. Return the element that was removed from the list.
	    *
	    * @param  index the index of the element to remove
	    * @return       the element that was removed, or {@code null} if it was not found
	    */
	   public E remove(final int index)
	   {
	      if (index < 0 || index >= size)
	      {
	         return null;
	      }
	      else if (index == 0)
	      {
	         return removeFirst();
	      }
	      else if (index == size - 1)
	      {
	         return removeLast();
	      }
	      else
	      {
	         Node<E> previous = head;

	         for (int i = 1; i < index; i++)
	         {
	            previous = previous.next;
	         }

	         final Node<E> current = previous.next;
	         previous.next = current.next;
	         size--;
	         return current.element;
	      }
	   }

	   /**
	    * Remove the head node and return the object that is contained in the removed node.
	    *
	    * @return the first element of the list, or {@code null} if list is empty
	    */
	   public E removeFirst()
	   {
	      if (size == 0)
	      {
	         return null;
	      }
	      final Node<E> temp = head;
	      head = head.next;
	      size--;
	      if (head == null)
	      {
	         tail = null;
	      }
	      return temp.element;
	   }

	   /**
	    * Remove the last node and return the object that is contained in the removed node.
	    *
	    * @return the last element of the list
	    */
	   public E removeLast()
	   {
	      if (size == 0)
	      {
	         return null;
	      }
	      else if (size == 1)
	      {
	         final Node<E> temp = head;
	         head = tail = null;
	         size = 0;
	         return temp.element;
	      }
	      else
	      {
	         Node<E> current = head;

	         for (int i = 0; i < size - 2; i++)
	         {
	            current = current.next;
	         }

	         final Node<E> temp = tail;
	         tail = current;
	         tail.next = null;
	         size--;
	         return temp.element;
	      }
	   }

	   /**
	    * Replace the element at the specified position in this list with the specified element.
	    *
	    * @param  index the index of the element to change
	    * @param  e     the element to put into the list
	    * @return       the old element at that location
	    */
	   public E set(final int index, final E e)
	   {
	      if (size == 0 || index < 0 || index >= size)
	      {
	         return null;
	      }
	      Node<E> current = head;
	      // move to node specified
	      for (int i = 0; i < index; i++)
	      {
	         current = current.next;
	      }
	      final E replacedItem = current.element;
	      current.element = e;
	      return replacedItem;
	   }

	   /**
	    * reutrn the number of items in the list
	    *
	    * @return the number of items in the list
	    */
	   public int size()
	   {
	      return size;
	   }

	   /** Override toString() to return elements in the list */
	   @SuppressWarnings("null")
	   @Override
	   public String toString()
	   {
	      final StringBuilder result = new StringBuilder("[");

	      Node<E> current = head;
	      for (int i = 0; i < size; i++)
	      {
	         result.append(current.element);
	         current = current.next;
	         if (current != null)
	         {
	            result.append(", "); // Separate two elements with a comma
	         }
	      }
	      result.append("]"); // Insert the closing ] in the string

	      return result.toString();
	   }


}
