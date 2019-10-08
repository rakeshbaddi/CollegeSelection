/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeselection;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rbaddi
 */
public class CollegeSelection {

    public static int rank = 0;
    public static int colid = 0;
    public static int courseid = 0;
    
    private static void CheckRankStatus() {
        try {
            //DEFAULT Method for connecting JDBC
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selection", "root", "");

            int rankStatus = 0;
            if (rank != 1) {
                PreparedStatement rpst = con.prepareStatement("select status from login where rank=?");
                rpst.setInt(1, rank - 1);
                ResultSet rrs = rpst.executeQuery();
                while (rrs.next()) {
                    rankStatus = rrs.getInt(1);
                }
                if (rankStatus == 0) {
                    System.out.println("Cannot login at this moment\n");
                    System.exit(0);
                }
            }
            
            con.close();
            //End of Connection
        } catch (SQLException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void CollegeSelection() {

        try {
            //DEFAULT Method for connecting JDBC
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selection", "root", "");

            Scanner scanner = new Scanner(System.in);

            // College Selection module
            System.out.print("\nList of Colleges:\n");
            Statement st = con.createStatement();
            ResultSet clglist = st.executeQuery("select clgid, clgname from clgdetails");
            while (clglist.next()) {
                System.out.println(clglist.getInt("clgid") + " - " + clglist.getString("clgname"));
            }

            System.out.print("\nEnter the desired College Id: ");
            colid = scanner.nextInt();

            PreparedStatement pst = con.prepareStatement("select clgid from clgdetails where clgid=?");
            pst.setInt(1, colid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Successfully selected college!");
                CourseSelection();
                Display();
            } else {
                System.out.println("\nInvalid College Id! Exiting...");
                System.exit(0);
            }

            con.close();
            //End of Connection

        } catch (SQLException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void CourseSelection() {

        try {
            //DEFAULT Method for connecting JDBC
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selection", "root", "");

            Scanner scanner = new Scanner(System.in);

            // Course Selection module
            PreparedStatement selectpst = con.prepareStatement("select courseid, course from coursedetails where clgid=? and status=0");
            selectpst.setInt(1, colid);
            ResultSet courselist = selectpst.executeQuery();
            int count = 0;
            while (courselist.next()) {
                count++;
            }

            courselist.beforeFirst();

            if (count <= 0) {
                System.out.println("\nSorry no courses are available at selected college\n\nExiting...");
                System.exit(0);
            }

            System.out.println("\nThe courses available at the selected college are: ");
            while (courselist.next()) {
                System.out.println(courselist.getInt("courseid") + " - " + courselist.getString("course"));
            }


            System.out.print("\nEnter the desired Course Id: ");
            courseid = scanner.nextInt();


            PreparedStatement pst = con.prepareStatement("select courseid from coursedetails where courseid=?");
            pst.setInt(1, courseid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Successfully selected course!");

                //Updating status
                PreparedStatement updatepst = con.prepareStatement("update coursedetails set status=1 where courseid=?");
                updatepst.setInt(1, courseid);
                int x = updatepst.executeUpdate();

                //Updating Rank Status
                PreparedStatement rankpst = con.prepareStatement("update login set status=1 where rank=?");
                rankpst.setInt(1, rank);
                int y = rankpst.executeUpdate();

            } else {
                System.out.println("Invalid Course Id! Exiting...");
                System.exit(0);
            }

            con.close();
            //End of Connection

        } catch (SQLException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void Display() {

        try {
            //DEFAULT Method for connecting JDBC
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selection", "root", "");


            //Displaying selection       
            String colname = "", crsname = "";

            PreparedStatement clgnamepst = con.prepareStatement("select clgname from clgdetails where clgid=?");
            clgnamepst.setInt(1, colid);
            ResultSet clgname = clgnamepst.executeQuery();
            while (clgname.next()) {
                colname = clgname.getString("clgname");
            }

            PreparedStatement coursepst = con.prepareStatement("select course from coursedetails where courseid=?");
            coursepst.setInt(1, courseid);
            ResultSet coursename = coursepst.executeQuery();
            while (coursename.next()) {
                crsname = coursename.getString("course");
            }

            System.out.println("\nCongratulations! You have selected **" + crsname + "** course at **" + colname + "**!");

            con.close();
            //End of Connection

        } catch (SQLException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //CollegeSelection obj = new CollegeSelection();
        try {
            //Connecting database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selection", "root", "");

            System.out.println("SQL Connection to database established!");

            Scanner scanner = new Scanner(System.in);


            // Login module
            System.out.print("Enter your Rank : ");
            String rank_number = scanner.nextLine();

            rank = Integer.parseInt(rank_number);

            CheckRankStatus();

            System.out.print("Enter Username: ");
            String uname = scanner.nextLine();
            System.out.print("Enter Password: ");
            String pword = scanner.nextLine();

            PreparedStatement logpst = con.prepareStatement("select * from login where username=? and password=? and rank=? and status=0");
            logpst.setString(1, uname);
            logpst.setString(2, pword);
            logpst.setInt(3, rank);
            ResultSet loginrs = logpst.executeQuery();
            if (loginrs.next()) {
                System.out.println("\nLogin Successful!");
                CollegeSelection();
            } else {
                System.out.println("\nInvalid credentials!");
                System.exit(0);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollegeSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
