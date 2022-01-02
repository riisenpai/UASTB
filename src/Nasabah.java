import java.util.Scanner;

//Class Induk yang mengimplementasikan InterfaceNasabah
public class Nasabah implements InterfaceNasabah {
	//deklarasi variabel
	String nama;
	String norek;
	int saldoAwal;
	//membuat scanner baru
	Scanner input = new Scanner(System.in);
	
	//method Nama, implemetasi dari InterfaceNasabah
	@Override
	public String Nama() {
		System.out.print("Nama Nasabah\t: ");
		// menggunakan scanner dan menyimpan apa yang diketik di variabel nama
		this.nama = input.next();
		return nama;
	}
	//method Norek, implemetasi dari InterfaceNasabah
	@Override
	public String Norek() {
		System.out.print("Nomor Rekening\t: ");
		// menggunakan scanner dan menyimpan apa yang diketik di variabel norek
		this.norek = input.next();
		return norek;
	}
	//method SaldoAwal, implemetasi dari InterfaceNasabah
	@Override
	public int SaldoAwal() {
		System.out.print("Saldo Awal\t: Rp ");
		// menggunakan scanner dan menyimpan apa yang diketik di variabel saldoAwal
		this.saldoAwal = input.nextInt();
		return saldoAwal;
	}

}
