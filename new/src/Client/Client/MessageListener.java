//Yizhou Wang
//669026
//DS project1
package Client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MessageListener extends Thread {
	//private BufferedWriter writer;
    private BufferedReader reader;

    public MessageListener(BufferedReader reader) {
        this.reader = reader;
      //  this.writer = writer;
    }

        @Override
        public void run() {

        try {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.replaceAll("#","\n");
                System.out.println(line);
                String command = parseJsonKey(line);
                operation(command);
                String value = parseJsonValue(line, command);
                
                parseJsonArrayValue(value);
            }

        } catch (SocketException e) {
            //msg.setText("Socket closed! Please restart it later");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
       //operation according to server command
    public void operation(String command) {
	switch (command) {
	case "openGameUI":
	    System.out.println("Open UI");
	    break;
	default:
	    break;
	}
    }
        
    //interpret value
    public String parseJsonValue(String json, String key) {
    		String message = json.replaceAll("\n", "");
    		JSONObject jsonObject = JSONObject.fromObject(message); 
    		Map object = (Map) jsonObject;
    		return (String)object.get(key);
    }
        //interpret key
    	public String parseJsonKey(String jsonStr) {
    		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
    		Map object = (Map) jsonObject;
    		if(object.containsKey("update_userList"))
    			return "update_userList";
    		if(object.containsKey("openGameUI"))
			return "openGameUI";
    		return null;
    	}
    	
    	public ArrayList<String> parseJsonArrayValue(String json) {
    		JSONArray jsonArray = JSONArray.fromObject(json); 
    		ArrayList<String> userList = new ArrayList<String>();
    		
    		for (int i = 0; i < jsonArray.size(); i++) {
    			userList.add(jsonArray.getString(i));
        }
    		
        ArrayList<String> online = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
        		int j = i+1;
            online.add("Client " + j + " " + userList.get(i));
            System.out.println(online.get(i));
        }
    		return online;
    	}
}

