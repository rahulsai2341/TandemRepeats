package com.survey.methods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DynamicProgramming {

	public static final int MATCH = 1;

	public static void main(String args[]) {
		Parser p = new Parser();
		String sequence = p.fileToHashMap("database6.txt");
		System.out.println("From Dynamic programming:");
		System.out.println("Sequence: "+ sequence+" ");
		long startTime = System.currentTimeMillis();
		System.out.println(buildScoringMatrix(sequence));
	    long endTime = System.currentTimeMillis();
	    System.out.println("Dynamic programming execution time: " + (endTime - startTime));
	}

	public  static HashSet buildScoringMatrix(String sequence) {
		long startTime_builddp = System.currentTimeMillis();
		int resultMatrix[][] = new int[sequence.length() + 1][sequence.length() + 1];
		int maxValue=0,maxRow,maxCol;
		ArrayList<Position> maxPositions = new ArrayList<>();

		for (int i = 0; i <= sequence.length(); i++) {
			resultMatrix[0][i] = 0;
			resultMatrix[i][i] = 0;
		}

		for (int i = 1; i <= sequence.length(); i++) {
			for (int j = i + 1; j <= sequence.length(); j++) {
				if (sequence.charAt(j - 1) == sequence.charAt(i - 1))
					resultMatrix[i][j] = resultMatrix[i - 1][j - 1] + MATCH;
				else
					resultMatrix[i][j] = 0;
				if(maxValue < resultMatrix[i][j]){
					maxValue = resultMatrix[i][j];
				}
			}
		}
		long endTime_builddp = System.currentTimeMillis();
	    System.out.println("Dynamic programming: Dp build time: " + (endTime_builddp - startTime_builddp));

		long startTime_getrepeat = System.currentTimeMillis();
		maxPositions = findMaxPositions(maxValue ,resultMatrix);
		
		HashSet<String> result = new HashSet<>();
		StringBuilder tempResult = new StringBuilder();
		for(Position position: maxPositions){
			maxRow = position.getRow(); maxCol = position.getCol();
			while(resultMatrix[maxRow][maxCol]>0){
				tempResult.append(sequence.charAt(maxRow-1));
				maxRow-=1;maxCol-=1;
			} 
			result.add(tempResult.reverse().toString());
			tempResult = new StringBuilder();
		}

		long endTime_getrepeat = System.currentTimeMillis();
	    System.out.println("Dynamic programming: finding repeat time: " + (endTime_getrepeat - startTime_getrepeat));
		return result;
	}
	
	public void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		        System.out.print(matrix[i][j] + " ");
		    }
		    System.out.println();
		}
		
	}
	private static ArrayList<Position> findMaxPositions(int max, int[][] resultMatrix) {
		ArrayList<Position> maxPositions = new ArrayList<>();
		for(int i=0;i<resultMatrix.length;i++){
			for(int j=i+1;j<resultMatrix.length;j++){
				if(max == resultMatrix[i][j]){
					maxPositions.add(new Position(i,j));
				}
			}
		}
		return maxPositions;
	}


}
class Position {
	private int row, col;

	Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}