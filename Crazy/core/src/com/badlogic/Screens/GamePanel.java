package com.badlogic.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class GamePanel implements ActionListener {
    JLabel instructions;
    JFrame frame;
    JPanel panelTitle;
    JPanel textFields;
    JPanel buttonField;
    JPanel VelPane;
    JPanel velOutputPane;
    JButton go;
    JTextField xVelText;
    JTextField yVelText;
    JLabel scoreLabel;
    JPanel scorePanel;
    JLabel y;
    JLabel x;

    DecimalFormat rounding = new DecimalFormat("#.####");
    JLabel xOutput;
    JLabel yOutput;
    JLabel zOutput;
    FileReader read = new FileReader();
    Terrain function = new Terrain();
    double positionX = read.x0;
    double positionY = read.y0;
    double positionZ = function.terrain(read.x0,read.y0);
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

        x = new JLabel();
        y = new JLabel();
        x.setText("x:");
        y.setText("y:");
        x.setFont(new Font("MV Boli",Font.PLAIN,17));
        y.setFont(new Font("MV Boli",Font.ITALIC,17));
        y.setForeground(Color.WHITE);
        x.setForeground(Color.WHITE);


        textFields=new JPanel();
        textFields.setBackground(Color.DARK_GRAY);
        textFields.setBounds(15,50,150,70);
        xVelText = new JTextField();
        yVelText = new JTextField();
        xVelText.setPreferredSize(new Dimension(100,25));
        yVelText.setPreferredSize(new Dimension(100,25));
        textFields.add(xVelText);
        textFields.add(yVelText);

        buttonField = new JPanel();
        go = new JButton("go");
        go.addActionListener(this);
        buttonField.setBackground(Color.DARK_GRAY);
        buttonField.setBounds(115,70,155,50);
        buttonField.add(go);

        VelPane = new JPanel();
        VelPane.setBackground(Color.DARK_GRAY);
        VelPane.setBounds(5,48,30,70);
        VelPane.add(x);
        VelPane.add(y);

        velOutputPane = new JPanel();
        xOutput = new JLabel();
        yOutput = new JLabel();
        zOutput = new JLabel();
        xOutput.setText("X FINAL POSITION:" + rounding.format(positionX));
        yOutput.setText("Y FINAL POSITION:" + rounding.format(positionY));
        zOutput.setText("HEIGHT AT GIVEN POSITION:" +  rounding.format(positionZ));
        yOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        xOutput.setForeground(Color.WHITE);
        xOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        yOutput.setForeground(Color.WHITE);
        zOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        zOutput.setForeground(Color.WHITE);
        velOutputPane.setBackground(Color.darkGray);
        velOutputPane.setBounds(-10,125,300,90);
        velOutputPane.add(xOutput);
        velOutputPane.add(yOutput);
        velOutputPane.add(zOutput);

        scoreLabel = new JLabel();
        scorePanel = new JPanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        scorePanel.setBounds(-62,220,300,50);
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
        frame.add(scorePanel);
        frame.add(VelPane);
        frame.add(textFields);
        frame.add(buttonField);
        frame.add(panelTitle);
        frame.add(velOutputPane);
    }
    public void update (double x,double y){
        positionX= x;
        positionY= y;
        positionZ = function.terrain(positionX,positionY);

        System.out.println("im updating");
        xOutput.setText("X FINAL POSITION:" + rounding.format(positionX));
        yOutput.setText("Y FINAL POSITION:" + rounding.format(positionY));
        zOutput.setText("HEIGHT FINAL POSITION:" +  rounding.format(positionZ));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==go) {
            xVel =Double.parseDouble(xVelText.getText());
            yVel =Double.parseDouble(yVelText.getText());
            shoot=true;
            score++;
            scoreLabel.setText("Score: "+ score);
        }
    }
}
