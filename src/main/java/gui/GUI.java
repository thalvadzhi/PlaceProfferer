/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import IR.index.ActivityPlace;
import IR.index.IndexManager;
import IR.queryParser.QueryParser;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author Anti
 */
public class GUI extends javax.swing.JFrame {

    private IndexManager manager;
    private List<ActivityPlace> resultPlaces;
    /**
     * Creates new form GUI
     */
    private void setComboBox(JComboBox box, String[] ss) {
        box.setModel(new javax.swing.DefaultComboBoxModel<>(ss));
    }

    private void setList(JList list, String[] ss) {
        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = ss;

            @Override
            public int getSize() {
                return ss.length; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getElementAt(int index) {
                return ss[index];//To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private String[] listToArrayWithAll(List<String> ls){
        ls.add(0, "All");
        return ls.toArray(new String[]{});
    }

    public GUI() {
        initComponents();
        manager = new IndexManager("Bulgaria.txt", true);
        List<String> allCities = manager.getAllCities();

        setComboBox(comboBoxCity, listToArrayWithAll(allCities));

        setComboBox(comboBoxCountry, listToArrayWithAll(manager.getAllCountries()));

//        String[] categories = {"All", "Museum", "Snowboard", "Playing"};
        setComboBox(comboBoxCategory, listToArrayWithAll(manager.getAllCategories()));

//        String[] activities = {"Playing", "Photographing", "Extra", "Enyoj", "Relax"};
        setList(lstActivity, listToArrayWithAll(manager.getAllVerbs()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBoxCity = new javax.swing.JComboBox<>();
        lblCity = new javax.swing.JLabel();
        lblCountry = new javax.swing.JLabel();
        comboBoxCountry = new javax.swing.JComboBox<>();
        lblCountry1 = new javax.swing.JLabel();
        comboBoxCategory = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        lblActivity = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstActivity = new javax.swing.JList<>();
        lblDistance = new javax.swing.JLabel();
        txtDistance = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaResult = new javax.swing.JTextArea();
        lbResult = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblDistance1 = new javax.swing.JLabel();
        lblDistance2 = new javax.swing.JLabel();
        txtLatitude = new javax.swing.JTextField();
        txtLongitude = new javax.swing.JTextField();
        lblKm = new javax.swing.JLabel();
        btnShowActivityPlace = new javax.swing.JButton();
        btnRate = new javax.swing.JButton();
        txtShowActivityPlace = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboBoxCity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCityActionPerformed(evt);
            }
        });

        lblCity.setText("City");

        lblCountry.setText("Country");

        comboBoxCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCountryActionPerformed(evt);
            }
        });

        lblCountry1.setText("Category");

        comboBoxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCategoryActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblActivity.setText("Activity");

        lstActivity.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstActivity);

        lblDistance.setText("Distance");

        txtDistance.setText("0.0");

        txtAreaResult.setColumns(20);
        txtAreaResult.setRows(5);
        jScrollPane2.setViewportView(txtAreaResult);

        lbResult.setText("Result");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblDistance1.setText("Latitude");

        lblDistance2.setText("Longitude");

        txtLatitude.setText("42.6977082");
        txtLatitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLatitudeActionPerformed(evt);
            }
        });

        txtLongitude.setText("23.3218675");
        txtLongitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLongitudeActionPerformed(evt);
            }
        });

        lblKm.setText("km");

        btnShowActivityPlace.setText("Show");
        btnShowActivityPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActivityPlaceActionPerformed(evt);
            }
        });

        btnRate.setText("Rate");
        btnRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(lblDistance)
                                            .addGap(22, 22, 22)
                                            .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(lblKm))
                                        .addComponent(lblActivity, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCity)
                                            .addComponent(lblCountry)
                                            .addComponent(lblCountry1))
                                        .addGap(29, 29, 29)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboBoxCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBoxCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDistance2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtLongitude, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                .addGap(95, 95, 95)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(154, 154, 154)
                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(175, 175, 175)
                                                .addComponent(lbResult)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnShowActivityPlace)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnRate))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtShowActivityPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                                                .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDistance1)
                        .addGap(18, 18, 18)
                        .addComponent(txtLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbResult)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCountry)
                            .addComponent(comboBoxCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBoxCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCity))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCountry1)
                            .addComponent(comboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnShowActivityPlace)
                            .addComponent(btnRate))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtShowActivityPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(lblActivity)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDistance)
                            .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKm))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDistance1)
                            .addComponent(txtLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDistance2))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCityActionPerformed

        System.out.println(comboBoxCity.getSelectedItem());
    }//GEN-LAST:event_comboBoxCityActionPerformed

    private void comboBoxCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxCountryActionPerformed

    private void comboBoxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxCategoryActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String country, city, cat;
        Double distance = 0.0;
        Double lat = 0.0;
        Double lon = 0.0;

        country = (String) comboBoxCountry.getSelectedItem();
        city = (String) comboBoxCity.getSelectedItem();
        cat = (String) comboBoxCategory.getSelectedItem();
        distance = getDoubleFromTxt(txtDistance);
        lat = getDoubleFromTxt(txtLatitude);
        lon = getDoubleFromTxt(txtLongitude);

        List<String> selectedValuesList = lstActivity.getSelectedValuesList();

        HashMap<String, String> queryValues = new HashMap<String, String>();

        if(lat != 0.0){
            queryValues.put("Lat", String.valueOf(lat));
        }else{
            queryValues.put("Lat", "None");
        }
        if(lon != 0.0){
            queryValues.put("Lon", String.valueOf(lon));
        }else{
            queryValues.put("Lon", "None");
        }
        if(distance != 0.0){
            queryValues.put("Distance", String.valueOf(distance));
        }else{
            queryValues.put("Distance", "None");
        }
        QueryParser parser = new QueryParser(manager);
        resultPlaces = parser.parseGetPlaces(queryValues, selectedValuesList);
        List<String> result = resultPlaces.stream().map(x -> x.getName()).collect(Collectors.toList());


        txtAreaResult.setText(formatResult(result));
    }//GEN-LAST:event_btnSearchActionPerformed
    //dummy
    private List<String> method(HashMap h, List<String> ls) {
        return null;
    }

    private double getDoubleFromTxt(JTextField txt) {
        try {
            return Double.valueOf(txt.getText());
        } catch (Exception e) {
            txtAreaResult.setText("Enter proper value for text fields");
            return 0.0;
        }
    }

    private String formatResult(List<String> ss) {
        StringBuilder sbuilder = new StringBuilder();
        int count = 0;
        for (String s : ss) {
            count++;
            sbuilder.append(count);
            sbuilder.append(".  " + s + '\n');
        }

        return sbuilder.toString();
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtLatitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLatitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLatitudeActionPerformed

    private void txtLongitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLongitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLongitudeActionPerformed

    private void btnShowActivityPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActivityPlaceActionPerformed
        String name = txtAreaResult.getSelectedText();
        txtShowActivityPlace.setText(name);
        ActivityPlace place = getPlaceFromName(name);
        if(place != null){
            double rating = place.getRating().getRating(); // call method to get actual rating od PLACE
            txtRate.setText(String.valueOf(rating));
        }
    }//GEN-LAST:event_btnShowActivityPlaceActionPerformed

    private ActivityPlace getPlaceFromName(String name) {
        List<ActivityPlace> collect = resultPlaces.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
        if(collect.size() != 0){
            return collect.get(0);
        }else{
            return null;
        }
    }

    private void btnRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRateActionPerformed
        Double value = 5.0;
        try{
            value = Double.valueOf(txtRate.getText());
        } catch(Exception x){
            txtShowActivityPlace.setText("Error Values!");
            return;
        }
        if(value >= 0 && value <= 5){
            String name = txtShowActivityPlace.getText();
            int id;
            try{
                id = getPlaceFromName(name).getId();
            } catch(Exception e){
                return;
            }
            manager.updateRatingById(id, value);
            txtShowActivityPlace.setText("Succesful");
        }else{
            txtShowActivityPlace.setText("Error values");
        }
    }//GEN-LAST:event_btnRateActionPerformed

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRate;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShowActivityPlace;
    private javax.swing.JComboBox<String> comboBoxCategory;
    private javax.swing.JComboBox<String> comboBoxCity;
    private javax.swing.JComboBox<String> comboBoxCountry;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbResult;
    private javax.swing.JLabel lblActivity;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblCountry1;
    private javax.swing.JLabel lblDistance;
    private javax.swing.JLabel lblDistance1;
    private javax.swing.JLabel lblDistance2;
    private javax.swing.JLabel lblKm;
    private javax.swing.JList<String> lstActivity;
    private javax.swing.JTextArea txtAreaResult;
    private javax.swing.JTextField txtDistance;
    private javax.swing.JTextField txtLatitude;
    private javax.swing.JTextField txtLongitude;
    private javax.swing.JTextField txtRate;
    private javax.swing.JTextField txtShowActivityPlace;
    // End of variables declaration//GEN-END:variables
}
