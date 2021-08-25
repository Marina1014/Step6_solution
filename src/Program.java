import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is responsible for handling user input on user choices.
 * When books are to be updated, retrieved etc. it will ask for help from the register.
 */
public class Program {
    // Register to hold all books and offers functionality to get/a$$/edit etc.
    BookRegister register;
    // To get input from user
    private Scanner input;

    /**
     * The object needs help from a book register, so why not create it in the constructor.
     * The object will also need help to read user input. Scanner to the rescue!
     * Without these two helpers, the object can not do its job.
     */
    public Program() throws IOException {
        register = new BookRegister();
        input = new Scanner(System.in);

    }


    /**
     *  Loop to handle user interaction until user decides to quit.
     */
    public void handleUserInteraction() throws IOException {
        int choice = 0;
        while(choice!=8){
            printMainMenu();
            choice = getIntegerFromUser(1, 8, "Make your choice:");
            switch (choice){
                case 1 -> printAllBooks();
                case 2 -> addBook();
                case 3 -> editBook();
                case 4 -> printBooksByGenre();
                case 5 -> printBooksByAuthor();
                case 6 -> printBookByISBN();
                case 7 -> removeBook();
                case 8 -> endUserInteraction();
            }
        }
    }

    private void endUserInteraction() throws IOException {
        System.out.println("Thank you for using this amazing book register. Bye!");
       register.writeBooksToFile();

    }


    private void removeBook() {
        System.out.println("Enter ISBN:");
        String isbn = input.nextLine();
        register.removeBook(isbn);
    }

    private void printBookByISBN() {
        System.out.println("Enter ISBN:");
        String isbn = input.nextLine();
        register.printBookByISBN(isbn);
    }

    private void printBooksByAuthor() {
        System.out.println("Enter author:");
        String author = input.nextLine();
        register.printBooksByAuthor(author);
    }

    private void printBooksByGenre() {
        Genre genre = getGenreFromUser("Enter Genre (CRIME, ACTION, FANTASY, CLASSIC or OTHER):");
        register.printBooksByGenre(genre);
    }

    private void editBook() {
        System.out.println("Enter ISBN:");
        String isbn = input.nextLine();
        Book oldVersion = register.getBook(isbn);
        if(oldVersion == null){
            System.out.println("Unable to update book. Book not found.");
            return;
        }
        System.out.println("Enter new title (current=" + oldVersion.getTitle()+"):");
        String title = input.nextLine();
        System.out.println("Enter new author (current=" + oldVersion.getAuthor()+"):");
        String author = input.nextLine();
        int pages = getIntegerFromUser(1, 10000,
                "Enter number of pages (current=" + oldVersion.getNumberOfPages()+"):");
        Genre genre = getGenreFromUser("Enter Genre (CRIME, ACTION, FANTASY, CLASSIC or OTHER): " +
                "Current="+oldVersion.getGenre().name());
        Book newVersion = new Book(isbn, title, author, pages, genre);
        register.editBook(oldVersion, newVersion);
    }

    private void printAllBooks() {
        register.printAllBooks();
    }

    private void addBook() {
        System.out.println("Enter ISBN:");
        String isbn = input.nextLine();
        System.out.println("Enter title:");
        String title = input.nextLine();
        System.out.println("Enter author:");
        String author = input.nextLine();
        int pages = getIntegerFromUser(1, 10000, "Enter number of pages:");
        Genre genre = getGenreFromUser("Enter Genre (CRIME, ACTION, FANTASY, CLASSIC or OTHER):");
        register.addBook(new Book(isbn, title, author, pages, genre));
    }

    /**
     *  Making sure a valid genre is provided by user.
     *  Looping until the valid genre is provided.
     *  As the method will be called in various situations
     *  (adding, editing, retrieving books) a prompt
     *  is provided as parameter.
     */
    private Genre getGenreFromUser(String prompt) {
       System.out.println(prompt);
        Genre genre = null;
        // Loop until a valid genre is set
        while(genre == null){
            try {
                genre = Genre.valueOf(input.nextLine());
            } catch (IllegalArgumentException iae){
                System.out.println("That is not a valid genre. Try again (CRIME, ACTION, FANTASY, CLASSIC or OTHER)");
            }
        }
        return genre;
    }

    /**
     *  Making sure a valid int is provided by user.
     *  Looping until the valid int is provided.
     *  As the method will be called in multiple situations,
     *  we need parameters to decide the valid range and prompt to the user.
     */
    private int getIntegerFromUser(int minValue, int maxValue, String prompt) {
        String errorMessage = "That is not a number between " + minValue + " and " + maxValue;
        int choice = minValue -1;
        while(choice < minValue || choice > maxValue){
            System.out.println(prompt);
            try {
                choice = input.nextInt();
            } catch(InputMismatchException inputMismatchException){
                System.out.println(errorMessage);
            }
            if(choice<minValue || choice>maxValue){
                System.out.println(errorMessage);
            }
        }
        input.nextLine(); // reading CR
        return choice;
    }

    private void printMainMenu() {
        System.out.println("1: All books");
        System.out.println("2: Add book");
        System.out.println("3: Edit book");
        System.out.println("4: Find book by genre");
        System.out.println("5: Find book by author");
        System.out.println("6: Find book by ISBN");
        System.out.println("7: Remove book");
        System.out.println("8: Quit");
    }
}
