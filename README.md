# InformaticaMobilaProiect

Proiect Android pentru disciplina Informatica Mobila.

## Predare proiecte

Predarea se face la datele anuntate. Dacă nu puteți veni la una din teme, o puteți trimite pe Teams-Chat și o susțineți următoarea dată. Temele din urmă nu se mai verifică.

## Task-uri

1. **Clasă obiecte cu 5 date membre de tipuri diferite** - inițializare cel putin 5 obiecte, afișarea tuturor obiectelor initializate in interfata aplicatiei - 0.5 pct la nota finala
2. **O lista cu 6 obiecte** (pot fi din clasa creata la tema anterioara), se vor citi in interfata 2 conditii si se vor afisa doar obiectele din lista care corespund celor doua conditii. (0.5 pct)
3. **Conectarea la un URL** (la alegere) si afisarea, in aplicatie, a unei informatii (sub forma a doua tag-uri/liste/campuri) disponibile online preluate dintr-un RSS feed sau dintr-un link personalizat (1 pct). Deschiderea unui browser pentru a vizualiza adresa (0.5 pct).
4. **Internationalizare**. Afisarea a cel putin 2 elemente grafice text si unul de tip imagine, in 4 limbi (0.5pct).
5. **Se vor crea 2 componente grafice** pentru preluarea a doua tipuri de date diferite, care vor fi scrise in fisier. Creare, scriere si citire intr-un fisier intern (0.5pct)/extern (0.5pct). Adaugare si stergere date (dupa ce au fost verificate folosind o conditie cu continutul uneia din cele doua componente grafice - ex.un id, un sir de caractere/cuvant) in fisier (0.5 pct).
6. **Se va deschide o noua fereastra**, in care se vor transmite si afisa doua valori preluate din prima fereastra (fie prin componente grafice cu doua tipuri de date, fie preluate din fisier sau baza de date). (1 pct)
7. **Se va folosi un fragment** pentru trimiterea de informatii intre ferestre diferite. Acelasi fragment va fi folosit in doua ferestre diferite. (1 pct)
8. **Se va crea un fisier intern/extern de tip XML/JSON** in care se vor face operatii de scriere, citire, adaugare a unui element/item, stergere completa pentru o structura de date care sa contina o lista, o alta structura si cu cel putin un element de tip numeric/boolean. (1 pct).
9. **Se va crea o baza de date** cu o tabela cu cel putin 3 campuri in care se vor face operatii CRUD (1 pct.). Adaugarea a inca unei tabele legata de prima care sa mai aiba alte 3 campuri si operatii CRUD asociate (1.5pct)

## Structura Proiect

```
app/
├── src/main/
│   ├── java/com/example/informaticamobilaproiect/
│   │   ├── MainActivity.kt (meniu principal)
│   │   ├── Task1Activity.kt
│   │   ├── Task2Activity.kt
│   │   ├── Task3Activity.kt
│   │   ├── Task4Activity.kt
│   │   ├── Task5Activity.kt
│   │   ├── Task6Activity.kt
│   │   ├── Task6SecondActivity.kt
│   │   ├── Task7Activity.kt
│   │   ├── Task7SecondActivity.kt
│   │   ├── Task7Fragment.kt
│   │   ├── Task8Activity.kt
│   │   ├── Task9Activity.kt
│   │   ├── Produs.kt
│   │   ├── data/
│   │   │   ├── AppDatabase.kt
│   │   │   ├── Dao.kt
│   │   │   ├── Repository.kt
│   │   │   └── Task9Models.kt
│   │   ├── fragments/
│   │   └── utils/
│   │       └── SupabaseHelper.kt
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml (meniu)
│   │   │   ├── activity_task1.xml
│   │   │   ├── activity_task2.xml
│   │   │   ├── activity_task3.xml
│   │   │   ├── activity_task4.xml
│   │   │   ├── activity_task5.xml
│   │   │   ├── activity_task6.xml
│   │   │   ├── activity_task6_second.xml
│   │   │   ├── activity_task7.xml
│   │   │   ├── activity_task7_second.xml
│   │   │   ├── activity_task8.xml
│   │   │   ├── activity_task9.xml
│   │   │   ├── fragment_task7.xml
│   │   ├── values/
│   │   ├── values-bg/
│   │   ├── values-de/
│   │   ├── values-en/
│   │   ├── values-fr/
│   │   └── ...
│   └── AndroidManifest.xml
```

## Tehnologii Utilizate

- **Kotlin** - Limbaj de programare
- **Room Database** - Baza de date locală SQLite
- **KSP** - Kotlin Symbol Processing pentru generarea codului Room
- **Coroutines** - Programare asincronă
- **ViewBinding** - Acces la views
- **Material Design 3** - Componente UI

## Implementare Curenta

