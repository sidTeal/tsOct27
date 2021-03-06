
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author student
 */
public class TravellingSalesmanGUI extends javax.swing.JFrame {

    /**
     * Creates new form TravellingSalesmanGUI
     */
    public TravellingSalesmanGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbGenerations = new javax.swing.JLabel();
        lbChildren = new javax.swing.JLabel();
        tfGenerations = new javax.swing.JTextField();
        tfChildren = new javax.swing.JTextField();
        lbSolutions = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taSolutions = new javax.swing.JTextArea();
        bnRun = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbGenerations.setText("Number of generations:");

        lbChildren.setText("Number of children per generation:");

        lbSolutions.setText("Ten Best Solutions");

        taSolutions.setColumns(20);
        taSolutions.setRows(5);
        jScrollPane1.setViewportView(taSolutions);

        bnRun.setText("Run");
        bnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnRunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSolutions)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbChildren)
                            .addComponent(lbGenerations))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfChildren)
                            .addComponent(tfGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addComponent(bnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGenerations)
                    .addComponent(tfGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbChildren)
                    .addComponent(tfChildren, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnRun))
                .addGap(18, 18, 18)
                .addComponent(lbSolutions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnRunActionPerformed
        try {
            ExcelReader excelReader = new ExcelReader();
            ArrayList<Location> locations = new ArrayList<>();
            Random rand = new Random();

            double distances[][] = new double[TravellingSalesman.NUM_OF_LOCATIONS][TravellingSalesman.NUM_OF_LOCATIONS]; // 2d array holding distances between locations

            // read input file into arrayLists
            ArrayList<String> codes = excelReader.columnToArrayListAsString("input.xlsx", 0);
            ArrayList<Double> latitudes = excelReader.columnToArrayListAsDouble("input.xlsx", 1);
            ArrayList<Double> longitudes = excelReader.columnToArrayListAsDouble("input.xlsx", 2);
            ArrayList<String> names = excelReader.columnToArrayListAsString("input.xlsx", 3);
            ArrayList<String> locatedIns = excelReader.columnToArrayListAsString("input.xlsx", 4);

            // build list of locations
            for (int i = 0; i < TravellingSalesman.NUM_OF_LOCATIONS; i++) {
                Location tempLocation = new Location(codes.get(i), names.get(i), locatedIns.get(i), latitudes.get(i), longitudes.get(i));
                locations.add(tempLocation);
            }

            // build distances array
            for (int i = 0; i < TravellingSalesman.NUM_OF_LOCATIONS; i++) {
                for (int j = 0; j < TravellingSalesman.NUM_OF_LOCATIONS; j++) {
                    distances[i][j] = Haversine.calc(locations.get(i).getLatitude(), locations.get(i).getLongitude(),
                            locations.get(j).getLatitude(), locations.get(j).getLongitude());
                }
            }

            //create initial 10 routes
            ArrayList<Route> parentRoutes = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ArrayList<Integer> initialSolution = InitialSolution.initialSolution();
                double dist = 0;

                // sum distance between airports
                for (int j = 0; j < initialSolution.size() - 1; j++) {
                    dist += distances[initialSolution.get(j)][initialSolution.get(j + 1)];
                }
                parentRoutes.add(new Route(initialSolution, dist));
            }

            // insertion sort routes by distance
            for (int i = 1; i < parentRoutes.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (parentRoutes.get(j).distance < parentRoutes.get(j - 1).distance) {
                        Collections.swap(parentRoutes, j, j - 1);
                    }
                }
            }

            int numGenerations = Integer.parseInt(tfGenerations.getText());
            int numChildren = Integer.parseInt(tfChildren.getText());
            for (int generations = 0; generations < numGenerations; generations++) { //number of generations
                for (int children = 0; children < numChildren; children++) { // number of children per generation
                    int parentIndex = Roulette.roulette(); // get parent using weighted roulette method

                    ArrayList<Integer> tempRoute = new ArrayList<>();

                    // copy parent route
                    for (int j = 0; j < parentRoutes.get(parentIndex).route.size() - 1; j++) {
                        tempRoute.add(parentRoutes.get(parentIndex).route.get(j));
                    }
                    tempRoute.remove(Integer.valueOf(0)); // remove start and end location (0 which is GRK - Killeen, TX airport)

                    // generate two different random numbers
                    int randomRoll = rand.nextInt(tempRoute.size()) + 1;
                    int randomRoll2 = rand.nextInt(tempRoute.size()) + 1;
                    while (randomRoll2 == randomRoll) {
                        randomRoll2 = rand.nextInt(tempRoute.size()) + 1;
                    }

                    // assign lower roll to indexMin and higher to indexMax
                    int indexMin, indexMax;
                    if (randomRoll <= randomRoll2) {
                        indexMin = randomRoll;
                        indexMax = randomRoll2;
                    } else {
                        indexMin = randomRoll2;
                        indexMax = randomRoll;
                    }
                    Collections.shuffle(tempRoute.subList(indexMin, indexMax)); // shuffle subarrayList between indexMin and indexMax
                    tempRoute.add(0, 0); // add start location GRK
                    tempRoute.add(0); // add end location GRK

                    double dist = 0;

                    // sum distance between locations
                    for (int j = 0; j < tempRoute.size() - 1; j++) {
                        dist += distances[tempRoute.get(j)][tempRoute.get(j + 1)];
                    }
                    Route tempChildRoute = new Route(tempRoute, dist); // create temporary child route

                    boolean isUnique = true; // unique route flag
                    if (tempChildRoute.distance < parentRoutes.get(9).distance) {
                        for (int j = 0; j < parentRoutes.size(); j++) { // test if route is already one of the parent routes
                            if (parentRoutes.get(j).distance == tempChildRoute.distance) {
                                isUnique = false;
                                break;
                            }
                        }
                        if (isUnique) { // if the route is unique, therefore not in the parent list of routes.
                            parentRoutes.remove(9); // remove the last route, which is the longest
                            parentRoutes.add(tempChildRoute); // add the new route

                            // insertion sort routes by distance
                            for (int k = 1; k < parentRoutes.size(); k++) {
                                for (int j = k; j > 0; j--) {
                                    if (parentRoutes.get(j).distance < parentRoutes.get(j - 1).distance) {
                                        Collections.swap(parentRoutes, j, j - 1);
                                    }
                                }
                            } // end insertion sort
                        } // end if isUnique
                    } // end if tempChildRoute < parenRoutes.get(9)
                } // end for children per generation
            } // end for generation

            //print routes
            for (int i = 0; i < parentRoutes.size(); i++) {
                taSolutions.append("\n"+ i + " )\n" + parentRoutes.get(i).route.toString() + "\n");
                for (int j = 0; j < parentRoutes.get(i).route.size(); j++) {
                    taSolutions.append(locations.get(parentRoutes.get(i).route.get(j)).getLocatedIn() + ", ");
                }
                taSolutions.append("\nDISTANCE: " + parentRoutes.get(i).distance + " km\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(TravellingSalesmanGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bnRunActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TravellingSalesmanGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TravellingSalesmanGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TravellingSalesmanGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TravellingSalesmanGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TravellingSalesmanGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnRun;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbChildren;
    private javax.swing.JLabel lbGenerations;
    private javax.swing.JLabel lbSolutions;
    private javax.swing.JTextArea taSolutions;
    private javax.swing.JTextField tfChildren;
    private javax.swing.JTextField tfGenerations;
    // End of variables declaration//GEN-END:variables
}
