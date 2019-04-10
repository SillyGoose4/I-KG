package cn.nnu.jyjs.knowledgegraph.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * --NO USE--
 * database operate util
 * create by wangj
 * in 3/12/2019
 */
public class DBUnits {
 
	private static String URL="";// URL指向要访问的数据库名
    private static String USER="";
    private static String PASSWORD="";
    private static String DRIVER="";
    private static String ConfigFilePath="src/main/jdbc.config";
    private static String buffer;
    private static Connection connection=null;
    
    /**
     * 静态加载块
     */
    static{

        //1.加载驱动程序
        try {
            File config = new File(ConfigFilePath);
            FileInputStream configString = new FileInputStream(config);
            buffer = getBuffer(configString);
            Incise(buffer);
            Class.forName(DRIVER);   //****************driver驱动程序名
            connection=DriverManager.getConnection(URL, USER, PASSWORD);// 获得链接管道
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    public static Connection getConnection(){
        return connection;
    }


    /**
     * 解析jdbc配置文件 内部方法、禁止访问
     * @param fileInputStream
     * @return
     * @throws IOException
     */
    private static String getBuffer(FileInputStream fileInputStream) throws IOException {//读文档
        String ret="";
        byte[] buf = new byte[1024];
        while(fileInputStream.read(buf)!=-1){
            ret += new String(buf);
        }    
        return ret;
    }

//    public static void store(String str,) {
//    	 String[] dic = str.split(",");//按逗号分开
//    	 System.out.println(""+" is :" + URL )
//    }
 /**
  * 
  * @param dic_name
  * @param dic_describe
  * @param dic_hz
  */
 public static int tdicInsert(String dic_name,String dic_pos,int dic_frequency) {
	 int dic_id = 0;
	 try {		 
		 String sql = "insert into tdic(dic_name,dic_pos,dic_frequency) values('"+dic_name+"','"+dic_pos+"','"+dic_frequency+"')";
		 PreparedStatement p = getConnection().prepareStatement(sql);
  		 p.executeUpdate();//表示执行PreparedStatement 中封装的sql语句
  		 Statement select = getConnection().createStatement();
  		 //ResultSet resultSet = select.executeQuery("select max(dic_id) from tdic");
  		 ResultSet resultSet = select.executeQuery("select max(dic_id) from tdic");
  		 resultSet.last();
  		 if(resultSet.isLast()) {  			 
  		    dic_id=resultSet.getInt(1);
  		 }
  		
	 } catch (SQLException e) {
	   e.printStackTrace();
	 }
	 return dic_id;
}
 	public static int selectBydicName(String dicName) {
 		int res = 0;
 		try {
 			String sql = "select dic_id from tdic where dic_name = '" + dicName + "')";
 			 PreparedStatement p = getConnection().prepareStatement(sql);
 			ResultSet resultSet;
			resultSet = p.executeQuery();
			if(resultSet.first()) {
				res = resultSet.getInt("dic_id");
				int frequency = resultSet.getInt("dic_frequency");
				frequency += 1;
				String s = "update dic_id set dic_frequency = " + frequency + "where dic_name = '" + dicName + "')";
	 			 PreparedStatement p2 = getConnection().prepareStatement(s);
	 			 p.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

 	public static void tdicbelongInsert(int class_id,int dic_id) {
 		try {
		 String sql = "insert into tdicbelong(class_id,dic_id) values('"+class_id+"','"+dic_id+"')";
		 PreparedStatement p = getConnection().prepareStatement(sql);
  		 p.executeUpdate();//表示执行PreparedStatement 中封装的sql语句
	 } catch (SQLException e) {
	   e.printStackTrace();
	 }
}   
    private static void Incise(String buffer){       //对字符串进行赋值操作
        String[] temp = buffer.split(System.lineSeparator());//按行分开
        for(int i = 0; i < temp.length; i++){
            if(temp[i].contains("Url")){
                URL =temp[i].substring(temp[i].indexOf("=")+1, temp[i].length()-1);//那么URL就是从=的下一个位置起到该行最后一个字符为止
                System.out.println("Url"+" is :" + URL );
            }else if(temp[i].contains("Driver")){
                DRIVER =temp[i].substring(temp[i].indexOf("=")+1, temp[i].length()-1);
                System.out.println("Driver"+" is :" + DRIVER );
            }else if (temp[i].contains("User")){
                USER =temp[i].substring(temp[i].indexOf("=")+1, temp[i].length()-1);
                System.out.println("User"+" is :" + USER );
            }else if (temp[i].contains("Password")){
                PASSWORD =temp[i].substring(temp[i].indexOf("=")+1, temp[i].length()-1);
                System.out.println("Password"+" is :" + PASSWORD );
            }
        }

    }

	public static Object[][] tdicSelect( ) {
		ResultSet rs = null;
		int count=0;
		Object[][] info=null;
		 try {		 
			 String sql = "select *from tclass";
			 PreparedStatement p = getConnection().prepareStatement(sql);
	  		 rs= p.executeQuery();//表示执行PreparedStatement 中封装的sql语句
	  		 count=0;
	  		while(rs.next()){
	  			count++;
	  			}
	  		System.out.println(count);
	  		rs = p.executeQuery();
	  		info = new Object[count][2];
	  		count=0;
			while(rs.next()){
				info[count][0] = Integer.valueOf( rs.getInt("class_id"));
				info[count][1] = rs.getString("class_name");
				count++;
			}
		 } catch (SQLException e) {
		   e.printStackTrace();
		 }		
			 return info;// TODO Auto-generated method stub
		
	}
	
	
	

	public static Object[][] tclassSelect( ) {
		ResultSet rs = null;
		int count=0;
		Object[][] info = null;
		 try {		 
			 String sql = "select *from vchinese";
			
			 PreparedStatement p = getConnection().prepareStatement(sql);
	  		 rs= p.executeQuery();//表示执行PreparedStatement 中封装的sql语句
	  		 count=0;
	  		while(rs.next()){
	  			count++;
	  			}
	  		System.out.println(count);
	  		info = new Object[count][7];
	  		rs = p.executeQuery();
	  		count=0;
			while(rs.next()){
				info[count][0] = Integer.valueOf( rs.getInt("class_id"));
				info[count][1] = rs.getString("class_name");
				info[count][2] = rs.getString("dic_id");
				info[count][3] = rs.getString("dic_name");
				info[count][4] = rs.getString("dic_pos");
				info[count][5] = rs.getString("dic_describe");
				info[count][6] = rs.getString("dic_frequency");
				count++;
			}
		 } catch (SQLException e) {
		   e.printStackTrace();
		 }	 
		 return info;// TODO Auto-generated method stub
	}
}
