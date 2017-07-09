package extract_txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
	private static final String filepath = "D:\\input";

	public static void main(String args[]) {
		File file = new File(filepath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					
				}
				else{
					File file2 = new File(filepath + "\\" + filelist[i]);
					String[] filelist2 = file2.list();
					for (int k = 0; k < filelist2.length; k++)
					{
						File readfile2 = new File(filepath + "\\" + filelist[i] + "\\" + filelist2[k]);
						String temp_path = filepath + "\\" + filelist[i] + "\\" + "output" + "\\";
						if (!readfile2.isDirectory()) {
							int cnt = 0;
							String filename = readfile2.getName();
							
							Pattern p_name = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})(.*)_(.*)(.txt)");
							Matcher m_name = p_name.matcher(filename);
							if (m_name.find())
							{
								print(filename, "title",temp_path);
								print(m_name.group(2), "title",temp_path);
								try { // 读取文件内容
									BufferedReader br = new BufferedReader(new FileReader(readfile2));
									String line = "";
									try {
										while ((line = br.readLine()) != null) {
											cnt +=count(line);
											
											reg(line,temp_path);
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
									try {
										br.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								print(String.valueOf(cnt-1),"totalWords",temp_path);
							}
						}
					}
					
				}
			}
		}
		
	}

	private static void reg(String line, String path) {
		Pattern p = Pattern.compile("(\\d+)(?: *)年(?: *)/(?: *)(\\d+)(?: *)月(?: *)/(?: *)(\\d+)(?: *)日(?: *)"
				+ "/(?: *)第(?: *)([\\d[\\w]]+)(?: *)版");
		Matcher m = p.matcher(line);
		while (m.find()) {
			System.out.println("find");
			print(m.group(1), "year",path);
			print(m.group(2), "month",path);
			print(m.group(3), "day",path);
			print(m.group(4), "edition",path);
		}
	}
 
	private static void print(String group,String string, String temp_path) {
		String path = temp_path+string+".txt";
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path,true));
			bw.write(group);
			bw.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block|[~!@#$%^&*()_+|\\\":?><`]      -=;/.,{}\\[\\]]
				e.printStackTrace();
			}
		}
	}
	
	

    public static int count(String str) {
        int count = 0;
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]|[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b]|[\\W&&[^\\._%]&&[^\\p{Z}]]");//[\\p{P}&&[^._]]|[\\p{S}&&[^._]]]&&[^._]|[\\p{P}]|[\\p{S}]");
        Matcher m = p.matcher(str);
        while(m.find()){
            count++;
            //System.out.println(m.group()+count);
        }
        p = Pattern.compile("[\\w&&[^\\d]]+");
        m = p.matcher(str);
        while(m.find()){
            count++;
           
        }
        p = Pattern.compile("\\d+\\.\\d+%*");
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("([^\\.\\d])\\d+([^%\\.\\d])");//([^]{0,})
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("^\\d+([^%\\.\\d])");//([^]{0,})
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("([^%\\.\\d])\\d+$");//([^]{0,})
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("^\\d+$");//([^]{0,})
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("(?:[^\\.])\\d+%");
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("(?:[\\W])_(?:[\\W])");
        m = p.matcher(str);
        while(m.find()){
            count++; 
            
        }
        p = Pattern.compile("(?:[^\\d]{1})\\.(?:[^\\d]{1})");
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        p = Pattern.compile("(?:[^\\d])%");
        m = p.matcher(str);
        while(m.find()){
            count++;
            
        }
        return count;
    }

}
