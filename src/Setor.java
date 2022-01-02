import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Inheritance
//Kelas Anak (Setor), turunan dari kelas induk (Nasabah)
public class Setor extends Nasabah{

	//Deklarasi variabel
	String noTransaksi;
	int setor;
	int jumlah;
	int bunga;
	int saldoAkhir;
	String waktu;
	//membuat scanner baru
	Scanner input = new Scanner(System.in);
	
	public void Transaksi() {
		System.out.print("Nomor Transaksi\t: ");
		// menggunakan scanner dan menyimpan apa yang diketik di variabel noTransaksi
		this.noTransaksi = input.next();
		
		//Tanggal dan waktu
		LocalDate wkt = LocalDate.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.waktu =  wkt.format(fmt);	
	}
	
	//method setor
	public void setor() {
		
		System.out.print("Jumlah Setoran\t: Rp ");
		// menggunakan scanner dan menyimpan apa yang diketik di variabel setor
		this.setor = input.nextInt();
		
		//operasi matematika untuk enjumlahkan saldoAwal dan setoran
		this.jumlah = this.saldoAwal + setor;
		
		//percabangan if untuk menentukan besarnya bunga yang diterima nasabah
		Double pengali=0.0;
		if (this.jumlah == 0) {
			System.out.println("Jumlah Saldo Anda 0 !!");
		}else if (this.jumlah > 0 && this.jumlah < 500000) {
			// this.bunga = (1/100)*this.jumlah;
			pengali = 0.01;
		}else if (this.jumlah >=500000 && this.jumlah < 1000000) {
			pengali = 0.02;
		}else if (this.jumlah >=1000000 && this.jumlah <5000000) {
			pengali = 0.03;
		}else if (this.jumlah >=5000000 && this.jumlah <10000000) {
			pengali = 0.04;
		}else if (this.jumlah >=10000000) {
			pengali = 0.05;
		}else {
			System.out.println("Masukkan Jumlah Saldo dan Setoran yang Benar!!");
		}
		this.bunga = (int) (this.jumlah * pengali);

		//proses matematika untuk menjumlahkan tabungan sebelumnya dan bunga yang diterima
		this.saldoAkhir = this.jumlah + this.bunga;
	}
	
}