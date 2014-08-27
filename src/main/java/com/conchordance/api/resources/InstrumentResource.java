package main.java.com.conchordance.api.resources;

import com.conchordance.instrument.Instrument;
import com.conchordance.instrument.InstrumentBank;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/instruments")
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentResource {
    @GET
    public List<Instrument> getInstruments() {
        return InstrumentBank.DEFAULT_BANK.getInstruments();
    }
    
    @OPTIONS
    public void options() {
    }
}
