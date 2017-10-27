
import java.awt.Color;
import java.io.IOException;
import javax.swing.WindowConstants;

public class TravellingSalesman {

//    public static final int NUM_OF_LOCATIONS = 46;
   public static final int NUM_OF_LOCATIONS = 46;
   
    public static void main(String[] args) {
        
        TravellingSalesmanGUI GUI = new TravellingSalesmanGUI();
        GUI.getContentPane().setBackground(Color.white);
        GUI.setTitle("Travelling Salesman Genetic Algorithm");
        GUI.setVisible(true);
        GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
//        ExcelReader excelReader = new ExcelReader();
//        ArrayList<Location> locations = new ArrayList<>();
//        Random rand = new Random();
//
//        double distances[][] = new double[NUM_OF_LOCATIONS][NUM_OF_LOCATIONS]; // 2d array holding distances between locations
//
//        // read input file into arrayLists
//        ArrayList<String> codes = excelReader.columnToArrayListAsString("input.xlsx", 0);
//        ArrayList<Double> latitudes = excelReader.columnToArrayListAsDouble("input.xlsx", 1);
//        ArrayList<Double> longitudes = excelReader.columnToArrayListAsDouble("input.xlsx", 2);
//        ArrayList<String> names = excelReader.columnToArrayListAsString("input.xlsx", 3);
//        ArrayList<String> locatedIns = excelReader.columnToArrayListAsString("input.xlsx", 4);
//
//        // build list of locations
//        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {
//            Location tempLocation = new Location(codes.get(i), names.get(i), locatedIns.get(i), latitudes.get(i), longitudes.get(i));
//            locations.add(tempLocation);
//        }
//
//        // build distances array
//        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {
//            for (int j = 0; j < NUM_OF_LOCATIONS; j++) {
//                distances[i][j] = Haversine.calc(locations.get(i).getLatitude(), locations.get(i).getLongitude(),
//                        locations.get(j).getLatitude(), locations.get(j).getLongitude());
//            }
//        }
//
//        //create initial 10 routes
//        ArrayList<Route> parentRoutes = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ArrayList<Integer> initialSolution = InitialSolution.initialSolution();
//            double dist = 0;
//            
//            // sum distance between airports
//            for (int j = 0; j < initialSolution.size() - 1; j++) {
//                dist += distances[initialSolution.get(j)][initialSolution.get(j + 1)];
//            }
//            parentRoutes.add(new Route(initialSolution, dist));
//        }
//
//        // insertion sort routes by distance
//        for (int i = 1; i < parentRoutes.size(); i++) {
//            for (int j = i; j > 0; j--) {
//                if (parentRoutes.get(j).distance < parentRoutes.get(j - 1).distance) {
//                    Collections.swap(parentRoutes, j, j - 1);
//                }
//            }
//        }
//
//        System.out.println("*******************************************************************");
//        System.out.println("Initial Random Parent Routes");
//        System.out.println("*******************************************************************");
//        
//        //print routes
//        for (int i = 0; i < 10; i++) {
//            System.out.println(parentRoutes.get(i).route);
//            System.out.println(parentRoutes.get(i).distance);
//            System.out.println("");
//        }
//        
//        for (int generations = 0; generations < 500000; generations++) { //number of generations
//            for (int children = 0; children < 50; children++) { // number of children per generation
//                int parentIndex = Roulette.roulette(); // get parent using weighted roulette method
//
//                
//                ArrayList<Integer> tempRoute = new ArrayList<>();
//                
//                // copy parent route
//                for (int j = 0; j < parentRoutes.get(parentIndex).route.size() - 1; j++) {
//                    tempRoute.add(parentRoutes.get(parentIndex).route.get(j));
//                }
//                tempRoute.remove(Integer.valueOf(0)); // remove start and end location (0 which is GRK - Killeen, TX airport)
//                
//                // generate two different random numbers
//                int randomRoll = rand.nextInt(tempRoute.size()) + 1;
//                int randomRoll2 = rand.nextInt(tempRoute.size()) + 1;
//                while (randomRoll2 == randomRoll) {
//                    randomRoll2 = rand.nextInt(tempRoute.size())+ 1;
//                }
//                
//                // assign lower roll to indexMin and higher to indexMax
//                int indexMin, indexMax;
//                if (randomRoll <= randomRoll2) {
//                    indexMin = randomRoll;
//                    indexMax = randomRoll2;
//                } else {
//                    indexMin = randomRoll2;
//                    indexMax = randomRoll;
//                }
//                Collections.shuffle(tempRoute.subList(indexMin, indexMax)); // shuffle subarrayList between indexMin and indexMax
//                tempRoute.add(0, 0); // add start location GRK
//                tempRoute.add(0); // add end location GRK
//
//                double dist = 0;
//
//                // sum distance between locations
//                for (int j = 0; j < tempRoute.size() - 1; j++) {
//                    dist += distances[tempRoute.get(j)][tempRoute.get(j + 1)];
//                }
//                Route tempChildRoute = new Route(tempRoute, dist); // create temporary child route
//
//                boolean isUnique = true; // unique route flag
//                if (tempChildRoute.distance < parentRoutes.get(9).distance) {
//                    for (int j = 0; j < parentRoutes.size(); j++) { // test if route is already one of the parent routes
//                        if (parentRoutes.get(j).distance == tempChildRoute.distance) {
//                            isUnique = false;
//                            break;
//                        }
//                    }
//                    if (isUnique) { // if the route is unique, therefore not in the parent list of routes. 
//                        parentRoutes.remove(9); // remove the last route, which is the longest
//                        parentRoutes.add(tempChildRoute); // add the new route
//
//                        // insertion sort routes by distance
//                        for (int k = 1; k < parentRoutes.size(); k++) {
//                            for (int j = k; j > 0; j--) {
//                                if (parentRoutes.get(j).distance < parentRoutes.get(j - 1).distance) {
//                                    Collections.swap(parentRoutes, j, j - 1);
//                                }
//                            }
//                        } // end insertion sort
//                    } // end if isUnique
//                } // end if tempChildRoute < parenRoutes.get(9)
//            } // end for children per generation
//        } // end for generation
//
//        System.out.println("*******************************************************************");
//        System.out.println("FINAL ROUTES");
//        System.out.println("*******************************************************************");
//        
//        //print routes
//        for (int i = 0; i < parentRoutes.size(); i++) {
//            System.out.println(parentRoutes.get(i).route);
//            for (int j = 0; j < parentRoutes.get(i).route.size(); j++) {
//                System.out.print(locations.get(parentRoutes.get(i).route.get(j)).getLocatedIn() + ", ");
//            }
//            System.out.println("");
//            System.out.println("DISTANCE: " + parentRoutes.get(i).distance + " km");
//            System.out.println("");
//        }
//    }

}
