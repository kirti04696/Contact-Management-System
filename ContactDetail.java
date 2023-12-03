import java.util.*;
import java.sql.*;

class ContactDetail{
	static Scanner sc=new Scanner(System.in);
	static ArrayList<Contact> cData = null;
	static Connection con=null;
	public static void main(String args[]) throws Exception{
		
		cData=new ArrayList();
		boolean flag = true;

		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/<DATABASE NAME>","<DB USERNAME>","<DB PASSWORD>");  
			 
			}catch(Exception e){ System.out.println(e);}  







		while(flag){
			System.out.println("enter the option");
			System.out.println("1.Add Contact");
			System.out.println("2.Delete Contact");
			System.out.println("3.Show Contact Details");
			System.out.println("4.Search");
			System.out.println("5.Update");
			System.out.println("6.Exit");

			String ch=sc.nextLine();
			//String cno=sc.nrxtLine();
			switch(ch)
			{
				case "1":
					addContact();
					break;
				case "2":
					deleteContact();
					break;
				case "3":
					showContact();
					break;
				case "6":
					flag = false;
					break;
				case "4":
					search();
					break;
				case "5":
					updateContact();
					break;
				default:
					System.out.println("enter valid option");
			}
		}
		con.close();
	}

	static void search(){
		System.out.print("Search input : ");
		String cname =sc.nextLine();
		try{
			Statement stmt=con.createStatement(); 
			String qry =  "select * from contact_detail where name like '%"+cname+"%' or contact_no like '%"+cname+"%'";
			ResultSet rs=stmt.executeQuery(qry);  
			while(rs.next()){
				System.out.println(
					"Name: " + rs.getString(1)+"\n"+
					"Number: "+ rs.getString(2)
				); 
			}
		}catch(Exception e){
			System.out.println("Something went wrong.");
		}
		
	}
	
	static void addContact(){
		
		System.out.print("Enter the Name : ");
		String cname =sc.nextLine();
		System.out.println();
		System.out.print("Enter Contact Number : ");
		String cno =sc.nextLine();
		try{
			Statement stmt=con.createStatement(); 
			String qry =  "insert into contact_detail (name,contact_no) values('"+cname+"','"+cno+"');";
			int rs=stmt.executeUpdate(qry);  
			System.out.println(rs+" contact added.");
		}catch(Exception e){

			System.out.println(e);
		}

	}
	static void deleteContact(){
		System.out.print("Enter the Name : ");
		String cname =sc.nextLine();
		try{
			Statement stmt=con.createStatement(); 
			String qry =  "delete from contact_detail where name ='"+cname+"'";
			int rs=stmt.executeUpdate(qry);  
			 System.out.println(rs+ " Contact Deleted");
			
		}catch(Exception e){
			System.out.println("Something went wrong.");
		}
		
	}
	static void showContact(){
		List<Contact> result = getContacts();
		if(result.size()>0){
			for(int i=0;i<result.size();i++){
				System.out.println(result.get(i));
			}
		}else{
			System.out.println("Contact is empty!");
		}

		// try{
		// 	Statement stmt=con.createStatement(); 
		// 	String qry =  "select * from contact_detail;";
		// 	ResultSet rs=stmt.executeQuery(qry);  
		// 	while(rs.next()){
		// 		System.out.println(
		// 			"\tName: " + rs.getString(1)+"\n"+
		// 			"\tNumber: "+ rs.getString(2)+"\n"
		// 		); 
		// 	}
		// }catch(Exception e){
		// 	System.out.println("Something went wrong.");
		// }
	}

	static List<Contact> getContacts(){
		List<Contact> result = new ArrayList<Contact>();
		try{
			
			Statement stmt=con.createStatement(); 
			String qry =  "select * from contact_detail;";
			ResultSet rs=stmt.executeQuery(qry);  
			while(rs.next()){
				Contact c = new Contact(Integer.parseInt(rs.getString(1)),rs.getString(2), rs.getString(3));
				result.add(c);
			}
		}catch(Exception e){
			System.out.println("Something went wrong."+e);
		}
		return result;
	}


	public static void updateContact(){
		showContact();
		System.out.println("Enter the contact id to update:");
		int id =sc.nextInt();
		System.out.println("Enter 1 to update name");
		System.out.println("Enter 2 to update no");
		int ch =sc.nextInt();

		if(ch == 1){
			System.out.println("Enter name");
			String cname = sc.nextLine();
			cname = sc.nextLine();
			try{
				Statement stmt=con.createStatement(); 
				String qry =  "update contact_detail set name = '"+cname +"' where id ="+ id +";";
				int rs=stmt.executeUpdate(qry);  
				System.out.println(rs+" contact updated.");
			}catch(Exception e){

				System.out.println(e);
			}
		}
		else if(ch==2){
			System.out.println("Enter No.");
			String cno = sc.nextLine();
			cno = sc.nextLine();
			try{
				Statement stmt=con.createStatement(); 
				String qry =  "update contact_detail set contact_no = '"+cno +"' where id ="+ id +";";
				int rs=stmt.executeUpdate(qry);  
				System.out.println(rs+" contact updated.");
			}catch(Exception e){

				System.out.println(e);
			}
		}
		else{
			System.out.println("Not a valid option");
		}

	}
	
}

class Contact{
	private int id;
	private String name;
	private String contactNo;
	Contact(int id, String n,String c){
		this.id = id;
		this.name=n;
		this.contactNo=c;
	}
	public String toString(){
		return "Contact Id: "+this.id+ "\n" + "Name: " + this.name +"\n" + "Number: "+this.contactNo+"\n";
	}
	public String getName(){
		return this.name;
	}
	public String getContactNo(){
		return this.contactNo;
	}
}