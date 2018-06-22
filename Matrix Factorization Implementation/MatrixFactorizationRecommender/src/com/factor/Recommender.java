package com.factor;

import java.util.*;
import java.io.*;
import java.util.Random;

class Recommender {
	static int userCount = 944;
	static int item = 1683;
	public static class Pair<A, B> {
		public final A a;
		public final B b;

		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}

	public static float[][] CreateMatrix(String inputFile) throws FileNotFoundException, IOException {
		// Initialize the matrix with -1 for all elements
		float[][] matrix = new float[userCount][item];
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j) {
				matrix[i][j] = -1f;
			}
		}

		// Read the input values and form the full matrix
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		StringTokenizer st = null;
		String row;
		while ((row = br.readLine()) != null) {
			st = new StringTokenizer(row, "\t");
			while (st.hasMoreTokens()) {
				int user = Integer.parseInt(st.nextToken());
				int item = Integer.parseInt(st.nextToken());
				float rating = Integer.parseInt(st.nextToken());
				matrix[user - 1][item - 1] = rating;
				st.nextToken();
			}
		}
		br.close();
		return matrix;
	}

	public static int[][] testData(String testFile) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(testFile));
		StringTokenizer st = null;
		String row;
		int[][] data = new int[17636][2];
		int i = 0;
		while ((row = br.readLine()) != null) {
			st = new StringTokenizer(row, ",");
			while (st.hasMoreTokens()) {
				data[i][0] = Integer.parseInt(st.nextToken());
				data[i][1] = Integer.parseInt(st.nextToken());
			}
			i += 1;
		}
		br.close();
		return data;
	}

	public static Pair<float[][], float[][]> myRecommender(float[][] matrix, int k, float rate, float lambda) {
		int maxIter = 1000;
		int n1 = matrix.length;
		int n2 = matrix[0].length;

		float[][] U = new float[n1][k];
		float[][] V = new float[n2][k];

		// Initialize U and V matrix
		Random rand = new Random();
		for (int i = 0; i < U.length; ++i) {
			for (int j = 0; j < U[0].length; ++j) {
				U[i][j] = rand.nextFloat() / (float) k;
			}
		}

		for (int i = 0; i < V.length; ++i) {
			for (int j = 0; j < V[0].length; ++j) {
				V[i][j] = rand.nextFloat() / (float) k;
			}
		}

		// Gradient Descent
		for (int iter = 0; iter < maxIter; ++iter) {
			// System.out.println("Iteration no. " + iter + " / " + maxIter);

			float[][] prodMatrix = new float[n1][n2];
			for (int i = 0; i < n1; ++i) {
				for (int j = 0; j < n2; ++j) {
					for (int l = 0; l < k; ++l) {
						prodMatrix[i][j] += U[i][l] * V[j][l];
					}
				}
			}

			float[][] errorMatrix = new float[n1][n2];
			for (int i = 0; i < n1; ++i) {
				for (int j = 0; j < n2; ++j) {
					if (matrix[i][j] == -1f) {
						errorMatrix[i][j] = 0f;
					} else {
						errorMatrix[i][j] = matrix[i][j] - prodMatrix[i][j];
					}
				}
			}

			float[][] UGrad = new float[n1][k];
			for (int i = 0; i < n1; ++i) {
				for (int j = 0; j < k; ++j) {
					for (int l = 0; l < n2; ++l) {
						UGrad[i][j] += errorMatrix[i][l] * V[l][j];
					}
				}
			}

			float[][] VGrad = new float[n2][k];
			for (int i = 0; i < n2; ++i) {
				for (int j = 0; j < k; ++j) {
					for (int l = 0; l < n1; ++l) {
						VGrad[i][j] += errorMatrix[l][i] * U[l][j];
					}
				}
			}

			float[][] Un = new float[n1][k];
			for (int i = 0; i < n1; ++i) {
				for (int j = 0; j < k; ++j) {
					Un[i][j] = (1f - rate * lambda) * U[i][j] + rate * UGrad[i][j];
				}
			}

			float[][] Vn = new float[n2][k];
			for (int i = 0; i < n2; ++i) {
				for (int j = 0; j < k; ++j) {
					Vn[i][j] = (1f - rate * lambda) * V[i][j] + rate * VGrad[i][j];
				}
			}

			U = Un;
			V = Vn;
		}

		Pair<float[][], float[][]> p = new Pair<float[][], float[][]>(U, V);
		return p;
	}

	public static void PredictRating(float[][] U, float[][] V, int[][] test) throws FileNotFoundException, IOException {
		int n1 = U.length;
		int n2 = V.length;
		int r = V[0].length;

		float[][] prodMatrix = new float[n1][n2];
		for (int i = 0; i < n1; ++i) {
			for (int j = 0; j < n2; ++j) {
				for (int k = 0; k < r; ++k) {
					prodMatrix[i][j] += U[i][k] * V[j][k];
				}
			}
		}

		PrintWriter writer = new PrintWriter("result.csv", "UTF-8");
		for (int i = 0; i < test.length; ++i) {
			int user = test[i][0] - 1;
			int movie = test[i][1] - 1;
			writer.println(prodMatrix[user][movie]);
		}
		writer.close();
	}

	public static void main(String args[]) throws IOException {
		System.out.println("Recommendation User Ratings!!!");

		String inputFile = args[0];
		String testFile = args[1];
		String input = args[2];
		int k = Integer.parseInt(input);
		input = args[3];
		float rate = Float.parseFloat(input);
		input = args[4];
		float lambda = Float.parseFloat(input);

		float[][] matrix = CreateMatrix(inputFile);
		//int[][] test = testData(testFile);

		Pair<float[][], float[][]> p = myRecommender(matrix, k, rate, lambda);
		//PredictRating(p.a, p.b, test);
	}
}