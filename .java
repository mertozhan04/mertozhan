import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Temel Sınıf
abstract class BaseEntity {
    private int id;
    private String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void bilgiGoster();
}

// Müşteri Sınıfı
class Musteri extends BaseEntity {
    public Musteri(int id, String name) {
        super(id, name);
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Müşteri ID: " + getId() + ", Adı: " + getName());
    }
}

// Film Sınıfı
class Film {
    private String ad;
    private int sure;
    private String tur;
    private List<Salon> salonlar;

    public Film(String ad, int sure, String tur) {
        this.ad = ad;
        this.sure = sure;
        this.tur = tur;
        this.salonlar = new ArrayList<>();
    }

    public String getAd() {
        return ad;
    }

    public int getSure() {
        return sure;
    }

    public String getTur() {
        return tur;
    }

    public void bilgiGoster() {
        System.out.println("Film Adı: " + ad + ", Süresi: " + sure + " dakika, Türü: " + tur);
    }

    // Film'in hangi salonlarda gösterildiğini ekle
    public void salonEkle(Salon salon) {
        salonlar.add(salon);
    }

    public List<Salon> getSalonlar() {
        return salonlar;
    }
}

// Salon Sınıfı
class Salon extends BaseEntity {
    private Film film;
    private List<Musteri> musteriler;

    public Salon(int id, String name, Film film) {
        super(id, name);
        this.film = film;
        this.musteriler = new ArrayList<>();
    }

    public Film getFilm() {
        return film;
    }

    public List<Musteri> getMusteriler() {
        return musteriler;
    }

    public void musteriEkle(Musteri musteri) {
        musteriler.add(musteri);
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Salon ID: " + getId() + ", Adı: " + getName());
        System.out.println("Gösterimdeki Film:");
        film.bilgiGoster();
        System.out.println("Kayıtlı Müşteriler:");
        for (Musteri musteri : musteriler) {
            musteri.bilgiGoster();
        }
    }
}

// Sinema Sistemi Sınıfı
class SinemaSistemi {
    private List<Musteri> musteriler;
    private List<Film> filmler;
    private List<Salon> salonlar;

    public SinemaSistemi() {
        this.musteriler = new ArrayList<>();
        this.filmler = new ArrayList<>();
        this.salonlar = new ArrayList<>();
    }

    public void varsayilanFilmVeSalonEkle(Film film, Salon salon) {
        filmler.add(film);
        salonlar.add(salon);
        film.salonEkle(salon);  // Film ile salonu ilişkilendir
    }

    public void yeniFilmVeSalonEkle(Film film, Salon salon) {
        filmler.add(film);
        salonlar.add(salon);
        film.salonEkle(salon);  // Film ile salonu ilişkilendir
        System.out.println("Yeni film eklendi: " + film.getAd());
        System.out.println("Yeni salon eklendi: " + salon.getName() + " (Film: " + film.getAd() + ")");
    }

    public void filmVeSalonlariListele() {
        System.out.println("=== Filmler ve Salonlar ===");
        for (Film film : filmler) {
            film.bilgiGoster();
            System.out.println("Gösterildiği Salonlar:");
            for (Salon salon : film.getSalonlar()) {
                System.out.println("   - Salon: " + salon.getName() + " (Salon ID: " + salon.getId() + ")");
            }
            System.out.println();
        }
    }

    public void salonMusterileriniListele(int salonId) {
        for (Salon salon : salonlar) {
            if (salon.getId() == salonId) {
                salon.bilgiGoster();
                return;
            }
        }
        System.out.println("Salon bulunamadı.");
    }

    public List<Film> getFilmler() {
        return filmler;
    }

    public List<Salon> getSalonlar() {
        return salonlar;
    }
}

// Main Sınıfı
public class Main {
    public static void main(String[] args) {
        SinemaSistemi sistem = new SinemaSistemi();
        Scanner scanner = new Scanner(System.in);

        // Varsayılan Filmler ve Salonlar (Mesaj Gösterilmez)
        sistem.varsayilanFilmVeSalonEkle(new Film("Inception", 148, "Bilim Kurgu"), new Salon(1, "Salon 1", new Film("Inception", 148, "Bilim Kurgu")));
        sistem.varsayilanFilmVeSalonEkle(new Film("Titanic", 195, "Romantik"), new Salon(2, "Salon 2", new Film("Titanic", 195, "Romantik")));
        sistem.varsayilanFilmVeSalonEkle(new Film("The Dark Knight", 152, "Aksiyon"), new Salon(3, "Salon 3", new Film("The Dark Knight", 152, "Aksiyon")));

        while (true) {
            System.out.println("\nMenü:");
            System.out.println("1. Yeni Müşteri Ekle");
            System.out.println("2. Filmleri ve Salonları Listele");
            System.out.println("3. Salon Müşterilerini Listele");
            System.out.println("4. Yeni Film ve Salon Ekle");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminizi yapın: ");
            int secim = scanner.nextInt();
            scanner.nextLine(); // Enter'ı tüketmek için

            switch (secim) {
                case 1: // Yeni Müşteri Ekle
                    System.out.print("Müşteri Adı: ");
                    String musteriAdi = scanner.nextLine();
                    sistem.filmVeSalonlariListele();
                    System.out.print("Salon Seçiniz (ID): ");
                    int salonId = scanner.nextInt() - 1;

                    if (salonId >= 0 && salonId < sistem.getSalonlar().size()) {
                        Musteri yeniMusteri = new Musteri(sistem.getSalonlar().get(salonId).getMusteriler().size() + 1, musteriAdi);
                        sistem.getSalonlar().get(salonId).musteriEkle(yeniMusteri);
                        System.out.println("Müşteri başarıyla eklendi: " + musteriAdi);
                    } else {
                        System.out.println("Geçersiz salon seçimi.");
                    }
                    break;

                case 2: // Filmleri ve Salonları Listele
                    sistem.filmVeSalonlariListele();
                    break;

                case 3: // Salon Müşterilerini Listele
                    System.out.print("Salon ID: ");
                    int id = scanner.nextInt();
                    sistem.salonMusterileriniListele(id);
                    break;

                case 4: // Yeni Film ve Salon Ekle
                    System.out.print("Film Adı: ");
                    String filmAdi = scanner.nextLine();
                    System.out.print("Film Süresi (dakika): ");
                    int filmSuresi = scanner.nextInt();
                    scanner.nextLine(); // Enter'ı tüketmek için
                    System.out.print("Film Türü: ");
                    String filmTuru = scanner.nextLine();

                    System.out.print("Salon Adı: ");
                    String salonAdi = scanner.nextLine();

                    Film yeniFilm = new Film(filmAdi, filmSuresi, filmTuru);
                    Salon yeniSalon = new Salon(sistem.getSalonlar().size() + 1, salonAdi, yeniFilm);
                    sistem.yeniFilmVeSalonEkle(yeniFilm, yeniSalon);
                    break;

                case 5: // Çıkış
                    System.out.println("Programdan çıkılıyor...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        }
    }
}
