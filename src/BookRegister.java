import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BookRegister {
    // Holding all the books
    private ArrayList<Book> books;

    // The file name to use both when reading and writing to file.
    private String booksFileName;

    /**
     *  Constructor!
     *  Creating the ArrayList needed and reading books from file.
     *  Then the object is ready to be used.
     */
    public BookRegister() throws IOException {
        books = new ArrayList<>();
        booksFileName = "bok.txt";
        readBooksFromFile(booksFileName);
    }


    public void writeBooksToFile() throws IOException {
        FileWriter fileWriter = new FileWriter(booksFileName);
        for (int i = 0; i<books.size();i++) {
            fileWriter.write(books.get(i).getIsbn()+"\n");
            fileWriter.write(books.get(i).getTitle()+"\n");
            fileWriter.write(books.get(i).getAuthor()+"\n");
            fileWriter.write(books.get(i).getNumberOfPages()+"\n");
            fileWriter.write(books.get(i).getGenre().name()+"\n");
            fileWriter.write("---");
            if(i!=books.size()-1){ // to avoid CR in end of file
                fileWriter.write("\n");
            }
        }
        fileWriter.close();
    }

    public void printBookByISBN(String isbn) {
        boolean found = false;
        for (Book b :
                books) {
            if(b.getIsbn().equalsIgnoreCase(isbn)){
                b.printState();
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("No book found with isbn="+isbn);
        }
    }
    public void printBooksByAuthor(String author) {
        System.out.println("All books by author:"+author);
        for (Book b :
                books) {
            if(b.getAuthor().equalsIgnoreCase(author)){
                b.printState();
            }
        }
    }

    public void printBooksByGenre(Genre genre) {
        System.out.println("All books in genre:"+genre.name());
        for (Book b :
                books) {
            if(b.getGenre() == genre){
                b.printState();
            }
        }
    }

    /**
     *  Holding books in an ArrayList is not optimal (but is what we currently know).
     *  Editing a book is handled by removing the old version and adding the new.
     *  Validating that there is an ISBN match in old and new version.
     *
     *  If we had our books in a Map, things would be easier.
     */
    public void editBook(Book oldVersion, Book newVersion) {
        if(!oldVersion.getIsbn().equalsIgnoreCase(newVersion.getIsbn())){
            System.out.println("Unable to update book. ISBN mismatch.");
            return;
        }
        books.remove(oldVersion);
        books.add(newVersion);
        System.out.println("Book updated");
    }

    private void readBooksFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        while(input.hasNext()){
            String isbn = input.nextLine();
            String title = input.nextLine();
            String author = input.nextLine();
            int pages = input.nextInt();
            input.nextLine(); // reading CR
            Genre genre = Genre.valueOf(input.nextLine());
            input.nextLine(); // reading ---
            books.add(new Book(isbn, title, author, pages, genre));
        }

    }

    public void printAllBooks(){
        for (Book b :
                books) {
            b.printState();
        }
    }


    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBook(String isbn) {
        for (Book b :
                books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return b;
            }
        }
        return null;
    }

    public void removeBook(String isbn) {
        Book b = getBook(isbn);

        if(b == null){
            System.out.println("Unable to remove book. Book not found.");
            return;
        }
        books.remove(b);
        System.out.println("Book removed");
    }
}
