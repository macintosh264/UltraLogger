package org.ultralogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class LoggerFile {
	
	private BufferedWriter out;

	public LoggerFile(File log, boolean is, int max,boolean yes) {
		if(yes){
			DateFormat formatter =new SimpleDateFormat("dd-MMM-yy");
			String time =formatter.format(new Date(System.currentTimeMillis()));
			String path  =log.getAbsolutePath();
			String name =log.getName();
			log = new File(path.substring(0, path.lastIndexOf(name))+time+"/"+name);
			File folder = new File(path.substring(0, path.lastIndexOf(name))+time+"/");
			if(!folder.exists()){
				folder.mkdir();
			}
			if(!log.exists()){
				try {
					log.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (max>0){
				try {
					int ten = max/10;
					ArrayList<String> last = new ArrayList<String>();
					int lines = 0;
					BufferedReader r = new BufferedReader(new FileReader(log));
					String s= r.readLine();
					while(s!=null){
						last.add(s);
						lines++;
						if(last.size()>ten){
							last.remove(last.size()-1);
						}
						s=r.readLine();
					}
					r.close();
					if(lines>max){
						out = new BufferedWriter(new FileWriter(log));
						for(Iterator<String> i = last.iterator();i.hasNext();out.append(i.next()));
						out.flush();
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				out = new BufferedWriter(new FileWriter(log,is));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			if(!log.exists()){
				try {
					log.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (max>0){
				try {
					int ten = max/10;
					ArrayList<String> last = new ArrayList<String>();
					int lines = 0;
					BufferedReader r = new BufferedReader(new FileReader(log));
					String s= r.readLine();
					while(s!=null){
						last.add(s);
						lines++;
						if(last.size()>ten){
							last.remove(last.size()-1);
						}
						s=r.readLine();
					}
					r.close();
					if(lines>max){
						out = new BufferedWriter(new FileWriter(log));
						for(Iterator<String> i = last.iterator();i.hasNext();out.append(i.next()));
						out.flush();
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				out = new BufferedWriter(new FileWriter(log,is));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public LoggerFile(File log, boolean is, int max,Date last) {
		DateFormat formatter =new SimpleDateFormat("dd-MMM-yy");
		String time =formatter.format(last);
		String path  =log.getAbsolutePath();
		String name =log.getName();
		log = new File(path.substring(0, path.lastIndexOf(name))+time+"/"+name);
		File folder = new File(path.substring(0, path.lastIndexOf(name))+time+"/");
		if(!folder.exists()){
			folder.mkdir();
		}
		if(!log.exists()){
			try {
				log.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (max>0){
			try {
				int ten = max/10;
				ArrayList<String> lastr = new ArrayList<String>();
				int lines = 0;
				BufferedReader r = new BufferedReader(new FileReader(log));
				String s= r.readLine();
				while(s!=null){
					lastr.add(s);
					lines++;
					if(lastr.size()>ten){
						lastr.remove(lastr.size()-1);
					}
					s=r.readLine();
				}
				r.close();
				if(lines>max){
					out = new BufferedWriter(new FileWriter(log));
					for(Iterator<String> i = lastr.iterator();i.hasNext();out.append(i.next()));
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			out = new BufferedWriter(new FileWriter(log,is));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int count =0;
	public static DateFormat d = DateFormat.getInstance();
	private ArrayList<String> msgs = new ArrayList<String>();
	
	public void log(String s){
		String time = d.format(new Date(System.currentTimeMillis()))+" ";
		msgs.add(time+s);
		count++;
		if(count==20){
			count=0;
			for(Iterator<String> i = msgs.iterator();i.hasNext();){
				String r = i.next();
				try {
					out.write(r);
					out.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			msgs.clear();
		}
	}

	public void close() {
		try {
			for(Iterator<String> i = msgs.iterator();i.hasNext();){
				String r = i.next();
				try {
					out.write(r);
					out.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			msgs.clear();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
