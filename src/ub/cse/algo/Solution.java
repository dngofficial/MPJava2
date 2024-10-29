package ub.cse.algo;

import java.util.*;

import static ub.cse.algo.Traversals.bfsPaths;

//import static ub.cse.algo.Traversals.pathsFromPriors;

public class Solution {

    private Info info;
    private Graph graph;
    private ArrayList<Client> clients;
    private ArrayList<Integer> bandwidths;

    /**
     * Basic Constructor
     *
     * @param info: data parsed from input file
     */
    public Solution(Info info) {
        this.info = info;
        this.graph = info.graph;
        this.clients = info.clients;
        this.bandwidths = info.bandwidths;
    }



    /**
     * Method that returns the calculated 
     * SolutionObject as found by your algorithm
     *
     * @return SolutionObject containing the paths, priorities and bandwidths
     */
    public SolutionObject outputPaths() {

        int[] priors = new int[graph.size()];
        Arrays.fill(priors, -1);

        int[][] distance = new int[graph.size()][2];
        for (int i = 0; i < graph.size(); i++) {
            int[] entryarr = {i, 0};
            distance[i] = entryarr;
        }

        // Run BFS, finding the nodes parent in the shortest path
        PriorityQueue<int[]> searchQueue = new PriorityQueue<>((v1, v2) -> v2[1] - v1[1]);
        searchQueue.add(distance[graph.contentProvider]);
        while (!searchQueue.isEmpty()) {
            int[] node = searchQueue.poll();
            int node_id = node[0];
            for (int neighbor : graph.get(node[0])) {
                if ((distance[node_id][1] + bandwidths.get(node_id) > distance[neighbor][1]) && (neighbor != graph.contentProvider)){

                    distance[neighbor][1] = distance[node_id][1] + bandwidths.get(node_id);
                    priors[neighbor] = node[0];
                    if(priors[neighbor] == -1) {
                        searchQueue.add(distance[neighbor]);
                    }
                }
            }
        }

        HashMap<Integer, ArrayList<Integer>> paths = new HashMap<>(clients.size());
        // For every client, traverse the prior array, creating the path
        for (Client client : clients) {
            ArrayList<Integer> path = new ArrayList<>();
            int currentNode = client.id;
            while (currentNode != -1) {
                /*
                    Add this ID to the beginning of the
                    path so the path ends with the client
                 */
                path.add(0, currentNode);
                currentNode = priors[currentNode];
            }
            paths.put(client.id, path);
        }

        // Get the final shortest paths


        /*HashMap<Integer, Integer> priorities =  new HashMap<Integer, Integer>();
        for (Client client: clients) {
            priorities.put(client.id, client.priority);
        }

        SolutionObject sol = new SolutionObject(paths, priorities, bandwidths);         */

        HashMap<Integer, Integer> priorities =  new HashMap<Integer, Integer>();
        for (Client client: clients) {
            priorities.put(client.id, client.priority);
        }
        System.out.println(paths.get(500));
        SolutionObject sol = new SolutionObject(paths, priorities, bandwidths);
        /* TODO: Your solution goes here */
        return sol;


        //return sol;
    }
}
