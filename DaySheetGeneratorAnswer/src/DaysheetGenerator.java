import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class DaysheetGenerator {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		//Creates a string with the file name
		String fileName = "resources/daysheet.yml";
		
		//Creates a new yaml daysheet 
		Yaml daysheet = new Yaml();
			
		// new instance of reader for YAML file
		Reader reader = null;
		
		try {
			// pass yaml file into the reader to create a new instance of the YAML file for output
			reader = new FileReader(fileName);

			// The YAML file is based as a key value pair.  For ordering, a Linked Hash Map is a map type that is adds to it as
			// the values come in as opposed to a Hash Map which sorts it all over the place.  In this case, we will use a Linked Hash Map
			// to store the values appropriately.
			
			// The LHM is casted as a key value pair due to the YAML file being set as a key value pair.  In this case, the key is
			// "appointments" while the value is everything in the appointments, aka String and List<Appointment>
			Map<String, List<Map<String,Object>>> lhm = new LinkedHashMap<>();
			
			// casted a as Map during load, saved to variable lhm
			lhm = (Map<String, List<Map<String,Object>>>)daysheet.load(reader);
			
			// get the value based on the key, "appointments"
			List<Map<String,Object>> mapOfAppointments = (List<Map<String,Object>>)lhm.get("appointments");

			Appointments appointments = convertMapOfAppointments(mapOfAppointments);
			
			Collections.sort(appointments.getAppointments());  
						
			// sort the date within the value in the key value pair
			//System.out.println(list.sort);
			
			// print list
			System.out.println(appointments.getAppointments());
			
		}
		// if file cannot be found, print exception
		catch (FileNotFoundException e) {
			System.out.println("Could not find yaml file " + e);
			
			e.printStackTrace();
		}
		finally 
		{
			// close the reader after trying to read the file as long as the file exists
			if (null != reader) {
				try {
					reader.close();
				} catch(final IOException ioe) {
					System.err.println("Recieved exception when trying to close reader" + ioe);
				}
			}
		}
		
	}

	private static Appointments convertMapOfAppointments(List<Map<String, Object>> mapOfAppointments) {
		
		Appointments appointments = new Appointments();
		appointments.setAppointments(new ArrayList<Appointment>());
		
		for (Map<String, Object> map : mapOfAppointments) {
			
			Appointment appointment = new Appointment();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			appointment.setDate(formatter.format(map.get("date")));
			appointment.setPatient((String)map.get("patient"));
			appointment.setReason((String)map.get("reason"));
			
			Integer time = (Integer)map.get("time");
			int hours = time / 60; 
		    int min = time % 60;
			
			appointment.setTime(hours + ":" + min);
			
			appointments.getAppointments().add(appointment);
			
		}
		
		return appointments;
	}
}
