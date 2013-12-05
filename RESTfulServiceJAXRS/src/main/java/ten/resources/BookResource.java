package ten.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/resources/{bookName}")
public class BookResource {

	@GET
	@Produces("text/plain")
	public String readBook(@PathParam("bookName") String bookName) {
		return "Reading book... " + bookName + "...:\n\n" + "In a country far far away, once upon a time";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/plain")
	public String createBook(@FormParam("title") String title, @FormParam("body") String body) throws IOException {
		File bookFile = new File("/" + title + ".txt");
		bookFile.createNewFile();
		FileWriter writer = new FileWriter(bookFile);
		writer.write(body);
		writer.flush();
		writer.close();
		return "recorded this new book in the library..." + title + body;
	}
	
}
