package com.badlogic.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotPanel implements ActionListener {
    JLabel instructions;
    JFrame frame;
    JPanel panelTitle;
    JPanel textFields;
    JPanel buttonField;
    JPanel VelPane;
    JPanel velOutputPane;
    JButton go;
    JLabel scoreLabel;
    JPanel scorePanel;
    JLabel xOutput;
    JLabel yOutput;
    FileReader read = new FileReader();
    double positionX = read.x0;
    double positionY = read.y0;
    boolean shoot=false;
    int score=0;

    double xVel;
    double yVel;


    public void startFrame(){
        panelTitle = new JPanel();
        panelTitle.setBackground(Color.DARK_GRAY);
        panelTitle.setBounds(-10,10,300,50);
        instructions = new JLabel();
        instructions.setText("To play input x and y velocities");
        instructions.setFont(new Font("MV Boli",Font.PLAIN,17));
        instructions.setForeground(Color.WHITE);
        panelTitle.add(instructions);

        textFields=new JPanel();
        textFields.setBackground(Color.DARK_GRAY);
        textFields.setBounds(15,50,150,70);

        buttonField = new JPanel();
        go = new JButton("go");
        go.addActionListener(this);
        buttonField.setBackground(Color.DARK_GRAY);
        buttonField.setBounds(115,70,155,50);
        buttonField.add(go);

        VelPane = new JPanel();
        VelPane.setBackground(Color.DARK_GRAY);
        VelPane.setBounds(5,48,30,70);

        velOutputPane = new JPanel();
        xOutput = new JLabel();
        yOutput = new JLabel();
        xOutput.setText("X FINAL POSITION:" + positionX);
        yOutput.setText("Y FINAL POSITION:" + positionY);
        yOutput.setFont(new Font("MV Boli",Font.PLAIN,15));
        xOutput.setForeground(Color.WHITE);
        xOutput.setFont(new Font("MV Boli",Font.PLAIN,15));
        yOutput.setForeground(Color.WHITE);
        velOutputPane.setBackground(Color.DARK_GRAY);
        velOutputPane.setBounds(-10,125,300,70);
        velOutputPane.add(xOutput);
        velOutputPane.add(yOutput);

        scoreLabel = new JLabel();
        scorePanel = new JPanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        scorePanel.setBounds(-62,185,300,50);
        scoreLabel.setText("Score: "+ score);
        scoreLabel.setFont(new Font("MV Boli",Font.PLAIN,18));
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);



        frame = new JFrame();
        frame.setTitle("Controller");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setSize(300,300);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.setVisible(true);
        frame.add(VelPane);
        frame.add(textFields);
        frame.add(buttonField);
        frame.add(velOutputPane);
        frame.add(panelTitle);
        frame.add(scorePanel);
    }
    public void update (double x,double y){
        positionX= x;
        positionY= y;
        System.out.println("im updating");
        xOutput.setText("X FINAL POSITION: " + positionX);
        yOutput.setText("Y FINAL POSITION: " + positionY);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==go) {
            shoot=true;
            score++;
            scoreLabel.setText("Score: "+ score);
        }
    }
}
