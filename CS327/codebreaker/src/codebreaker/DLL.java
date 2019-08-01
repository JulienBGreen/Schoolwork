package codebreaker;

public class DLL<T> {
	public DLLNode<T> root;
	
	public DLL(DLLNode<T> root){
		this.root = root;
	}
	
	public void switchNodes(DLLNode<T> first, DLLNode<T> second, DLLNode<T>[] index){
		DLLNode<T> secondTempNext = second.next;
		DLLNode<T> secondTempPrev = second.prev;
		int tempSecondIndex = second.arrayIndex;
		second.arrayIndex = first.arrayIndex;
		first.arrayIndex = tempSecondIndex;
		second.next = first.next;
		second.prev = first.prev;
		first.next = secondTempNext;
		first.prev = secondTempPrev;
		//TODO SWITCH POINTERS IN INDEX ARRAY
		index[first.arrayIndex]=first;
		index[second.arrayIndex]=second;
	}
	
	
	
}
