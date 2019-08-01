import java.io.*;
import java.util.*;


public class Main {
	static Control startVertex;
	//Starting vertex, global so that it can be accessed through recursion
	public static void main(String[] args){
		
		
		//time limit and penalty
		Graph graph = new Graph();
		//THE GRAPH
		double timeLimit, penalty, timeLeft;
		int pace = 5;
		//Didn't know where to input pace so I placed an arbitrary number there
		
		try{
			//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv  Set up of course file
			File file = new File("westpoint14-timeo.course");
			Scanner txtScanner = new Scanner(file); //Course file
			txtScanner.next();
			timeLimit = txtScanner.nextDouble();
			penalty = txtScanner.nextDouble();
			txtScanner.next();
			String tempStartName = txtScanner.next(); //name of start vertex
			txtScanner.next();
			txtScanner.next();
			startVertex = new Control(tempStartName, 0, 1, 0, (int)timeLimit );
			graph.insertVertex(startVertex);
			
			
			while(txtScanner.hasNext()){
				String tempName = txtScanner.next();
				int tempCode = txtScanner.nextInt();
				int tempPoints = txtScanner.nextInt();
				int tempStart = txtScanner.nextInt();
				int tempEnd = txtScanner.nextInt();
				Control tempControl = new Control(tempName, tempCode, 
						tempPoints, tempStart, tempEnd); 
				//after obtaining control info from text file, make temporary
				//control and place it in the graph
				graph.insertVertex(tempControl); 
			}
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			
			//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv Setup of Map file
			file = new File("westpoint14-timeo.map");
			txtScanner = new Scanner(file); //Map file
			txtScanner.nextLine();
			
			while(txtScanner.hasNext()){
				Control tempContA = (Control) graph.vertices.get(txtScanner.next());
				Control tempContB = (Control) graph.vertices.get(txtScanner.next());
				double tempDist = txtScanner.nextDouble();
				double tempRevDist = txtScanner.nextDouble();
				Path tempPath = new Path(tempContA, tempContB, tempDist, tempRevDist);
				graph.insertEdge(tempPath);
			}
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			
			Stack<Pair<Control, Integer>> currSolution = new Stack<Pair<Control, Integer>>();
			ArrayList<Pair<Control, Integer>> bestSolution = new ArrayList<Pair<Control,Integer>>();
			//a stack to keep the currently being found solution in order
			//and an array list that keeps the solution (Pair object made to associate time stamp
			//with control
			
			//Solve recursively solves the problem, the rest is out put handling
			Solve(startVertex, currSolution, graph, timeLimit, pace, bestSolution);
			String output = "";
			int score = 0;
			for(int i = 0; i < bestSolution.size(); i++){
				output += "Time visited: " + bestSolution.get(i).second + " Points: " +
						bestSolution.get(i).first.pointVal + " ID: " + bestSolution.get(i).first.id +
						" Code: " + bestSolution.get(i).first.code + "\n";
				score += bestSolution.get(i).first.pointVal;
			}
			output = "Time left: " + bestSolution.get(bestSolution.size()).first.code + 
					" Score " + score + "0\n" + output;
		}catch(FileNotFoundException e){
			System.out.println("No file given");
		}


	}

	/**
	 * recursive method
	 * What this is supposed to do is recurse through all possible sets of paths, but will
	 * prune the paths through the graph that dont make sense, ie the ones that will result in 
	 * going over the time limit or traveling to nodes already traveled to or traveling to
	 * not in the scorable time limit of that node, for some reason it only ever finds the first
	 * node and runs into stack overflow
	 * 
	 * @param c current vertex
	 * @param currSol currently being created solution
	 * @param graph The graph
	 * @param timeLeft time left in solution
	 * @param pace pace given
	 * @param bestSolution best solution found so far
	 */
	

	private static void Solve(Control c, Stack<Pair<Control, Integer>> currSol, Graph graph, double timeLeft, 
			int pace, ArrayList<Pair<Control, Integer>> bestSolution){
		
		if(c == startVertex && timeLeft <= 0){
			if(currSol.size() > bestSolution.size()){
				bestSolution = new ArrayList<Pair<Control, Integer>>(currSol);
				bestSolution.add(new Pair<Control, Integer>(
						new Control("timeLeft", (int)timeLeft, 0, 0, 0), 0));
			}
			return;
		}
		for(int count = 0; count < c.numEdges; count++){
			
			if( (timeLeft - ((c.edges.get(count).dist/1000)*pace)) > 0){
				Control possControl;
				if( c.edges.get(count).vertex1 == c)
					possControl = (Control) c.edges.get(count).vertex1;
				else
					possControl = (Control) c.edges.get(count).vertex2;
				
				double tempTime;
				if(c != startVertex){
					tempTime = (timeLeft - 
							possControl.edges.get(possControl.edges.indexOf(startVertex)).dist/1000);
				}else
					tempTime = timeLeft;
				
				if(tempTime >= 0 && tempTime >= possControl.openTime && tempTime <= possControl.closeTime &&
						currSol.contains(possControl) == false){
					currSol.push(new Pair(possControl, timeLeft));
					Solve(possControl, currSol, graph, tempTime, pace, bestSolution);
				}

			}
		}




	}
}

class Pair<T, M>{
	T first;
	M second;
	public Pair(T first, M second){
		this.first = first;
		this.second = second;
	}
}

