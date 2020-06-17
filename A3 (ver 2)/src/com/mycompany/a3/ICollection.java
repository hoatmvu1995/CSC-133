package com.mycompany.a3;

public interface ICollection<E> {
	public void add(E element);
	public IIterator<E> getIterator();
}
