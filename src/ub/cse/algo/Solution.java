package ub.cse.algo;

import java.util.*;
import java.util.stream.IntStream;

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

        /*System.out.println(bandwidths);

        System.out.println(graph);

        for(Client client : clients){
            System.out.println(client.id);
        }
        System.out.println("running");*/

       // HashMap<Integer, ArrayList<Integer>> normalBFSpaths = bfsPaths(graph, clients);

        HashMap<Integer, Boolean> ClientCheck = new HashMap<>();


        HashMap<Integer, Integer> store = new HashMap<>();


        PriorityQueue<int[]> todo = new PriorityQueue<>((v1, v2) -> v1[1] - v2[1]);



        for (int i = 0; i < graph.size(); i++) {
            int[] arr = {i, bandwidths.get(i)};
            todo.add(arr);
            store.put(i, -1);
        }

        for (Client client : clients){
            ClientCheck.put(client.id, true);
        }



        //int[] entryarr = {1, 1};
        //todo.add(entryarr);


        while (!todo.isEmpty()) {

            int[] extraced_node = todo.poll();
            boolean ignore_client = false;

                if (ClientCheck.get(extraced_node[0]) != null || extraced_node[1]  > 1) {
                    ignore_client = true;
                    //System.out.println("not client");
                }


            if (!ignore_client) {
                store.put(extraced_node[0], 1);

                int[] priors = new int[graph.size()];
                Arrays.fill(priors, -1);

                priors[graph.contentProvider] = -99;

                // Run BFS, finding the nodes parent in the shortest path
                Queue<Integer> searchQueue = new LinkedList<>();
                searchQueue.add(graph.contentProvider);
                while (!searchQueue.isEmpty()) {
                    int node = searchQueue.poll();
                    if (store.get(node) != 1) {
                        for (int neighbor : graph.get(node)) {
                            if (priors[neighbor] == -1 && neighbor != graph.contentProvider && store.get(node) != 1) {
                                priors[neighbor] = node;
                                searchQueue.add(neighbor);
                            }
                        }
                    }
                    //System.out.println(Arrays.toString(priors));

                    //System.out.println(Arrays.toString(priors));
                }

                //System.out.println(Arrays.toString(priors));



                boolean contains = false;

                for (int i = 0; i < graph.size(); i++) {
                    if (i != graph.contentProvider && priors[i] == -1 && store.get(i) != 1) {
                        contains = true;
                    }
                }

                if (contains) {
                    store.put(extraced_node[0], -1);
                }

            }
            // }

            //System.out.println(store.toString());
        }










        int[] priors = new int[graph.size()];
        Arrays.fill(priors, -1);

        priors[graph.contentProvider] = -1;

        // Run BFS, finding the nodes parent in the shortest path
        Queue<Integer> searchQueue = new LinkedList<>();
        searchQueue.add(graph.contentProvider);
        while (!searchQueue.isEmpty()) {

            int node = searchQueue.poll();
            //System.out.println(node);

            if (store.get(node) != 1) {
                for (int neighbor : graph.get(node)) {
                    if (priors[neighbor] == -1 && neighbor != graph.contentProvider && store.get(node) != 1) {

                        //System.out.println("parent of neighbor");

                        priors[neighbor] = node;
                        searchQueue.add(neighbor);

                    }
                }
            }
        }

        //System.out.println(store.toString());

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



















        /*int[] priors = new int[graph.size()];
        Arrays.fill(priors, 0);

        int[][] distance = new int[graph.size()][2];
        HashMap<Integer, ArrayList<Integer>> previously = new HashMap<>();


        for (int i = 0; i < graph.size(); i++) {
            int[] entryarr = {i, Integer.MIN_VALUE};
            distance[i] = entryarr;
            previously.put(i, new ArrayList<>());
        }

        distance[graph.contentProvider][1] = 0;


        // Run BFS, finding the nodes parent in the shortest path
        PriorityQueue<int[]> searchQueue = new PriorityQueue<>((v1, v2) -> v2[1] - v1[1]);
        searchQueue.add(distance[graph.contentProvider]);
        while (!searchQueue.isEmpty()) {
            int[] node = searchQueue.poll();
            int node_id = node[0];
            for (int neighbor : graph.get(node[0])) {
                int distance1 = Math.max(distance[neighbor][1], Math.min(distance[node_id][1], bandwidths.get(neighbor)));
                if ((distance1 > distance[neighbor][1]) && (neighbor != graph.contentProvider)){

                    distance[neighbor][1] = distance1;
                    priors[neighbor] = node[0];
                    if (!previously.get(neighbor).contains(node_id)){
                        searchQueue.add(distance[neighbor]);
                        previously.get(neighbor).add(node_id);
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
                /*\
                    Add this ID to the beginning of the
                    path so the path ends with the client

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

        SolutionObject sol = new SolutionObject(paths, priorities, bandwidths);    */




System.out.println(paths);

        HashMap<Integer, Integer> priorities = new HashMap<Integer, Integer>();
        for (Client client : clients) {
            priorities.put(client.id, client.priority);
        }
        SolutionObject sol = new SolutionObject(paths, priorities, bandwidths);
        /* TODO: Your solution goes here */
        return sol;


        //return sol;
    }


}
