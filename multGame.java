import java.util.Random;
import java.sql.*;

public class multGame {
	
	static int randomNumber() {

		int randomNum = new Random().nextInt(15);

		return randomNum;
	}
	
	public static void game() {
		
		Terminal.ecrireStringln("Welcome to Multiplication game !");
		
		boolean answer = true;

		int count = 0;
		
		while(answer == true) {
			
			int num1 = randomNumber();
			
			int num2 = randomNumber();
			
			Terminal.ecrireStringln(num1 + " x "+ num2 + " ?");
			int reponse = Terminal.lireInt();
			
			
			if(reponse == (num1*num2)) {
				Terminal.ecrireStringln("Correct !");
				count = count +1;
			} else {
				Terminal.ecrireStringln("FAUUX !");
				answer = false;
			}
			
			Terminal.sautDeLigne();
		}
		
		Terminal.ecrireStringln("Nombre de calcul réussi: " + count);
		
		// Add nickname
		
		String name = "";
		while(name==null || name.length()==0 || name.length()>3) {
			Terminal.ecrireStringln("Entrer vos initiales. (3 caractère)");
			name = Terminal.lireString();
		}
		
		send_score(name,count);
		
		Terminal.sautDeLigne();
		Terminal.ecrireStringln("Voulez vous recommencer ? (y/n)");
		char retry = Terminal.lireChar();
		if(retry == 'y') {
			answer = true;	
		} else {
			Terminal.ecrireStringln("Ok cool ...");
		}
	
	}
	
	
	public static void send_score (String name, int score) {
		try {
			
			// Connection à la base de donnée
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/score_boards"
							+ "?useUnicode=true"
							+ "&useJDBCCompliantTimezoneShift=true"
							+ "&useLegacyDatetimeCode=false"
							+ "&serverTimezone=UTC"
							, "root", "");

			Statement stmt = con.createStatement();
			
			// insert data
			String data = "insert into score"
					+ "(pl_name,pl_score)"
					+ " value "
					+ "(\""+name+"\","+score+")";
			stmt.executeUpdate(data);
			
			// Display score
			Terminal.sautDeLigne();
			Terminal.ecrireStringln("******************");
			Terminal.ecrireStringln("Score Boards");
			Terminal.sautDeLigne();
			 ResultSet rs = stmt.executeQuery("select * from score;");
			 while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			
			con.close();
			
		} catch (Exception e) {
			System.out.println("Erreur envoi du score: " + e);
		}
	}

	public static void main (String[] args) {
		game();
	}
}