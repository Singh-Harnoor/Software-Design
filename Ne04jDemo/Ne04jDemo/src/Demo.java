
public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String author = "J.K. Rowling";
		String title = "Harry Potter and the prisoner of Azkaban";
		Neo4jBooks nb = new Neo4jBooks();
		nb.insertAuthor(author);
		nb.insertTitle(title);
		nb.insertBook(author, title);
		nb.printBook(author, title);
		nb.close();
	}

}
