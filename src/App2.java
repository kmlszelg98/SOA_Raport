
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import ticket.Ticket;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Kamil on 22.05.2017.
 */

@Path("/mobile")
public class App2 {


    public static final Client client = Client.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String start() {
        return "GENEROWANIE RAPORTOW";
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Terminy terminy) {
        //Database database = new Database();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(terminy.ile);
        Timestamp timestamp = Timestamp.valueOf(now);
        Timestamp start2 = Timestamp.valueOf(start);

        TypeReference reference = new TypeReference<ArrayList<Ticket>>(){};
        ObjectMapper mapper = new ObjectMapper();
        WebResource active = client.resource("http://localhost:8080/Bilet/start/tickets/all");
        ClientResponse responseActive = active.accept("application/json").get(ClientResponse.class);
        String outputActive = responseActive.getEntity(String.class);
        ArrayList<Ticket> ticketActive = new ArrayList<>();
        try {
            ticketActive = mapper.readValue(outputActive,reference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Ticket> list = new ArrayList<>();
        for(Ticket t:ticketActive){
            if(t.getStartTime().before(timestamp) && t.getStartTime().after(start2)) list.add(t);
        }
                //database.main(start2,timestamp);
        System.out.println("TWOJ RAPORT");
        int n=0;
        int u=0;
        if(terminy.getStrefa()==0){
            for(Ticket ticket:list) {
                if(ticket.getRodzajBiletu().equals("Normalny")) n++;
                else u++;
            }
        } else {
            for(Ticket ticket:list){
                if(ticket.getStrefa()==terminy.getStrefa()) {
                    if(ticket.getRodzajBiletu().equals("Normalny")) n++;
                    else u++;
                }
            }
        }
        System.out.println("W ciagu dni: "+terminy.getIle()+" na parkingu bylo kupionych:");
        System.out.println("Normalnych: "+n+" Ulgowych: "+u);


    }

}