- [x] **Meniu Principal** (`MainActivity`) - Navigare catre toate task-urile
- [x] **Task 1**: Clasa `Produs` cu 5 date membre (String, Int, Double, Boolean, Char) si 5 obiecte initializate si afisate in `Task1Activity`
- [x] **Task 2**: Lista cu 6 obiecte, citire 2 conditii din interfata (stoc minim + categorie) si afisarea produselor filtrate in `Task2Activity`
- [x] **Task 3**: Conectare la URL (JSON API), afisare informatii in 2 campuri (titlu + corp) si deschidere browser in `Task3Activity`
- [x] **Task 4**: Internationalizare - 2 elemente text + 1 imagine (steag) in 4 limbi (RO, EN, DE, FR) cu schimbare dinamica a limbii in `Task4Activity`
- [x] **Task 5**: Fisier intern SI extern - 2 componente (ID/text + numar), selector tip fisier, afisare cale, operatii de creare, scriere, citire, adaugare (cu verificare duplicat) si stergere (dupa ID) in `Task5Activity`
- [x] **Task 6**: Doua ferestre - transmitere a 2 valori (nume + varsta) prin Intent de la `Task6Activity` la `Task6SecondActivity` si afisarea lor in fereastra noua
- [x] **Task 7**: Fragment comun (`Task7Fragment`) folosit in doua ferestre diferite (`Task7Activity` + `Task7SecondActivity`) pentru transmiterea de informatii intre ele prin intermediul unei interfețe
- [x] **Task 8**: Fișier intern/extern JSON/XML cu operatii CRUD (scriere, citire, adaugare, stergere) pentru o listă de elemente, fiecare conținând: o listă (subelemente), o altă structură (detalii) și un element boolean (activ) în `Task8Activity`
- [x] **Task 9**: Baza de date Room (SQLite) cu 2 tabele relationate (`produse` si `comenzi`) cu operatii CRUD complete (Create, Read, Update, Delete) în `Task9Activity`

## Build si Rulare

### Cerinte
- Java JDK 17+ (recomandat JDK 21 sau 26)
- Android SDK 34
- Gradle 8.10
- Android Gradle Plugin 8.5.2
- Kotlin 1.9.24
- KSP 1.9.24-1.0.20

### Comanda build
```bash
./gradlew assembleDebug
```

APK-ul va fi generat la: `app/build/outputs/apk/debug/app-debug.apk`

## Task 9 - Baza de Date Locala (Room)

Task 9 foloseste **Room Database** (SQLite) pentru stocarea locala a datelor. **Nu necesita nicio configurare** - baza de date se creeaza automat la prima rulare.

### Quick Start
1. Build > Run app
2. Apasa pe **Task 9 - Baza de Date**
3. Baza de date vine pre-popolata cu 7 produse si 7 comenzi (pentru testare)
4. Adauga/modifica/sterge produse si comenzi folosind butoanele

### Structura bazei de date:

**Tabela `produse`** (3 campuri):
- `id` (primary key, auto-increment)
- `nume` (String)
- `pret` (Double)
- `categorie` (String)

**Tabela `comenzi`** (legata de produse, 4 campuri):
- `id` (primary key, auto-increment)
- `produsId` (foreign key -> produse.id)
- `cantitate` (Int)
- `dataComanda` (String)
- `status` (String)

### Operatii CRUD:

**Pentru PRODUSE:**
- **Create**: Completeaza nume, pret, categorie si apasa "Adaugă Produs"
- **Read**: Selecteaza un produs din dropdown pentru a-l vedea/edita
- **Update**: Selecteaza produs, modifica campurile, apasa "Actualizează Produs"
- **Delete**: Selecteaza produs, apasa "Șterge Produs", apoi "Refresh DB" pentru a vedea rezultatele

**Pentru COMENZI:**
- **Create**: Completeaza produs_id, cantitate, status si apasa "Adaugă Comandă"
- **Read**: Selecteaza o comanda din dropdown pentru a o vedea/edita
- **Update**: Selecteaza comanda, modifica campurile, apasa "Actualizează Comandă"
- **Delete**: Selecteaza comanda, apasa "Șterge Comandă", apoi "Refresh DB"

### Caracteristici
- Nu necesita configurare - se creeaza automat
- Datele persista intre rulari
- Buton "Refresh DB" pentru a reincarca datele dupa operatii
- Foloseste Repository Pattern pentru arhitectura curata

### Unde se stocheaza datele?
```
/data/data/com.example.informaticamobilaproiect/databases/task9_database
```

Pentru a inspecta baza de date:
1. Device File Explorer in Android Studio (View > Tool Windows)
2. Navigheaza la calea de mai sus
3. Copy database file pentru inspectie

### Resetare Baza de Date
Pentru a sterge toate datele:
1. Settings > Apps > InformaticaMobilaProiect
2. Storage > Clear Data
3. La urmatoarea rulare, baza de date se va recrea (cu datele initiale)