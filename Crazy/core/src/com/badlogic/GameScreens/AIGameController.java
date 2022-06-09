package com.badlogic.GameScreens;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class AIGameController implements ActionListener {
    private JLabel instructions;
    public JFrame frame;
    private  JPanel panelTitle;
    private JPanel textFields;
    private JPanel buttonField;
    private JPanel VelPane;
    private JPanel velOutputPane;
    public JButton go;
    private JLabel scoreLabel;
    private JPanel scorePanel;
    private static JLabel xOutput;
    private static JLabel yOutput;
    private static JLabel zOutput;

    static DecimalFormat rounding = new DecimalFormat("#.####");
    static FileReader read = new FileReader();
    static TerrainInput function = new TerrainInput();
    public static double positionX = read.x0;
    public static double positionY = read.y0;
    public static double positionZ=  function.terrain(read.x0,read.y0);
    public static boolean shoot=false;
    public static int score=0;

    public  void startFrame(){
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
        zOutput = new JLabel();
        xOutput.setText("X FINAL POSITION:" + rounding.format(positionX));
        yOutput.setText("Y FINAL POSITION:" + rounding.format(positionY));
        zOutput.setText("HEIGHT FINAL POSITION:" +  rounding.format(positionZ));
        yOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        xOutput.setForeground(Color.WHITE);
        xOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        yOutput.setForeground(Color.WHITE);
        zOutput.setFont(new Font("MV Boli",Font.PLAIN,13));
        zOutput.setForeground(Color.WHITE);
        velOutputPane.setBackground(Color.DARK_GRAY);
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
        frame.add(VelPane);
        frame.add(textFields);
        frame.add(buttonField);
        frame.add(velOutputPane);
        frame.add(panelTitle);
        frame.add(scorePanel);
    }
    public static void update(double x, double y){
        positionX= x;
        positionY= y;
        positionZ=function.terrain(x,y);
        System.out.println("im updating");
        xOutput.setText("X FINAL POSITION:" + rounding.format(positionX));
        yOutput.setText("Y FINAL POSITION:" + rounding.format(positionY));
        zOutput.setText("HEIGHT FINAL POSITION:" +  rounding.format(positionZ));
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
