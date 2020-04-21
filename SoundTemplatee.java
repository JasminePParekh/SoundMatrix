import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.math.*;
public class SoundTemplatee extends JFrame implements Runnable, ActionListener
{
	int r = 29;
	int c = 4;
	int col = 0;
	int speedVal = 1000;
	JToggleButton button[][] = new JToggleButton[r][c];
	String [][] sboard = new String[r][c];
	JPanel panel = new JPanel();
	JMenu size, mysongs, usermenu;
	JMenuItem add, remove, ttls, aom, som;
	JScrollPane scp;
	JFrame frame = new JFrame();
	JMenuBar menubar;
	JButton clear, rand, pause, save, rename;
	Thread timing;
	AudioClip soundClip[] = new AudioClip[29];
	boolean running = true;
	boolean paused=false;
	JMenuItem userItem[];
    int songNumber = 3;
    boolean canSaveBool = false;
    String strr,str;
	String[] files ={"C6","B5","ASharp5","A5","GSharp5","G5","FSharp5","F5","E5","DSharp5","D5","CSharp5","C5","B4","ASharp4","A4","GSharp4","G4","FSharp4","F4","E4","DSharp4","D4","CSharp4","C4","bd1","bd2","cc","snare"};
	public SoundTemplatee()
	{
		this.setLayout(new BorderLayout());
		try
		{
			for(int x = 0; x < files.length; x++)
				soundClip[x] = JApplet.newAudioClip(new URL("file:Music/"+files[x]+".wav"));

		}catch(MalformedURLException mue)
		{
			System.out.println("NAH SON");
		}
		panel.setLayout(new GridLayout(r,c,10,10));
		for (int x= 0; x < r; x++){
			for (int y = 0; y < c; y++){
				button[x][y] = new JToggleButton();
				button[x][y].setText(files[x]);
				panel.add(button[x][y]);
			}
		}
		scp = new JScrollPane(panel);
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		menubar = new JMenuBar();
		size = new JMenu ("Size");

		add = new JMenuItem("Add Column");
		add.addActionListener(this);

	 	remove = new JMenuItem("Remove Column");
	 	remove.addActionListener(this);

	 	// usersongs = new JMenu ("Rewrite Songs");
	 	// userItem = new JMenuItem[]{new JMenuItem("User 1"), new JMenuItem("User 2"), new JMenuItem("User 3")};
   //      userItem[0].addActionListener(this);
   //      userItem[1].addActionListener(this);
   //      userItem[2].addActionListener(this);
   //      for(int i=0;i<3;i++){
   //          usersongs.add(userItem[i]);
   //      }
   //      menubar.add(usersongs);

        usermenu = new JMenu ("User Actions");
	 	userItem = new JMenuItem[]{new JMenuItem("New Song"), new JMenuItem("Load Previous")};
        userItem[0].addActionListener(this);
        userItem[1].addActionListener(this);
        for(int i=0;i<2;i++){
            usermenu.add(userItem[i]);
        }
        menubar.add(usermenu);

		size.add(add);
		size.add(remove);
		menubar.add(size);

		mysongs = new JMenu ("My Songs");

		ttls = new JMenuItem ("Twinkle Twinkle Little Star");
		ttls.addActionListener(this);

		som = new JMenuItem ("Saints Go Marching");
		som.addActionListener(this);

		aom = new JMenuItem ("All of Me");
		aom.addActionListener(this);

		mysongs.add(ttls);
		mysongs.add(som);
		mysongs.add(aom);
		menubar.add(mysongs);

		clear = new JButton ("Clear");
		rand = new JButton ("Random");
		pause = new JButton ("Pause");
		save = new JButton("Save");
		clear.addActionListener(this);
		rand.addActionListener(this);
		pause.addActionListener(this);
		save.addActionListener(this);

		menubar.add(clear);
		menubar.add(rand);
		menubar.add(pause);
		menubar.add(save);

		this.add(menubar, BorderLayout.NORTH);
		this.add(scp, BorderLayout.CENTER);
		setSize(1200,800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timing = new Thread(this);
		timing.start();
	}

	public void clear(){
		for (int i = 0; i < r; i++){
			for (int j = 0; j < c; j++){
				button[i][j].setSelected(false);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clear) {
			clear();
		}
		if (e.getSource() == rand) {
			songNumber = 0;
			clear();
			for (int i = 0; i < r; i++){
				for (int j = 0; j < c; j++){
					if ((int)(Math.random() * 10 + 1) < 5)
						button[i][j].setSelected(true);
				}
			}
		}
		if(e.getSource() == pause){
			if(running){
				timing.stop();
				running = false;
			}
			else{
				timing = new Thread(this);
				timing.start();
				running = true;
			}
		}
		if (e.getSource() == add){
			resizeMatrix(r, c+1);
		}
		if (e.getSource() == remove){
			resizeMatrix(r, c-1);
		}
		if(e.getSource()==ttls){
			songNumber = 3;
			System.out.println("Playing TTTTwinkle");
			speedVal = 700;
			sing("ttls.txt");
			col=-1;	
		}
		if(e.getSource()==som){
			songNumber = 3;
			System.out.println("Playing BLACK");
			speedVal = 500;
			sing("som.txt");
			col=-1;
		}
		if(e.getSource()==aom){
			songNumber = 3;
			System.out.println("Playing ALLOFME");
			speedVal = 300;
			sing("aom.txt");
			col=-1;
		}
		if(e.getSource() == userItem[0]){
			str = JOptionPane.showInputDialog(null, "Enter your song name: ");
			canSaveBool = true;
		}
        if(e.getSource() == save){ 
        	System.out.println("BOOL: " + canSaveBool);
        	if(canSaveBool){
	        	String fileee =  str + ".txt";
	        	System.out.println("Saving to " + fileee);
	            try
	            {
	                BufferedWriter OutputStream=new BufferedWriter(new FileWriter(fileee));
	                for(int i=0;i<button.length;i++){
	                    for(int j=0;j<button[0].length;j++){
	                        if(button[i][j].isSelected()){
	                            OutputStream.write("X ");
	                        }else{
	                            OutputStream.write("0 ");
	                        }
	                    }
	                    OutputStream.newLine();
	                }
	                OutputStream.close();
	            }
	            catch (IOException io)
	            {
	                System.err.println("File does not existtt");
	            }
	          canSaveBool = false;  
	        }
	        else{
	        	JOptionPane.showMessageDialog(null, "Could not save to anything. No file was selected. Try to make a new song or edit a previous one in the User Actions Menu", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	     }
        if(e.getSource()==userItem[1]){
        	strr = JOptionPane.showInputDialog(null, "Enter song you want to upload (don't add .txt at the end): ");
        	str = strr;
        	strr += ".txt";
        	canSaveBool = true;
        	speedVal = 1000;
			sing(strr);
			col=-1;	
			songNumber = 0;
		}
	}
	public void sing(String songfile){
		String text;
		try {
			BufferedReader input = new BufferedReader(new FileReader(songfile));
			text=input.readLine();
			if(songfile.equals("ttls.txt") || songfile.equals("aom.txt") || songfile.equals("som.txt")){
				resizeMatrix(r,text.length()/2 +1);
			}
			else{
				resizeMatrix(r,text.length()/2);
			}
			int row = 0;
			while(text!= null)
			{
				String[] output = text.split(" ");
				int col = 0;
				for (String str: output){
					button[row][col].setSelected(false);
					if (str.equals("X"))
						button[row][col].setSelected(true);
					col++;
				}
				row++;
				text=input.readLine();
			}

		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
			JOptionPane.showMessageDialog(null, "Could find this file. Make sure you did not include .txt in your input. Make sure you saved your song after editing", "Error", JOptionPane.ERROR_MESSAGE);
			canSaveBool = false;
           
		}


	}
	public void resizeMatrix(int rr, int cc){
		r = rr;
		c = cc;
		this.remove(scp);
		panel = new JPanel();
		//panel.setPreferredSize(new Dimension(c*50,r*40));
		panel.setLayout(new GridLayout(rr,cc,10,10));
		button=new JToggleButton[rr][cc];
		System.out.println(rr+", "+cc);
		for (int x= 0; x < rr; x++){
			for (int y = 0; y < cc; y++){
				//xSystem.out.println("X: " + x + "  Y: " + y);
				button[x][y] = new JToggleButton();
				button[x][y].setText(files[x]);
				panel.add(button[x][y]);
			}
		}
		scp = new JScrollPane(panel);
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scp, BorderLayout.CENTER);
		this.revalidate();
	}

	public void run()
	{
		System.out.println(songNumber);
		do {
			try {
					while(col>=0 && col<button[0].length){
	                   	for(int i=0;i<button.length;i++){
	                       	if(button[i][col] != null && button[i][col].isSelected())
	                       	{
	                         	soundClip[i].stop();
	                            soundClip[i].play();
	                       	}
	                    }
                   		timing.sleep(speedVal);
                   		col++;
                   		if(col == button[0].length){
                   			col = 0;
                   		}
	                }
					timing.sleep(0);
				}
			catch(InterruptedException e){
			}
		} while(running);
	}

	public static void main(String args[])
	{
		SoundTemplatee app=new SoundTemplatee();

	}
}
