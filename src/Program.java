//mengimport library java yang diperlukan
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Program {
	
	// Menyiapkan paramater JDBC untuk koneksi ke database
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/bank?serverTimezone=Asia/Jakarta";
	static final String USER = "root";
	static final String PASS = "";

	// Menyiapkan objek yang diperlukan untuk mengelola database
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	static BufferedReader input = new BufferedReader(inputStreamReader);

	public static void main(String[] args) {
		Header h = new Header("\t\t\t\t\t\t\tBank PBO","\t\t\t\t\t\tProses Setoran Tabungan");
		System.out.println(h.bank);
		System.out.println(h.judul);
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		
		// Melakukan koneksi ke database
		try {	
	            // register driver yang akan dipakai
	            Class.forName(JDBC_DRIVER);
				// buat koneksi ke database
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);
				// buat objek statement
	            stmt = conn.createStatement();

	            //perulangan while untuk menampilkan menu
				while (!conn.isClosed()) {
	                ShowMenu();
	            }

	            //menutup statement dan koneksi
				stmt.close();
	            conn.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	//method untuk menampilkan menu
	private static void ShowMenu() {
		System.out.println("\n========= MENU UTAMA =========");
        System.out.println("0. Exit");
		System.out.println("1. Lihat Data");
        System.out.println("2. Input Data");
        System.out.println("3. Ubah Data");
        System.out.println("4. Delete Data");
		System.out.println("5. Cari Data");
		System.out.println("6. Lihat Jadwal");
        System.out.println("");
        System.out.print("PILIHAN > ");

        try {
            int pilihan = Integer.parseInt(input.readLine());
            
            //percabangan switch untuk menu
            switch (pilihan) {
                case 0:
                    System.out.println("\t\t\t\t\t\t\tTerima Kasih! Sampai Jumpa Kembali :)");
					Footer f = new Footer("\t\t\t\t\t\t\tCopyright © 2021 - BANK PBO");
					System.out.println(f.credit);
					System.exit(0);
                    break;
                case 1:
                    LihatData();
                    break;
                case 2:
                    InputData();
                    break;
                case 3:
                    EditData();
                    break;
                case 4:
                	DeleteData();
                	break;
				case 5:
                	CariData();
                	break;
				case 6:
                	LihatJadwal();
                	break;
                default:
                    System.out.println("Pilihan salah!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	//Method untuk melihat data pada database 
	private static void LihatData() {
		 // query untuk mengambil data dari database
		String sql = "SELECT * FROM setor";

        try {
            // eksekusi query dan simpan hasilnya di obj ResultSet
			rs = stmt.executeQuery(sql);
            
            System.out.println("\t\t\t\t\t\t Data Transaksi ");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            System.out.println("|No. Transaksi\t|Tanggal\t|Nama Nasabah\t|No. Rekening\t|Saldo Awal\t|Setoran\t|Bunga\t|Saldo Akhir\t|");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

            //perulangan while untuk menampilkan data hasil query
            while (rs.next()) {
                String no = rs.getString("no_transaksi");
                String tgl = rs.getString("tgl");
                String nama = rs.getString("nama");
                int norek = rs.getInt("norek");
                int saldoAwal = rs.getInt("saldoawal");
                int setor = rs.getInt("setor");
                int bunga = rs.getInt("bunga");
                int saldoAkhir = rs.getInt("saldoakhir");
                
                System.out.println(String.format("|%s\t\t|%s\t|%s\t\t|%d\t\t|%d\t\t|%d\t\t|%d\t|%d\t\t|", no,tgl,nama,norek,saldoAwal,setor,bunga,saldoAkhir));
            }          
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
			Footer f = new Footer("\t\t\t\t\tCopyright © 2021 - BANK PBO");
			System.out.println(f.credit);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	//method untuk menginputkan data ke database
	private static void InputData() {
		try {
			System.out.println("--- Tambah Data ---");
			
			//membuat objek baru dan menjalankan method
			Setor tbg = new Setor();
			tbg.Transaksi();
			tbg.Nama();
			tbg.Norek();
			tbg.SaldoAwal();
			tbg.setor();
			
			//inisialisasi variabel
			String noTransaksi=tbg.noTransaksi;
			String waktu=tbg.waktu;
			String nama=tbg.nama.toUpperCase(); //String
			String norek=tbg.norek;
			int saldoAwal=tbg.saldoAwal;
			int setor=tbg.setor;
			int bunga=tbg.bunga;
			int saldoAkhir=tbg.saldoAkhir;
			
			//query sql untuk memasukkan data ke database
			String sql = "INSERT INTO setor (no_transaksi,tgl,nama,norek,saldoawal,setor,bunga,saldoakhir) VALUES ('%s','%s','%s','%s','%d','%d','%d','%d')";
			sql = String.format(sql,noTransaksi,waktu,nama,norek,saldoAwal,setor,bunga,saldoAkhir);
			//mengeksekusi query
			stmt.execute(sql);
			
			//Method DateTime untuk menampilkan tanggal dan waktu sesuai format
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter frmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm 'WIB'");
			String tm =  time.format(frmt);
			//Manipulation file
    		File data= new File("Tabungan.txt"); //membuat objek kelas file
    		//membuat objek PrintWriter baru, kemudian menambahkan data dibawah
			PrintWriter pr= new PrintWriter (new FileWriter (data, false));
    		pr.println("	    Bank PBO");
    		pr.println("	  Bukti Setoran");
    		pr.println("	"+tm);
    		pr.println("---------------------------------");	
    		pr.println("Nomor Transaksi	: "+noTransaksi.trim());
    		pr.println("Nama Nasabah	: "+nama.trim());
    		pr.println("Nomor Rekening  : "+norek);
    		pr.println("Saldo Awal		: "+saldoAwal);
    		pr.println("Jumlah Setoran	: "+setor);
    		pr.println("Bunga			: "+bunga);
    		pr.println("Saldo Akhhir	: "+saldoAkhir);
    		pr.println("\nTerima Kasih!!");
    		pr.close();
    		System.out.println("Data Berhasil Ditambahkan!");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Terjadi kesalahan input data");
		}
	}

	//Method untuk mengedit data pada database
	private static void EditData () throws SQLException{
		try {
			System.out.println("--- Ubah Data ---");
			//membuat objek baru dan menjalankan method
			Setor tbg = new Setor();
			tbg.Transaksi();
			tbg.Nama();
			tbg.Norek();
			tbg.SaldoAwal();
			tbg.setor();
			//inisialisasi variabel
			String noTransaksi=tbg.noTransaksi;
			String waktu=tbg.waktu;
			String nama=tbg.nama.toUpperCase();//string
			String norek=tbg.norek;
			int saldoAwal=tbg.saldoAwal;
			int setor=tbg.setor;
			int bunga=tbg.bunga;
			int saldoAkhir=tbg.saldoAkhir;
			
			//query sql untuk mengupdate data
			String sql = "UPDATE setor SET tgl='%s',nama='%s',norek='%s',saldoawal='%d',setor='%d', bunga='%d', saldoakhir='%d' WHERE no_transaksi ='%s'";
			sql = String.format(sql,waktu,nama,norek,saldoAwal,setor,bunga,saldoAkhir,noTransaksi);
			//mengeksekusi query
			stmt.execute(sql);
			
			//Method date untuk menampilkan tanggal dan waktu sesuai format
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter frmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm 'WIB'");
			String tm =  time.format(frmt);
			//Manipulation file
    		File data= new File("Tabungan.txt"); //membuat objek kelas file
    		//membuat objek PrintWriter baru, kemudian menambahkan data dibawah ke file
    		PrintWriter pr= new PrintWriter (new FileWriter (data, false));
    		pr.println("	      Bank PBO");
    		pr.println("	  Bukti Ubah Setoran");
    		pr.println("	 "+tm);
    		pr.println("---------------------------------");	
    		pr.println("Nomor Transaksi	: "+noTransaksi.trim());
    		pr.println("Nama Nasabah	: "+nama.trim());
    		pr.println("Nomor Rekening  : "+norek);
    		pr.println("Saldo Awal		: "+saldoAwal);
    		pr.println("Jumlah Setorang	: "+setor);
    		pr.println("Bunga			: "+bunga);
    		pr.println("Saldo Akhhir	: "+saldoAkhir);
    		pr.println("\nTerima Kasih!!");
    		pr.close();
			if(stmt.executeUpdate(sql) > 0){
	            System.out.println("Berhasil mengedit data Transaksi (No. Transaksi "+noTransaksi+")");
	        }
		}
		catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//method untuk mendelete data pada database
	private static void DeleteData() {
		try{
			System.out.println("--- Delete Data ---");
    		System.out.print("Nomor Transaksi yang akan dihapus: ");
    		String no = input.readLine();
    		
    		//query sql untuk delete data
			String sql = String.format("DELETE FROM setor WHERE no_transaksi = '%s'", no);
    		//mengeksekusi query
			stmt.execute(sql);

			if(stmt.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data Transaksi (No. Transaksi "+no+")");
	        }
	    }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data transaksi");
	    }
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	private static void CariData () throws SQLException, IOException {
		LihatData();
		System.out.println("--- Cari Data ---");
		System.out.print("Masukkan Nama Nasabah : ");
        
		String keyword = input.readLine();
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM setor WHERE nama LIKE '%"+keyword+"%'";
        ResultSet rs = statement.executeQuery(sql); 
        
		System.out.println("\t\t\t\t\t\t Data Hasil Pencarian ");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|No. Transaksi\t|Tanggal\t|Nama Nasabah\t|No. Rekening\t|Saldo Awal\t|Setoran\t|Bunga\t|Saldo Akhir\t|");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        while(rs.next()){
        	String no = rs.getString("no_transaksi");
            String tgl = rs.getString("tgl");
            String nama = rs.getString("nama");
            int norek = rs.getInt("norek");
            int saldoAwal = rs.getInt("saldoawal");
            int setor = rs.getInt("setor");
            int bunga = rs.getInt("bunga");
            int saldoAkhir = rs.getInt("saldoakhir");
			System.out.print("\n");
            System.out.println(String.format("|%s\t\t|%s\t|%s\t\t|%d\t\t|%d\t\t|%d\t\t|%d\t|%d\t\t|", no,tgl,nama,norek,saldoAwal,setor,bunga,saldoAkhir));
        }
	}

	//method untuk melihat jadwal menggunakan ArrayList
	private static void LihatJadwal() {
			System.out.println("--- Jadwal Bank ---");
			// membuat sebuah arraylist 
			ArrayList<Jadwal> jd = new ArrayList<Jadwal>();
  
			// membuat objects untuk class Jadwal
			Jadwal j1 = new Jadwal("Senin  : 08.00 - 16.00");
			Jadwal j2 = new Jadwal("Selasa : 08.00 - 15.30");
			Jadwal j3 = new Jadwal("Rabu   : 08.30 - 16.00");
			Jadwal j4 = new Jadwal("Kamis  : 08.30 - 16.00");
			Jadwal j5 = new Jadwal("Juma't : 08.00 - 14.00");
	  
			// menambahkan objek ke arraylist
			jd.add(j1);
			jd.add(j2);
			jd.add(j3);
			jd.add(j4);
			jd.add(j5);
	  
			// mencetak collection ArrayList
			for (Jadwal i : jd)
				System.out.println(i);
	}
}
