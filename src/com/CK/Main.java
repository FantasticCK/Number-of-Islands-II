package com.CK;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
    }
}

class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        int size = positions.length;
        if (size == 0) return res;
        int[][] grid = new int[m][n];
        UnionFind uf = new UnionFind(grid);

        for (int i = 0; i < size; i++) {
            int r = positions[i][0], c = positions[i][1];
            if (grid[r][c] != 1) {
                grid[r][c] = 1;
                uf.count++;
            }
            if (r - 1 >= 0 && grid[r - 1][c] == 1) {
                uf.union(r * n + c, (r - 1) * n + c);
            }
            if (r + 1 < m && grid[r + 1][c] == 1) {
                uf.union(r * n + c, (r + 1) * n + c);
            }
            if (c - 1 >= 0 && grid[r][c - 1] == 1) {
                uf.union(r * n + c, r * n + c - 1);
            }
            if (c + 1 < n && grid[r][c + 1] == 1) {
                uf.union(r * n + c, r * n + c + 1);
            }
            res.add(uf.count);
        }

        return res;
    }

    class UnionFind {
        int count; // # of connected components
        int[] parent;
        int[] rank;

        public UnionFind(int[][] grid) { // for problem 200
            count = 0;
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    parent[i * n + j] = i * n + j;
                    rank[i * n + j] = 1;
                }
            }
        }

        public int find(int i) { // path compression
            if (parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int x, int y) { // union with rank
            int rootx = find(x);
            int rooty = find(y);
            if (rootx != rooty) {
                if (rank[rootx] >= rank[rooty]) {
                    parent[rooty] = rootx;
                    rank[rootx] += rank[rooty];
                } else {
                    parent[rootx] = rooty;
                    rank[rooty] += rank[rootx];
                }
                --count;
            }
        }

        public int getCount() {
            return count;
        }
    }
}