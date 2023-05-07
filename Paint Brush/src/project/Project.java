/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project;

import javax.swing.JFrame;
import panel.MyPanel;

/**
 *
 * @author Esraa Ebrahim
 */
public class Project {

    public static void main(String[] args) {
       JFrame f = new JFrame();
       f.setTitle("QA");
       MyPanel mp = new MyPanel();
       f.setContentPane(mp);
       f.setSize(1000, 1000);
       
       f.setVisible(true) ;
    }

}
